package com.nnk.springboot.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
		when(bidListService.getAllBidLists()).thenReturn(List.of(new BidList(1,"Account Test", "Type Test", 10d)));

		mockMvc.perform(get("/bidlist/list"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("bidlists"))
		.andExpect(view().name("bidlist/list"));
		
		verify(bidListService, times(1)).getAllBidLists();
	}

	@Test
	public void testAddBidForm() throws Exception {
		mockMvc.perform(get("/bidlist/add"))
		.andExpect(status().isOk())
		.andExpect(view().name("bidlist/add"))
		.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testUpdateBid() throws Exception {
		BidList bid = new BidList(1, "Account Test", "Type Test", 10d);
		when(bidListService.getBidListById(1)).thenReturn(Optional.of(bid));
		
		mockMvc.perform(get("/bidlist/update/1"))
		.andExpect(status().isOk())
		.andExpect(view().name("bidlist/update"))
		.andExpect(model().attributeExists("bidlist"));
		
		mockMvc.perform(post("/bidlist/update/1")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("account", "AccountTest")
				.param("type", "Type test")
				.param("bidquantity", "10d"))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/bidlist/list"));

		
		verify(bidListService, times(1)).getBidListById(1);
		verify(bidListService, times(1)).updateBidList(Mockito.anyInt(), Mockito.any(BidList.class));
	}

	@Test
	public void testDeleteBid() throws Exception {
		mockMvc.perform(get("/bidlist/delete/1"))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/bidlist/list"));
		
		verify(bidListService, times(1)).deleteBidList(1);
	}

}
