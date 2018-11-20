package com.gail.daoImpl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigIntegerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gail.dao.VendorDao;
import com.gail.model.Vendor;
import com.gail.utility.Constants;
import com.gail.utility.ErrorDetails;
import com.gail.utility.ExceptionUtil;
import com.gail.utility.GailNominationServiceException;
import com.gail.validation.model.VendorUserModel;

@Repository("vendorDao")
public class VendorDaoImpl extends GenericDaoImpl<Vendor, Long> implements VendorDao {

	private static final Logger logger = LoggerFactory.getLogger(VendorDaoImpl.class);

	@SuppressWarnings("rawtypes")
	@Transactional
	@Override
	public Boolean isVendor(BigInteger userId) {
		Criteria cr = currentSession().createCriteria(Vendor.class);
		cr.add(Restrictions.eq("userId", userId));
		List list = cr.list();
		if (list.isEmpty() || list.size() < 1) {
			logger.info("No Data found for payerKey {}  ", userId);
			return false;
		} else
			return true;
	}

	@Transactional
	@Override
	public Vendor getVendorByMaterialCode(String materialCode) {

		Vendor vendorModel = null;
		Criteria cr = currentSession().createCriteria(Vendor.class);
		cr.add(Restrictions.eq("materialCode", materialCode));
		vendorModel = (Vendor) cr.uniqueResult();
		if (vendorModel != null) {
			logger.info("Data found for materialCode  {} ", materialCode);
			return vendorModel;
		}
		logger.info("No Data found for materialCode {}  ", materialCode);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public BigInteger getVendorUserId(String contractId) throws GailNominationServiceException {
		BigInteger UserId = null;
		String query;
		try {
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("select user_id from seller where material_code in(");
			sqlQuery.append(" Select material_code from material where material_id in(");
			sqlQuery.append(" select material_id from contracts where contract_id=" + "" + contractId + "))");
			query = sqlQuery.toString();
			SQLQuery queryto = currentSession().createSQLQuery(query);
			queryto.addScalar("user_id", BigIntegerType.INSTANCE);
			List<VendorUserModel> userModel = queryto.setResultTransformer(Transformers.aliasToBean(VendorUserModel.class)).list();
			
			if(userModel.isEmpty()){
				return BigInteger.ZERO;
			}

			for (VendorUserModel model : userModel) {
				UserId = model.getUser_id();
			}
		} catch (Throwable ex) {

			ExceptionUtil.handleException(
					new ErrorDetails(Constants.GET_SELLER_USER_ID_BY_CONTRACT_ID, Constants.ERROR_TYPE_CODE_INTERNAL,
							Constants.ERROR_TYPE_ERROR, "Error while fetching Seller User Id."),
					ex, String.valueOf(contractId));
		}

		return UserId;

	}

}
