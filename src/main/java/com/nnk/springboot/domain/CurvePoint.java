package com.nnk.springboot.domain;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "curvepoint")
public class CurvePoint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotNull(message = "Curve ID must not be null")
	@Column(name = "curve_id")
	@Min(value = 1, message = "Curve ID must be positive and higher than 0")
	private Integer curve_id;

	private Timestamp as_of_date;
	private Double term;
	private Double value;

	private Timestamp creation_date;

	// GETTERS AND SETTERS


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCurve_id() {
		return curve_id;
	}

	public void setCurve_id(Integer curve_id) {
		this.curve_id = curve_id;
	}

	public Timestamp getAs_of_date() {
		return as_of_date;
	}

	public void setAs_of_date(Timestamp as_of_date) {
		this.as_of_date = as_of_date;
	}

	public Double getTerm() {
		return term;
	}

	public void setTerm(Double term) {
		this.term = term;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Timestamp getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Timestamp creation_date) {
		this.creation_date = creation_date;
	}

	public CurvePoint() {
		super();
	}

	public CurvePoint(Integer id,
			@NotNull(message = "Curve ID is mandatory") @Min(value = 1, message = "Curve ID must be positive and higher than 0") Integer curve_id,
			Timestamp as_of_date, Double term, Double value, Timestamp creation_date) {
		super();
		this.id = id;
		this.curve_id = curve_id;
		this.as_of_date = as_of_date;
		this.term = term;
		this.value = value;
		this.creation_date = creation_date;
	}

	public CurvePoint(
			@NotNull(message = "Curve ID is mandatory") @Min(value = 1, message = "Curve ID must be positive and higher than 0") Integer curve_id,
			Double term, Double value) {
		super();
		this.curve_id = curve_id;
		this.term = term;
		this.value = value;
	}

	public CurvePoint(Integer id,
			@NotNull(message = "Curve ID must not be null") @Min(value = 1, message = "Curve ID must be positive and higher than 0") Integer curve_id,
			Double term, Double value) {
		super();
		this.id = id;
		this.curve_id = curve_id;
		this.term = term;
		this.value = value;
	}


}
