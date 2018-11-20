package com.gail.daoImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gail.dao.TickerDao;
import com.gail.model.Ticker;
import com.gail.utility.Constants;
import com.gail.utility.Util;

@Repository("tickerDao")
public class TickerDaoImpl extends GenericDaoImpl<Ticker, Long> implements TickerDao {

	private static final Logger logger = LoggerFactory.getLogger(TickerDao.class);

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Ticker> getTickersByKey(String payerKey) {

		Criteria cr = currentSession().createCriteria(Ticker.class);
		cr.add(Restrictions.or(Restrictions.like("payerId", "%" + payerKey + "%"),
				Restrictions.like("payerId", "All")));
		cr.add(Restrictions.ge("endDate", new Timestamp(
				Util.getCurrentUTCDate().getTime() + Constants.MINUTES * Constants.SECOND * Constants.MILLISECONDS)));
		cr.add(Restrictions.lt("startDate", new Timestamp(
				Util.getCurrentUTCDate().getTime() + Constants.MINUTES * Constants.SECOND * Constants.MILLISECONDS)));
		cr.add(Restrictions.eq("status", Constants.ACTIVE_STATUS_FOR_TICKER));
		List<Ticker> tickerModelList = (ArrayList<Ticker>) cr.list();

		if (!tickerModelList.isEmpty()) {
			logger.info("Data found for tickerId  {} ", payerKey);
			return tickerModelList;
		}
		logger.info("No Data found for tickerId {}  ", payerKey);
		return null;

	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Ticker> getTickerDetails() {

		Criteria cr = currentSession().createCriteria(Ticker.class);
		cr.add(Restrictions.eq("status", Constants.ACTIVE_STATUS_FOR_TICKER));
		cr.addOrder(Order.desc("endDate"));
		List<Ticker> tickerList = cr.list();

		if (tickerList.isEmpty()) {
			logger.info("No ticker found  {}");
		}
		return tickerList;

	}
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public Ticker saveTickerDta(Ticker ticker) {
	currentSession().saveOrUpdate(ticker); 
		logger.info("Ticker detail saved  for ticker  {} ", ticker.getTickerText());
		return ticker;
	
	}
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public Ticker getTickerById(Integer tickerId) {
		Ticker tickerModel = null;
		Criteria cr = currentSession().createCriteria(Ticker.class);
		cr.add(Restrictions.eq("tickerId", tickerId));
		tickerModel = (Ticker) cr.uniqueResult();
		if (tickerModel != null) {
			logger.info("Data found for ticker  {} ", tickerId);
			return tickerModel;
		}
		logger.info("No Data found for ticker {}  ", tickerId);
		return null;
	}
	
	
	
	
	
	
	
	
	
	

}
