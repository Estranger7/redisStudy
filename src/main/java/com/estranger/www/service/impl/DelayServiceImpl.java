package com.estranger.www.service.impl;

import com.estranger.www.service.DelayService;
import com.estranger.www.thread.DistributedLock;
import com.estranger.www.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by：Estranger
 * Description：
 * Date：2020/9/14 15:19
 */
@Service("DelayService")
public class DelayServiceImpl implements DelayService{

    private static Logger logger = LoggerFactory.getLogger(DelayServiceImpl.class);

    public static final String delayKey = "testDelay";

    @Autowired
    private DistributedLock distributedLock;



    @Override
    public void delayKey() {
        String value = UUID.randomUUID().toString();
        //加锁
        Boolean lock = distributedLock.Lock(delayKey,value,Long.valueOf(9),TimeUnit.SECONDS);
        if(lock){
            doSomeThing();
            //释放锁
            distributedLock.unLock(delayKey,value);
        }

    }

    private void doSomeThing() {
        try {
            logger.info("------- 业务执行开始 -------");
            Thread.sleep(Long.valueOf(12*1000));
            logger.info("------- 业务执行完成了 -------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
