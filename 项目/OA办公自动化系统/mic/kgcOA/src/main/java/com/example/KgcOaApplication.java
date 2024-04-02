package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Hello world!
 */
@SpringBootApplication
@MapperScan("com.example.mapper")
@EnableTransactionManagement
// @EnableScheduling
// @EnableAsync
public class KgcOaApplication {
    public static void main(String[] args) {
        SpringApplication.run(KgcOaApplication.class, args);
    }
}
