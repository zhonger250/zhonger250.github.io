package com.example.config;

import com.example.common.utils.RedisUtil;
import com.example.filter.TokenAuthorityFilter;
import com.example.filter.TokenLoginFilter;
import com.example.service.SysUserService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: zhonger250
 * @Date: 2024/4/9 16:10
 * @Description: Security配置类
 */
@Component
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private RedisUtil redisUtil;

    @Resource
    private SysUserService sysUserService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 指定UserDetailService实体类
        auth.userDetailsService(userDetailsService);
    }

    /**
     * 配置SpringSecurity忽略的地址
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/favicon.ico",
                "/swagger-resources/**",
                "/webjars/**",
                "/v2/**",
                "/swagger-ui.html/**",
                "/doc.html");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 指定某些接口不需要通过验证即可访问。登陆接口肯定是不需要认证的
                .antMatchers("/vue-admin/system/user/login").permitAll()
                // 这里意思是其它所有接口需要认证才能访问
                .anyRequest().authenticated()
                .and()
                .csrf().disable() // 关闭csrf跨站请求伪造
                // 开启跨域以便前端调用接口
                .cors() // 支持跨域
                .and()
                .addFilterBefore(new TokenAuthorityFilter(sysUserService, redisUtil),
                        UsernamePasswordAuthenticationFilter.class
                ).addFilter(new TokenLoginFilter(this.authenticationManager())
                        //禁用session
                ).sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
