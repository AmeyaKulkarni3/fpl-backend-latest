package com.ameya.fplbackend.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class RoleEntity implements Serializable {

	private static final long serialVersionUID = 3869613830848495532L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(nullable=false,length=20)
	private String name;
	
	@ManyToMany(mappedBy="roles")
	private Collection<PlayerEntity> players;
	
	@ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
	@JoinTable(name="roles_authorities",
			joinColumns = @JoinColumn(name="roles_id",referencedColumnName="id"),
			inverseJoinColumns = @JoinColumn(name="authorities_id",referencedColumnName="id"))
	private Collection<AuthorityEntity> authorities;

	public RoleEntity() {
	
	}
	
	public RoleEntity(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<PlayerEntity> getPlayers() {
		return players;
	}

	public void setPlayers(Collection<PlayerEntity> players) {
		this.players = players;
	}

	public Collection<AuthorityEntity> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<AuthorityEntity> authorities) {
		this.authorities = authorities;
	}
	
	
	
}
