package com.nnk.springboot.integration.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BidListIT {


	@Autowired
	private BidListRepository bidListRepository;

	@Test
	public void bidListTest() {
		BidList bid = new BidList("Account Test", "Type Test", 10d);

		// Save
		bid = bidListRepository.save(bid);
		assertNotNull(bid.getBidlist_id());
		assertEquals(10d, bid.getBidquantity(), 0.01);

		// Update
		bid.setBidquantity(20d);
		bid = bidListRepository.save(bid);
		assertEquals(20d, bid.getBidquantity(), 0.01);

		// Find
		List<BidList> listResult = bidListRepository.findAll();
		assertTrue(listResult.size() > 0);
		
		//find by id
		Optional<BidList> foundBid = bidListRepository.findById(bid.getBidlist_id());
		assertTrue(foundBid.isPresent());
		assertEquals(bid.getBidlist_id(), foundBid.get().getBidlist_id());

		// Delete
		Integer id = bid.getBidlist_id();
		bidListRepository.delete(bid);
		Optional<BidList> bidList = bidListRepository.findById(id);
		assertFalse(bidList.isPresent());
	}
	
}
