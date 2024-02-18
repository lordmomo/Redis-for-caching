package com.momo.redisandmysqlcache.service.impl.repository;

import com.momo.redisandmysqlcache.repository.LeaderboardRepository;
import com.momo.redisandmysqlcache.service.interfaces.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class LeaderboardServiceImpl implements LeaderboardService {
    @Autowired
    LeaderboardRepository leaderboardRepository;
    @Override
    public void addScore(int userId, double score) {
        leaderboardRepository.addScore(userId,score);
    }

    @Override
    public Double getScore(int userId) {
        return leaderboardRepository.getScore(userId);
    }

    @Override
    public Long getPosition(int userId) {
        return leaderboardRepository.getPosition(userId);
    }

    @Override
    public Set<ZSetOperations.TypedTuple<Object>> getTopPlayers(int rankRange) {
        return leaderboardRepository.getTopPlayers(rankRange);
    }
}
