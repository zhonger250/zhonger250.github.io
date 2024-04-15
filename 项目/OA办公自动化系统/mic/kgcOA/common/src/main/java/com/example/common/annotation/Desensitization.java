package com.example.common.annotation;

import com.example.common.constant.CommonConstant;
import com.example.common.serializer.SensitiveInfoSerializer;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 丁祥瑞
 * 数据脱敏的注解:
 * 如果类中的某个属性上使用了此注解, 返回此类对象的JSON格式数据时
 * 该属性值会被脱敏处理
 * <p>
 * JsonSerialize注解: 可以指定使用哪个序列化器进行序列化
 * JacksonAnnotationsInside注解: 如果某个自定义注解上面使用了@JacksonAnnotationsInside注解,
 * 当某个属性使用了该自定义注解时, 该属性上的所有Jackson注解都会被解析(
 *      如果某个属性上使用了自定义注解@Desensitization, 属性上有几个Jack注解?
 *      属性上有@JacksonAnnotationsInside和@JsonSerialize注解, 其中@JsonSerialize注解指定属性使用哪个序列化器进行序列化.
 * )
 * 如果某个属性上使用了@Desensitization, 那么这个属性就会使用我们@JackSerialize指定的序列化器进行序列化.
 * SensitiveInfoSerializer序列化器的作用就是脱敏
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveInfoSerializer.class)
public @interface Desensitization {
    /**
     * 脱敏的数据类型
     */
    CommonConstant.DesensitizationType type();

    /**
     * 属性前多少位不需要脱敏
     */
    int prefixLen() default 0;

    /**
     * 属性的后多少位不需要脱敏
     */
    int suffixLen() default 0;
}
