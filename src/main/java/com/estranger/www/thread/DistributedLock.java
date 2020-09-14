package com.estranger.www.thread;

import com.estranger.www.service.impl.DelayServiceImpl;
import com.estranger.www.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by：Estranger
 * Description：失效key守护线程
 * Date：2020/9/14 15:26
 */
@Component
public class DistributedLock {

    private static Logger logger = LoggerFactory.getLogger(DelayServiceImpl.class);

    // 延时脚本
    private static final String POSTPONE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('expire', KEYS[1], ARGV[2]) else return '0' end";

    private static final String RELEASE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    @Autowired
    private RedisUtils redisUtils;

    public Boolean Lock(String key, String value, Long time, TimeUnit timeUnit) {
        Boolean nxFlag = redisUtils.setNx(key, value, time, timeUnit);
        if (nxFlag) {
            logger.info("------- 线程{}" + Thread.currentThread().getName() +"加锁成功 -------");
            PostponeTask postponeTask = new PostponeTask(key, value, time, timeUnit,this);
            Thread thread = new Thread(postponeTask);
            thread.setDaemon(true);
            thread.start();
        }
        return nxFlag;

    }

    public Boolean expandTime(String key, String value, Long time){
        logger.info("进入延时方法");
        RedisScript redisScript = RedisScript.of(POSTPONE_LOCK_SCRIPT,String.class);
//        redisScript.setLocation(new ClassPathResource("/script/expandLock.lua"));
        Boolean aBoolean = Boolean.valueOf(redisUtils.eval(redisScript, key, value, time));
        return aBoolean;
    }

    public void unLock(String delayKey, String value) {
        DefaultRedisScript redisScript = new DefaultRedisScript();
        redisScript.setResultType(List.class);
        redisScript.setLocation(new ClassPathResource("/script/releaseLock.lua"));
        redisUtils.eval(redisScript,delayKey,value);
        logger.info("------- 解锁了 -------");
    }
}
