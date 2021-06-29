package com.ameya.fplbackend.security;

import java.util.Collection;
import java.util.HashSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ameya.fplbackend.entity.AuthorityEntity;
import com.ameya.fplbackend.entity.PlayerEntity;
import com.ameya.fplbackend.entity.RoleEntity;

public class PlayerPrinciple implements UserDetails {

	private static final long serialVersionUID = -6096728378813576980L;
	
	private PlayerEntity playerEntity;

	public PlayerPrinciple(PlayerEntity playerEntity) {
		this.playerEntity = playerEntity;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> authorities = new HashSet<>();
		Collection<AuthorityEntity> authorityEntities = new HashSet<>();
		
		Collection<RoleEntity> roles = playerEntity.getRoles();
		
		if(roles == null) return authorities;
		
		roles.forEach((role) -> {
			authorityEntities.addAll(role.getAuthorities());
		});
		
		authorityEntities.forEach((authorityEntity) -> {
			authorities.add(new SimpleGrantedAuthority(authorityEntity.getName()));
		});
		return null;
	}

	@Override
	public String getPassword() {
		return playerEntity.getEncryptedPassword();
	}

	@Override
	public String getUsername() {
		return playerEntity.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
