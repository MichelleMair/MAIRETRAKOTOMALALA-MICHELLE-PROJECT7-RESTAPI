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

public class RuleNameTest {
	
	private Validator validator;
	private RuleName ruleName;
	
	@BeforeEach
	void setup() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		ruleName = new RuleName();
	}
	
	@Test
	void ruleNameFields_shouldNotBeNullOrEmpty() {
		ruleName.setName("Nametest");
		ruleName.setDescription("Desctest");
		
		Set<ConstraintViolation<RuleName>> violations = validator.validate(ruleName);
		
		assertTrue(violations.isEmpty());
	}
	
	@Test
	void rulenameName_shouldNotBeNullOrEmpty() {
		ruleName.setName("");
		ruleName.setDescription("Desctest");
		
		Set<ConstraintViolation<RuleName>> violations = validator.validate(ruleName);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}
	
	@Test
	void rulename_shouldNotBeNull() {
		ruleName.setName(null);
		ruleName.setDescription(null);
		
		Set<ConstraintViolation<RuleName>> violations = validator.validate(ruleName);
		
		assertFalse(violations.isEmpty());
		assertEquals(2, violations.size());
	}
	
	@Test
	void rulenameDescription_shouldNotBeNullOrEmpty() {
		ruleName.setName("Nametest");
		ruleName.setDescription("");
		
		Set<ConstraintViolation<RuleName>> violations = validator.validate(ruleName);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}
	
	@Test
	void ruleNameJsonField_ShouldNotThrowVAlidationError_WhenNull() {
		ruleName.setName("Nametest");
		ruleName.setDescription("Desctest");
		ruleName.setJson(null);
		
		Set<ConstraintViolation<RuleName>> violations = validator.validate(ruleName);
		
		assertTrue(violations.isEmpty());
	}
	
	@Test
	void ruleNameOptionalFields_ShouldAllowNullValues() {
		ruleName.setName("Nametest");
		ruleName.setDescription("Desctest");
		ruleName.setJson(null);
		ruleName.setTemplate(null);
		ruleName.setSqlstr(null);
		ruleName.setSqlpart(null);
		
		Set<ConstraintViolation<RuleName>> violations = validator.validate(ruleName);
		
		assertTrue(violations.isEmpty());
	}
	
	@Test
	void ruleNameName_ShouldThrowValidationError_WhenTooLong() {
		ruleName.setName("a".repeat(256));
		ruleName.setDescription("Desctest");
		
		Set<ConstraintViolation<RuleName>> violations = validator.validate(ruleName);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}
	
	@Test
	void ruleNameDescription_ShouldThrowValidationError_WhenTooLong() {
		ruleName.setName("Nametest");
		ruleName.setDescription("a".repeat(256));
		
		Set<ConstraintViolation<RuleName>> violations = validator.validate(ruleName);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}
}
