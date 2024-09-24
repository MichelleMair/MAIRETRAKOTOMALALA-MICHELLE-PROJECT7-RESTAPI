package com.nnk.springboot.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

/**
 * CustomUserDetailsService is responsible for loading user-specific data during authentication
 * 
 * It implements the Spring Security {@link UserDetailsService} interface
 * which defines the method to retrieve user details based on the username provided during login 
 * 
 * CustomUserDetailsService is used to verify the existence of a user in database and
 * provides the user's credentials and roles for authentication.
 * 
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	/**
	 * Loads a user based on their username. 
	 * This method is called by Spring Security during the authentication process
	 * 
	 * It searchs the user in the database,
	 * converts the {@link User} entity into a {@link UserDetails} object
	 * and assigns the appropriate role to the user for authorization
	 * 
	 * @param username the username of the user attempting to authenticate
	 * @return a {@link UserDetails} object containing the user's credentials and roles 
	 * @throws UsernameNotFoundException if no user with the given name is found 
	 * @see UserRepository#findByUsername(String) 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//Find user in database
		User userEntity = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found : " + username));

		// Converting the user found in databade to UserDetails with permissions 
		return new org.springframework.security.core.userdetails.User(
				userEntity.getUsername(),
				userEntity.getPassword(), 
				getAuthorities(userEntity.getRole()));
	}

	/**
	 * Retrieve a list of authorities (roles) based on user's role
	 * Each user is assigned a role which is used for authorization checks 
	 * 
	 * @param role the role of the user ("ADMIN" or "USER")
	 * @return a list of {@link GrantedAuthority} containing the user's roles prefixed with "ROLE_"
	 */
	private List<GrantedAuthority> getAuthorities(String role) {
		return List.of(new SimpleGrantedAuthority("ROLE_" + role));
	}

}
