package com.gail.daoImpl;

import java.math.BigInteger;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gail.dao.PayerDao;
import com.gail.model.Payer;
import com.gail.utility.QueryConstants;

@Repository("payerDao")
public class PayerDaoImpl extends GenericDaoImpl<Payer, Long> implements PayerDao {

	public static final Logger logger = LoggerFactory.getLogger(PayerDaoImpl.class);

	@Transactional
	@Override
	public Payer fetchUserByPayerId(BigInteger payerId) {
		Query query = currentSession().createQuery(QueryConstants.payerExists).setParameter("payerId", payerId);
		if (query.list().size() > 0) {
			return (Payer) query.list().get(0);

		} else {
			return null;
		}
	}

	@Transactional
	@Override
	public Payer getPayerByKey(String payerKey) {

		Payer payerModel = null;
		Criteria cr = currentSession().createCriteria(Payer.class);
		cr.add(Restrictions.eq("payerKey", payerKey));
		payerModel = (Payer) cr.uniqueResult();
		if (payerModel != null) {
			logger.info("Data found for payerKey  {} ", payerKey);
			return payerModel;
		}
		logger.info("No Data found for payerKey {}  ", payerKey);
		return null;
	}

}
