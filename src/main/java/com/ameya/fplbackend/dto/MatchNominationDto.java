package com.ameya.fplbackend.dto;

import java.io.Serializable;

public class MatchNominationDto implements Serializable {

	private static final long serialVersionUID = -1550771681514698022L;
	
	private long nominationId;
	private String nomination;
	private MatchPlayerDto matchPlayerDto;
	
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
	public MatchPlayerDto getMatchPlayerDto() {
		return matchPlayerDto;
	}
	public void setMatchPlayerDto(MatchPlayerDto matchPlayerDto) {
		this.matchPlayerDto = matchPlayerDto;
	}
	
	

}
