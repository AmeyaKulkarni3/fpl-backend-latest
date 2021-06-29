package com.ameya.fplbackend.model.response;

public class PlayerNominationRest {
	
	private String nomination;
	private PlayerMatchRest playerMatchRest;
	
	public String getNomination() {
		return nomination;
	}
	public void setNomination(String nomination) {
		this.nomination = nomination;
	}
	public PlayerMatchRest getPlayerMatchRest() {
		return playerMatchRest;
	}
	public void setPlayerMatchRest(PlayerMatchRest playerMatchRest) {
		this.playerMatchRest = playerMatchRest;
	}

}
