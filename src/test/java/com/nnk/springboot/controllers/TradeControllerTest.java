package com.nnk.springboot.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;

@ExtendWith(MockitoExtension.class)
public class TradeControllerTest {

	private MockMvc mockMvc;

	@Mock
	private TradeService tradeService;

	@InjectMocks
	private TradeController tradeController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(tradeController).build();
	}

	@Test
	public void testHome() throws Exception {
		when(tradeService.getAllTrades()).thenReturn(List.of(new Trade("Account1", "Type1")));

		mockMvc.perform(get("/trade/list")).andExpect(status().isOk()).andExpect(model().attributeExists("trades"))
				.andExpect(view().name("trade/list"));

	}

	@Test
	public void testAddTradeForm() throws Exception {

		mockMvc.perform(get("/trade/add")).andExpect(status().isOk()).andExpect(model().attributeExists("trade"))
				.andExpect(view().name("trade/add"));

	}

	@Test
	public void testValidate() throws Exception {
		Trade trade = new Trade("Account1", "Type1", 100.0);
		when(tradeService.saveTrade(any(Trade.class))).thenReturn(trade);

		mockMvc.perform(post("/trade/validate")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("account", "Account1")
				.param("type", "Type1")
				.param("buy_quantity", "100"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/trade/list"));

		verify(tradeService).saveTrade(any(Trade.class));
	}

	@Test
	public void testShowUpdateForm() throws Exception {
		Trade trade = new Trade("Account1", "Type1", 100.0);
		when(tradeService.getTradeById(anyInt())).thenReturn(Optional.of(trade));

		mockMvc.perform(get("/trade/update/1")).andExpect(status().isOk()).andExpect(model().attributeExists("trade"))
				.andExpect(view().name("trade/update"));
	}

	@Test
	public void testUpdateTrade() throws Exception {
		Trade trade = new Trade("Account1", "Type1", 100.0);

		when(tradeService.updateTrade(Mockito.anyInt(), Mockito.any(Trade.class))).thenReturn(trade);

		mockMvc.perform(post("/trade/update/1").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("account", "Account1")
				.param("type", "Type1")
				.param("buy_quantity", "100"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/trade/list"));

		verify(tradeService).updateTrade(Mockito.anyInt(), Mockito.any(Trade.class));
	}
	
	@Test
	public void testUpdateTradeWithValidationErrors() throws Exception {
		mockMvc.perform(post("/trade/update/1")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("account", "")
				.param("type", "Type1")
				.param("buy_quantity", "100"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasFieldErrors("trade", "account"))
		.andExpect(view().name("trade/update"));
	}

	@Test
	public void testDeleteBid() throws Exception {

		mockMvc.perform(get("/trade/delete/1")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/trade/list"));

		verify(tradeService).deleteTrade(1);
	}

}
