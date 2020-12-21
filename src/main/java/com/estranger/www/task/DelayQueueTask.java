package com.estranger.www.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.estranger.www.common.contants.ConstantsValue;
import com.estranger.www.model.Task;
import com.estranger.www.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * Created by：Estranger
 * Description：延时队列定时任务
 * publicQueue每隔十秒往zSet结构中塞入一个不重复的uuid，设置当前时间延迟15秒的
 * Date：2020/12/1 15:46
 */
@Component
public class DelayQueueTask {

    private static Logger log = LoggerFactory.getLogger(DelayQueueTask.class);

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    ThreadPoolTaskExecutor queueThreadPool;

    @Scheduled(cron = "0/5 * * * * *")
    public void consumeQueue(){
        String key = ConstantsValue.REDIS_KEY.DELAY_QUEUE;
        Set<Object> queueSet =  redisUtils.zrangeByScore(key, 0, System.currentTimeMillis());
        if(!CollectionUtils.isEmpty(queueSet)){
            try {
                for (Object queueValue:
                     queueSet) {
                    queueThreadPool.execute(() -> {
                        if(redisUtils.zremrange(key,String.valueOf(queueValue)) > 0){//删除成功，相当于出队列
                            doSomething(String.valueOf(queueValue));
                        }
                    });
                }
            }catch (Exception e){
                log.error("queueThreadPool线程池异常："+e.getMessage(),e);
            }
        }
    }

    private void doSomething(String queueValue) {
        Task task = JSON.parseObject(queueValue,Task.class);
        System.out.println(task.toString());
    }

}
