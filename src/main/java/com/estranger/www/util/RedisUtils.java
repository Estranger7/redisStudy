package com.estranger.www.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by：Estranger
 * Description：操作redis工具类
 * Date：2020/5/26 18:00
 */
@Component
public class RedisUtils {

   @Autowired
   private RedisTemplate<String,String> stringRedisTemplate;

   @Autowired
   private RedisTemplate<String,Object> redisTemplate;




    //删除key
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    //设置一个string
    public void setStringValue(String key, String value) {
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
    public void expire(String key,Long timeout,TimeUnit timeUnit){
        redisTemplate.expire(key,timeout,timeUnit);
    }


    //设置一个zset
    public void zadd(String key,String value,long score){
        redisTemplate.opsForZSet().add(key,value,score);
    }


    //移除某一个范围内的zset
    public void zremrangeByScore(String key,long start,long end){
        redisTemplate.opsForZSet().removeRangeByScore(key,start,end);
    }

    //移除某个特定值
    public long zremrange(String key,String value){
        return  redisTemplate.opsForZSet().remove(key,value);
    }

    //获取某个范围内的集合
    public Set<Object> zrangeByScore(String key, long start, long end){
        return redisTemplate.opsForZSet().rangeByScore(key,start,end);
    }

    //获取zset的大小
    public Long zcard(String key){
        return  redisTemplate.opsForZSet().zCard(key);
    }

    //setNx
    public Boolean setNx(String key, String value,Long timeout,TimeUnit timeUnit){
        return stringRedisTemplate.opsForValue().setIfAbsent(key,value,timeout,timeUnit);
    }

    public Long eval(RedisScript redisScript, String key, String value){
        return (Long)stringRedisTemplate.execute(redisScript, Collections.singletonList(key),value);
    }

    public String eval(RedisScript redisScript, String key, String value, Long time){
        List<String> keys = Collections.singletonList(key);
        return (String) stringRedisTemplate.execute(redisScript, keys,value,String.valueOf(time));
    }

}
