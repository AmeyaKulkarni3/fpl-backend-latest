package com.ameya.fplbackend.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;

@Entity(name="players")
public class PlayerEntity implements Serializable {

	private static final long serialVersionUID = -6522234824015548891L;
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable=false)
	private String playerId;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=false)
	private String encryptedPassword;
	
	private double points;
	
	@ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
	@JoinTable(name="players_roles",
			joinColumns = @JoinColumn(name="players_id",referencedColumnName="id"),
			inverseJoinColumns = @JoinColumn(name="roles_id",referencedColumnName="id"))
	private Collection<RoleEntity> roles;
	
	@OneToMany(mappedBy="player",cascade = CascadeType.PERSIST)
	private List<NominationEntity> nominations;
	
	public PlayerEntity() {}

	public PlayerEntity(String playerId, String name, String encryptedPassword, double points,
			Collection<RoleEntity> roles, List<NominationEntity> nominations) {
		this.playerId = playerId;
		this.name = name;
		this.encryptedPassword = encryptedPassword;
		this.points = points;
		this.roles = roles;
		this.nominations = nominations;
	}



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

	public Collection<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Collection<RoleEntity> roles) {
		this.roles = roles;
	}

	public List<NominationEntity> getNominations() {
		return nominations;
	}

	public void setNominations(List<NominationEntity> nominations) {
		this.nominations = nominations;
	}
	
	
	
	

}
