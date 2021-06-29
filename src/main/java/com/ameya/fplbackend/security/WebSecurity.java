package com.ameya.fplbackend.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ameya.fplbackend.repository.PlayerRepository;
import com.ameya.fplbackend.service.PlayerService;
import com.google.common.collect.ImmutableList;

@EnableGlobalMethodSecurity(securedEnabled=true)
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
	
	private final PlayerService playerDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final PlayerRepository playerRepository;
	
	public WebSecurity(PlayerService playerDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder,PlayerRepository playerRepository) {
		super();
		this.playerDetailsService = playerDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.playerRepository = playerRepository;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(playerDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable().authorizeRequests()
//		.antMatchers(HttpMethod.POST,SecurityConstants.SIGN_UP_URL)
//		.permitAll()
//		.anyRequest().authenticated().and()
//		.addFilter(getAuthenticationFilter())
//		.addFilter(new AuthorizationFilter(authenticationManager()))
//		.sessionManagement()
//		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		
//		http.cors();
		
		http.cors().and().csrf().disable().authorizeRequests()
			.antMatchers(HttpMethod.POST,SecurityConstants.SIGN_UP_URL)
			.permitAll()
			.antMatchers(HttpMethod.DELETE,"/players/admin/**").hasRole("ADMIN")
			.anyRequest().authenticated().and()
			.addFilter(getAuthenticationFilter())
			.addFilter(new AuthorizationFilter(authenticationManager(),playerRepository))
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.headers().cacheControl();
	}
	
	
	
//	@Override
//	public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web)
//			throws Exception {
//		
//		web.ignoring().antMatchers(HttpMethod.OPTIONS,"/**");
//	}

	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        //configuration.setAllowedOrigins(ImmutableList.of("http://localhost:8080","http://localhost:8084"));
        configuration.setAllowedOrigins(ImmutableList.of("http://localhost:3000","http://localhost:3000/login","http://localhost:3000/matches"));
        configuration.setAllowedMethods(ImmutableList.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
	
	public AuthenticationFilter getAuthenticationFilter() throws Exception{
		final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
//		filter.setFilterProcessesUrl("/players/login");
		return filter;
	}
}
