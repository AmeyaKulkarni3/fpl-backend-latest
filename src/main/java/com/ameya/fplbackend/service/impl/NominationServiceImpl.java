package com.ameya.fplbackend.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ameya.fplbackend.dto.MatchDto;
import com.ameya.fplbackend.dto.MatchNominationDto;
import com.ameya.fplbackend.dto.MatchPlayerDto;
import com.ameya.fplbackend.dto.NominationDto;
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
import com.ameya.fplbackend.repository.NominationRepository;
import com.ameya.fplbackend.repository.PlayerRepository;
import com.ameya.fplbackend.service.MatchService;
import com.ameya.fplbackend.service.NominationService;
import com.ameya.fplbackend.service.PlayerService;

@Service
@Transactional
public class NominationServiceImpl implements NominationService {

	@Autowired
	NominationRepository nominationRepository;

	@Autowired
	MatchRepository matchRepository;

	@Autowired
	PlayerRepository playerRepository;

	@Autowired
	MatchService matchService;

	@Autowired
	PlayerService playerService;

	@Override
	public NominationDto createNomination(NominationDto nomination) {

		ModelMapper modelMapper = new ModelMapper();

		if (nominationRepository.findByPlayerIdAndMatchId(nomination.getPlayerId(), nomination.getMatchId()) != null) {
			throw new RecordExistsException("Nomination Already Exists");
		}

		PlayerEntity playerEntity = playerRepository.findById(nomination.getPlayerId());

		if (playerEntity == null)
			throw new ResourceNotFoundException("Player Does Not Exist");

		MatchEntity matchEntity = matchRepository.findById(nomination.getMatchId());

		if (matchEntity == null)
			throw new ResourceNotFoundException("Match Does Not Exist");

		NominationEntity newNomination = new NominationEntity();

		newNomination.setMatch(matchEntity);
		newNomination.setPlayer(playerEntity);
		newNomination.setNomination(nomination.getNomination());

		NominationEntity createdNomination = nominationRepository.save(newNomination);

		List<NominationEntity> playerNominations = playerEntity.getNominations();
		playerNominations.add(createdNomination);

		playerEntity.setNominations(playerNominations);

		List<NominationEntity> matchNominations = matchEntity.getNominations();
		matchNominations.add(createdNomination);

		matchEntity.setNominations(matchNominations);

		PlayerDto newPlayer = new PlayerDto();
		newPlayer.setId(playerEntity.getId());
		newPlayer.setEncryptedPassword(playerEntity.getEncryptedPassword());
		newPlayer.setName(playerEntity.getName());
		newPlayer.setPlayerId(playerEntity.getPlayerId());
		newPlayer.setPoints(playerEntity.getPoints());

		List<NominationEntity> entityNominations = playerEntity.getNominations();
		List<PlayerNominationDto> playerNominationDto = new ArrayList<>();
		for (NominationEntity n : entityNominations) {
			PlayerNominationDto dto = modelMapper.map(n, PlayerNominationDto.class);
			playerNominationDto.add(dto);
		}
		newPlayer.setNominations(playerNominationDto);

		Collection<RoleEntity> roles = playerEntity.getRoles();
		Collection<String> newRoles = new ArrayList<>();
		for (RoleEntity r : roles) {
			String role = r.getName();
			newRoles.add(role);
		}
		newPlayer.setRoles(newRoles);

		MatchDto newMatch = modelMapper.map(matchEntity, MatchDto.class);

		PlayerDto updatedPlayer = playerService.updatePlayerNomination(newPlayer);
		MatchDto updatedMatch = matchService.updateMatchNomination(newMatch);

		if (updatedPlayer == null || updatedMatch == null)
			throw new ResourceNotFoundException("");

		NominationDto returnValue = modelMapper.map(createdNomination, NominationDto.class);

		return returnValue;
	}

	@Override
	public List<PlayerNominationDto> getPlayerNominations(String playerId) {

		PlayerEntity playerEntity = playerRepository.findByPlayerId(playerId);
		long id = playerEntity.getId();

		List<NominationEntity> nominationEntities = nominationRepository.findByPlayerId(id);

		if (nominationEntities == null) {
			throw new ResourceNotFoundException("No Nomination Exists");
		}

		List<PlayerNominationDto> returnValue = new ArrayList<>();

		for (NominationEntity entity : nominationEntities) {
			MatchEntity match = entity.getMatch();
			PlayerNominationDto dto = new PlayerNominationDto();
			PlayerMatchDto playerMatchDto = new PlayerMatchDto();
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
			dto.setNomination(entity.getNomination());
			dto.setPlayerMatchDto(playerMatchDto);
			dto.setNominationId(entity.getNominationId());
			returnValue.add(dto);
		}
		return returnValue;
	}

	public NominationDto updateNomination(NominationDto nomination) {

		NominationEntity nominationEntity = nominationRepository.findByPlayerIdAndMatchId(nomination.getPlayerId(),
				nomination.getMatchId());

		if (nominationEntity == null) {
			throw new RecordExistsException("Nomination Doesn't exist");
		}

		if (nomination.getNomination().equals("")) {
			nominationEntity.setNomination(null);
		} else {
			nominationEntity.setNomination(nomination.getNomination());
		}

		NominationEntity newNomination = nominationRepository.save(nominationEntity);

		NominationDto returnValue = new NominationDto();
		returnValue.setMatchId(newNomination.getMatch().getId());
		returnValue.setPlayerId(newNomination.getPlayer().getId());
		returnValue.setNomination(newNomination.getNomination());

		return returnValue;

	}

	@Override
	public void deleteByNominationId(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllById(PlayerEntity player) {

		List<Long> ids = new ArrayList<>();
		List<PlayerNominationDto> nominations = getPlayerNominations(player.getPlayerId());
		nominations.forEach((nomination) -> {
			ids.add(nomination.getNominationId());
		});

		nominationRepository.deleteAllById(ids);

	}

	@Override
	public List<MatchNominationDto> getMatchNominations(long id) {

		List<NominationEntity> nominations = nominationRepository.findAllByMatchId(id);
		
		List<MatchNominationDto> matchNominationDtos = new ArrayList<>();
		
		for(NominationEntity nomination : nominations) {
			MatchNominationDto matchNominationDto = new MatchNominationDto();
			MatchPlayerDto matchPlayerDto = new MatchPlayerDto();
			matchPlayerDto.setId(nomination.getPlayer().getId());
			matchPlayerDto.setName(nomination.getPlayer().getName());
			matchPlayerDto.setPlayerId(nomination.getPlayer().getPlayerId());
			matchPlayerDto.setPoints(nomination.getPlayer().getPoints());
			matchNominationDto.setMatchPlayerDto(matchPlayerDto);
			matchNominationDto.setNomination(nomination.getNomination());
			matchNominationDto.setNominationId(nomination.getNominationId());
			matchNominationDtos.add(matchNominationDto);
		}

		return matchNominationDtos;
	}

}
