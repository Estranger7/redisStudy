# 概述
此项目旨在编写一些demo，来学习redis的一些特性


## 分布式锁超时问题
笔记待完善


## redis zset结构实现简单限流功能
测试流程：访问`MessageController`的`reply`方法    
实现思路：
- 先调用zremrangebyscore(key,0,nowTime-period*1000),移除当前时间窗口之前的行为记录，剩下的都是时间窗口内的；
- 此时再获取时间窗口内的行为数量，判断当前这次的请求是否超过限制；
- 如果没有超限制，记录行为，add一个value为当前时间戳的key、value值，并设置此key的失效时间

## zset结构实现延时队列
测试流程：访问`QueueController`的`delay`方法，往队列中塞入延时十秒的十条任务，再通过task定时任务出队列
实现思路：
- 因为没有具体的业务场景，故直接使用了调用conroller的方式作为生产者来生产消息，在zset结构中塞入一个键值，并且设置其
score为当前时间延后十秒的事件戳；
- 出队列则是通过定时任务来轮询，zrangeByScore一直取，取出到当前时间的所有消息。再通过zrem抢占cpu执行权，执行出队列，
即延时消费。
