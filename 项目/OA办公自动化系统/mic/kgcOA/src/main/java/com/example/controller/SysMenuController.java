package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.constant.ResultConstant;
import com.example.common.constant.SystemConstant;
import com.example.common.exception.HttpException;
import com.example.common.utils.MenuUtil;
import com.example.dto.SysMenuAddDTO;
import com.example.dto.SysMenuUpdateDTO;
import com.example.entity.SysMenu;
import com.example.service.SysMenuService;
import com.example.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @Author: zhonger250
 * @Date: 2024-03-28 14:50:50
 * @Description: (SysMenu)表控制层
 */
@RestController
@RequestMapping("/vue-admin/system/sysMenu")
@Api(tags = "系统菜单")
@Validated
@Slf4j
public class SysMenuController {
    /**
     * 服务对象
     */
    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private SysRoleService sysRoleService;

    private static void checkMenuType(Integer type) {
        if (type != SystemConstant.MenuType.CATALOG_MENU.getCode() &&
                type != SystemConstant.MenuType.COMMON_MENU.getCode() &&
                type != SystemConstant.MenuType.BUTTON.getCode()) {
            throw new HttpException(ResultConstant.MENU_TYPE_ERROR);
        }
    }

    /**
     * 查询所有菜单信息
     *
     * @return 所有菜单信息
     */
    @GetMapping("/list")
    @CrossOrigin
    @ApiOperation(value = "查询所有菜单信息")
    @Cacheable(cacheNames = "system:systemMenu#1", key = "'list'")
    public List<SysMenu> selectAll() {
        // 获得所有菜单
        List<SysMenu> sysMenuList = sysMenuService.list();
        List<SysMenu> result = MenuUtil.tree(sysMenuList);


        /*
         排序,
         因为只有三级菜单, 所以就不使用递归进行处理, 直接遍历即可
         CATALOG_MENU(1,"目录菜单"),
         COMMON_MENU(2,"普通菜单"),
         BUTTON(3,"按钮菜单");
        */
        result.sort(Comparator.comparingInt(SysMenu::getSort));
        for (SysMenu sysMenu : result) {
            List<SysMenu> children = sysMenu.getChildren();
            children.sort(Comparator.comparingInt(SysMenu::getSort));
            for (SysMenu child : children) {
                List<SysMenu> children1 = child.getChildren();
                children1.sort(Comparator.comparingInt(SysMenu::getSort));
            }
        }
        // 返回数据
        return result;
    }

    /**
     * 从sysMenuList菜单集合中找到parentMenu的子菜单
     *
     * @param parentMenu  父级菜单
     * @param sysMenuList 所有菜单
     */
    private void getChildMenu(SysMenu parentMenu, List<SysMenu> sysMenuList) {
        // 遍历所有的菜单
        sysMenuList.forEach(sysMenu -> {
            // 如果菜单的父ID与父级菜单的ID相等, 那么此菜单的就是父级菜单的子菜单
            if (Objects.equals(sysMenu.getParentId(), parentMenu.getId())) {
                // 那么此菜单的就是父级菜单的子菜单
                parentMenu.getChildren().add(sysMenu);
                // 接着找到此菜单下的子菜单
                getChildMenu(sysMenu, sysMenuList);
            }
        });
    }

    @PutMapping("/switchStatus/{id}")
    @ApiOperation(value = "切换菜单状态")
    @ApiImplicitParam(value = "菜单ID", name = "id", paramType = "path", dataType = "int")
    @CrossOrigin
    @Caching(evict = {
            @CacheEvict(cacheNames = "system:systemMenu", key = "'list'"),
            @CacheEvict(cacheNames = "system:systemMenu", key = "#id")
    })
    public boolean switchStatus(@PathVariable Serializable id) {
        SysMenu sysMenu = checkMenuId(id);
        // 如果菜单下有子菜单, 不能禁用此菜单
        // todo 如果菜单无论是否被角色使用, 菜单都能禁

        if (sysMenu.getStatus() == SystemConstant.SysMenuStatus.ENABLE.getCode()) {
            int count = sysMenuService.count(sysMenu.getId());
            if (count > 0) {
                throw new HttpException(ResultConstant.MENU_CANNOT_BE_DISABLED);
            }
            sysMenu.setStatus(SystemConstant.SysMenuStatus.DISABLE.getCode());
        } else {
            sysMenu.setStatus(SystemConstant.SysMenuStatus.ENABLE.getCode());
        }
        // 更新
        sysMenuService.updateById(sysMenu);
        return true;
    }

    // todo 根据菜单ID获得菜单下的子菜单信息(要加缓存)

