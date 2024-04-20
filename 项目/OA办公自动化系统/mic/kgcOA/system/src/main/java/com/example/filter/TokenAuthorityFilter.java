package com.example.filter;

import cn.hutool.core.util.StrUtil;
import com.example.common.constant.RedisPrefix;
import com.example.common.constant.ResultConstant;
import com.example.common.utils.JwtUtil;
import com.example.common.utils.RedisUtil;
import com.example.common.utils.ResponseUtil;
import com.example.common.vo.Result;
import com.example.entity.CustomerUser;
import com.example.entity.SysUser;
import com.example.service.SysUserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: zhonger250
 * @Date: 2024/4/9 15:22
 * @Description: 鉴权的过滤器
 */
public class TokenAuthorityFilter extends OncePerRequestFilter {

    private SysUserService sysUserService;

    private RedisUtil redisUtil;

    public TokenAuthorityFilter(SysUserService sysUserService, RedisUtil redisUtil) {
        this.sysUserService = sysUserService;
        this.redisUtil = redisUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 登录请求直接放行
        if ("/vue-admin/system/user/login".equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
//            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
        if (authenticationToken != null) {
            // 将认证信息存入到SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // 放行
            filterChain.doFilter(request,response);
            // 如果想在程序中获得登录的用户信息可以使用以下代码
//            CustomerUser customerUser = (CustomerUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }else {
            Result result = Result.builder().code(ResultConstant.NO_PERMISSION.getCode()).message(ResultConstant.NO_PERMISSION.getMessage()).build();
            ResponseUtil.out(response,result);
        }
    }


    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // 获得请求头中的令牌
        String token = request.getHeader("token");

        // 解析令牌信息, 获得令牌ID
        //      解析错误, 令牌有问题
        //      解析正确, 根据用户的ID获取权限集合

        // 令牌为空, 没有权限
        if (StrUtil.isBlank(token)) {
            return null;
        }

        // 解析令牌信息
        String userId = JwtUtil.getUserId(token);
        String account = JwtUtil.getUsername(token);


        // 获得用户对应的权限集合
        List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) redisUtil.get(RedisPrefix.SYSTEM_USER_PERMISSIONS + userId);


        // 根据用户Id获得用户信息
        SysUser sysUser = sysUserService.getById(userId);
        // 封装用户信息
        CustomerUser customerUser = new CustomerUser(sysUser, authorities);
        // 返回认证信息
        return new UsernamePasswordAuthenticationToken(customerUser, null, authorities);
    }
}
