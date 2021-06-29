package com.ameya.fplbackend.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.ameya.fplbackend.entity.PlayerEntity;
import com.ameya.fplbackend.repository.PlayerRepository;

import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter{
	
	private final PlayerRepository playerRepository;

	public AuthorizationFilter(AuthenticationManager authenticationManager, PlayerRepository playerRepository) {
		super(authenticationManager);
		this.playerRepository = playerRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String header = request.getHeader(SecurityConstants.HEADER_STRING);
		if(header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

		String token = request.getHeader(SecurityConstants.HEADER_STRING);
		
		if(token != null) {
			token = token.replace(SecurityConstants.TOKEN_PREFIX,"");
			
			String player = Jwts.parser()
					.setSigningKey(SecurityConstants.getTokenSecret())
					.parseClaimsJws(token)
					.getBody()
					.getSubject();
			
			if(player != null) {
				PlayerEntity playerEntity = playerRepository.findByName(player);
				PlayerPrinciple playerPrinciple = new PlayerPrinciple(playerEntity);
				return new UsernamePasswordAuthenticationToken(player,null,playerPrinciple.getAuthorities());
			}
			return null;
		}
		return null;
	}
}
