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
		http.authorizeHttpRequests(
				(requests) -> requests.requestMatchers("/", "/home", "/login").permitAll().requestMatchers("/admin/**")
						.hasRole("ADMIN").requestMatchers("/user/**").hasRole("USER").anyRequest().authenticated())
				.formLogin((form) -> form.loginPage("/app/login").defaultSuccessUrl("/app/secure/article-details", true)
						.permitAll())
				.logout((logout) -> logout.permitAll());
		return http.build();
	}
}