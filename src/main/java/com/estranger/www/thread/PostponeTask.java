package com.estranger.www.thread;

import com.estranger.www.service.impl.DistributeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * Created by：Estranger
 * Description：延时
 * Date：2020/9/14 15:40
 */
public class PostponeTask implements Runnable{

    private static Logger logger = LoggerFactory.getLogger(PostponeTask.class);

    private String key;

    private String value;

    private Long expireTime;

    private TimeUnit timeUnit;

    private Boolean isRunning;

    private DistributedLock distributedLock;

    public PostponeTask(String key, String value, Long expireTime, TimeUnit timeUnit,DistributedLock distributedLock) {
        this.key = key;
        this.value = value;
        this.expireTime = expireTime;
        this.timeUnit = timeUnit;
        this.isRunning = true;
        this.distributedLock = distributedLock;
    }

    @Override
    public void run() {
        long waitTime = expireTime * 2/3;
        while(isRunning){
            try {
                Thread.sleep(waitTime*1000);
                //延时
                if(distributedLock.expandTime(key,value,expireTime)){
                    logger.info("------- 线程{}" + Thread.currentThread().getName() +" 延时成功 -------");
                }else{
                    stop();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void stop() {
        this.isRunning = Boolean.FALSE;
    }
}
