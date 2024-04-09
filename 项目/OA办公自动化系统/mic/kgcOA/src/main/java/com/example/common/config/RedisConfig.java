package com.example.common.config;

import com.example.common.serializer.SimpleGrantedAuthorityDeserializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Redis的配置类
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object>
    redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String,
                Object>();
        template.setConnectionFactory(redisConnectionFactory);

        //创建Redis的序列化器
        StringRedisSerializer stringRedisSerializer = new
                StringRedisSerializer();

        //创建Redis的序列化器
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer(Object.class);


        /////////////////////////////////////////////////////////////////////
        // 例如将一个person对象保存到redis中. 其中key是scott, value是person对象的json格式的数据
        // redisUtil.set("scott",person对象);


        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);

        // value序列化方式采用jackson
        // 在使用Redis保存值的时候, 将对象转为JSON格式的字符串保存到Redis中
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        // 注册模块, 指定SimpleGrantedAuthority对象的反序列化器是 new SimpleGrantedAuthorityDeserializer()
        om.registerModule(new SimpleModule().addDeserializer(SimpleGrantedAuthority.class, new SimpleGrantedAuthorityDeserializer()));


        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);


        ////////////////////////////////////////////////////////////////////
        // redis中保存数据, key是字符串, 值是map对象
        // redisUtil.hmset("scott",map对象);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        ///////////////////////////////////////////////////////////////////
        template.afterPropertiesSet();
        return template;
    }
}
