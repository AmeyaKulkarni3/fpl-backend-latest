package com.ameya.fplbackend.dto;

import java.io.Serializable;

public class PlayerNominationDto implements Serializable {

	private static final long serialVersionUID = 6385068542772454432L;
	
	private long nominationId;
	private String nomination;
	private PlayerMatchDto playerMatchDto;
	
	public long getNominationId() {
		return nominationId;
	}
	public void setNominationId(long nominationId) {
		this.nominationId = nominationId;
	}
	public String getNomination() {
		return nomination;
	}
	public void setNomination(String nomination) {
		this.nomination = nomination;
	}
	public PlayerMatchDto getPlayerMatchDto() {
		return playerMatchDto;
	}
	public void setPlayerMatchDto(PlayerMatchDto playerMatchDto) {
		this.playerMatchDto = playerMatchDto;
	}
	
	

}
