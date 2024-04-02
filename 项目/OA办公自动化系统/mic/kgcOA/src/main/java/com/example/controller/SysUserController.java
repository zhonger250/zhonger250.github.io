package com.example.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.constant.ResultConstant;
import com.example.common.constant.SystemConstant;
import com.example.common.exception.HttpException;
import com.example.dto.DistributionRoleDTO;
import com.example.dto.SysUserAddDTO;
import com.example.dto.SysUserUpdateDTO;
import com.example.entity.SysDept;
import com.example.entity.SysRole;
import com.example.entity.SysUser;
import com.example.entity.SysUserRole;
import com.example.service.SysDeptService;
import com.example.service.SysRoleService;
import com.example.service.SysUserRoleService;
import com.example.service.SysUserService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @Author: zhonger250
 * @Date: 2024-03-26 14:58:06
 * @Description: (SysUser)表控制层
 */
@RestController
@RequestMapping("/vue-admin/system/sysUser")
@Api(tags = "系统用户")
@Validated
@Slf4j
public class SysUserController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Resource
    private SysDeptService sysDeptService;

    /**
     * 查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询所有数据")
    @CrossOrigin
    public List<SysUser> selectAll() {
        return sysUserService.list();
    }

    /**
     * 分页查询所有数据
     *
     * @param current  当前页
     * @param pageSize 每页显示条数
     * @return 分页的数据
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", defaultValue = "5", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "realName", value = "真实姓名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "deptId", value = "部门ID", defaultValue = "5", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "job", value = "岗位", dataType = "String", paramType = "query")
    })
    @CrossOrigin
    public IPage<SysUser> selectPage(@RequestParam(defaultValue = "1") int current,
                                     @RequestParam(defaultValue = "5") int pageSize,
                                     @RequestParam(defaultValue = "") String realName,
                                     @RequestParam(defaultValue = "0") int deptId,
                                     @RequestParam(defaultValue = "") String job) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.likeRight(StrUtil.isNotBlank(realName),SysUser::getRealName,realName);
        queryWrapper.eq(deptId!=0,SysUser::getDeptId,deptId);
        queryWrapper.eq(StrUtil.isNotBlank(job),SysUser::getJob,job);

        IPage<SysUser> page = new Page<>(current,pageSize);
        sysUserService.page(page,queryWrapper);
        List<SysUser> records = page.getRecords();
        // 得到ID
        records.forEach(this::getUserInfo);


//        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.likeRight(StrUtil.isNotBlank(realName), SysUser::getRealName, realName);
//        queryWrapper.eq(deptId != 0, SysUser::getDeptId, deptId);
//        queryWrapper.eq(StrUtil.isNotBlank(job), SysUser::getJob, job);
//
//        // 分页条件
//        IPage<SysUser> page = new Page<>(current, pageSize);
//        // 查询
//        sysUserService.page(page, queryWrapper);
//        // 获得当前页的每条记录
//        List<SysUser> records = page.getRecords();
//        // 方法引用
//        records.forEach(this::getUserInfo);
        return page;
    }

    private void getUserInfo(SysUser sysUser) {
        Integer id = sysUser.getId();
        // 根据用户ID查询得到角色ID
        LambdaQueryWrapper<SysUserRole> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(SysUserRole::getUid,id);
        List<SysUserRole> sysUserRoleList = sysUserRoleService.list(queryWrapper2);
        for (SysUserRole sysUserRole : sysUserRoleList) {

            SysRole sysRole = sysRoleService.getById(sysUserRole.getRid());
            if (sysRole!=null){
                String roleNames = sysUser.getRoleNames()==null?"": sysUser.getRoleNames();
                roleNames+=sysRole.getRoleName()+";";
                sysUser.setRoleNames(roleNames);
            }
        }
        String deptName = sysDeptService.getById(sysUser.getDeptId()).getDeptName();
        sysUser.setDeptName(deptName);
    }

//    private void getUserInfo(SysUser sysUser) {
//        // 获得查询到的每个用户ID
//        Integer id = sysUser.getId();
//        // 根据用户ID 查询角色ID
//        LambdaQueryWrapper<SysUserRole> queryWrapper1 = new LambdaQueryWrapper<>();
//        queryWrapper1.eq(SysUserRole::getUid, id);
//        List<SysUserRole> sysUserRoleList = sysUserRoleService.list(queryWrapper1);
//        // 获得用户每个的角色的编号
//        for (SysUserRole ur : sysUserRoleList) {
//            // 根据角色编号查询对应的角色信息s
//            SysRole sysRole = sysRoleService.getById(ur.getRid());
//            String roleName = sysUser.getRoleNames() == null ? "" : sysUser.getRoleNames();
//            // 在原有的角色名称后面拼接新的角色名
//            roleName += sysRole.getRoleName() + ";";
//            // 保存拼接以后的角色
//            sysUser.setRoleNames(roleName);
//        }
//        // 获得每个用户的部门编号
//        Integer deptId2 = sysUser.getDeptId();
//        SysDept sysDept = sysDeptService.getById(deptId2);
//        sysUser.setDeptName(sysDept.getDeptName());
//    }


    /**
     * 通过用户的ID查询用户信息
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/{id}")
    @CrossOrigin
    @ApiOperation(value = "通过用户的ID查询用户信息")
    @ApiImplicitParam(name = "id", value = "主键", paramType = "path", dataType = "string")
    public SysUser selectOne(@PathVariable Serializable id) {
        // 根据ID获得用户信息
        SysUser sysUser = this.sysUserService.getById(id);
        if (sysUser == null) {
            throw new HttpException(ResultConstant.USER_NOT_EXIST);
        }
        // 加载用户的相关信息
        getUserInfo(sysUser);
        return sysUser;
    }

    /**
     * 新增用户信息
     *
     * @param sysUserDTO 实体对象
     * @return 新增结果
     */
    @PostMapping("/insert")
    @ApiOperation(value = "新增数据")
    @CrossOrigin
    public boolean insert(@RequestBody SysUserAddDTO sysUserDTO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserDTO, sysUser);


        sysUser.setStatus(SystemConstant.SysUserStatus.ENABLE.getCode());
        // todo 创建人的用户ID
        sysUser.setPassword(SecureUtil.md5("666666"));

        return this.sysUserService.save(sysUser);
    }

    /**
     * 修改数据
     *
     * @param sysUserDTO 实体对象
     * @return 修改结果
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改数据")
    @CrossOrigin
    public boolean update(@RequestBody SysUserUpdateDTO sysUserDTO) {
        SysUser sysUser = this.sysUserService.getById(sysUserDTO.getId());
        BeanUtils.copyProperties(sysUserDTO, sysUser);
        // todo 修改人的用户ID
        return this.sysUserService.updateById(sysUser);
    }

    /**
     * 给用户分配角色
     *
     * @param roleDTO 用户
     * @return 是否分配成功
     */
    @PutMapping("/distributionRole")
    @ApiOperation(value = "给用户分配角色")
    @CrossOrigin
    public boolean distributionRole(@RequestBody DistributionRoleDTO roleDTO) {
        return sysUserRoleService.distributionRole(roleDTO);
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
        return this.sysUserService.removeById(Integer.parseInt(id));
    }

    /**
     * 切换用户状态
     * @param id 用户ID
     * @return 是否切换成功
     */
    @PutMapping("/switchStatus/{id}")
    @ApiOperation(value = "切换用户状态")
    @CrossOrigin
    @ApiImplicitParam(name = "id",value = "用户ID", paramType = "path", dataType = "int")
    public boolean switchStatus(@PathVariable Serializable id){
        SysUser sysUser = sysUserService.getById(id);
        if (sysUser.getStatus()==SystemConstant.SysUserStatus.DISABLE.getCode()){
            sysUser.setStatus(SystemConstant.SysUserStatus.ENABLE.getCode());
        }else {
            sysUser.setStatus(SystemConstant.SysUserStatus.DISABLE.getCode());
        }
        return sysUserService.updateById(sysUser);
    }

    /**
     * 查询用户的所有角色以及系统的所有角色信息
     * @param id
     * @return
     */
    @GetMapping("/selectUserRoleIdsAndAllRole/{id}")
    @ApiOperation(value = "查询用户的所有角色以及系统的所有角色信息")
    @ApiImplicitParam(name = "id",value = "用户Id",paramType = "path",dataType = "int")
    @CrossOrigin
    public Map<String, Object> selectUserRoleIdsAndAllRole(@PathVariable Serializable id){
        Map<String, Object> map = new HashMap<>();
        // 根据用户的ID获得用户和角色的关系
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUid,id);
        List<SysUserRole> list = sysUserRoleService.list(queryWrapper);
        // 获得用对应角色ID的集合
        List<Integer> userRoleIds = list.stream().map(SysUserRole::getRid).collect(Collectors.toList());
        // 获得系统中所有角色信息
        List<SysRole> sysRoleList = sysRoleService.list();
        // 封装到map中
        // 获得用户信息
        SysUser sysUser = sysUserService.getById(id);
        map.put("userRoleIds", userRoleIds);
        map.put("allRoles",sysRoleList);
        map.put("sysUser",sysUser);
        return map;
    }
}
