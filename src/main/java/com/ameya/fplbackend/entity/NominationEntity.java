package com.ameya.fplbackend.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.ManyToOne;

@Entity
@Table(name="nominations")
public class NominationEntity implements Serializable{

	private static final long serialVersionUID = 3868680291329902899L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long nominationId;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="players_id")
	private PlayerEntity player;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="matches_id")
	private MatchEntity match;
	
	private String nomination;
	
	public NominationEntity() {

	}

	public NominationEntity(PlayerEntity player, MatchEntity match, String nomination) {
		super();
		this.player = player;
		this.match = match;
		this.nomination = nomination;
	}

	public long getNominationId() {
		return nominationId;
	}

	public void setNominationId(long nominationId) {
		this.nominationId = nominationId;
	}

	public PlayerEntity getPlayer() {
		return player;
	}

	public void setPlayer(PlayerEntity player) {
		this.player = player;
	}

	public MatchEntity getMatch() {
		return match;
	}

	public void setMatch(MatchEntity match) {
		this.match = match;
	}

	public String getNomination() {
		return nomination;
	}

	public void setNomination(String nomination) {
		this.nomination = nomination;
	}
	
	
	
	
	
	
	
	
	

}
