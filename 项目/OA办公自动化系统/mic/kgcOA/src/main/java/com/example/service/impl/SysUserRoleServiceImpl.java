package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.DistributionRoleDTO;
import com.example.mapper.SysUserRoleMapper;
import com.example.entity.SysUserRole;
import com.example.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhonger250
 * @Date: 2024-03-26 14:58:24
 * @Description: (SysUserRole)表服务实现类
 */
@Slf4j
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean distributionRole(DistributionRoleDTO roleDTO) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        // 根据用户的ID删除用户原来拥有的角色
        queryWrapper.eq(SysUserRole::getUid,roleDTO.getUserId());
        baseMapper.delete(queryWrapper);
        List<SysUserRole> sysUserRoles = new ArrayList<>();
        // 添加用户拥有的最新的角色
        for (Integer roleId : roleDTO.getRoleIds()) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUid(roleDTO.getUserId());
            sysUserRole.setRid(roleId);
            sysUserRoles.add(sysUserRole);
        }
        saveBatch(sysUserRoles);
        return true;
    }
}
