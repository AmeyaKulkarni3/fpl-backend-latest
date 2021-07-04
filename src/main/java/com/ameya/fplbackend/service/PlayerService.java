package com.ameya.fplbackend.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.ameya.fplbackend.dto.PlayerDto;

public interface PlayerService extends UserDetailsService{
	
	PlayerDto createPlayer(PlayerDto player);
	
	PlayerDto getPlayer(String name);
	
	PlayerDto getPlayerByPlayerId(String id);
	
	public PlayerDto updatePlayerNomination(PlayerDto player);
	
	PlayerDto getPlayerById(long id);
	
	void deletePlayerByPlayerId(String playerId);

	void updatePoints(long id);

}
