package com.gail.service;

import com.gail.model.Ticker;
import com.gail.responseData.TickerDataList;
import com.gail.utility.GailNominationServiceException;

public interface TickerService extends GenericService<Ticker, Long> {

	public TickerDataList getTickerDetail() throws GailNominationServiceException;

	public String saveTickerData(TickerDataList tickerData) throws GailNominationServiceException;

}
