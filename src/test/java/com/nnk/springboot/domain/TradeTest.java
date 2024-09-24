package com.nnk.springboot.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
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
	void tradeFields_shouldNotBeNull() {
		trade.setAccount(null);
		trade.setType(null);
		trade.setBuy_quantity(null);
		
		Set<ConstraintViolation<Trade>> violations = validator.validate(trade);
		
		assertFalse(violations.isEmpty());
		assertEquals(3, violations.size());
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
	void tradeBuyQuantity_shouldThrowValidationError_WhenEmpty() {
		trade.setAccount("AccountTest");
		trade.setType("TypeTest");
		trade.setBuy_quantity(null);
		
		Set<ConstraintViolation<Trade>> violations = validator.validate(trade);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}
	
	@Test
	void tradeBuyQuantity_shouldThrowValidationError_WhenNegative() {
		trade.setAccount("AccountTest");
		trade.setType("TypeTest");
		trade.setBuy_quantity(-5.0);
		
		Set<ConstraintViolation<Trade>> violations = validator.validate(trade);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}
	
	@Test
	void tradeOptionalFields_ShouldAllowNullValues() {
		trade.setAccount("AccountTest");
		trade.setType("TypeTest");
		trade.setBuy_quantity(1.0);
		trade.setSell_quantity(null);
		trade.setBuy_price(null);
		trade.setSell_price(null);
		
		Set<ConstraintViolation<Trade>> violations = validator.validate(trade);
		
		assertTrue(violations.isEmpty());
	}
	
	@Test
	void tradeAccount_ShouldThrowValidationError_WhenTooLong() {
		trade.setAccount("a".repeat(256));
		trade.setType("TypeTest");
		trade.setBuy_quantity(1.0);	
		
		Set<ConstraintViolation<Trade>> violations = validator.validate(trade);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}
	
	@Test
	void tradeType_ShouldThrowValidationError_WhenTooLong() {
		trade.setAccount("AccountTest");
		trade.setType("a".repeat(256));
		trade.setBuy_quantity(1.0);	
		
		Set<ConstraintViolation<Trade>> violations = validator.validate(trade);
		
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
	}
	
	@Test
	void tradeBuyQuantity_ShouldAllowExtremeValues() {
		trade.setAccount("AccountTest");
		trade.setType("TypeTest");
		trade.setBuy_quantity(Double.MAX_VALUE);	
		
		Set<ConstraintViolation<Trade>> violations = validator.validate(trade);
		
		assertTrue(violations.isEmpty());
	}
	
	@Test
	void whenCreateTradeWithAllFields_thenCorrect () {
		trade.setTrade_id(1);
		trade.setAccount("AccountTest"); 
		trade.setType("TypeTest"); 
		trade.setBuy_quantity(2.0);
		trade.setSell_quantity(3.0); 
		trade.setBuy_price(100.0); 
		trade.setSell_price(150.0);
		trade.setTrade_date(new Timestamp(System.currentTimeMillis())); 
		trade.setSecurity("SecurityTest"); 
		trade.setStatus("StatusTest");
		trade.setTrader("TraderTest");
		trade.setBenchmark("BenchmarkTest");
		trade.setBook("BookTest");
		trade.setCreation_name("CreatorTest"); 
		trade.setCreation_date(new Timestamp(System.currentTimeMillis())); 
		trade.setRevision_name("RevisorTest"); 
		trade.setRevision_date(new Timestamp(System.currentTimeMillis())); 
		trade.setDeal_name("DealNameTest"); 
		trade.setDeal_type("DealTypeTest"); 
		trade.setSourcelist_id("SourceListIdTest"); 
		trade.setSide("SideTest");

	    assertNotNull(trade);
	    assertEquals("AccountTest", trade.getAccount());
	    assertEquals("TypeTest", trade.getType());
	    assertEquals(2.0, trade.getBuy_quantity());
	    assertEquals(3.0, trade.getSell_quantity());
	}
	
}
