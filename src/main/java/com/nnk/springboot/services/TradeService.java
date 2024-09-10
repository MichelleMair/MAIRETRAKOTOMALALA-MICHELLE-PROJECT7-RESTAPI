package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

@Service
public class TradeService {

	@Autowired
	private TradeRepository tradeRepository;

	public List<Trade> getAllTrades() {
		return tradeRepository.findAll();
	}

	public Optional<Trade> getTradeById(Integer trade_id) {
		return tradeRepository.findById(trade_id);
	}

	public Trade saveTrade(Trade trade) {
		return tradeRepository.save(trade);
	}

	public void deleteTrade(Integer trade_id) {
		tradeRepository.deleteById(trade_id);
	}
}
