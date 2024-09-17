package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

import jakarta.persistence.EntityNotFoundException;

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
	
	public Trade updateTrade(Integer trade_id, Trade updatedTrade) {
		return tradeRepository.findById(trade_id)
				.map(trade -> {
					trade.setAccount(updatedTrade.getAccount());
					trade.setType(updatedTrade.getType());
					trade.setBuy_quantity(updatedTrade.getBuy_quantity());
					return tradeRepository.save(trade);
				})
				.orElseThrow(() -> new EntityNotFoundException("Trade with id " + trade_id + " not found"));
	}

	public void deleteTrade(Integer trade_id) {
		if(tradeRepository.existsById(trade_id)) {
			tradeRepository.deleteById(trade_id);
		} else {
			throw new EntityNotFoundException("Trade with id " + trade_id + " not found");
		}
	}
}
