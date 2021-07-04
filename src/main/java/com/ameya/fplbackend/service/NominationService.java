package com.ameya.fplbackend.service;

import java.util.List;

import com.ameya.fplbackend.dto.MatchNominationDto;
import com.ameya.fplbackend.dto.NominationDto;
import com.ameya.fplbackend.dto.PlayerNominationDto;
import com.ameya.fplbackend.entity.PlayerEntity;

public interface NominationService {
	
	NominationDto createNomination(NominationDto nomination);
	
	List<PlayerNominationDto> getPlayerNominations(String playerId);
	
	NominationDto updateNomination(NominationDto nomination);
	
	void deleteByNominationId(long id);
	
	void deleteAllById(PlayerEntity player);
	
	List<MatchNominationDto> getMatchNominations(long id);

}
