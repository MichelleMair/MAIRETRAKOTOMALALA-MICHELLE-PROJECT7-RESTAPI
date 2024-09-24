package com.nnk.springboot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.nnk.springboot.config.CustomUserDetailsService;
import com.nnk.springboot.domain.User;

/**
 * Repository interface for accessing {@link User} data from the database
 * 
 * Allowing the retrieval of {@link User} entities used for authentication purposes
 * 
 * The {@link CustomUserDetailsService} uses this repository to load user details for authentication process
 * 
 * {@link #findByUsername(String)} - used to authenticate users
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

	/**
	 * Finds a user by their username
	 * 
	 * @param username
	 * @return {@link Optional} contains the found {@link User} or empty if no user found
	 */
	Optional<User> findByUsername(String username);
}
