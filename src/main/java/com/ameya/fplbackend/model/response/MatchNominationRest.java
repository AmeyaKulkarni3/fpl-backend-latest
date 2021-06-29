package com.ameya.fplbackend.model.response;

public class MatchNominationRest {
	
	private String nomination;
	private MatchPlayerRest matchPlayerRest;
	
	public String getNomination() {
		return nomination;
	}
	public void setNomination(String nomination) {
		this.nomination = nomination;
	}
	public MatchPlayerRest getMatchPlayerRest() {
		return matchPlayerRest;
	}
	public void setMatchPlayerRest(MatchPlayerRest matchPlayerRest) {
		this.matchPlayerRest = matchPlayerRest;
	}
}
