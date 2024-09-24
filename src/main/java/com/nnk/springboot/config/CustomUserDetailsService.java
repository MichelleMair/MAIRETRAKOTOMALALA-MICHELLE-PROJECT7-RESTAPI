package com.nnk.springboot.config;

import java.util.Collection;
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

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userEntity = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found : " + username));

		// Conversion de l'utilisateur de la base de données en UserDetails
		return new org.springframework.security.core.userdetails.User(userEntity.getUsername(),
				userEntity.getPassword(), getAuthorities(userEntity));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(User userEntity) {
		return List.of(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole()));
	}

}
