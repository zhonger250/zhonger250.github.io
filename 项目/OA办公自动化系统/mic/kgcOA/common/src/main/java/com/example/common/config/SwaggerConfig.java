package com.example.common.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kgc
 * Swagger的配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                // 是否开启
                .enable(true).select()
                // 扫描的路径包
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                // 指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any()).build().pathMapping("/")
                .securitySchemes(security());
    }

    private List<ApiKey> security() {
        List<ApiKey> list=new ArrayList<>();
        ApiKey apiKey= new ApiKey("token", "token", "header");
        list.add(apiKey);
        return list;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("文档标题")
                .contact(new Contact("zhonger250", "zhonger250.github.io", "1637211792@qq.com"))
                .description("描述信息")
                .version("v1.0")
                .build();
    }
}
