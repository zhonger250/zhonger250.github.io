package com.example.filter;

import com.example.common.constant.ResultConstant;
import com.example.common.constant.SystemConstant;
import com.example.common.utils.JwtUtil;
import com.example.common.utils.ResponseUtil;
import com.example.common.vo.Result;
import com.example.dto.LoginDTO;
import com.example.entity.CustomerUser;
import com.example.entity.SysUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhonger250
 * @Date: 2024/4/9 14:36
 * @Description: 登录的过滤器
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    public TokenLoginFilter(AuthenticationManager authenticationManager){
        // 设置认证管理器
        setAuthenticationManager(authenticationManager);
        // 是否只支持post请求
        setPostOnly(false);
        // 登录的请求地址和请求方式
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/vue-admin/system/user/login","POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // 读取的登录的数据
            LoginDTO loginDTO = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);
            // 封装登录的数据
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginDTO.getAccount(),loginDTO.getPassword());
            // 认证操作(实际使用的是UserDetailServiceImpl)
            return this.getAuthenticationManager().authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 获得登录成功的用户信息
        CustomerUser customerUser = (CustomerUser) authResult.getPrincipal();
        SysUser sysUser = customerUser.getSysUser();

        if (sysUser.getStatus().equals(SystemConstant.SysUserStatus.DISABLE.getCode())){
            Result build = Result.builder().code(ResultConstant.ACCOUNT_DISABLE.getCode()).message(ResultConstant.ACCOUNT_DISABLE.getMessage()).build();

            ResponseUtil.out(response,build);
        }
        // 生成token
        String token = JwtUtil.createToken(String.valueOf(sysUser.getId()), sysUser.getAccount());
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);

        //


       ResponseUtil.out(response, Result.success(map));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Result result = Result.builder().code(ResultConstant.LOGIN_ERROR.getCode()).message(ResultConstant.LOGIN_ERROR.getMessage()).build();
        ResponseUtil.out(response,result);
    }

}
