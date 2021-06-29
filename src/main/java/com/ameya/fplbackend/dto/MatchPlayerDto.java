package com.ameya.fplbackend.dto;

import java.io.Serializable;

public class MatchPlayerDto implements Serializable {

	private static final long serialVersionUID = -826128252329177039L;
	
	private long id;
	private String playerId;
	private String name;
	private double points;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
}
