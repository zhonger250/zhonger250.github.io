package com.example.common.config;

import com.example.common.utils.CustomizedRedisCacheManager;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author: zhonger250
 * @Date: 2024/3/29 16:48
 * @Description:
 */
@Configuration
@EnableCaching
@EnableConfigurationProperties(CacheProperties.class)

public class SpringCacheConfig {
    @Bean
    RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties){
        RedisCacheConfiguration configuration=
                RedisCacheConfiguration.defaultCacheConfig();
        //创建Redis的序列化器
        StringRedisSerializer stringRedisSerializer = new
                StringRedisSerializer();
        //创建Redis的序列化器
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer(Object.class);
        // value序列化方式采用jackson
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        configuration=configuration
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer));
        configuration=configuration
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
        CacheProperties.Redis redis= cacheProperties.getRedis();
        if(redis.getTimeToLive()!=null){
            configuration=configuration.entryTtl(redis.getTimeToLive());
        }
        if(redis.getKeyPrefix()!=null){
            configuration=configuration.prefixKeysWith(redis.getKeyPrefix());
        }
        if(!redis.isCacheNullValues()){
            configuration=configuration.disableCachingNullValues();
        }
        if(!redis.isUseKeyPrefix()){
            configuration=configuration.disableKeyPrefix();
        }
        return configuration;
    }

    /**
     * 通过spring给属性赋值
     * 1.set方法
     * 2.构造方法
     * 3.实现接口方式
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory,
                                     RedisCacheConfiguration redisCacheConfiguration){
        CustomizedRedisCacheManager redisCacheManager = new CustomizedRedisCacheManager(
                RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),redisCacheConfiguration
        );
        return redisCacheManager;
    }

}
