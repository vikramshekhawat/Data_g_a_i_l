package com.gail.daoImpl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.DateType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gail.dao.UserDao;
import com.gail.model.User;
import com.gail.responseData.DownloadUserDTO;
import com.gail.utility.Constants;
import com.gail.utility.QueryConstants;

@Repository("userDao")
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

	public static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	@Transactional
	public User fetchUserByUserName(String userName) {
		Query query = currentSession().createQuery(QueryConstants.userExists).setParameter("userName", userName);
		if (query.list().size() > 0) {
			return (User) query.list().get(0);
		} else {
			return null;
		}
	}

	public String getQuery() {

		StringBuffer sqlQuery = new StringBuffer();
		sqlQuery.append(
				"SELECT pt.payer_key as payer_id, pt.payer_name, uc.contact_value as 'primary_email_id', (SELECT GROUP_CONCAT(us.contact_value)");
		sqlQuery.append(
				" FROM  user_contacts us, user u WHERE us.user_id = u.user_id and u.payer_id = pt.payer_id and priority = 2) as 'secondary_email_id1',");
		sqlQuery.append(
				" (SELECT GROUP_CONCAT(us.contact_value)  FROM  user_contacts us, user u WHERE us.user_id = u.user_id and u.payer_id = pt.payer_id");
		sqlQuery.append(
				" and priority = 3) as 'secondary_email_id2', (SELECT GROUP_CONCAT(us.contact_value)  FROM  user_contacts us, user u");
		sqlQuery.append(
				" WHERE us.user_id = u.user_id and u.payer_id = pt.payer_id and priority = 4) as 'secondary_email_id3', ");
		sqlQuery.append(
				" (SELECT GROUP_CONCAT(us.contact_value)  FROM  user_contacts us, user u WHERE us.user_id = u.user_id and u.payer_id = pt.payer_id");
		sqlQuery.append(
				" and priority = 5) as 'secondary_email_id4', pt.cutofftime as 'cutoff_time', r.region_name as 'region', ut.created_date");
		sqlQuery.append(" FROM user_contacts uc, user ut, payer pt, region r");
		sqlQuery.append(
				" WHERE uc.user_id = ut.user_id AND pt.payer_id = ut.payer_id AND r.region_id = pt.region_id AND priority = 1 AND uc.status = ");
		sqlQuery.append(+Constants.ACTIVE_STATUS + " AND ut.status = " + Constants.ACTIVE_STATUS);
		String query = sqlQuery.toString();
		logger.info("First Query for getting user details  - {}", query);
		return sqlQuery.toString();
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<DownloadUserDTO> getUserReport(String query) {
		SQLQuery queryto = currentSession().createSQLQuery(query);
		queryto.addScalar("payer_id", BigIntegerType.INSTANCE);
		queryto.addScalar("payer_name", StringType.INSTANCE);
		queryto.addScalar("primary_email_id", StringType.INSTANCE);
		queryto.addScalar("secondary_email_id1", StringType.INSTANCE);
		queryto.addScalar("secondary_email_id2", StringType.INSTANCE);
		queryto.addScalar("secondary_email_id3", StringType.INSTANCE);
		queryto.addScalar("secondary_email_id4", StringType.INSTANCE);
		queryto.addScalar("cutoff_time", StringType.INSTANCE);
		queryto.addScalar("region", StringType.INSTANCE);
		queryto.addScalar("created_date", DateType.INSTANCE);
		System.out.println(query);

		List<DownloadUserDTO> downloadUserDTOList = queryto
				.setResultTransformer(Transformers.aliasToBean(DownloadUserDTO.class)).list();

		return downloadUserDTOList;
	}

	@Transactional
	public User getPayerByUserId(BigInteger userId) {

		Query query = currentSession().createQuery(QueryConstants.getUserById).setParameter("userId", userId);
		if (query.list().size() > 0) {
			return (User) query.list().get(0);
		} else {
			return null;
		}

	}
	
	@Transactional
	public User getUserByUserName(String userName) {
		User user = null;
		Criteria cr = currentSession().createCriteria(User.class);
		cr.add(Restrictions.eq("userName", userName));
		cr.add(Restrictions.eq("status", Constants.ACTIVE_STATUS));
		user = (User) cr.uniqueResult();
		if (user != null) {
			logger.info("user exist for userName  {} ", userName);
			return user;
		}
		logger.info("user not exist for userName  {}  ", userName);
		return null;

	}

}
