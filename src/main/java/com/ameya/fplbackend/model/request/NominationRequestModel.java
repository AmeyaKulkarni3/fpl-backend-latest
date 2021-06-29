package com.ameya.fplbackend.model.request;

public class NominationRequestModel {

//	private long nominationId;
//	private long playerId;
	private long matchId;
	private String nomination;
	
//	public long getNominationId() {
//		return nominationId;
//	}
//	public void setNominationId(long nominationId) {
//		this.nominationId = nominationId;
//	}
//	
//	public long getPlayerId() {
//		return playerId;
//	}
//	public void setPlayerId(long playerId) {
//		this.playerId = playerId;
//	}
	public long getMatchId() {
		return matchId;
	}
	public void setMatchId(long matchId) {
		this.matchId = matchId;
	}
	public String getNomination() {
		return nomination;
	}
	public void setNomination(String nomination) {
		this.nomination = nomination;
	}
	
	

}
