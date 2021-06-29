package com.ameya.fplbackend.repository;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ameya.fplbackend.entity.MatchEntity;

@Repository
public interface MatchRepository extends CrudRepository<MatchEntity, Long> {
	
	MatchEntity findByMatchDate(LocalDate date);
	
	MatchEntity findById(long id);

}
