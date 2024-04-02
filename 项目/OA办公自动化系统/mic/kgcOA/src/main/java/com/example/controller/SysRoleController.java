package com.example.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.annotation.Idempotent;
import com.example.common.constant.ResultConstant;
import com.example.common.exception.HttpException;
import com.example.common.utils.RedisUtil;
import com.example.dto.DistributionMenuDTO;
import com.example.dto.SysRoleAddDTO;
import com.example.dto.SysRoleUpdateDTO;
import com.example.entity.SysMenu;
import com.example.entity.SysRole;
import com.example.entity.SysRoleMenu;
import com.example.service.SysMenuService;
import com.example.service.SysRoleMenuService;
import com.example.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhonger250
 * @Date: 2024-03-22 16:13:24
 * @Description: (SysRole)表控制层
 */
@RestController
@RequestMapping("/vue-admin/system/sysRole")
@Api(tags = "系统角色")
@Validated
@Slf4j
public class SysRoleController {
    /**
     * 服务对象
     */
    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private SysRoleMenuService roleMenuService;

    @Resource
    private SysMenuService menuService;

    /**
     * 查询所有角色信息
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询所有数据")
    @CrossOrigin
    public List<SysRole> selectAll() {
        return sysRoleService.list();
    }

    /**
     * 分页查询角色信息
     *
     * @param current 当前页
     * @param pageSize    每页显示条数
     * @return 分页的数据
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "roleName", value = "角色名", defaultValue = "1", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", defaultValue = "5", dataType = "int", paramType = "query")
    })
    @CrossOrigin
    public IPage<SysRole> selectPage(@RequestParam(defaultValue = "1") int current,
                                     @RequestParam(defaultValue = "5") int pageSize,
                                     @RequestParam(defaultValue = "") String roleName) {
        IPage<SysRole> page = new Page<>(current, pageSize);
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(roleName), SysRole::getRoleName, roleName);
        return sysRoleService.page(page, queryWrapper);
    }


    /**
     * 得到角色权限信息
     * @param id 角色ID
     * @return 权限列表
     */
    @GetMapping("/RoleMenuInfo/{id}")
    @CrossOrigin
    @ApiOperation(value = "角色权限信息")
    @ApiImplicitParam(name = "id",value = "角色ID",dataType = "int",paramType = "path")
    public List<SysMenu> getRoleMenuInfo(@PathVariable Serializable id){
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRid,id);
        List<SysRoleMenu> list = roleMenuService.list(queryWrapper);
        List<SysMenu> menuList = new ArrayList<>();
        for (SysRoleMenu sysRoleMenu : list) {
            SysMenu sysMenu = menuService.getById(sysRoleMenu.getMid());
            if (sysMenu!=null){
                menuList.add(sysMenu);
            }
        }
        return  menuList;
    }



    /**
     * 新增角色信息
     *
     * @param sysRoleDTO 角色信息
     * @return 新增结果
     */
    @PostMapping("/insert")
    @ApiOperation(value = "新增数据")
    @CrossOrigin
    @Idempotent
    public boolean insert(@RequestBody SysRoleAddDTO sysRoleDTO) {
        if (StrUtil.isEmpty(sysRoleDTO.getRoleName())) {
            throw new HttpException(ResultConstant.ROLE_NAME_NULL_ERROR);
        }
        if (StrUtil.isEmpty(sysRoleDTO.getRoleName())) {
            throw new HttpException(ResultConstant.ROLE_DESCRIPTION_NULL_ERROR);
        }
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(sysRoleDTO, sysRole);
        // todo 创建人的用户ID
        return this.sysRoleService.save(sysRole);
    }

    /**
     * 修改角色信息
     *
     * @param sysRoleDTO 角色信息
     * @return 修改结果
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改数据")
    @CrossOrigin
    public boolean update(@RequestBody SysRoleUpdateDTO sysRoleDTO) {
        if (StrUtil.isEmpty(sysRoleDTO.getRoleName())) {
            throw new HttpException(ResultConstant.ROLE_NAME_NULL_ERROR);
        }
        if (StrUtil.isEmpty(sysRoleDTO.getRoleName())) {
            throw new HttpException(ResultConstant.ROLE_DESCRIPTION_NULL_ERROR);
        }
        SysRole sysRole = this.sysRoleService.getById(sysRoleDTO.getId());
        BeanUtils.copyProperties(sysRoleDTO, sysRole);
        // todo 设置角色修改人ID

        return this.sysRoleService.updateById(sysRole);
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除结果
     */
    @DeleteMapping("/del/{id}")
    @ApiOperation(value = "根据主键删除信息")
    @ApiImplicitParam(name = "id", value = "主键", paramType = "path", dataType = "string")
    @CrossOrigin
    public boolean delete(@PathVariable String id) {
        return this.sysRoleService.removeById(id);
    }

    /**
     * 批量删除角色信息
     *
     * @param ids ids
     * @return 是否删除成功
     */
    @DeleteMapping("/delete/all")
    @ApiOperation(value = "根据主键删除多条数据")
    public boolean delete(List<String> ids) {
        // todo 判断角色是否被使用, 如果被使用不能删除
        return this.sysRoleService.removeByIds(ids);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据角色ID获取角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "角色ID",dataType = "int",paramType = "path")
    })
    public SysRole detail(@PathVariable Serializable id){
        return this.sysRoleService.getById(id);
    }
}
