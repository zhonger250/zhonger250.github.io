package com.example.common.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shkstart
 * mybatis-plus配置类
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 分页插件
     *
     * @return 分页插件对象
     * @Bean 注解的作用：能够将方法的返回值交给Spring容器进行管理, 在Spring容器对应Bean的名字救赎方法的名字
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 乐观锁插件
     *
     * @return 乐观锁对象
     * 表中的乐观锁就是一个version字段. 新增数据时version字段的默认值为1,如果更新了这条数据,version自动的+1
     * <p>
     * 假设同时由两个请求更新一条数据, 刚开始时这条记录的version是1. 其中一个请求更新了这条记录以后, 此时version的值为2了.
     * 另一个请求在更新这条记录时, 发现版本已经从1变成了2, 另一个请求不能在更新这条记录了.
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}
