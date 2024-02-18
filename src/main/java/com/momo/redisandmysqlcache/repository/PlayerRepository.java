package com.momo.redisandmysqlcache.repository;

import com.momo.redisandmysqlcache.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Integer> {
}
