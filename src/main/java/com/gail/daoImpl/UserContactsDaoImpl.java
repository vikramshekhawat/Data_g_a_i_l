package com.gail.daoImpl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gail.dao.UserContactsDao;
import com.gail.model.UserContacts;
import com.gail.utility.Constants;
import com.gail.utility.QueryConstants;

@Repository("userContactsDao")
public class UserContactsDaoImpl extends GenericDaoImpl<UserContacts, Long> implements UserContactsDao {
	private static final Logger logger = LoggerFactory.getLogger(UserContactsDao.class);

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<UserContacts> getContactsByUserId(BigInteger userId) {
		logger.info("fetching contacts based on user id {}", userId);
		Query query = currentSession().createQuery(QueryConstants.getContacts).setParameter("userId", userId);
		if (query.list().size() > 0) {
			return (List<UserContacts>) query.list();
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<UserContacts> getContactListByPayerId(BigInteger payerId) {
		logger.info("fetching all the user contacts");
		Query query = currentSession().createSQLQuery(QueryConstants.GET_USER_CONTACT_BY_PAYER_Id)
				.addEntity(UserContacts.class).setParameter("payerId", payerId);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<UserContacts> getContactDetail(BigInteger userId) {
		Criteria cr = currentSession().createCriteria(UserContacts.class);
		cr.add(Restrictions.eq("userId", userId));
		cr.add(Restrictions.eq("status", Constants.ACTIVE_USER_CONTACTS));
		List<UserContacts> contactModel = cr.list();
		if (!contactModel.isEmpty()) {
			logger.info(" contacts Found for  userId {} ", userId);
		} else
			logger.info(" No contacts Found for  userId {} ", userId);
		return contactModel;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<UserContacts> getContactDetailByPayerKey(String payerKey) {
		Query query = currentSession().createSQLQuery(QueryConstants.GET_USER_CONTACT_BY_PAYER_KEY)
				.addEntity(UserContacts.class).setParameter("payerKey", payerKey);
		return query.list();
	}

}
