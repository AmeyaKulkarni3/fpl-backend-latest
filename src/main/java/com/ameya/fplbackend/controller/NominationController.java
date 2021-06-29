package com.ameya.fplbackend.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ameya.fplbackend.dto.NominationDto;
import com.ameya.fplbackend.dto.PlayerDto;
import com.ameya.fplbackend.dto.PlayerMatchDto;
import com.ameya.fplbackend.dto.PlayerNominationDto;
import com.ameya.fplbackend.model.request.NominationRequestModel;
import com.ameya.fplbackend.model.response.NominationRest;
import com.ameya.fplbackend.model.response.PlayerMatchRest;
import com.ameya.fplbackend.model.response.PlayerNominationRest;
import com.ameya.fplbackend.service.MatchService;
import com.ameya.fplbackend.service.NominationService;
import com.ameya.fplbackend.service.PlayerService;

@RestController
@RequestMapping
public class NominationController {
	
	@Autowired
	NominationService nominationService;
	
	@Autowired
	MatchService matchService;
	
	@Autowired
	PlayerService playerService;
	
	@PostMapping("players/{id}/nominations/create")
	public NominationRest createNomination(@RequestBody NominationRequestModel nomination, @PathVariable String id) {
		
		PlayerDto playerDto = playerService.getPlayerByPlayerId(id);
				
		NominationDto nominationDto = new NominationDto();
		nominationDto.setMatchId(nomination.getMatchId());
		nominationDto.setNomination(nomination.getNomination());
		nominationDto.setPlayerId(playerDto.getId());		
		
		NominationDto createdNomination = nominationService.createNomination(nominationDto);
		
		NominationRest returnValue = new NominationRest();
		
		returnValue.setNomination(createdNomination.getNomination());
		returnValue.setPlayerId(createdNomination.getPlayerId());
		returnValue.setMatchId(createdNomination.getMatchId());
		
		return returnValue ;
	}
	
	@GetMapping("players/{id}/nominations")
	public List<PlayerNominationRest> getPlayerNominations(@PathVariable String id) {
		
		List<PlayerNominationRest> returnValue = new ArrayList<>();
		
		List<PlayerNominationDto> nominationsDto = nominationService.getPlayerNominations(id);
		
		ModelMapper modelMapper = new ModelMapper();
		
		for(PlayerNominationDto dto : nominationsDto) {
			PlayerMatchDto playerMatchDto = dto.getPlayerMatchDto();
			PlayerNominationRest rest = new PlayerNominationRest();
			PlayerMatchRest playerMatchRest = modelMapper.map(playerMatchDto, PlayerMatchRest.class);
			rest.setNomination(dto.getNomination());
			rest.setPlayerMatchRest(playerMatchRest);
			returnValue.add(rest);
		}
		
		return returnValue;
		
	}
	
	@PutMapping("players/{id}/nominations")
	public NominationRest updatePlayerNomination(@PathVariable String  id, @RequestBody NominationRequestModel nomination) {
		
		PlayerDto playerDto = playerService.getPlayerByPlayerId(id);
		
		NominationDto nominationDto = new NominationDto();
		nominationDto.setMatchId(nomination.getMatchId());
		nominationDto.setNomination(nomination.getNomination());
		nominationDto.setPlayerId(playerDto.getId());		
		
		NominationDto createdNomination = nominationService.updateNomination(nominationDto);
		
		NominationRest returnValue = new NominationRest();
		
		returnValue.setNomination(createdNomination.getNomination());
		returnValue.setPlayerId(createdNomination.getPlayerId());
		returnValue.setMatchId(createdNomination.getMatchId());
		
		return returnValue ;
		
		
	}

}
