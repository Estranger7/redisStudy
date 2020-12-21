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
