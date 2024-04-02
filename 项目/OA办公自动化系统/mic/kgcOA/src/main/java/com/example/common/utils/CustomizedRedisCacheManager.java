package com.example.common.utils;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.time.Duration;

/**
 * @Author: zhonger250
 * @Date: 2024/4/1 14:20
 * @Description:
 */
public class CustomizedRedisCacheManager extends RedisCacheManager {
    public CustomizedRedisCacheManager(RedisCacheWriter cacheWriter,
                                       RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }
//    @CacheEvict(value = "system:systemMenu#-1", key = "'list'")
    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        // {"system:systemMenu","-1"}
        String[] array = name.split("#");
        if (array.length>1){
            // 数组的第二个元素是过期时间
            int ttl = Integer.parseInt(array[1]);
            cacheConfig =   cacheConfig.entryTtl(Duration.ofMinutes(ttl));
            // 数组的第一个元素是缓存的名字
            name=array[0];
        }

        return super.createRedisCache(name, cacheConfig);
    }
}
