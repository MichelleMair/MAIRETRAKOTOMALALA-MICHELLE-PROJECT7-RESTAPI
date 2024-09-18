package com.nnk.springboot.services;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTests {
	
	@Mock
	private RuleNameRepository ruleNameRepository;
	
	@InjectMocks
	private RuleNameService ruleNameService; 
	
	
	@Test
	public void testGetRulenameById() {
		RuleName ruleName = new RuleName(1, "Nametest", "Desctest", "Jsontest", "Templatetest", "Sqlstrtest", "Sqlparttest");
	
		when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
		
		Optional<RuleName> result = ruleNameService.getRuleNameById(1);
		
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getId());
	}
	
	@Test
	public void testSaveRuleName() {
		RuleName ruleName = new RuleName("Nametest", "Desctest", "Jsontest", "Templatetest", "Sqlstrtest", "Sqlparttest");
		when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);
		
		RuleName savedRulename = ruleNameService.saveRuleName(ruleName);
		
		assertNotNull(savedRulename);
		assertEquals("Nametest", savedRulename.getName());
		assertEquals("Desctest", savedRulename.getDescription());
	}
	
	@Test
	public void testUpdateRulename() {
		RuleName existingRulename = new RuleName(1, "Nametest", "Desctest", "Jsontest", "Templatetest", "Sqlstrtest", "Sqlparttest");
		RuleName updatedRulename = new RuleName("Nameupdated", "Descupdated", "Jsonupdated", "Templateupdated", "Sqlstrupdated", "Sqlpartupdated");
	
		when(ruleNameRepository.findById(1)).thenReturn(Optional.of(existingRulename));
		when(ruleNameRepository.save(existingRulename)).thenReturn(existingRulename);
		
		RuleName result = ruleNameService.updateRuleName(1, updatedRulename);
		assertEquals("Nameupdated", result.getName());
		assertEquals("Descupdated", result.getDescription());
	}
	
	@Test
	public void testDeleteRulenameById() {
		when(ruleNameRepository.existsById(1)).thenReturn(true);
		
		ruleNameService.deleteRuleName(1);
		
		verify(ruleNameRepository, times(1)).deleteById(1);
	}
	
	@Test
	public void testDeleteRulenameById_NotFound() {
		when(ruleNameRepository.existsById(1)).thenReturn(false);
		
		assertThrows(EntityNotFoundException.class, () -> ruleNameService.deleteRuleName(1));
	}

}
