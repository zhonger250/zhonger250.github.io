package com.example.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 丁祥瑞
 * 系统拦截器
 */
@Slf4j
public class SystemInterceptor implements HandlerInterceptor {
    /**
     * 请求处理之前进行调用(Controller方法调用之前)
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @return true:请求进入到控制其中, false:请求结束,不会进入控制层
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("请求进入到拦截器中");
        return true;
    }
}

