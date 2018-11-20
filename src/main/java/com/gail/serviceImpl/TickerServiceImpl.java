package com.gail.serviceImpl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gail.dao.TickerDao;
import com.gail.model.Ticker;
import com.gail.responseData.TickerData;
import com.gail.responseData.TickerDataList;
import com.gail.service.TickerService;
import com.gail.utility.Constants;
import com.gail.utility.ErrorDetails;
import com.gail.utility.ExceptionUtil;
import com.gail.utility.GailNominationServiceException;
import com.gail.utility.JsonParseUtil;
import com.gail.utility.Util;

import jersey.repackaged.com.google.common.collect.Lists;

@Service("tickerService")
public class TickerServiceImpl extends GenericServiceImpl<Ticker, Long> implements TickerService {

	@Autowired
	TickerDao tickerDao;

	@Override
	public TickerDataList getTickerDetail() throws GailNominationServiceException {
		TickerDataList tickerList = new TickerDataList();
		try {
			List<TickerData> list = Lists.newArrayList();
			List<Ticker> tickerModel = tickerDao.getTickerDetails();

			for (Ticker model : tickerModel) {
				TickerData tickerData = new TickerData();
				tickerData.setTickerText(model.getTickerText());
				tickerData.setTickerId(model.getTickerId().toString());
				tickerData.setStart(model.getStartDate().toString());
				tickerData.setEnd(model.getEndDate().toString());
				list.add(tickerData);

			}

			tickerList.setDataList(list);

		} catch (Throwable ex) {

			ExceptionUtil
					.handleException(
							new ErrorDetails(Constants.GET_TICKER_ERROR, Constants.ERROR_TYPE_CODE_INTERNAL,
									Constants.ERROR_TYPE_ERROR, "Error while getting ticker."),
							ex, tickerList.toString());
		}
		return tickerList;

	}

	@Override
	public String saveTickerData(TickerDataList tickerData) throws GailNominationServiceException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Ticker tickerModel = null;

		try {

			if (tickerData.getTickerId() == null) {
				tickerModel = new Ticker();
				tickerModel.setStartDate(new Timestamp(simpleDateFormat.parse(tickerData.getStartDate()).getTime()));
				tickerModel.setEndDate(new Timestamp(simpleDateFormat.parse(tickerData.getEndDate()).getTime()));
				tickerModel.setTickerText(tickerData.getTickerText());
				tickerModel.setPayerId(String.join(",", tickerData.getPayerId()));
				tickerModel.setStatus(Constants.ACTIVE_STATUS);
				tickerModel.setCreatedDate(Util.getCurrentUTCDate());
				tickerModel.setUpdatedDate(Util.getCurrentUTCDate());
				tickerModel.setUpdatedBy(Integer.parseInt(tickerData.getUserId()));
				tickerModel.setCreatedBy(Integer.parseInt(tickerData.getUserId()));
				tickerModel = tickerDao.saveTickerDta(tickerModel);
			} else {
				tickerModel = tickerDao.getTickerById(Integer.parseInt(tickerData.getTickerId()));
				tickerModel.setTickerText(tickerData.getTickerText());
				tickerModel.setStartDate(new Timestamp(simpleDateFormat.parse(tickerData.getStartDate()).getTime()));
				if(tickerData.getStatus()!=null){
				tickerModel.setStatus(Integer.parseInt(tickerData.getStatus()));
				}
				tickerModel.setEndDate(new Timestamp(simpleDateFormat.parse(tickerData.getEndDate()).getTime()));
				tickerModel.setUpdatedDate(Util.getCurrentUTCDate());
				tickerModel.setUpdatedBy(Integer.parseInt(tickerData.getUserId()));
				tickerModel = tickerDao.saveTickerDta(tickerModel);
			}

		} catch (Throwable ex) {

			ExceptionUtil
					.handleException(
							new ErrorDetails(Constants.SAVING_TICKER_DATA_ERROR, Constants.ERROR_TYPE_CODE_INTERNAL,
									Constants.ERROR_TYPE_ERROR, "Error While Saving Ticker."),
							ex, tickerData.toString());
		}

		String json = JsonParseUtil.getJsonStringFromObject(tickerModel);
		return json;
	}

}
