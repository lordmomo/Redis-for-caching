package com.momo.redisandmysqlcache.service.impl.repository;

import com.momo.redisandmysqlcache.service.interfaces.RateLimiterService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RateLimiterServiceImpl implements RateLimiterService {

    private final RedisTemplate<String,Object> redisTemplate;

    public RateLimiterServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean allowRequest(int key, int maxRequests, int timeWindowSeconds ){
        String redisKey = "rateLimit:"+key;
        long currentRequests = redisTemplate.opsForValue().increment(redisKey,1);
        if(currentRequests == 1){
            redisTemplate.expire(redisKey,timeWindowSeconds, TimeUnit.SECONDS);
        }
        return currentRequests <= maxRequests;
    }
}
