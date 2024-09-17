package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RatingService {

	@Autowired
	private RatingRepository ratingRepository;

	public List<Rating> getAllRatings() {
		return ratingRepository.findAll();
	}

	public Optional<Rating> getRatingById(Integer id) {
		return ratingRepository.findById(id);
	}

	public Rating saveRating(Rating rating) {
		return ratingRepository.save(rating);
	}
	
	
	public Rating updateRating(Integer id, Rating updatedRating) {
		return ratingRepository.findById(id)
				.map(rating -> {
					rating.setMoodys_rating(updatedRating.getMoodys_rating());
					rating.setSandprating(updatedRating.getSandprating());
					rating.setFitch_rating(updatedRating.getFitch_rating());
					rating.setOrder_number(updatedRating.getOrder_number());
					return ratingRepository.save(rating);
				})
				.orElseThrow(() -> new EntityNotFoundException("Rating with id " + id + " not found"));
	}

	public void deleteRating(Integer id) {
		if(ratingRepository.existsById(id)) {
			ratingRepository.deleteById(id);
		} else {
			throw new EntityNotFoundException("Rating with id " + id + " not found");
		}
	}
}