    /**
     * 查看菜单详细信息
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/{id}")
    @CrossOrigin
    @ApiOperation(value = "查询单条数据")
    @ApiImplicitParam(name = "id", value = "主键", paramType = "path", dataType = "string")
    @Cacheable(cacheNames = "system:systemMenu#30", key = "#id")
    public SysMenu selectOne(@PathVariable Serializable id) {
        SysMenu sysMenu = checkMenuId(id);
        // 获得所有菜单信息
        List<SysMenu> sysMenuList = sysMenuService.list();
        // 获得当前菜单下的所有子菜单信息
        getChildMenu(sysMenu, sysMenuList);

        // 获得所有菜单的
        return sysMenu;
    }

    /**
     * 新增数据
     *
     * @param sysMenuDTO 实体对象
     * @return 新增结果
     */
    @PostMapping("/insert")
    @ApiOperation(value = "新增数据")
    @CrossOrigin
    @CacheEvict(value = "system:systemMenu", key = "'list'")
    public boolean insert(@RequestBody SysMenuAddDTO sysMenuDTO) {
        // todo 参数非空验证
        // 菜单父级ID是否存在
        SysMenu sysMenu = new SysMenu();
        Integer parentId = sysMenuDTO.getParentId();
        if (parentId == 0) {
            sysMenu.setType(SystemConstant.MenuType.CATALOG_MENU.getCode());
            sysMenu.setStatus(SystemConstant.SysMenuStatus.ENABLE.getCode());
            // 得到目录菜单的最大sort
            LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysMenu::getParentId, parentId);
            List<SysMenu> catalogList = sysMenuService.list(queryWrapper);
            SysMenu menu = catalogList.stream().max(Comparator.comparingInt(SysMenu::getSort)).get();
            sysMenu.setSort(menu.getSort() + 1);
            // 设置type
            sysMenu.setType(SystemConstant.MenuType.CATALOG_MENU.getCode());
        } else {
            checkMenuParentId(parentId);
            SysMenu parentMenu = sysMenuService.getById(parentId);

            // 序号应该是自增, 每添加一个菜单, 得到父级菜单下的所有子菜单, 并得到最大sort值
            // 将该sort值+1, 再赋值给新增的
            LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysMenu::getParentId, parentId);
            List<SysMenu> children = sysMenuService.list(queryWrapper);
            if (children.size() > 0) {
                Integer sort = children.get(children.size() - 1).getSort();
                sysMenu.setSort(sort + 1);
            } else {
                sysMenu.setSort(1);
            }
            sysMenu.setStatus(SystemConstant.SysMenuStatus.ENABLE.getCode());
            // 设置type
            sysMenu.setType(parentMenu.getType() + 1);
        }
        BeanUtils.copyProperties(sysMenuDTO, sysMenu);
        // todo 验证标题是否重复
        // todo 设置创建人ID

        return this.sysMenuService.save(sysMenu);
    }

    /**
     * 修改数据
     *
     * @param sysMenuDTO 实体对象
     * @return 修改结果
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改数据")
    @CrossOrigin
    @Caching(evict = {
            @CacheEvict(cacheNames = "system:systemMenu", key = "'list'"),
            @CacheEvict(cacheNames = "system:systemMenu", key = "#sysMenuDTO.id")
    })
    public boolean update(@RequestBody SysMenuUpdateDTO sysMenuDTO) {
        // todo 参数非空验证
        // 菜单父级ID是否存在
        Integer parentId = sysMenuDTO.getParentId();
//        SysMenu parentMenu = sysMenuService.getById(parentId);
        checkMenuParentId(parentId);

        // 验证菜单的类型是否正确(1.目录菜单   2.普通菜单   3.按钮
        Integer type = sysMenuDTO.getType();
        checkMenuType(type);
        SysMenu sysMenu = sysMenuService.getById(sysMenuDTO.getId());

        BeanUtils.copyProperties(sysMenuDTO, sysMenu);
        // todo 验证标题是否重复
        // todo 设置创建人ID
        return this.sysMenuService.updateById(sysMenu);
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除结果
     */
    @DeleteMapping("/del/{id}")
    @ApiOperation(value = "根据主键删除信息")
    @ApiImplicitParam(name = "id", value = "主键", paramType = "path", dataType = "int")
    @CrossOrigin
    @Caching(evict = {
            @CacheEvict(cacheNames = "system:systemMenu", key = "'list'"),
            @CacheEvict(cacheNames = "system:systemMenu", key = "#id")
    })
    public boolean delete(@PathVariable int id) {

        // 如果菜单下有子菜单, 就不能删
        int count = sysMenuService.count(id);
        if (count > 0) {
            throw new HttpException(ResultConstant.MENU_CANNOT_BE_DISABLED);
        }
        // todo 删除菜单当前菜单缓存       删除整个菜单缓存
        return sysMenuService.removeById(id);
    }

    /**
     * 1.查询出所有的菜单信息,
     * 2.再根据角色ID查询角色对应的ID,
     * 3.如果角色拥有某个菜单,对应的所有菜单集合中的菜单的状态是被选中的
     * 4.组成一个树形结构的json格式的数据
     *
     * @param roleId 角色ID
     * @return
     */
    @GetMapping("/toAssignMenus/{roleId}")
    @ApiOperation(value = "查询角色对应的菜单以及所有的菜单信息")
    @CrossOrigin
    @ApiImplicitParam(name = "roleId", value = "角色Id", dataType = "int", paramType = "path", required = true)
    public List<SysMenu> toAssignMenus(@PathVariable("roleId") int roleId) {

        if (sysRoleService.getById(roleId)==null){
            throw new HttpException(ResultConstant.ROLE_NOTE_EXISTS);
        }

        return sysMenuService.getMenuListByRoleID(roleId);
    }


    private SysMenu checkMenuId(Serializable id) {
        // 根据ID获得当前菜单的信息
        SysMenu sysMenu = this.sysMenuService.getById(id);

        if (sysMenu == null) {
            throw new HttpException(ResultConstant.MENU_NOT_EXIST);
        }
        return sysMenu;
    }

    private void checkMenuParentId(int parentId) {
        if (parentId != 0) {
            SysMenu parentMenu = sysMenuService.getById(parentId);
            if (parentMenu == null) {
                throw new HttpException(ResultConstant.MENU_PARENT_NOT_EXIST);
            }
        }

    }
}
