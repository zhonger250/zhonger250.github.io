package com.example.common.config;

import com.example.common.deserializer.XssDeserializer;
import com.example.common.filter.XssFilter;
import com.example.common.interceptor.IdempotentInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author 丁祥瑞
 * SpringBoot项目的配置类
 */
@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    @Value("${file-save-path}")
    private String uploadPath;

    /**
     * 接口幂等性拦截器
     */
    @Resource
    private IdempotentInterceptor idempotentInterceptor;

    /**
     * 如果用户发送的请求是: https://localhost/upload/1.txt
     * 从uploadPath(X:/upload/)中获取到1.txt文件，并返回给用户
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置文件上传的地址
        // 例如: http://localhost/upload/1.txt
        log.debug("uploadPath: {}", uploadPath);
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + uploadPath);
    }

    //    @Bean
    public FilterRegistrationBean<XssFilter> filterRegistrationBean() {
        FilterRegistrationBean<XssFilter> bean = new FilterRegistrationBean<>();
        // 设置要注册的过滤器
        bean.setFilter(new XssFilter());
        // 设置过滤器的请求路径
        bean.addUrlPatterns("/*");
        // 设置过滤器执行的顺序
        bean.setOrder(1);
        return bean;
    }

    /**
     * 替换SpringBoot中默认的反序列化器
     *
     * @return
     */
    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        // 创建MappingJackson2HttpMessageConverter对象
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // SpringBoot将对象转换为json格式数据或者将json数据转为对象, 使用Jackson框架
        // ObjectMapper是Jackson框架的核心类, 用于读取和写入JSON数据
        //String类型防XSS

        ObjectMapper mapper = new ObjectMapper();
        // 替换原来的String类型反序列化器, 换成我们自己的(XssDeserializer)
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(String.class, XssDeserializer.xss);
        mapper.registerModule(simpleModule);
        converter.setObjectMapper(mapper);
        // 返回转换器
        return converter;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 幂等性的拦截器拦截用户所有的控制器请求
        registry.addInterceptor(idempotentInterceptor).addPathPatterns("/**");
    }
}
