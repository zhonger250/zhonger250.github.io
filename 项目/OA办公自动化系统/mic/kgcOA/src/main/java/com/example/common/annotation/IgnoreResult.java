package com.example.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Dxr
 * 注解: 在编写程序时, 有时候需要给程序添加一些程序信息, 但是这些额外信息不能通过
 * 常规的编写代码的方式实现
 *
 * IgnoreResult注解的作用: 如果控制器中的方法上使用了IgnoreResult注解后, 此方法的返回值就不会封装到Result对
 * 象中了.
 *
 * 如何自定义注解:
 * 1.自定义注解需要使用@interface关键字
 * 2.需要使用元注解修饰自定义注解.
 *          @Target(ElementType.METHOD) :自定义注解只能用在方法上
 *          ElementType.TYPE可以用在类上   ElementType.Field自定义注解可以用在属性上
 *          @Retention(RetentionPolicy.RUNTIME) :自定义注解的信息, 在程序运行发过程中可以被读取到的
 *
 * 注意的是: 定义的注解,起到一个标识的作用. 方法的返回值是否封装到Result对象中, 是需要进行额外编码的.
 * 项目中这些通用的功能, 一般使用AOP技术实现.
 *
 * 项目的通用功能: 自定义注解+AOP
 */
@Target(ElementType.METHOD) // 表示此注解只能修饰方法
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreResult {


}
