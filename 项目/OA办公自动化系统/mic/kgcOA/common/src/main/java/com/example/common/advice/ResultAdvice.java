package com.example.common.advice;


import com.example.common.annotation.IgnoreResult;
import com.example.common.vo.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;

/**
 * @author Dxr
 * 控制器结果增强
 * 获得控制器方法的返回结果, 如果不是Result类型的, 将返回记过封装到Result对象中
 */
@ControllerAdvice("com.example.controller")
public class ResultAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 如果supports方法返回值是true, 就会执行beforeBodyWrite方法
     * (返回supports方法发返回值, 控制是否将控制器发结果封装到Result对象中)
     *
     * @param methodParameter 目标方法参数
     * @param aClass          类型
     * @return 是否支持
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        //获取方法的信息
        Method method = methodParameter.getMethod();
        // 判断方法上是否有IgnoreResult注解, 如果方法上使用了IgnoreResult注解, 则不进行封装
        if (method.isAnnotationPresent(IgnoreResult.class)) {
            return false;
        }
        return true;
    }

    /**
     * 将控制器方法的返回值封装到Result对象中
     *
     * @param body 控制器方法的返回值
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 将body封装到Result对象中, 注意: 如果方法的返回值已经是Result类型的对象了, 不需要将方法的返回值封装到result对象中了.
        // 如果控制器方法打返回值类型是String类型的数据, 需要在控制器方法中手动将结果封装到result中
        if (body == null) {
            return Result.success();
        } else if (body instanceof Result) {
            return body;
        }else {
            return Result.success(body);
        }
    }
}
