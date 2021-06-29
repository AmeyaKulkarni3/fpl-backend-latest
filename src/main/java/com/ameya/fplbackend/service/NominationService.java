package com.ameya.fplbackend.service;

import java.util.List;

import com.ameya.fplbackend.dto.NominationDto;
import com.ameya.fplbackend.dto.PlayerNominationDto;

public interface NominationService {
	
	NominationDto createNomination(NominationDto nomination);
	
	List<PlayerNominationDto> getPlayerNominations(String playerId);
	
	NominationDto updateNomination(NominationDto nomination);

}
