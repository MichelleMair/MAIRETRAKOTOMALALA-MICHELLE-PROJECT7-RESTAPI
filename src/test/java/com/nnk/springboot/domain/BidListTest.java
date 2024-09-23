package com.nnk.springboot.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class BidListTest {
	
	private Validator validator;
	private BidList bidList;
	
	@BeforeEach
	void setup() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		bidList = new BidList();
	}
	
	@Test
	public void whenCreateBidList_thenCorrect() {
		bidList.setAccount("AccountTest");
		bidList.setType("TypeTest");
		bidList.setBidquantity(10.0);
		
		assertNotNull(bidList);
		assertEquals("AccountTest", bidList.getAccount());
		assertEquals("TypeTest", bidList.getType());
		assertEquals(10.0, bidList.getBidquantity(), 0.01);
	}
	
	@Test
	void bidListFields_shouldNotBeNullOrEmpty() {
		bidList.setAccount("AccountTest");
		bidList.setType("TypeTest");
		bidList.setBidquantity(10.0);
		
		Set<ConstraintViolation<BidList>> violations = validator.validate(bidList);
		
		assertTrue(violations.isEmpty());
	}
	
	@Test
	void bidListAccount_shouldThrowValidationError_whenEmpty() {
		bidList.setAccount("");
		bidList.setType("TypeTest");
		bidList.setBidquantity(10.0);
		
		Set<ConstraintViolation<BidList>> violations = validator.validate(bidList);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}
	
	@Test
	void bidListType_shouldThrowValidationError_whenEmpty() {
		bidList.setAccount("AccountTest");
		bidList.setType("");
		bidList.setBidquantity(10.0);
		
		Set<ConstraintViolation<BidList>> violations = validator.validate(bidList);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}
	
	@Test
	void bidListBidQuantity_shouldThrowValidationError_WhenNegative() {
		bidList.setAccount("AccountTest");
		bidList.setType("TypeTest");
		bidList.setBidquantity(-10.0);
		
		Set<ConstraintViolation<BidList>> violations = validator.validate(bidList);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}

}
