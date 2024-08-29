package com.nnk.springboot.domain;

import javax.validation.constraints.NotBlank;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rating")
public class Rating {
	// TODO: Map columns in data table RATING with corresponding java fields

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotBlank(message = "Moody's Rating is mandatory")
	private String moodysRating;

	@NotBlank(message = "S & P Rating is mandatory")
	private String sandPRating;

	@NotBlank(message = "Fitch Rating is mandatory")
	private String fitchRating;

	@NotBlank(message = "Order number is mandatory")
	private Integer orderNumber;

	// GETTERS AND SETTERS
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMoodysRating() {
		return moodysRating;
	}

	public void setMoodysRating(String moodysRating) {
		this.moodysRating = moodysRating;
	}

	public String getSandPRating() {
		return sandPRating;
	}

	public void setSandPRating(String sandPRating) {
		this.sandPRating = sandPRating;
	}

	public String getFitchRating() {
		return fitchRating;
	}

	public void setFitchRating(String fitchRating) {
		this.fitchRating = fitchRating;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	// No args constructor
	public Rating() {
		super();
	}

	// All args constructor
	public Rating(Integer id, @NotBlank(message = "Moody's Rating is mandatory") String moodysRating,
			@NotBlank(message = "S & P Rating is mandatory") String sandPRating,
			@NotBlank(message = "Fitch Rating is mandatory") String fitchRating,
			@NotBlank(message = "Order number is mandatory") Integer orderNumber) {
		super();
		this.id = id;
		this.moodysRating = moodysRating;
		this.sandPRating = sandPRating;
		this.fitchRating = fitchRating;
		this.orderNumber = orderNumber;
	}

	// Constructor with moodysRating, sandRating, fitchRating and orderNumber args
	public Rating(@NotBlank(message = "Moody's Rating is mandatory") String moodysRating,
			@NotBlank(message = "S & P Rating is mandatory") String sandPRating,
			@NotBlank(message = "Fitch Rating is mandatory") String fitchRating,
			@NotBlank(message = "Order number is mandatory") Integer orderNumber) {
		super();
		this.moodysRating = moodysRating;
		this.sandPRating = sandPRating;
		this.fitchRating = fitchRating;
		this.orderNumber = orderNumber;
	}

}
