package com.ameya.fplbackend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ameya.fplbackend.entity.NominationEntity;

@Repository
public interface NominationRepository extends CrudRepository<NominationEntity, Long> {
	
	public List<NominationEntity> findByPlayerId(long id);
	public List<NominationEntity> findByMatchId(long id);
	public NominationEntity findByPlayerIdAndMatchId(long playerId,long matchId);
//	public List<NominationEntity> getAllNominations(String playerId);

}
