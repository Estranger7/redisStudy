package com.estranger.www.service.impl;

import com.estranger.www.service.DistributeService;
import com.estranger.www.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

/**
 * Created by：Estranger
 * Description：这个类演示了，当业务逻辑执行时间过长时，key已经过期，但业务逻辑没执行完，此时锁被别的线程获取到了，
 * 导致业务逻辑不能严格串行执行，违背了分布式锁的理念。
 * Date：2020/9/11 16:09
 */
@Service("DistributeService")
public class DistributeServiceImpl implements DistributeService {

    private static Logger logger = LoggerFactory.getLogger(DistributeServiceImpl.class);

    @Autowired
    private RedisUtils redisUtils;


    /**
     * Author：Estranger
     * Description：
     * 执行顺序：调用此方法，打印了xxx线程获取到了锁，打印了失效的key为：xxx时，再调用一次。此时锁就会被别的线程获取到，
     * 但是线程1还在执行业务逻辑。所以，当业务逻辑时间较长时，不建议使用setnx
     * Date：2020/9/14
     */
    @Override
    public void expireKey() {
        if(redisUtils.setNx("testExpire","1",Long.valueOf(5), TimeUnit.SECONDS)){
            logger.info(Thread.currentThread().getName() + "获取到了锁");
            doSomeThing();
        }
    }

    private void doSomeThing() {
        try {
            Thread.sleep(10*1000);
            System.out.println(Thread.currentThread().getName() + "我是业务逻辑");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
