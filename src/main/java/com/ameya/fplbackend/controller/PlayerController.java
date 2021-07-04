package com.ameya.fplbackend.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ameya.fplbackend.dto.PlayerDto;
import com.ameya.fplbackend.dto.PlayerMatchDto;
import com.ameya.fplbackend.dto.PlayerNominationDto;
import com.ameya.fplbackend.model.request.PlayerRequest;
import com.ameya.fplbackend.model.response.PlayerMatchRest;
import com.ameya.fplbackend.model.response.PlayerNominationRest;
import com.ameya.fplbackend.model.response.PlayerRest;
import com.ameya.fplbackend.service.PlayerService;
import com.ameya.fplbackend.shared.Roles;

//@CrossOrigin(origins = {"http://localhost:3000/login"})
@RestController
@RequestMapping("players")
public class PlayerController {
	
	@Autowired
	PlayerService playerService;
	
	@GetMapping(path="/{id}")
	public PlayerRest getPlayer(@PathVariable String id) {
		
		PlayerRest returnValue = new PlayerRest();
		PlayerDto playerDto = playerService.getPlayerByPlayerId(id);
		
		ModelMapper modelMapper = new ModelMapper();
		
		returnValue.setName(playerDto.getName());
		returnValue.setPlayerId(playerDto.getPlayerId());
		returnValue.setPoints(playerDto.getPoints());
		
		List<PlayerNominationRest> playerNominationRests = new ArrayList<>();
		List<PlayerNominationDto> playerNominations = playerDto.getNominations();

		for(PlayerNominationDto playerNominationDto : playerNominations) {
			PlayerMatchDto playerMatchDto = playerNominationDto.getPlayerMatchDto();
			PlayerMatchRest rest = modelMapper.map(playerMatchDto, PlayerMatchRest.class);
			PlayerNominationRest nominationRest = new PlayerNominationRest();
			nominationRest.setPlayerMatchRest(rest);
			nominationRest.setNomination(playerNominationDto.getNomination());
			playerNominationRests.add(nominationRest);
		}
		
		returnValue.setNominations(playerNominationRests);
		
		return returnValue;
	}
	
	@PostMapping
	public PlayerRest createPlayer(@RequestBody PlayerRequest newPlayer) {
		
		PlayerRest returnValue = new PlayerRest();
		
		PlayerDto playerDto = new PlayerDto();
		BeanUtils.copyProperties(newPlayer, playerDto);
		
		playerDto.setRoles(new HashSet<>(Arrays.asList(Roles.ROLE_USER.name())));
		
		PlayerDto createdPlayer = playerService.createPlayer(playerDto);
		BeanUtils.copyProperties(createdPlayer, returnValue);
		
		return returnValue;
	}
	
	@DeleteMapping("/admin/{playerId}")
	public String deletePlayer(@PathVariable String playerId) {
		
		playerService.deletePlayerByPlayerId(playerId);
		
		return "Player Succesfully Deleted.";
	}
	

}
