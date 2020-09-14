//package com.estranger.www.listener;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.stereotype.Component;
//
///**
// * Created by：Estranger
// * Description：redis
// * Date：2020/9/11 16:29
// */
//@Component
//public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
//    private final static Logger LOGGER = LoggerFactory.getLogger(RedisKeyExpirationListener.class);
//
//
//    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
//        super(listenerContainer);
//    }
//
//    /**
//     * 针对redis数据失效事件，进行数据处理
//     * @param message
//     * @param pattern
//     */
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        // message.toString()可以获取失效的key
//        String expiredKey = message.toString();
//        LOGGER.info("失效的key为："+ expiredKey);
//    }
//}
