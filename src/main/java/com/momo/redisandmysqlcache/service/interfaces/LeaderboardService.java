package com.momo.redisandmysqlcache.service.interfaces;


import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

public interface LeaderboardService {
    void addScore(int userId, double score);

    Double getScore(int userId);

    Long getPosition(int userId);

    Set<ZSetOperations.TypedTuple<Object>> getTopPlayers(int rankRange);
}
