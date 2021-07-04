package com.ameya.fplbackend.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ameya.fplbackend.dto.MatchDto;
import com.ameya.fplbackend.dto.MatchNominationDto;
import com.ameya.fplbackend.dto.MatchPlayerDto;
import com.ameya.fplbackend.entity.MatchEntity;
import com.ameya.fplbackend.entity.NominationEntity;
import com.ameya.fplbackend.entity.PlayerEntity;
import com.ameya.fplbackend.exception.RecordExistsException;
import com.ameya.fplbackend.exception.ResourceNotFoundException;
import com.ameya.fplbackend.repository.MatchRepository;
import com.ameya.fplbackend.service.MatchService;
import com.ameya.fplbackend.service.PlayerService;

@Service
@Transactional
public class MatchServiceImpl implements MatchService {
	
	@Autowired
	MatchRepository matchRepository;
	
	@Autowired
	PlayerService playerService;

	@Override
	public MatchDto getMatch(LocalDate date) {
		
		MatchEntity matchEntity = matchRepository.findByMatchDate(date);
		
		if(matchEntity == null) throw new ResourceNotFoundException(date.toString());
		
		MatchDto matchDto = new MatchDto();
		matchDto.setId(matchEntity.getId());
		matchDto.setMatchDate(matchEntity.getMatchDate());
		matchDto.setMatchNumber(matchEntity.getMatchNumber());
		matchDto.setMatchTime(matchEntity.getMatchTime());
		matchDto.setMatchVenue(matchEntity.getMatchVenue());
		matchDto.setResult(matchEntity.getResult());
		matchDto.setTeam1(matchEntity.getTeam1());
		matchDto.setTeam2(matchEntity.getTeam2());
		matchDto.setTeam1Count(matchEntity.getTeam1Count());
		matchDto.setTeam2Count(matchEntity.getTeam2Count());
		matchDto.setNoNomination(matchEntity.getNoNomination());
		List<MatchNominationDto> nominations = new ArrayList<>();
		List<NominationEntity> entity = matchEntity.getNominations();
		for(NominationEntity n : entity) {
			PlayerEntity player = n.getPlayer();
			
			MatchPlayerDto matchPlayerDto = new MatchPlayerDto();
			
			matchPlayerDto.setId(player.getId());
			matchPlayerDto.setName(player.getName());
			matchPlayerDto.setPlayerId(player.getPlayerId());
			matchPlayerDto.setPoints(player.getPoints());
			
			MatchNominationDto dto = new MatchNominationDto();
			
			dto.setNominationId(n.getNominationId());
			dto.setNomination(n.getNomination());
			dto.setMatchPlayerDto(matchPlayerDto);
			nominations.add(dto);
		}
		matchDto.setNominations(nominations);
		
		return matchDto;
	}

	@Override
	public List<MatchDto> getAllMatches() {
		Iterable<MatchEntity> matches = matchRepository.findAll();
		
		if(matches == null) throw new ResourceNotFoundException("Matches Not Found");
		
		List<MatchDto> returnValue = new ArrayList<>();
		for(MatchEntity match : matches) {
			MatchDto newMatch = new MatchDto();
			List<NominationEntity> nominations = match.getNominations();
			List<MatchNominationDto> matchNominationDtos = new ArrayList<>();
			for(NominationEntity entity : nominations) {
				MatchPlayerDto matchPlayerDto = new MatchPlayerDto();
				PlayerEntity player = entity.getPlayer();
				matchPlayerDto.setId(player.getId());
				matchPlayerDto.setName(player.getName());
				matchPlayerDto.setPlayerId(player.getPlayerId());
				matchPlayerDto.setPoints(player.getPoints());
				
				MatchNominationDto matchNominationDto = new MatchNominationDto();
				
				matchNominationDto.setNominationId(entity.getNominationId());
				matchNominationDto.setNomination(entity.getNomination());
				matchNominationDto.setMatchPlayerDto(matchPlayerDto);
				matchNominationDtos.add(matchNominationDto);			
			}
			newMatch.setId(match.getId());
			newMatch.setMatchDate(match.getMatchDate());
			newMatch.setMatchNumber(match.getMatchNumber());
			newMatch.setMatchTime(match.getMatchTime());
			newMatch.setMatchVenue(match.getMatchVenue());
			newMatch.setNominations(matchNominationDtos);
			newMatch.setResult(match.getResult());
			newMatch.setTeam1(match.getTeam1());
			newMatch.setTeam2(match.getTeam2());
			newMatch.setTeam1Count(match.getTeam1Count());
			newMatch.setTeam2Count(match.getTeam2Count());
			newMatch.setNoNomination(match.getNoNomination());

			returnValue.add(newMatch);
		}
		
		return returnValue;
	}

