package com.ameya.fplbackend.service;

import java.time.LocalDate;
import java.util.List;

import com.ameya.fplbackend.dto.MatchDto;

public interface MatchService {
	
	MatchDto getMatch(LocalDate date);
	
	List<MatchDto> getAllMatches();
	
	MatchDto updateMatchNomination(MatchDto match);
	
	MatchDto getMatchById(long id);

}
