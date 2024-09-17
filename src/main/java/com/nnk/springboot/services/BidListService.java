package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

import jakarta.persistence.EntityNotFoundException;


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
	
	//Enregistrer un nouveau bidlist avec validation 
	public BidList saveBidList(BidList bidList) {
		return bidListRepository.save(bidList);
	}

	public BidList updateBidList(Integer bidlistId, BidList updatedBidlist) {
		return bidListRepository.findById(bidlistId)
				.map(bidList -> {
					bidList.setAccount(updatedBidlist.getAccount());
					bidList.setType(updatedBidlist.getType());
					bidList.setBidquantity(updatedBidlist.getBidquantity());
					return bidListRepository.save(bidList);
				})
				.orElseThrow(() -> new EntityNotFoundException("BidList with id " + bidlistId + " not found"));
	}

	public void deleteBidList(Integer bidListId) {
		if(bidListRepository.existsById(bidListId)) {
			bidListRepository.deleteById(bidListId);
		} else {
			throw new EntityNotFoundException("BidList with id " + bidListId + " not found");
		}
	}
}
