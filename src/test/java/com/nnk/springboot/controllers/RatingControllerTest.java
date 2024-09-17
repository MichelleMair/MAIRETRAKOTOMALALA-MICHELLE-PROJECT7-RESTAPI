package com.nnk.springboot.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;

@ExtendWith(MockitoExtension.class)
public class RatingControllerTest {

	private MockMvc mockMvc;

	@Mock
	private RatingService ratingService;

	@InjectMocks
	private RatingController ratingController;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(ratingController).build();
	}

	@Test
	public void testHome() throws Exception {
		when(ratingService.getAllRatings()).thenReturn(List.of(new Rating()));

		mockMvc.perform(get("/rating/list")).andExpect(status().isOk()).andExpect(model().attributeExists("ratings"))
				.andExpect(view().name("rating/list"));
	}

	@Test
	public void testAddRatingForm() throws Exception {
		mockMvc.perform(get("/rating/add")).andExpect(status().isOk()).andExpect(view().name("rating/add"));
	}

	
	@Test
	public void testUpdateRating() throws Exception {
		Rating rating = new Rating("Moody's", "S&P", "Fitch", 1);
		
		when(ratingService.updateRating(Mockito.anyInt(), Mockito.any(Rating.class))).thenReturn(rating);
		
		mockMvc.perform(post("/rating/update/1")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("moodys_rating", "Moody's")
				.param("sandprating", "S&P")
				.param("fitch_rating", "Fitch")
				.param("order_number", "1"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/rating/list"));
		
		verify(ratingService).updateRating(Mockito.anyInt(), Mockito.any(Rating.class));
	}

	@Test
	public void testAddNewRating() {
		// ARRANGE
		Rating rating = new Rating("Moody's", "S&P", "Fitch", 1);
		when(ratingService.saveRating(any(Rating.class))).thenReturn(rating);

		Model model = new ExtendedModelMap();
		BindingResult bindingResult = mock(BindingResult.class);

		// ACT
		String view = ratingController.validate(rating, bindingResult, model);

		// ASSERT (vérifications)
		assertEquals("redirect:/rating/list", view);
		verify(ratingService, times(1)).saveRating(rating);

	}

}
