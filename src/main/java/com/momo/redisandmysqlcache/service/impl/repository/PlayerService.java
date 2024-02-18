package com.momo.redisandmysqlcache.service.impl.repository;

import com.momo.redisandmysqlcache.model.Player;
import com.momo.redisandmysqlcache.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public void addScore(int userId,String username, double score) {
        Player player = playerRepository.findById(userId).orElse(new Player());
        player.setUsername(username);
        player.setScore(player.getScore() + score);
        playerRepository.save(player);
    }

}
