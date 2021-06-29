package com.ameya.fplbackend.model.response;

public class NominationRest {

	private String nomination;
	private long playerId;
	private long matchId;
	
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
	public String getNomination() {
		return nomination;
	}
	public void setNomination(String nomination) {
		this.nomination = nomination;
	}

}
