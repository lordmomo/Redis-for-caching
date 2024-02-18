package com.momo.redisandmysqlcache.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class LeaderboardRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ZSetOperations<String,Object> zSetOperations;

    @Autowired
    public LeaderboardRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.zSetOperations = redisTemplate.opsForZSet();
    }

    public void addScore(int userId, Double score){

        zSetOperations.add("leaderboard",userId,score);
    }

    public  Double getScore(int userId) {
        return zSetOperations.score("leaderboard",userId);
    }

    public Long getPosition(int userId) {
        return zSetOperations.reverseRank("leaderboard",userId) + 1;
    }

    public Set<ZSetOperations.TypedTuple<Object>> getTopPlayers(int rankRange) {
        return zSetOperations.reverseRangeWithScores("leaderboard",0,rankRange-1);
    }
}
