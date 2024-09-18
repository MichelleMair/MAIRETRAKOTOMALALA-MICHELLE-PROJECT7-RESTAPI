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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTests {
	
	@Mock
	private TradeRepository tradeRepository;
	
	@InjectMocks
	private TradeService tradeService;
	
	@Test
	public void testGetTradeById() {
		Trade trade = new Trade(1, "AccountTest", "TypeTest", 1.0);
		
		when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
		
		Optional<Trade> result = tradeService.getTradeById(1);
		
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getTrade_id());
	}
	
	@Test
	public void testSaveTrade() {
		Trade trade = new Trade("AccountTest", "TypeTest", 1.0);
		when(tradeRepository.save(trade)).thenReturn(trade);
		
		Trade savedTrade = tradeService.saveTrade(trade);
		
		assertNotNull(savedTrade);
		assertEquals("AccountTest", savedTrade.getAccount());
		assertEquals("TypeTest", savedTrade.getType());
		assertEquals(1.0, savedTrade.getBuy_quantity());
	}
	
	@Test
	public void testUpdateTrade() {
		Trade existingTrade = new Trade(1, "AccountTest", "TypeTest", 1.0);
		Trade updatedTrade = new Trade("AccountUpdated", "TypeUpdated", 2.0);
		
		when(tradeRepository.findById(1)).thenReturn(Optional.of(existingTrade));
		when(tradeRepository.save(existingTrade)).thenReturn(existingTrade);
		
		Trade result = tradeService.updateTrade(1, updatedTrade);
		
		assertEquals("AccountUpdated", result.getAccount());
		assertEquals("TypeUpdated", result.getType());
		assertEquals(2.0, result.getBuy_quantity());
	}
	
	
	@Test
	public void testDeleteTradeById() {
		when(tradeRepository.existsById(1)).thenReturn(true);
		
		tradeService.deleteTrade(1);
		
		verify(tradeRepository, times(1)).deleteById(1);
	}
	
	@Test
	public void testDeleteTradeById_NotFound() {
		when(tradeRepository.existsById(1)).thenReturn(false);
		
		assertThrows(EntityNotFoundException.class, () -> tradeService.deleteTrade(1));
	}
	
}
