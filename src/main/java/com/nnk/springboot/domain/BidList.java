package com.nnk.springboot.domain;

import java.sql.Timestamp;

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
@Table(name = "bidlist")
public class BidList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bidlist_id")
	private Integer bidlist_id;

	@NotBlank(message = "")
	@NotEmpty(message = "Account is mandatory")
	private String account;

	@NotBlank(message = "")
	@NotEmpty(message = "Type is mandatory")
	private String type;

	@NotNull(message = "Bid Quantity is mandatory")
	@Min(value = 1, message = "Bid Quantity must be positive and higher than 0")
	private Double bidquantity;

	private Double askquantity;
	private Double bid;
	private Double ask;

	private String benchmark;
	private Timestamp bidlist_date;
	private String commentary;
	private String security;
	private String status;
	private String trader;
	private String book;
	private String creation_name;
	private Timestamp creation_date;
	private String revision_name;
	private Timestamp revision_date;
	private String deal_name;
	private String deal_type;
	private String sourcelist_id;
	private String side;

	// GETTERS & SETTERS

	public Integer getBidlist_id() {
		return bidlist_id;
	}

	public void setBidlist_id(Integer bidlist_id) {
		this.bidlist_id = bidlist_id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getBidquantity() {
		return bidquantity;
	}

	public void setBidquantity(Double bidquantity) {
		this.bidquantity = bidquantity;
	}

	public Double getAskquantity() {
		return askquantity;
	}

	public void setAskquantity(Double askquantity) {
		this.askquantity = askquantity;
	}

	public Double getBid() {
		return bid;
	}

	public void setBid(Double bid) {
		this.bid = bid;
	}

	public Double getAsk() {
		return ask;
	}

	public void setAsk(Double ask) {
		this.ask = ask;
	}

	public String getBenchmark() {
		return benchmark;
	}

	public void setBenchmark(String benchmark) {
		this.benchmark = benchmark;
	}

	public Timestamp getBidlist_date() {
		return bidlist_date;
	}

	public void setBidlist_date(Timestamp bidlist_date) {
		this.bidlist_date = bidlist_date;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrader() {
		return trader;
	}

	public void setTrader(String trader) {
		this.trader = trader;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public String getCreation_name() {
		return creation_name;
	}

	public void setCreation_name(String creation_name) {
		this.creation_name = creation_name;
	}

	public Timestamp getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Timestamp creation_date) {
		this.creation_date = creation_date;
	}

	public String getRevision_name() {
		return revision_name;
	}

	public void setRevision_name(String revision_name) {
		this.revision_name = revision_name;
	}

	public Timestamp getRevision_date() {
		return revision_date;
	}

	public void setRevision_date(Timestamp revision_date) {
		this.revision_date = revision_date;
	}

	public String getDeal_name() {
		return deal_name;
	}

	public void setDeal_name(String deal_name) {
		this.deal_name = deal_name;
	}

	public String getDeal_type() {
		return deal_type;
	}

	public void setDeal_type(String deal_type) {
		this.deal_type = deal_type;
	}

	public String getSourcelist_id() {
		return sourcelist_id;
	}

	public void setSourcelist_id(String sourcelist_id) {
		this.sourcelist_id = sourcelist_id;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	// All args constructor
	public BidList(Integer bidlist_id, @NotBlank(message = "Account is mandatory") String account,
			@NotBlank(message = "Type is mandatory") String type,
			@NotBlank(message = "Bid Quantity is mandatory") Double bidquantity, Double askquantity, Double bid,
			Double ask, String benchmark, Timestamp bidlist_date, String commentary, String security, String status,
			String trader, String book, String creation_name, Timestamp creation_date, String revision_name,
			Timestamp revision_date, String deal_name, String deal_type, String sourcelist_id, String side) {
		super();
		this.bidlist_id = bidlist_id;
		this.account = account;
		this.type = type;
		this.bidquantity = bidquantity;
		this.askquantity = askquantity;
		this.bid = bid;
		this.ask = ask;
		this.benchmark = benchmark;
		this.bidlist_date = bidlist_date;
		this.commentary = commentary;
		this.security = security;
		this.status = status;
		this.trader = trader;
		this.book = book;
		this.creation_name = creation_name;
		this.creation_date = creation_date;
		this.revision_name = revision_name;
		this.revision_date = revision_date;
		this.deal_name = deal_name;
		this.deal_type = deal_type;
		this.sourcelist_id = sourcelist_id;
		this.side = side;
	}

	public BidList(@NotBlank(message = "Account is mandatory") String account,
			@NotBlank(message = "Type is mandatory") String type,
			@NotBlank(message = "Bid Quantity is mandatory") Double bidquantity) {
		super();
		this.account = account;
		this.type = type;
		this.bidquantity = bidquantity;
	}

	// No Args constructor
	public BidList() {
		super();
		// TODO Auto-generated constructor stub
	}

}
