package com.ameya.fplbackend.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class PlayerDto implements Serializable {

	private static final long serialVersionUID = 5623049963761926449L;
	private long id;
	private String playerId;
	private String name;
	private String password;
	private String encryptedPassword;
	private double points;
	private Collection<String> roles;
	private List<PlayerNominationDto> nominations;
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEncryptedPassword() {
		return encryptedPassword;
	}
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}
	public double getPoints() {
		return points;
	}
	public void setPoints(double points) {
		this.points = points;
	}
	public Collection<String> getRoles() {
		return roles;
	}
	public void setRoles(Collection<String> roles) {
		this.roles = roles;
	}
	public List<PlayerNominationDto> getNominations() {
		return nominations;
	}
	public void setNominations(List<PlayerNominationDto> nominations) {
		this.nominations = nominations;
	}
	
	

}
