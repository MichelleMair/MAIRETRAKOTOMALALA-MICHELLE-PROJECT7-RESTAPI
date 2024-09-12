package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "rating")
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@NotEmpty(message = "Moody's rating is mandatory")
	@NotBlank(message = "The field must not contain only blankspaces")
	@Column(name = "moodys_rating", nullable = false, length= 255)
	private String moodys_rating;
	
	@NotEmpty(message = "S&P Rating is mandatory")
	@NotBlank(message = "The field must not contain only blankspaces")
	@Column(name = "sandprating", nullable = false, length= 255)
	private String sandprating;
	
	@NotEmpty(message = "Fitch Rating is mandatory")
	@NotBlank(message = "The field must not contain only blankspaces")
	@Column(name = "fitch_rating", nullable = false, length= 255)
	private String fitch_rating;
	
	@NotNull(message = "Order number is mandatory")
	@Column(name = "order_number")
	@Min(value = 1, message = "Order number must be positive and higher than 0")
	private Integer order_number;

	
	//GETTERS & SETTERS
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMoodys_rating() {
		return moodys_rating;
	}

	public void setMoodys_rating(String moodys_rating) {
		this.moodys_rating = moodys_rating;
	}

	public String getSandprating() {
		return sandprating;
	}

	public void setSandprating(String sandprating) {
		this.sandprating = sandprating;
	}

	public String getFitch_rating() {
		return fitch_rating;
	}

	public void setFitch_rating(String fitch_rating) {
		this.fitch_rating = fitch_rating;
	}

	public Integer getOrder_number() {
		return order_number;
	}

	public void setOrder_number(Integer order_number) {
		this.order_number = order_number;
	}

	public Rating() {
		super();
	}

	public Rating(
			@NotEmpty(message = "Moody's rating is mandatory") @NotBlank(message = "The field must not contain only blankspaces") String moodys_rating,
			@NotEmpty(message = "S&P Rating is mandatory") @NotBlank(message = "The field must not contain only blankspaces") String sandprating,
			@NotEmpty(message = "Fitch Rating is mandatory") @NotBlank(message = "The field must not contain only blankspaces") String fitch_rating,
			@NotNull(message = "Order number is mandatory") @Min(value = 1, message = "Order number must be positive and higher than 0") Integer order_number) {
		super();
		this.moodys_rating = moodys_rating;
		this.sandprating = sandprating;
		this.fitch_rating = fitch_rating;
		this.order_number = order_number;
	}

	public Rating(Integer id,
			@NotEmpty(message = "Moody's rating is mandatory") @NotBlank(message = "The field must not contain only blankspaces") String moodys_rating,
			@NotEmpty(message = "S&P Rating is mandatory") @NotBlank(message = "The field must not contain only blankspaces") String sandprating,
			@NotEmpty(message = "Fitch Rating is mandatory") @NotBlank(message = "The field must not contain only blankspaces") String fitch_rating,
			@NotNull(message = "Order number is mandatory") @Min(value = 1, message = "Order number must be positive and higher than 0") Integer order_number) {
		super();
		this.id = id;
		this.moodys_rating = moodys_rating;
		this.sandprating = sandprating;
		this.fitch_rating = fitch_rating;
		this.order_number = order_number;
	}

}
