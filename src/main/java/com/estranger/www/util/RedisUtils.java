package com.estranger.www.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by：Estranger
 * Description：操作redis工具类
 * Date：2020/5/26 18:00
 */
@Component
public class RedisUtils {


    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //删除key
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    //设置一个string
    public void setStringValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object getStringValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    //设置一个hash
    public void setHashValue(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public Object getHashValue(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    //判断key是否存在
    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    //判断hashKey是否存在
    public boolean hasHashKey(String key,String hashKey){
        return redisTemplate.opsForHash().hasKey(key,hashKey);
    }

    //设置key的有效期
    public void expire(String key,Long timeout){
        redisTemplate.expire(key,timeout,TimeUnit.HOURS);
    }

    //setNx
    public Boolean setNx(String key, Object value,Long timeout,TimeUnit timeUnit){
        return redisTemplate.opsForValue().setIfAbsent(key,value,timeout,timeUnit);
    }

    public void eval(DefaultRedisScript redisScript, String key, String value){
        RedisSerializer<String> argsSerializer = new StringRedisSerializer();
        RedisSerializer<String> resultSerializer = new StringRedisSerializer();
        redisTemplate.execute(redisScript,argsSerializer,resultSerializer, Collections.singletonList(key),Collections.singletonList(value));
    }

    public String eval(RedisScript redisScript, String key, String value, Long time){
        List<String> keys = Collections.singletonList(key);
        RedisSerializer<String> argsSerializer = new StringRedisSerializer();
        RedisSerializer<String> resultSerializer = new StringRedisSerializer();
        return redisTemplate.execute(redisScript, argsSerializer,resultSerializer, keys,value,String.valueOf(time));
    }
}
