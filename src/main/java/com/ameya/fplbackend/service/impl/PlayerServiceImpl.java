package com.ameya.fplbackend.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ameya.fplbackend.dto.MatchNominationDto;
import com.ameya.fplbackend.dto.PlayerDto;
import com.ameya.fplbackend.dto.PlayerMatchDto;
import com.ameya.fplbackend.dto.PlayerNominationDto;
import com.ameya.fplbackend.entity.MatchEntity;
import com.ameya.fplbackend.entity.NominationEntity;
import com.ameya.fplbackend.entity.PlayerEntity;
import com.ameya.fplbackend.entity.RoleEntity;
import com.ameya.fplbackend.exception.RecordExistsException;
import com.ameya.fplbackend.exception.ResourceNotFoundException;
import com.ameya.fplbackend.repository.MatchRepository;
import com.ameya.fplbackend.repository.PlayerRepository;
import com.ameya.fplbackend.repository.RoleRepository;
import com.ameya.fplbackend.security.PlayerPrinciple;
import com.ameya.fplbackend.service.MatchService;
import com.ameya.fplbackend.service.NominationService;
import com.ameya.fplbackend.service.PlayerService;
import com.ameya.fplbackend.shared.Utils;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {
	
	@Autowired
	PlayerRepository playerRepository;
	
	@Autowired
	Utils utils;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	NominationService nominationService;
	
	@Autowired
	MatchRepository matchRepsitory;

	@Override
	public PlayerDto createPlayer(PlayerDto player) {
		
		if(playerRepository.findByName(player.getName()) != null) throw new RecordExistsException("Record Already Exists");
		
		PlayerEntity playerEntity = new PlayerEntity();
		BeanUtils.copyProperties(player, playerEntity);
		
		String publicPlayerId = utils.generateUserId(30);
		playerEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(player.getPassword()));
		playerEntity.setPlayerId(publicPlayerId);
		
		Collection<RoleEntity> roleEntities = new HashSet<>();
		for(String role : player.getRoles()) {
			RoleEntity roleEntity = roleRepository.findByName(role);
			if(roleEntity != null ) {
				roleEntities.add(roleEntity);
			}
			
		}
		
		playerEntity.setRoles(roleEntities);
		
		PlayerEntity createdPlayer = playerRepository.save(playerEntity);
		
		PlayerDto returnValue = new PlayerDto();
		BeanUtils.copyProperties(createdPlayer, returnValue);
		
		return returnValue;
	}

	
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		
		PlayerEntity playerEntity = playerRepository.findByName(name);
		
		if(playerEntity == null) throw new UsernameNotFoundException(name);
		
		return new PlayerPrinciple(playerEntity);
		
