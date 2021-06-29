package com.ameya.fplbackend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ameya.fplbackend.entity.PlayerEntity;

@Repository
public interface PlayerRepository extends CrudRepository<PlayerEntity, Long> {
	
	PlayerEntity findByName(String name);
	PlayerEntity findByPlayerId(String id);
	PlayerEntity findById(long id);

}