	@Override
	public MatchDto updateMatchNomination(MatchDto match) {
		
		MatchEntity matchEntity = matchRepository.findById(match.getId());
		
		if(matchEntity == null) throw new ResourceNotFoundException("Matches Not Found");
		
		ModelMapper modelMapper = new ModelMapper();
		matchEntity = modelMapper.map(match, MatchEntity.class);
		
		MatchEntity newMatch =  matchRepository.save(matchEntity);
		
		MatchDto returnValue = modelMapper.map(newMatch, MatchDto.class);
		
		return returnValue;
	}

	@Override
	public MatchDto getMatchById(long id) {
		
		MatchEntity match = matchRepository.findById(id);
		
		MatchDto matchDto = new MatchDto();
		matchDto.setId(match.getId());
		matchDto.setMatchDate(match.getMatchDate());
		matchDto.setMatchNumber(match.getMatchNumber());
		matchDto.setMatchTime(match.getMatchTime());
		matchDto.setMatchVenue(match.getMatchVenue());
		matchDto.setResult(match.getResult());
		matchDto.setTeam1(match.getTeam1());
		matchDto.setTeam2(match.getTeam2());
		matchDto.setTeam1Count(match.getTeam1Count());
		matchDto.setTeam2Count(match.getTeam2Count());
		matchDto.setNoNomination(match.getNoNomination());		
		List<MatchNominationDto> nominations = new ArrayList<>();
		List<NominationEntity> entity = match.getNominations();
		for(NominationEntity n : entity) {
			PlayerEntity player = n.getPlayer();
			
			MatchPlayerDto matchPlayerDto = new MatchPlayerDto();
			
			matchPlayerDto.setId(player.getId());
			matchPlayerDto.setName(player.getName());
			matchPlayerDto.setPlayerId(player.getPlayerId());
			matchPlayerDto.setPoints(player.getPoints());
			
			MatchNominationDto dto = new MatchNominationDto();
			
			dto.setNominationId(n.getNominationId());
			dto.setNomination(n.getNomination());
			dto.setMatchPlayerDto(matchPlayerDto);
			nominations.add(dto);
		}
		matchDto.setNominations(nominations);
		
		return matchDto;
	}

	@Override
	public MatchDto updateResult(long id, String result) {
		
		MatchEntity matchEntity = matchRepository.findById(id);
		
		matchEntity.setResult(result);
		
		MatchEntity updatedMatch = matchRepository.save(matchEntity);
		
		ModelMapper modelMapper = new ModelMapper();
		
		MatchDto matchDto = modelMapper.map(updatedMatch, MatchDto.class);
		
		playerService.updatePoints(id);
		
		return matchDto;
	}

	@Override
	public void updateTeamCount(LocalDate date) {

		MatchEntity matchEntity = matchRepository.findByMatchDate(date);
		
		if(matchEntity.getTeam1Count() != 0 || matchEntity.getTeam2Count() != 0 || matchEntity.getNoNomination() != 0) {
			throw new RecordExistsException("Record Already Exists");
		}
		
		List<NominationEntity> nominations = matchEntity.getNominations();
		
		String team1 = matchEntity.getTeam1();
		String team2 = matchEntity.getTeam2();
		
		int team1Count = 0;
		int team2Count = 0;
		int noNomination = 0;
		for(NominationEntity nomination : nominations) {
			if(nomination.getNomination().equals(team1)) {
				team1Count++;
			} else if(nomination.getNomination().equals(team2)) {
				team2Count++;
			} else {
				noNomination++;
			}
		}
		
		matchEntity.setTeam1Count(team1Count);
		matchEntity.setTeam2Count(team2Count);
		matchEntity.setNoNomination(noNomination);
		
		matchRepository.save(matchEntity);
	}

}
