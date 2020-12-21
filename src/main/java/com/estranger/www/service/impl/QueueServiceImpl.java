package com.estranger.www.service.impl;

import com.alibaba.fastjson.JSON;
import com.estranger.www.common.contants.ConstantsValue;
import com.estranger.www.model.Task;
import com.estranger.www.service.QueueService;
import com.estranger.www.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by：Estranger
 * Description：
 * Date：2020/12/1 16:17
 */
@Service("QueueService")
public class QueueServiceImpl implements QueueService{

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void delay() {
        Task task = new Task();
        String value;
        for(int i=0;i<10;i++){
            int num = i + 1;
            task.setId(UUID.randomUUID().toString());
            task.setMsg("第" + num + "个msg");
            value = JSON.toJSONString(task);
            redisUtils.zadd(ConstantsValue.REDIS_KEY.DELAY_QUEUE,value ,System.currentTimeMillis() + 10000);
        }
    }
}
