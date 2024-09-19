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

public class TradeTest {

	private Validator validator;
	private Trade trade;
	
	@BeforeEach
	void setup() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		trade = new Trade();
	}
	
	@Test
	void tradeFields_shouldNotBeNullOrEmpty() {
		trade.setAccount("AccountTest");
		trade.setType("TypeTest");
		trade.setBuy_quantity(2.0);
		
		Set<ConstraintViolation<Trade>> violations = validator.validate(trade);
		
		assertTrue(violations.isEmpty());
	}
	
	@Test
	void tradeAccount_shouldThrowValidationError_whenEmpty() {
		trade.setAccount("");
		trade.setType("TypeTest");
		trade.setBuy_quantity(2.0);
		
		Set<ConstraintViolation<Trade>> violations = validator.validate(trade);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}
	
	@Test
	void tradeType_shouldThrowValidationError_whenEmpty() {
		trade.setAccount("AccountTest");
		trade.setType("");
		trade.setBuy_quantity(2.0);
		
		Set<ConstraintViolation<Trade>> violations = validator.validate(trade);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}
	
	@Test
	void tradeBuyQuantity_shouldThrowValidationError_whenEmpty() {
		trade.setAccount("AccountTest");
		trade.setType("TypeTest");
		trade.setBuy_quantity(null);
		
		Set<ConstraintViolation<Trade>> violations = validator.validate(trade);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}
}
