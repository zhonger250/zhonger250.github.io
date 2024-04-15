package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.SysUser;
import com.example.entity.SysUserRole;
import com.example.mapper.SysUserMapper;
import com.example.mapper.SysUserRoleMapper;
import com.example.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author: zhonger250
 * @Date: 2024-03-26 14:58:06
 * @Description: (SysUser)表服务实现类
 */
@Slf4j
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Integer id) {
        // 根据用户ID删除, 用户角色关系表中的数据
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUid,id);
        sysUserRoleMapper.delete(queryWrapper);
        //删除用户表中的数据
        baseMapper.deleteById(id);

        return true;
    }
}