//		return new User(playerEntity.getName(),playerEntity.getEncryptedPassword(),new ArrayList<>());
	}

	@Override
	public PlayerDto getPlayer(String name) {
		
		PlayerEntity playerEntity = playerRepository.findByName(name);
		
		if(playerEntity == null) throw new UsernameNotFoundException(name);
		
		PlayerDto returnValue = new PlayerDto();
		BeanUtils.copyProperties(playerEntity, returnValue);
		
		return returnValue;
		
	}

	@Override
	public PlayerDto getPlayerByPlayerId(String id) {

		PlayerDto returnValue = new PlayerDto();
		PlayerEntity playerEntity = playerRepository.findByPlayerId(id);
		
		if(playerEntity == null) {
			throw new UsernameNotFoundException(id);
		}
		
		List<PlayerNominationDto> playerNominationDto = new ArrayList<>();
		List<NominationEntity> nominations = playerEntity.getNominations();
		for(NominationEntity nomination : nominations) {
			PlayerMatchDto playerMatchDto = new PlayerMatchDto();
			MatchEntity match = nomination.getMatch();
			playerMatchDto.setId(match.getId());
			playerMatchDto.setMatchDate(match.getMatchDate());
			playerMatchDto.setMatchNumber(match.getMatchNumber());
			playerMatchDto.setMatchTime(match.getMatchTime());
			playerMatchDto.setMatchVenue(match.getMatchVenue());
			playerMatchDto.setResult(match.getResult());
			playerMatchDto.setTeam1(match.getTeam1());
			playerMatchDto.setTeam2(match.getTeam2());
			playerMatchDto.setTeam1Count(match.getTeam1Count());
			playerMatchDto.setTeam2Count(match.getTeam2Count());
			playerMatchDto.setNoNomination(match.getNoNomination());
			PlayerNominationDto playerNomDto = new PlayerNominationDto();
			playerNomDto.setNomination(nomination.getNomination());
			playerNomDto.setNominationId(nomination.getNominationId());
			playerNomDto.setPlayerMatchDto(playerMatchDto);
			playerNominationDto.add(playerNomDto);
		}
		
		returnValue.setId(playerEntity.getId());
		returnValue.setPlayerId(playerEntity.getPlayerId());
		returnValue.setEncryptedPassword(playerEntity.getEncryptedPassword());
		returnValue.setName(playerEntity.getName());
		returnValue.setPoints(playerEntity.getPoints());
		returnValue.setNominations(playerNominationDto);
		
		return returnValue;
	}
	
	@Override
	public PlayerDto updatePlayerNomination(PlayerDto player) {
		
		ModelMapper modelMapper = new ModelMapper();
		
		PlayerEntity playerEntity = playerRepository.findById(player.getId());
		
		if(playerEntity == null) throw new ResourceNotFoundException("Player Not Found");
		
		List<PlayerNominationDto> nominationDto = player.getNominations();
		List<NominationEntity> nominationEntity = new ArrayList<>();
		for(PlayerNominationDto n : nominationDto) {
			NominationEntity entity = modelMapper.map(n, NominationEntity.class);
			nominationEntity.add(entity);
		}
		playerEntity.setNominations(nominationEntity);
		
		PlayerEntity newPlayer =  playerRepository.save(playerEntity);
		
		PlayerDto returnValue = modelMapper.map(newPlayer, PlayerDto.class);
		
		return returnValue;
	}


	@Override
	public void deletePlayerByPlayerId(String playerId) {
		
		PlayerEntity player = playerRepository.findByPlayerId(playerId);
		
		if(player == null) throw new ResourceNotFoundException("Player Not Found");
		
		nominationService.deleteAllById(player);		
		
		playerRepository.deleteById(player.getId());
	}


	@Override
	public PlayerDto getPlayerById(long id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void updatePoints(long id) {
		List<MatchNominationDto> nominations = nominationService.getMatchNominations(id);
		MatchEntity match = matchRepsitory.findById(id);
		int team1Count = match.getTeam1Count();
		int team2Count = match.getTeam2Count();
		int noNomination = match.getNoNomination();
		String result = match.getResult();
		String team1 = match.getTeam1();
		String team2 = match.getTeam2();
		
		for(MatchNominationDto dto : nominations) {
			double points = 0;
			PlayerEntity player = playerRepository.findById(dto.getMatchPlayerDto().getId());
			if(noNomination == 0) {
				if(dto.getNomination().equals(result)) {
					if(result.equals(team1)) {
						points = ((double)team2Count * 10)/((double)team1Count);
					} else if(result.equals(team2)) {
						points = ((double)team1Count * 10)/((double)team2Count);
					}
					
				} else if(dto.getNomination().equals("DRAW")) {
					points = 10;
				} else {
					points = -10;
				}
			} else {
				if(result.equals(team1)) {
					team2Count = team2Count + noNomination;
				} else if(result.equals(team2)) {
					team1Count = team1Count + noNomination;
				}
				if(dto.getNomination().equals(result)) {
					if(result.equals(team1)) {
						points = ((double)team2Count * 10)/((double)team1Count);
					} else if(result.equals(team2)) {
						points = ((double)team1Count * 10)/((double)team2Count);
					}
					
				} else if(dto.getNomination().equals("DRAW")) {
					points = 10;
				} else {
					points = -10;
				}
			}
			
			double newPoints = player.getPoints() + points;
			player.setPoints(newPoints);
			playerRepository.save(player);
			
		}
				
		
	}

}
