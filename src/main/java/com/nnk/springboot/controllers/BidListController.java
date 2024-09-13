package com.nnk.springboot.controllers;

import java.security.Principal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;

import jakarta.validation.Valid;

@Controller
public class BidListController {
	
	private static final Logger logger = LoggerFactory.getLogger(BidListController.class);

	@Autowired
	private BidListService bidListService;

	// Call service find all bids to show to the view
	@GetMapping("/bidlist/list")
	public String home(Model model, Principal principal) {
		if(principal != null) {
			String username = principal.getName();
			model.addAttribute("username", username);
		}
		model.addAttribute("bidlists", bidListService.getAllBidLists());
		return "bidlist/list";
	}

	@GetMapping("/bidlist/add")
	public String addBidForm(Model model) {
		model.addAttribute("bidlist", new BidList());
		return "bidlist/add";
	}

	// Check data valid and save to db, after saving return bid list
	@PostMapping("/bidlist/validate")
	public String validate(@Valid BidList bid, BindingResult result, Model model) {
		
		logger.info("ACCOUNT : " + bid.getAccount());
		logger.info("TYPE: " + bid.getType());
		logger.info("BID QUANTITY: " + bid.getBidquantity());
		
		if (result.hasErrors()) {
			logger.error("Result for bidlist has errors ", result.getAllErrors());
			logger.info("Result error: ACCOUNT : " + bid.getAccount());
			logger.info("Result error: TYPE: " + bid.getType());
			logger.info("Result error: BID QUANTITY: " + bid.getBidquantity());
			
			model.addAttribute("bidlist", bid);
			
			return "bidlist/add";
		}
		logger.info("Saving ACCOUNT : " + bid.getAccount());
		logger.info("Saving TYPE: " + bid.getType());
		logger.info("Saving BID QUANTITY: " + bid.getBidquantity());
		bidListService.saveBidList(bid);
		return "redirect:/bidlist/list";
	}

	// Get Bid by Id and to model then show to the form
	@GetMapping("/bidlist/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Optional<BidList> bidList = bidListService.getBidListById(id);
		if (bidList.isPresent()) {
			model.addAttribute("bidlist", bidList.get());
			return "bidlist/update";
		} else {
			return "redirect:/bidlist/list";
		}
	}

	// Check required fields, if valid call service to update Bid and return
	// list Bid
	@PostMapping("/bidlist/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("bidlist", bidList);
			return "bidlist/update";
		}
		bidListService.updateBidList(id, bidList);
		return "redirect:/bidlist/list";
	}

	// Find Bid by Id and delete the bid, return to Bid list
	@GetMapping("/bidlist/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		bidListService.deleteBidList(id);
		return "redirect:/bidlist/list";
	}
}
