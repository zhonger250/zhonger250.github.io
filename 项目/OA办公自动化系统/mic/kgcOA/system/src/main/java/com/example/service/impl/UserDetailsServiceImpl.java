package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.constant.RedisPrefix;
import com.example.common.constant.ResultConstant;
import com.example.common.constant.SystemConstant;
import com.example.common.exception.HttpException;
import com.example.common.utils.RedisUtil;
import com.example.entity.CustomerUser;
import com.example.entity.SysUser;
import com.example.service.SysMenuService;
import com.example.service.SysUserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhonger250
 * @Date: 2024/4/8 17:57
 * @Description: SpringSecurity认证业务类
 */
@Service("userDetailService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        // 根据用户名获得用户信息
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        if (sysUser==null){
            // 如果用户为空, 说明用户不存在
            throw new HttpException(ResultConstant.LOGIN_ERROR);
        }
        if (sysUser.getStatus().equals(SystemConstant.SysUserStatus.DISABLE.getCode())){
            throw new HttpException(ResultConstant.ACCOUNT_DISABLE);
        }

        // 根据用户的ID获得用户的权限集合, 将其保存到Redis中
        List<String> buttonPermission = sysMenuService.getButtonPermission(sysUser.getId());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String button : buttonPermission) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(button);
            authorities.add(simpleGrantedAuthority);
        }
        redisUtil.set(RedisPrefix.SYSTEM_USER_PERMISSIONS +sysUser.getId(),authorities);

        // 返回用户信息
        return new CustomerUser(sysUser,authorities);
    }
}
