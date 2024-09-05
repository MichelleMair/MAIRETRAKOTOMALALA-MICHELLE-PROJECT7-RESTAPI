package com.nnk.springboot.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;

@Controller
public class BidListController {

	@Autowired
	private BidListService bidListService;

	// Call service find all bids to show to the view
	@GetMapping("/bidList/list")
	public String home(Model model) {
		model.addAttribute("bidLists", bidListService.getAllBidLists());
		return "bidList/list";
	}

	@GetMapping("/bidList/add")
	public String addBidForm(BidList bid) {
		return "bidList/add";
	}

	// Check data valid and save to db, after saving return bid list
	@PostMapping("/bidList/validate")
	public String validate(@Valid BidList bid, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "bidList/add";
		}
		bidListService.saveBidList(bid);
		return "redirect:/bidList/list";
	}

	// Get Bid by Id and to model then show to the form
	@GetMapping("/bidList/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Optional<BidList> bidList = bidListService.getBidListById(id);
		if (bidList.isPresent()) {
			model.addAttribute("bidList", bidList.get());
			return "bidList/update";
		} else {
			return "redirect:/bidList/list";
		}
	}

	// Check required fields, if valid call service to update Bid and return
	// list Bid
	@PostMapping("/bidList/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("bidList", bidList);
			return "bidList/update";
		}
		bidList.setBidListId(id);
		bidListService.saveBidList(bidList);
		return "redirect:/bidList/list";
	}

	// Find Bid by Id and delete the bid, return to Bid list
	@GetMapping("/bidList/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		bidListService.deleteBidList(id);
		return "redirect:/bidList/list";
	}
}
