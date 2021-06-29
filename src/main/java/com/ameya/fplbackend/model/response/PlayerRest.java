package com.ameya.fplbackend.model.response;

import java.util.List;

public class PlayerRest {
	
	private String playerId;
	private String name;
	private double points;
	private List<PlayerNominationRest> nominations;
	
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPoints() {
		return points;
	}
	public void setPoints(double points) {
		this.points = points;
	}
	public List<PlayerNominationRest> getNominations() {
		return nominations;
	}
	public void setNominations(List<PlayerNominationRest> nominations) {
		this.nominations = nominations;
	}

}
