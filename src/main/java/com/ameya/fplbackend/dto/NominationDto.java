package com.ameya.fplbackend.dto;

import java.io.Serializable;

public class NominationDto implements Serializable{

	private static final long serialVersionUID = -7872447205812765230L;
	
	private long nominationId;
//	private PlayerDto playerDto;
//	private MatchDto matchDto;
	private String nomination;
	private long playerId;
	private long matchId;
	
	public long getNominationId() {
		return nominationId;
	}
	public void setNominationId(long nominationId) {
		this.nominationId = nominationId;
	}
//	public PlayerDto getPlayerDto() {
//		return playerDto;
//	}
//	public void setPlayerDto(PlayerDto playerDto) {
//		this.playerDto = playerDto;
//	}
//	public MatchDto getMatchDto() {
//		return matchDto;
//	}
//	public void setMatchDto(MatchDto matchDto) {
//		this.matchDto = matchDto;
//	}
	public String getNomination() {
		return nomination;
	}
	public void setNomination(String nomination) {
		this.nomination = nomination;
	}
	public long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
	public long getMatchId() {
		return matchId;
	}
	public void setMatchId(long matchId) {
		this.matchId = matchId;
	}


}
