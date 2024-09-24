package com.nnk.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * The {@code SecurityConfig} class is responsible for configuring the security of the app. 
 * 
 * It defines the authentication provider, password encoding and access control for endpoints
 * 
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final CustomUserDetailsService userDetailsService;

	
	/**
	 * Constructor that injects a custom user details service
	 * to handle user authentication 
	 * 
	 * @param userDetailsService the {@link CustomUserDetailsService} used to retrieve user details 
	 */
	public SecurityConfig(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	/**
	 * Bean (Spring component) that provided encoding password used to securely hash and verify user passwords 
	 * 
	 * This method configures a delegating password encoder to support multiple encoding formats
	 * 
	 * @return a {@link PasswordEncoder} used to encode and verify user passwords
	 */
	@Bean
	protected PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	/**
	 * Bean (Spring component) that configures a DAO based authentication provider,
	 * which retrieves user details from a data source (via {@link CustomUserDetailsService}) 
	 * and compares password hashes for authentication
	 * 
	 * @return a {@link DaoAuthenticationProvider} configured with the custom user details 
	 * service and password encoder 
	 */
	@Bean
	protected DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	/**
	 * Configures the security filter chain for the application 
	 * defining which URLs are publicly accessible, which require authentication,
	 * and the role based access control for specific routes 
	 * 
	 * It also defines the login and logout behavior.
	 * 
	 * @param http the {@link HttpSecurity} object used to configure the security filters 
	 * @return a {@link SecurityFilterChain} that defines the security configuration 
	 * @throws Exception if any errors occur during security configuration 
	 */
	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> {
				// Public access
				requests.requestMatchers("/login", "/home", "/css/**").permitAll();
				// Restricted access, admin role only
				requests.requestMatchers("/user/**").hasRole("ADMIN");
				// Require authentication for all other requests 
				requests.anyRequest().authenticated();
			})
			.formLogin((form) -> form
					.loginPage("/login")
					.defaultSuccessUrl("/default", true)
					.permitAll()
			)
			.logout((logout) -> logout
					.logoutUrl("/app-logout")
					.logoutSuccessUrl("/login?logout")
					.invalidateHttpSession(true)
					.deleteCookies("JSESSIONID")
			)
			.sessionManagement(session -> session
					.maximumSessions(1)
					.expiredUrl("/login?expired=true")
			);
		return http.build();
	}
}
