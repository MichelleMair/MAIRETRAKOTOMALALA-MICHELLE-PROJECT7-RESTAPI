package com.nnk.springboot.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;

@ExtendWith(MockitoExtension.class)
public class BidListControllerTest {

	private MockMvc mockMvc;

	@Mock
	private BidListService bidListService;

	@InjectMocks
	private BidListController bidListController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(bidListController).build();
	}

	@Test
	public void testHome() throws Exception {
		when(bidListService.getAllBidLists()).thenReturn(List.of(new BidList("Account Test", "Type Test", 10d)));

		mockMvc.perform(get("/bidList/list")).andExpect(status().isOk()).andExpect(model().attributeExists("bidLists"))
				.andExpect(view().name("bidList/list"));
	}

	@Test
	public void testAddBidForm() throws Exception {
		mockMvc.perform(get("/bidList/add")).andExpect(status().isOk()).andExpect(view().name("bidList/add"));
	}

	@Test
	public void testUpdateBid() throws Exception {
		BidList bid = new BidList("Account Test", "Type Test", 10d);
		BindingResult bindingResult = Mockito.mock(BindingResult.class);

		when(bindingResult.hasErrors()).thenReturn(false);

		String view = bidListController.updateBid(1, bid, bindingResult, Mockito.mock(Model.class));

		assertEquals("redirect:/bidList/list", view);
	}

	@Test
	public void testDeleteBid() throws Exception {
		String view = bidListController.deleteBid(1, Mockito.mock(Model.class));
		assertEquals("redirect:/bidList/list", view);
	}

}
