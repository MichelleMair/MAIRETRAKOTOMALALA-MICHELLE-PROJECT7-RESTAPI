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

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTests {
	
	@Mock
	private RatingRepository ratingRepository;
	
	@InjectMocks
	private RatingService ratingService;
	
	@Test
	public void testGetRatingById() {
		Rating rating = new Rating(1, "MoodysRating", "SandPRating", "FitchRating", 1);
		
		when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
		
		Optional<Rating> result = ratingService.getRatingById(1);
		
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getId());
	}
	
	
	@Test
	public void testSaveRating() {
		Rating rating = new Rating("MoodysRating", "SandPRating", "FitchRating", 1);
		when(ratingRepository.save(rating)).thenReturn(rating);
		
		Rating savedRating = ratingService.saveRating(rating);
		
		assertNotNull(savedRating);
		assertEquals("MoodysRating", savedRating.getMoodys_rating());
	}
	
	@Test
	public void testUpdateRating() {
		Rating existingRating = new Rating(1, "MoodysRating", "SandPRating", "FitchRating", 1);
		Rating updatedRating = new Rating("MoodysUpdated", "SandPUpdated", "FitchUpdated", 5);
		
		when(ratingRepository.findById(1)).thenReturn(Optional.of(existingRating));
		when(ratingRepository.save(existingRating)).thenReturn(existingRating);
		
		Rating result = ratingService.updateRating(1, updatedRating);
		assertEquals("MoodysUpdated", result.getMoodys_rating());
		assertEquals("SandPUpdated", result.getSandprating());
		assertEquals("FitchUpdated", result.getFitch_rating());
		assertEquals(5, result.getOrder_number());
	}
	
	@Test
	public void testDeleteRating() {
		when(ratingRepository.existsById(1)).thenReturn(true);
		
		ratingService.deleteRating(1);
		
		verify(ratingRepository, times(1)).deleteById(1);
	}
	
	@Test
	public void testDeleteRating_NotFound() {
		when(ratingRepository.existsById(1)).thenReturn(false);
		assertThrows(EntityNotFoundException.class, () -> ratingService.deleteRating(1));
	}

}
