package com.momo.redisandmysqlcache.service.interfaces;

public interface RateLimiterService {
    boolean allowRequest(int key, int maxRequests, int timeWindowSeconds );
}
