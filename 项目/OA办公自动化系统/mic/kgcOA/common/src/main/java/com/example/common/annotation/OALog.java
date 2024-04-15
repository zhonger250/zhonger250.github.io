package com.example.common.annotation;

import com.example.common.constant.CommonConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 丁祥瑞
 * 自定义日志注解: 如果某方法被该注解修饰, 该方法再被调用时会自动记录用户的操作日志
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OALog {
    /**
     * 日志类型, 默认的日志类型是操作日志
     */
    CommonConstant.LogType logType() default CommonConstant.LogType.OPERATION;

    /**
     * 日志操作类型, 默认的日志操类型是其他
     */
    CommonConstant.OperatorType operatorType() default CommonConstant.OperatorType.OTHER;

    /**
     * 方法的具体作用
     */
    String content() default "";

    /**
     * 项目中的模块
     */
    CommonConstant.Module module() default CommonConstant.Module.SYSTEM;
}
