package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@Service
public class BidListService {

	@Autowired
	private BidListRepository bidListRepository;

	public List<BidList> getAllBidLists() {
		return bidListRepository.findAll();
	}

	public Optional<BidList> getBidListById(Integer bidListId) {
		return bidListRepository.findById(bidListId);
	}

	public BidList saveBidList(BidList bidList) {
		return bidListRepository.save(bidList);
	}

	public void deleteBidList(Integer bidListId) {
		bidListRepository.deleteById(bidListId);
	}
}
