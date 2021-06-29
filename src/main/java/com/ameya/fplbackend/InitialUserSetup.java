package com.ameya.fplbackend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ameya.fplbackend.entity.AuthorityEntity;
import com.ameya.fplbackend.entity.PlayerEntity;
import com.ameya.fplbackend.entity.RoleEntity;
import com.ameya.fplbackend.repository.AuthorityRepository;
import com.ameya.fplbackend.repository.PlayerRepository;
import com.ameya.fplbackend.repository.RoleRepository;
import com.ameya.fplbackend.shared.Roles;
import com.ameya.fplbackend.shared.Utils;

@Component
public class InitialUserSetup {

	@Autowired
	AuthorityRepository authorityRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	Utils utils;
	
	@Autowired
	PlayerRepository playerRepository;

	@EventListener
	@Transactional
	public void onApplicationEvent(ApplicationReadyEvent event) {

		AuthorityEntity readAuthority = createAuthority("READ_AUTHORITY");
		AuthorityEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
		AuthorityEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");
		
		createRole(Roles.ROLE_USER.name(),Arrays.asList(readAuthority,writeAuthority));
		RoleEntity roleAdmin = createRole(Roles.ROLE_ADMIN.name(),Arrays.asList(readAuthority,writeAuthority,deleteAuthority));
		
		if(roleAdmin == null) {
			return;
		}
		
		PlayerEntity adminPlayer = new PlayerEntity();
		adminPlayer.setName("Ameya");
		adminPlayer.setEncryptedPassword(bCryptPasswordEncoder.encode("12345678"));
		adminPlayer.setPlayerId(utils.generateUserId(30));
		adminPlayer.setRoles(Arrays.asList(roleAdmin));
		
		ArrayList<PlayerEntity> playerEntity = (ArrayList<PlayerEntity>) playerRepository.findAll();
		if(playerEntity.size() == 0) {
			playerRepository.save(adminPlayer);
		}
		
		
	}

	@Transactional
	private AuthorityEntity createAuthority(String name) {

		AuthorityEntity authority = authorityRepository.findByName(name);
		if (authority == null) {
			authority = new AuthorityEntity(name);
			authorityRepository.save(authority);
		}
		return authority;
	}

	@Transactional
	private RoleEntity createRole(String name, Collection<AuthorityEntity> authorities) {

		RoleEntity role = roleRepository.findByName(name);
		if (role == null) {
			role = new RoleEntity(name);
			role.setAuthorities(authorities);
			roleRepository.save(role);
		}
		return role;
	}
}
