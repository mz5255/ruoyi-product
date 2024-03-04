package com.ruoyi.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 马震
 * @version 1.0
 * @date 2024/3/1 16:26
 */
@Configuration
public class RedisConfig {
    @Autowired
    RedisTemplate redisTemplate;
    /**
     * redis缓存
     * @param name  键的名字
     * @param list  存的集合
     *              31分钟过期
     * @return
     */
    public List redistCache(String name, List list){
        if(redisTemplate.hasKey(name)){
            list =  redisTemplate.opsForList().range(name,0,-1);
        }else{
            redisTemplate.opsForList().rightPushAll(name,list);
            redisTemplate.expire(name,31, TimeUnit.MINUTES);
        }
        return list;
    }
}
