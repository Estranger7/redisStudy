package com.estranger.www.service.impl;

import com.estranger.www.common.contants.ConstantsValue;
import com.estranger.www.service.MessageService;
import com.estranger.www.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by：Estranger
 * Description：
 * Date：2020/11/18 17:52
 */
@Service("MessageService")
public class MessageServiceImpl implements MessageService {

    //限定时间
    private final Integer period = 60;
    //限定次数
    private final Integer maxCount = 3;

    @Autowired
    private RedisUtils redisUtils;


    @Override
    public String reply(String userId) {
        String msg;
        String key = ConstantsValue.REDIS_KEY.REPLY + "_" + userId;
        String value = String.valueOf(UUID.randomUUID());
        Long nowTime = System.currentTimeMillis();

        Boolean canReply = isAllowed(key, value, nowTime, period, maxCount);
        if (canReply) {
            //执行回贴的逻辑
//            doSomeThing();
            msg = "回帖成功";
        } else {
            msg = "回帖失败，一分钟内仅允许回帖三次，请遵守规则";
        }
        return msg;
    }

    private Boolean isAllowed(String key, String value, Long nowTime, Integer period, Integer maxCount) {
        //移除当前时间窗口之前的行为
        redisUtils.zremrangeByScore(key, 0, nowTime - period * 1000);
        //获取当前zset的大小
        Integer count = (redisUtils.zcard(key)).intValue();
        Boolean replyFlag = count < maxCount;
        if (replyFlag) {
            //记录行为
            redisUtils.zadd(key, value, nowTime);
            //设置当前key的失效时间（避免冷用户一直不回复，即不会触发zremrangeByScore，导致内存占用）
            redisUtils.expire(key, Long.valueOf(period), TimeUnit.SECONDS);
        }
        return replyFlag;
    }
}
