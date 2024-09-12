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
@Table(name = "trade")
public class Trade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "trade_id")
	private Integer trade_id;

	@NotEmpty(message = "Account is mandatory")
	@NotBlank(message = "The field must not contain only blankspaces")
	@Column(name = "account", nullable = false, length= 255)
	private String account;


	@NotEmpty(message = "Type is mandatory")
	@NotBlank(message = "The field must not contain only blankspaces")
	@Column(name = "type", nullable = false, length= 255)
	private String type;

	@NotNull(message = "Buy Quantity is mandatory")
	@Column(name ="buy_quantity")
	@Min(value = 1, message = "Buy Quantity must be positive and higher than 0")
	private Double buy_quantity;

	private Double sell_quantity;

	private Double buy_price;

	private Double sell_price;

	private Timestamp trade_date;

	private String security;

	private String status;

	private String trader;

	private String benchmark;

	private String book;

	private String creation_name;

	private Timestamp creation_date;

	private String revision_name;

	private Timestamp revision_date;

	private String deal_name;

	private String deal_type;

	private String sourcelist_id;

	private String side;

	
	//GETTERS & SETTERS
	public Integer getTrade_id() {
		return trade_id;
	}

	public void setTrade_id(Integer trade_id) {
		this.trade_id = trade_id;
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

	public Double getBuy_quantity() {
		return buy_quantity;
	}

	public void setBuy_quantity(Double buy_quantity) {
		this.buy_quantity = buy_quantity;
	}

	public Double getSell_quantity() {
		return sell_quantity;
	}

	public void setSell_quantity(Double sell_quantity) {
		this.sell_quantity = sell_quantity;
	}

	public Double getBuy_price() {
		return buy_price;
	}

	public void setBuy_price(Double buy_price) {
		this.buy_price = buy_price;
	}

	public Double getSell_price() {
		return sell_price;
	}

	public void setSell_price(Double sell_price) {
		this.sell_price = sell_price;
	}

	public Timestamp getTrade_date() {
		return trade_date;
	}

	public void setTrade_date(Timestamp trade_date) {
		this.trade_date = trade_date;
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

	public String getBenchmark() {
		return benchmark;
	}

	public void setBenchmark(String benchmark) {
		this.benchmark = benchmark;
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

	public Trade(
			@NotEmpty(message = "Account is mandatory") @NotBlank(message = "The field must not contain only blankspaces") String account,
			@NotEmpty(message = "Type is mandatory") @NotBlank(message = "The field must not contain only blankspaces") String type) {
		super();
		this.account = account;
		this.type = type;
	}

	public Trade(
			@NotEmpty(message = "Account is mandatory") @NotBlank(message = "The field must not contain only blankspaces") String account,
			@NotEmpty(message = "Type is mandatory") @NotBlank(message = "The field must not contain only blankspaces") String type,
			@NotNull(message = "Buy Quantity is mandatory") @Min(value = 1, message = "Buy Quantity must be positive and higher than 0") Double buy_quantity) {
		super();
		this.account = account;
		this.type = type;
		this.buy_quantity = buy_quantity;
	}

	public Trade() {
		super();
	}

	public Trade(Integer trade_id,
			@NotEmpty(message = "Account is mandatory") @NotBlank(message = "The field must not contain only blankspaces") String account,
			@NotEmpty(message = "Type is mandatory") @NotBlank(message = "The field must not contain only blankspaces") String type,
			@NotNull(message = "Buy Quantity is mandatory") @Min(value = 1, message = "Buy Quantity must be positive and higher than 0") Double buy_quantity,
			Double sell_quantity, Double buy_price, Double sell_price, Timestamp trade_date, String security,
			String status, String trader, String benchmark, String book, String creation_name, Timestamp creation_date,
			String revision_name, Timestamp revision_date, String deal_name, String deal_type, String sourcelist_id,
			String side) {
		super();
		this.trade_id = trade_id;
		this.account = account;
		this.type = type;
		this.buy_quantity = buy_quantity;
		this.sell_quantity = sell_quantity;
		this.buy_price = buy_price;
		this.sell_price = sell_price;
		this.trade_date = trade_date;
		this.security = security;
		this.status = status;
		this.trader = trader;
		this.benchmark = benchmark;
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
	

}
