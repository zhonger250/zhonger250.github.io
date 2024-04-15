package com.example.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhonger250
 * @Date: 2024/4/1 15:10
 * @Description: 保证接口的幂等性
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public  @interface  Idempotent{
    /**
     * 过期时间
     */
    int ttl() default 60;

    /**
     * 时间单位
     */
    TimeUnit unit() default TimeUnit.SECONDS;
}



//@Target(ElementType.METHOD)
//@Retention(RetentionPolicy.RUNTIME)
//public @interface Idempotent {
//    /**
//     * 过期时间
//     */
//    int ttl() default 60;
//
//    /**
//     * 时间单位
//     */
//    TimeUnit unit() default TimeUnit.SECONDS;
//}
