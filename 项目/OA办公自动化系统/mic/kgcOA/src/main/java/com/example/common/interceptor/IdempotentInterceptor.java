package com.example.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.example.common.annotation.Idempotent;
import com.example.common.constant.ResultConstant;
import com.example.common.exception.HttpException;
import com.example.common.utils.RedisUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhonger250
 * @Date: 2024/4/1 15:15
 * @Description: 幂等性拦截器
 */
@Component
public class IdempotentInterceptor extends WebContentInterceptor {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        // 找到请求所对应的控制器中的方法
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            // 如果方法上使用了幂等性注解, 保证接口的幂等性
            if (method.isAnnotationPresent(Idempotent.class)) {
                // 获得方法上的幂等性注解
                Idempotent annotation = method.getAnnotation(Idempotent.class);
                // 令牌过期时间
                int ttl = annotation.ttl();
                // 过期时间单位
                TimeUnit unit = annotation.unit();
                // 获得用户请求中的令牌
                String parameter = request.getHeader("token");
                if (StrUtil.isEmpty(parameter)) {
                    parameter = request.getParameter("token");
                }
                // 判断令牌是否存在
                if (StrUtil.isEmpty(parameter) || redisUtil.hasKey(parameter)) {
                    // 如果令牌已经存在, 认为已经提交
                    throw new HttpException(ResultConstant.REPEAT_COMMIT);
                }
                // 如果令牌已经存在, 说明是第一次访问, 在redis中保存信息
                redisUtil.set(parameter, parameter, ttl, unit);
            }
        }
        return true;
    }


    //    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        // 找到请求所对应的控制器中的方法
//        if (handler instanceof HandlerMethod) {
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            Method method = handlerMethod.getMethod();
//            // 如果方法上使用了幂等性注解, 保证接口的幂等性
//            if (method.isAnnotationPresent(Idempotent.class)) {
//                // 获得方法上的幂等性注解
//                Idempotent annotation = method.getAnnotation(Idempotent.class);
//                // 令牌的过期时间
//                int ttl = annotation.ttl();
//                // 过期时间单位
//                TimeUnit unit = annotation.unit();
//                // 获得用户请求中的令牌
//                String token = request.getHeader("token");
//                if (StrUtil.isEmpty(token)) {
//                    token = request.getParameter("token");
//                }
//                // 判断令牌是否存在
//                if (StrUtil.isEmpty(token) || redisUtil.hasKey(token)) {
//                    // 如果令牌为空或者令牌已经存在, 认为令牌已经提交
//                    throw new HttpException(ResultConstant.REPEAT_COMMIT);
//                }
//                // 如果令牌不存在, 说明是第一次访问, 在redis中保存令牌信息
//                redisUtil.set(token, token, ttl, unit);
//            }
//        }
//        return true;
//    }

}
