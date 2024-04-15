package com.example.common.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

 /**
 * Redis工具类
 *
 */
@Component
public class RedisUtil {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    private static final String lua =
            "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1])"
            +"else return 0 end";

    public long increment(String key){
       return redisTemplate.opsForValue().increment(key);
    }

    public boolean execute(String lockName,String uuid){
        long result = redisTemplate.execute(
                new DefaultRedisScript<Long>(lua,Long.class),
                Arrays.asList(lockName),
                uuid
        ).longValue();
        return result==1;
    }

    /**
     * 一般用于分布式锁: 保存的key和value是锁的信息。
     *判断key是否存在,如果key不存在,才能向redis中保存key value。保存成功true 保存失败false
     *
     * 假设key:  lock_key
     * 同一时间有多个请求访问setIfAbsent函数,第一个请求调用setIfAbsent函数时,lock_key是不存在的,所以
     * 第一个请求保存key和value是成功的,也就是第一个请求获得了这把锁, 第一个请求去处理他的业务。
     *
     * 在第一个请求处理业务的工程中,其他请求调用setIfAbsent函数,因为第一个请求已经保存过lock_key和 对应的value
     * 了,其他请求就不能在保存lock_key。其他请求获得锁失败了,其他的请求在这不停的向redis中保存lock_key和value,
     * 直到第一个请求处理完业务,释放锁（从redis中删除lock_key）。
     *
     * 其他请求中,有一个请求能够保存lock_key和 对应的value,说明获得锁。该请求退出循环,处理自己的业务。
     * 在保存lock_key和value时,都要指定过期时间。因为有可能,其他请求在处理业务时,出现异常。没有将锁释放掉。
     *
     * @param key
     * @param value
     * @param times
     * @param timeUnit
     * @return
     */
    public boolean setIfAbsent(String key,Object value, long times,TimeUnit timeUnit){
        return  redisTemplate.opsForValue().setIfAbsent(key,value,times,timeUnit);
    }


    public  void set(String key,Object value,long times,TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key,value,times,timeUnit);
    }

    public  void set(String key,Object value){
        redisTemplate.opsForValue().set(key,value);
    }

    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }


    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    //判断hash类型中是否有元素
    public boolean hasKey(String key,Object hk){
        return redisTemplate.opsForHash().hasKey(key,hk);
    }

    //获得hash类型中的value值
    public Object get(String key,Object hk){
        return redisTemplate.opsForHash().get(key,hk);
    }

    //保存hash类型的数据
    public void putKey(String key,Object hk,Object hv){
         redisTemplate.opsForHash().put(key,hk,hv);
    }

    //根据key获得hash类型的数据
    public Map<Object,Object>  getHash(String key){
       return  redisTemplate.opsForHash().entries(key);
    }

    public boolean delete(String key,String hkey){
        return redisTemplate.opsForHash().delete(key,hkey)>0;
    }

    public boolean delete(String key){
        return redisTemplate.delete(key);
    }

}