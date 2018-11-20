package com.gail.dao;

import java.util.List;

import com.gail.model.Ticker;

public interface TickerDao extends GenericDao<Ticker, Long> {

	public List<Ticker> getTickersByKey(String payerKey);
	
	public List<Ticker> getTickerDetails();
	public Ticker saveTickerDta(Ticker ticker);
	public Ticker getTickerById(Integer tickerId);

}
