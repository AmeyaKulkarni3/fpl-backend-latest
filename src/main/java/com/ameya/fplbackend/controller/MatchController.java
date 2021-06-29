package com.ameya.fplbackend.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ameya.fplbackend.dto.MatchDto;
import com.ameya.fplbackend.dto.MatchNominationDto;
import com.ameya.fplbackend.dto.MatchPlayerDto;
import com.ameya.fplbackend.entity.MatchEntity;
import com.ameya.fplbackend.model.response.MatchNominationRest;
import com.ameya.fplbackend.model.response.MatchPlayerRest;
import com.ameya.fplbackend.model.response.MatchRest;
import com.ameya.fplbackend.model.response.MatchScheduleRest;
import com.ameya.fplbackend.repository.MatchRepository;
import com.ameya.fplbackend.service.MatchService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/matches")
public class MatchController {
	
	@Autowired
	private MatchRepository matchRepository;
	
	@Autowired
	private MatchService matchService;
	
	@GetMapping
	public List<MatchRest> getAllMatches(@RequestHeader("Authorization") String authorization){
		System.out.print(authorization);
		
		List<MatchDto> matchDtos = matchService.getAllMatches();
		
		List<MatchRest> returnValue = new ArrayList<>();
		for(MatchDto dto: matchDtos) {
			List<MatchNominationDto> nominationDto = dto.getNominations();
			List<MatchNominationRest> matchNominationRest = new ArrayList<>();
			for(MatchNominationDto matchNominationDto : nominationDto) {
				MatchPlayerDto playerDto = matchNominationDto.getMatchPlayerDto();
				MatchPlayerRest rest = new MatchPlayerRest();
				rest.setPlayerId(playerDto.getPlayerId());
				rest.setName(playerDto.getName());
				rest.setPoints(playerDto.getPoints());
				MatchNominationRest nominationRest = new MatchNominationRest();
				nominationRest.setMatchPlayerRest(rest);
				nominationRest.setNomination(matchNominationDto.getNomination());
				matchNominationRest.add(nominationRest);
			}
			MatchRest matchRest = new MatchRest();
			matchRest.setMatchNumber(dto.getMatchNumber());
			matchRest.setMatchDate(dto.getMatchDate());
			matchRest.setMatchTime(dto.getMatchTime());
			matchRest.setMatchVenue(dto.getMatchVenue());
			matchRest.setResult(dto.getResult());
			matchRest.setTeam1(dto.getTeam1());
			matchRest.setTeam2(dto.getTeam2());
			matchRest.setMatchNominationRest(matchNominationRest);
			returnValue.add(matchRest);
		}
		
		return returnValue;
	}
	
	@PostMapping
	public MatchEntity addMatch(@RequestBody MatchEntity match) {
		return matchRepository.save(match);
	}
	

	@GetMapping("/date/{date}")
	public MatchRest getMatch(@PathVariable String date) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
		
		LocalDate matchDate = LocalDate.parse(date,formatter);
		
		MatchDto matchDto = matchService.getMatch(matchDate);
		
		List<MatchNominationDto> nominationsDto = matchDto.getNominations();
		List<MatchNominationRest> nominationsRest = new ArrayList<>();
		for(MatchNominationDto dto : nominationsDto) {
			MatchPlayerDto matchPlayerDto = dto.getMatchPlayerDto();
			MatchPlayerRest matchPlayerRest = new MatchPlayerRest();
			matchPlayerRest.setPlayerId(matchPlayerDto.getPlayerId());
			matchPlayerRest.setName(matchPlayerDto.getName());
			matchPlayerRest.setPoints(matchPlayerDto.getPoints());
			MatchNominationRest rest = new MatchNominationRest();
			rest.setMatchPlayerRest(matchPlayerRest);
			rest.setNomination(dto.getNomination());
			nominationsRest.add(rest);
		}
		
		MatchRest matchRest = new MatchRest();
		matchRest.setMatchNumber(matchDto.getMatchNumber());
		matchRest.setTeam1(matchDto.getTeam1());
		matchRest.setTeam2(matchDto.getTeam2());
		matchRest.setResult(matchDto.getResult());
		matchRest.setMatchDate(matchDto.getMatchDate());
		matchRest.setMatchTime(matchDto.getMatchTime());
		matchRest.setMatchVenue(matchDto.getMatchVenue());
		matchRest.setMatchNominationRest(nominationsRest);
		
		return matchRest;
	}
	
	@RequestMapping(value = "/schedule", method = RequestMethod.GET)
	public List<MatchScheduleRest> getMatchesSchedule() {
		
		List<MatchDto> matchDtos = matchService.getAllMatches();
		
		List<MatchScheduleRest> returnValue = new ArrayList<>();
		for(MatchDto dto: matchDtos) {
			MatchScheduleRest matchRest = new MatchScheduleRest();
			matchRest.setMatchNumber(dto.getMatchNumber());
			matchRest.setMatchDate(dto.getMatchDate());
			matchRest.setMatchTime(dto.getMatchTime());
			matchRest.setMatchVenue(dto.getMatchVenue());
			matchRest.setResult(dto.getResult());
			matchRest.setTeam1(dto.getTeam1());
			matchRest.setTeam2(dto.getTeam2());
			returnValue.add(matchRest);
		}	
		return returnValue;		
	}
	
	@GetMapping("/{id}")
	public MatchRest getMatchById(@PathVariable long id) {
		
		MatchDto match = matchService.getMatchById(id);
		
		List<MatchNominationDto> nominationsDto = match.getNominations();
		List<MatchNominationRest> nominationsRest = new ArrayList<>();
		for(MatchNominationDto dto : nominationsDto) {
			MatchPlayerDto matchPlayerDto = dto.getMatchPlayerDto();
			MatchPlayerRest matchPlayerRest = new MatchPlayerRest();
			matchPlayerRest.setPlayerId(matchPlayerDto.getPlayerId());
			matchPlayerRest.setName(matchPlayerDto.getName());
			matchPlayerRest.setPoints(matchPlayerDto.getPoints());
			MatchNominationRest rest = new MatchNominationRest();
			rest.setMatchPlayerRest(matchPlayerRest);
			rest.setNomination(dto.getNomination());
			nominationsRest.add(rest);
		}
		
		MatchRest matchRest = new MatchRest();
		matchRest.setMatchNumber(match.getMatchNumber());
		matchRest.setTeam1(match.getTeam1());
		matchRest.setTeam2(match.getTeam2());
		matchRest.setResult(match.getResult());
		matchRest.setMatchDate(match.getMatchDate());
		matchRest.setMatchTime(match.getMatchTime());
		matchRest.setMatchVenue(match.getMatchVenue());
		matchRest.setMatchNominationRest(nominationsRest);
		
		return matchRest;
		
	}
	

}
