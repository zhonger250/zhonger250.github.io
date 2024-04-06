package com.example.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.constant.SystemConstant;
import com.example.common.utils.MenuUtil;
import com.example.dto.RoleMenuDTO;
import com.example.entity.RouterVO;
import com.example.entity.SysMenu;
import com.example.entity.SysRoleMenu;
import com.example.entity.SysUserRole;
import com.example.mapper.SysMenuMapper;
import com.example.mapper.SysUserRoleMapper;
import com.example.service.SysMenuService;
import com.example.service.SysRoleMenuService;
import com.example.service.SysUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: zhonger250
 * @Date: 2024-03-28 14:27:28
 * @Description: (SysMenu)表服务实现类
 */
@Slf4j
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    int[] a = {-1, -1};
    @Resource
    private SysRoleMenuService sysRoleMenuService;
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    private static List<RouterVO> builderRouterVO(List<SysMenu> treeMenuList) {
        List<RouterVO> routerVOList = new ArrayList<>();
        for (SysMenu sysMenu : treeMenuList) {
            // 每个sysMenu都是一个routerVO对象
            RouterVO routerVO = new RouterVO();
            routerVO.setPath(sysMenu.getPath());
            routerVO.setIcon(sysMenu.getIcon());
            routerVO.setTitle(sysMenu.getTitle());
            routerVO.setComponent(sysMenu.getComponent());
            // 判断当前菜单是否是按钮
            if (sysMenu.getType() == SystemConstant.MenuType.BUTTON.getCode()) {
                // 如果当前菜单是按钮, 有一种按钮也是要分配路由地址的, 例如 分配菜单按钮
                // 分配菜单按钮, 通过路由地址, 跳转到另一个页面上, 只不过在管理页面的左侧菜单上不显示
                if (StrUtil.isNotEmpty(sysMenu.getComponent())) {
                    // 如果按钮的component不为空, 说明当前按钮需要根据路由地址进行跳转, 跳转到指定组件上
                    // 但是这个路由地址不能在页面上展示
                    routerVO.setHidden(true);
                } else {
                    continue;
                }
            }
            if (CollUtil.isNotEmpty(sysMenu.getChildren())) {
                List<SysMenu> children = sysMenu.getChildren();
                List<RouterVO> routerVOChildren = builderRouterVO(children);
                routerVO.setChildren(routerVOChildren);
                if (sysMenu.getTitle().equals("系统管理")) {
                    routerVO.setAlwaysShow(true);
                }
            }
            routerVOList.add(routerVO);
        }
        return routerVOList;
    }

    @Override
    public int count(int menuParentId) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getParentId, menuParentId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public List<SysMenu> getMenuListByRoleID(int roleId) {
        // 查询所有菜单信息
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getStatus, SystemConstant.SysMenuStatus.ENABLE.getCode());
        List<SysMenu> sysMenuList = baseMapper.selectList(queryWrapper);
        // 根据角色ID 查询角色对应的菜单信息(sys_role_menu)
        LambdaQueryWrapper<SysRoleMenu> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(SysRoleMenu::getRid, roleId);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(queryWrapper2);
        // 遍历角色菜单信息集合, 如果角色拥有某个菜单, 所有菜单集合中的对应的菜单就应该被选中
        for (SysRoleMenu sysRoleMenu : sysRoleMenuList) {
            for (SysMenu sysMenu : sysMenuList) {
                if (Objects.equals(sysMenu.getId(), sysRoleMenu.getMid())) {
                    sysMenu.setIsChecked(true);
                }
            }
        }
        // 将处理后的菜单信息树化

        return MenuUtil.tree(sysMenuList);
    }

    @Transactional()
    @Override
    public boolean doAssignMenu(RoleMenuDTO roleMenuDTO) {
        // 根据角色Id删除角色原有的菜单信息
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRid, roleMenuDTO.getRoleId());
        sysRoleMenuService.remove(queryWrapper);

        // 添加角色对应的菜单的信息
        List<SysRoleMenu> sysRoleMenuList = new ArrayList<>();
        List<Integer> menuIds = roleMenuDTO.getMenuIds();
        for (Integer menuId : menuIds) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMid(menuId);
            sysRoleMenu.setRid(roleMenuDTO.getRoleId());
            sysRoleMenuList.add(sysRoleMenu);
        }
        return sysRoleMenuService.saveBatch(sysRoleMenuList);
    }

    @Override
    public List<RouterVO> getMenuRouterVO(Integer userId) {
        List<SysMenu> sysMenuList = getSysMenus(userId);

        // 2.根据查询出来的菜单那信息(SysMenu) 生成一个树形结构
        List<SysMenu> treeMenuList = MenuUtil.tree(sysMenuList);

        // 3.将SysMenu树形菜单信息转为RouterVO树形菜单信息
        return builderRouterVO(treeMenuList);
    }

    /**
     * 获得用户所有可用的菜单
     *
     * @param userId 用户ID
     * @return 所有可用的菜单集合
     */
    private List<SysMenu> getSysMenus(Integer userId) {
        Set<SysMenu> sysMenus = new HashSet<>();

        // 1.根据用户ID查询用户对应的菜单信息(SysMenu)
        // 一个用户对应多个角色 一个角色对应多个菜单
        List<SysMenu> sysMenuList = new ArrayList<>();
        LambdaQueryWrapper<SysUserRole> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(SysUserRole::getUid, userId);
        List<SysUserRole> sysUserRoleList = sysUserRoleMapper.selectList(queryWrapper1);
        for (SysUserRole sysUserRole : sysUserRoleList) {
            // 根据用户ID获得角色ID
            Integer rid = sysUserRole.getRid();
            LambdaQueryWrapper<SysRoleMenu> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(SysRoleMenu::getRid, rid);
            List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(queryWrapper2);
            for (SysRoleMenu sysRoleMenu : sysRoleMenuList) {
                // 根据角色ID获得菜单的ID
                Integer mid = sysRoleMenu.getMid();
                SysMenu sysMenu = sysMenuService.getById(mid);
                // 如果菜单是禁用的,结束本次循环, 进入下次循环
                if (sysMenu.getStatus() == SystemConstant.SysMenuStatus.DISABLE.getCode()) {
                    continue;
                }
                if (sysMenus.add(sysMenu)) { // 如果能够添加到set集合中, 说明没有重复
                    sysMenuList.add(sysMenu);
                }
            }
        }
        return sysMenuList;
    }

    @Override
    public List<String> getButtonPermission(Integer userId) {
        // 根据用户的ID获得所有当前用户的菜单
        List<SysMenu> sysMenus = getSysMenus(userId);
        // 获得按钮中按钮权限标识符
        return sysMenus.stream().filter(sysMenu -> sysMenu.getType() == SystemConstant.MenuType.BUTTON.getCode())
                .map(SysMenu::getPermission).collect(Collectors.toList());
    }

}
