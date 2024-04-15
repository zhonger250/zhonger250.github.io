package com.example.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;

/**
 * @Author: zhonger250
 * @Date: 2024/4/8 17:12
 * @Description: 支持SpringSecurity的用户类
 */
public class CustomerUser extends User {

    /**
     * 系统中的用户类
     */
    private SysUser sysUser;


    public CustomerUser(SysUser sysUser, Collection<? extends GrantedAuthority> authorities) {
        super(sysUser.getAccount(), "{bcrypt}" + sysUser.getPassword(),
                authorities);
        this.sysUser = sysUser;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }
}
