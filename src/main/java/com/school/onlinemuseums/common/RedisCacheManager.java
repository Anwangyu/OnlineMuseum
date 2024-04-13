package com.school.onlinemuseums.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisCacheManager {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    // 设置缓存
    public void setCache(Object key, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    // 获取缓存
    public Object getCache(Object key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 删除缓存
    public boolean deleteCache(Object key) {
        return redisTemplate.delete(key);
    }
}
