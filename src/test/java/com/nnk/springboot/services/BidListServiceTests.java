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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class BidListServiceTests {
	
	@Mock
	private BidListRepository bidListRepository;
	
	@InjectMocks
	private BidListService bidListService;
	
	@Test
	public void testGetBidListById() {
		BidList bidList = new BidList();
		
		bidList.setBidlist_id(1);
		when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));
		
		Optional<BidList> result = bidListService.getBidListById(1);
		
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getBidlist_id());
	}
	
	@Test
	public void testSaveBidList() {
		BidList bidList = new BidList("Test Account","Test Type", 100.0);
		when(bidListRepository.save(bidList)).thenReturn(bidList);
		
		BidList savedBidList = bidListService.saveBidList(bidList);
		
		assertNotNull(savedBidList);
		assertEquals("Test Account", savedBidList.getAccount());
	}
	
	@Test
	public void testUpdateBidList() {
		BidList existingBid = new BidList(1, "Test Account", "Test Type", 100.0);
		BidList updateBid = new BidList("Updated Account", "Updated Type", 200.0);
		
		when(bidListRepository.findById(1)).thenReturn(Optional.of(existingBid));
		when(bidListRepository.save(existingBid)).thenReturn(existingBid);
		
		BidList result = bidListService.updateBidList(1, updateBid);
		
		assertEquals("Updated Account", result.getAccount());
		assertEquals("Updated Type", result.getType());
		assertEquals(200.0, result.getBidquantity());
	}
	
	@Test
	public void testDeleteBidList() {
		when(bidListRepository.existsById(1)).thenReturn(true);
		bidListService.deleteBidList(1);
		verify(bidListRepository, times(1)).deleteById(1);
	}
	
	@Test
	public void testDeleteBidList_NotFound() {
		when(bidListRepository.existsById(1)).thenReturn(false);
		assertThrows(EntityNotFoundException.class, () -> bidListService.deleteBidList(1));
	}

}
