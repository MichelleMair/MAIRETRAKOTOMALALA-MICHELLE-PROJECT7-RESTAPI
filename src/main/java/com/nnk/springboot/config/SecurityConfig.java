package com.nnk.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nnk.springboot.services.UserDetailsServiceImpl;

@Configuration
public class SecurityConfig {

	private final UserDetailsServiceImpl userDetailsService;

	public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/curvePoint/**", "/bidList/**", "/rating/**", "/public/**", "/css/**").permitAll()
				.requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/user/**").hasRole("USER").anyRequest()
				.authenticated())
				.formLogin((form) -> form.loginPage("/login").defaultSuccessUrl("/rating/list", true).permitAll())
				.logout((logout) -> logout.logoutUrl("/app-logout").logoutSuccessUrl("/login?logout").permitAll());
		return http.build();
	}
}