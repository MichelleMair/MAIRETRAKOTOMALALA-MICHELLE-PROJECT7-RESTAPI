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

public class RatingTest {
	
	private Validator validator;
	private Rating rating;
	
	@BeforeEach
	void setup() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		rating = new Rating();
	}

	@Test
	void ratingFields_shouldNotBeNullOrEmpty() {
		rating.setMoodys_rating("MoodysRating");
		rating.setSandprating("SandPRating");
		rating.setFitch_rating("FitchRating");
		rating.setOrder_number(1);
		
		Set<ConstraintViolation<Rating>> violations = validator.validate(rating);
		
		assertTrue(violations.isEmpty());
	}
	
	@Test
	void ratingMoodys_shouldThrowValidationError_whenEmpty() {
		rating.setMoodys_rating("");
		rating.setSandprating("SandPRating");
		rating.setFitch_rating("FitchRating");
		rating.setOrder_number(1);
		
		Set<ConstraintViolation<Rating>> violations = validator.validate(rating);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}
	
	@Test
	void ratingSandP_shouldThrowValidationError_whenEmpty() {
		rating.setMoodys_rating("MoodysRating");
		rating.setSandprating("");
		rating.setFitch_rating("FitchRating");
		rating.setOrder_number(1);
		
		Set<ConstraintViolation<Rating>> violations = validator.validate(rating);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}
	
	@Test
	void ratingFitch_shouldThrowValidationError_whenEmpty() {
		rating.setMoodys_rating("MoodysRating");
		rating.setSandprating("SandPRating");
		rating.setFitch_rating("");
		rating.setOrder_number(1);
		
		Set<ConstraintViolation<Rating>> violations = validator.validate(rating);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}
	
	@Test
	void ratingOrderNumber_shouldThrowValidationError_whenEmpty() {
		rating.setMoodys_rating("MoodysRating");
		rating.setSandprating("SandPRating");
		rating.setFitch_rating("FitchRating");
		rating.setOrder_number(null);
		
		Set<ConstraintViolation<Rating>> violations = validator.validate(rating);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}
}
