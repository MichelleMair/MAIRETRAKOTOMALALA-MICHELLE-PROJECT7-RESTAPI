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

public class CurvePointTest {

	private Validator validator;
	private CurvePoint curvePoint;
	
	@BeforeEach
	void setup() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		curvePoint = new CurvePoint();
	}
	
	@Test
	void curvePointFields_ShouldNotBeNullOrEmpty() {
		curvePoint.setCurve_id(2);
		curvePoint.setTerm(3.0);
		
		Set<ConstraintViolation<CurvePoint>> violations = validator.validate(curvePoint);
		
		assertTrue(violations.isEmpty());	
	}
	
	@Test
	void curvePointCurveId_ShouldThrowValidationError_whenEmpty() {
		curvePoint.setCurve_id(null);
		curvePoint.setTerm(3.0);
		
		Set<ConstraintViolation<CurvePoint>> violations = validator.validate(curvePoint);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}
	
	@Test
	void curvePoint_ShouldThrowValidationError_whenFieldsAreNull() {
		curvePoint.setCurve_id(null);
		curvePoint.setTerm(null);
		
		Set<ConstraintViolation<CurvePoint>> violations = validator.validate(curvePoint);
		
		assertFalse(violations.isEmpty());
		assertEquals(2, violations.size());
	}
	
	@Test
	void curvePointCurveId_ShouldThrowValidationError_WhenNegative() {
		curvePoint.setCurve_id(-1);
		curvePoint.setTerm(3.0);
		
		Set<ConstraintViolation<CurvePoint>> violations = validator.validate(curvePoint);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}
	
	@Test
	void curvePointTerm_ShouldThrowValidationError_WhenNegative() {
		curvePoint.setCurve_id(1);
		curvePoint.setTerm(-1.0);
		
		Set<ConstraintViolation<CurvePoint>> violations = validator.validate(curvePoint);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}
	
	@Test
	void curvePointOptionalFields_ShouldAllowNullValues() {
		curvePoint.setCurve_id(2);
		curvePoint.setTerm(3.0);
		curvePoint.setValue(null);
		
		Set<ConstraintViolation<CurvePoint>> violations = validator.validate(curvePoint);
		
		assertTrue(violations.isEmpty());
	}
	
	@Test
	void curvePointCurveId_SHouldThrowValidationError_WhenZero() {
		curvePoint.setCurve_id(0);
		curvePoint.setTerm(3.0);
		
		Set<ConstraintViolation<CurvePoint>> violations = validator.validate(curvePoint);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());		
		
	}
	
	@Test
	void curvePointTerm_SHouldThrowValidationError_WhenZero() {
		curvePoint.setCurve_id(1);
		curvePoint.setTerm(0.0);
		
		Set<ConstraintViolation<CurvePoint>> violations = validator.validate(curvePoint);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());		
		
	}
}
