package com.momo.redisandmysqlcache.controller;

import com.momo.redisandmysqlcache.service.interfaces.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    @Autowired
    LeaderboardService leaderboardService;

    @PostMapping("/add-score")
    public void addScore(@RequestParam int userId, @RequestParam double score){
        leaderboardService.addScore(userId,score);
    }

    @GetMapping("/get-user-score/{userId}")
    public Double getScore(@PathVariable("userId") int userId){
        return leaderboardService.getScore(userId);
    }

    @GetMapping("/get-user-position/{userId}")
    public  Long getPosition(@PathVariable("userId") int userId){
        return leaderboardService.getPosition(userId);
    }

    @GetMapping("/get-top-players/{rankRange}")
    public Set<ZSetOperations.TypedTuple<Object>> getTopPlayers(@PathVariable("rankRange") int rankRange){
       return leaderboardService.getTopPlayers(rankRange);
    }
}
