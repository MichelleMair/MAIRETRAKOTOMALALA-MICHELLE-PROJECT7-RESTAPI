package com.nnk.springboot.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
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
	void bidListBidQuantity_shouldThrowValidationError_whenNull() {
		bidList.setAccount("AccountTest");
		bidList.setType("TypeTest");
		bidList.setBidquantity(null);
		
		Set<ConstraintViolation<BidList>> violations = validator.validate(bidList);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}
	
	@Test
	void bidList_shouldThrowValidationError_whenFieldsAreNull() {
		bidList.setAccount(null);
		bidList.setType(null);
		bidList.setBidquantity(null);
		
		Set<ConstraintViolation<BidList>> violations = validator.validate(bidList);
		
		assertFalse(violations.isEmpty());
		assertEquals(3, violations.size());
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
	
	@Test
	void bidListBidQuantity_shouldThrowValidationError_WhenBelowMinimum() {
		bidList.setAccount("AccountTest");
		bidList.setType("TypeTest");
		bidList.setBidquantity(0.0);
		
		Set<ConstraintViolation<BidList>> violations = validator.validate(bidList);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}
	
	@Test
	void bidListOptionalFields_ShouldAllowNullValues() {
		bidList.setAccount("AccountTest");
		bidList.setType("TypeTest");
		bidList.setBidquantity(10.0);
		bidList.setAskquantity(null);
		bidList.setBid(null);
		bidList.setAsk(null);
		bidList.setBenchmark(null);
		bidList.setCommentary(null);
		
		Set<ConstraintViolation<BidList>> violations = validator.validate(bidList);
		
		assertTrue(violations.isEmpty());
	}
	
	@Test
	void bidList_ShouldHaveDefaultValuesForOptionalFields() {
		bidList.setAccount("AccountTest");
		bidList.setType("TypeTest");
		bidList.setBidquantity(10.0);
		
		assertNull(bidList.getAskquantity());
		assertNull(bidList.getBid());
		assertNull(bidList.getAsk());
		assertNull(bidList.getCommentary());
	}
	
	@Test
	void bidListBidQuantity_ShouldHandleLargeValues() {
		bidList.setAccount("AccountTest");
		bidList.setType("TypeTest");
		bidList.setBidquantity(Double.MAX_VALUE);
		
		Set<ConstraintViolation<BidList>> violations = validator.validate(bidList);
		
		assertTrue(violations.isEmpty());
	}
	
	@Test
	void bidListAccount_ShouldThrowValidationError_WhenTooLong() {
		bidList.setAccount("a".repeat(31));
		bidList.setType("TypeTest");
		bidList.setBidquantity(10.0);
		
		Set<ConstraintViolation<BidList>> violations = validator.validate(bidList);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}
	
	@Test
	void whenCreateBidListWithAllFields_thenCorrect() {
		bidList.setAccount("AccountTest");
		bidList.setType("TypeTest");
		bidList.setBidquantity(10.0);
		bidList.setAskquantity(15.0);
		bidList.setBid(20.0);
		bidList.setAsk(25.0);
		bidList.setBenchmark("BenchmarkTest");
		bidList.setBidlist_date(new Timestamp(System.currentTimeMillis()));
		bidList.setCommentary("CommentaryTest");
		bidList.setSecurity("SecurityTest");
		bidList.setStatus("StatusTest");
		bidList.setTrader("TraderTest");
		bidList.setBook("BookTest");
		bidList.setCreation_name("CreatorTest");
		bidList.setCreation_date(new Timestamp(System.currentTimeMillis()));
		bidList.setRevision_name("RevisorTest");
		bidList.setRevision_date(new Timestamp(System.currentTimeMillis()));
		bidList.setDeal_name("DealNameTest");
		bidList.setDeal_type("DealTypeTest");
		bidList.setSourcelist_id("SourceListIdTest");
		bidList.setSide("SideTest");
		
		assertNotNull(bidList);
		assertEquals("AccountTest", bidList.getAccount());
		assertEquals("TypeTest", bidList.getType());
		assertEquals(10.0, bidList.getBidquantity(), 0.01);
		assertEquals(15.0, bidList.getAskquantity(), 0.01);
	}
}
