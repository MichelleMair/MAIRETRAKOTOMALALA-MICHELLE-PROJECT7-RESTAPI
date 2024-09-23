package com.nnk.springboot.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class UserTest {

	private Validator validator;
	private User user; 
	
	@BeforeEach
	void setup() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		user = new User();
	}
	
	@Test
	void userFields_shouldNotBeNullOrEmpty() {
		user.setUsername("Usertest");
		user.setPassword("Password123!");
		
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		
		assertTrue(violations.isEmpty());
	}
	
	
	@Test
	void userUsername_shouldThrowValidationError_whenEmpty() {
		user.setUsername("");
		user.setPassword("Password123!");
		
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}
	
	@Test
	void userPassword_shouldThrowValidationError_whenInvalid() {
		user.setUsername("Usertest");
		user.setPassword("password"); //do not respect pattern
		
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		
		assertFalse(violations.isEmpty());
	}
	
	@Test
	void userPassword_shouldThrowValidationError_whenNull() {
		user.setUsername("Usertest");
		user.setPassword(null); //should not be null
		
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		
		assertFalse(violations.isEmpty());
	}
	
	@Test
	void userUsername_ShouldThrowValidationError_WhenTooLong() {
		user.setUsername("a".repeat(300));
		user.setPassword("Password123!");
		
		Set<ConstraintViolation<User>> violations = validator.validate(user);	
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
		assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Username cannot be longer than 255 characters")));
	}
	
}
