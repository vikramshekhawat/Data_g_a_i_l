package com.gail.daoImpl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gail.dao.NominationDao;
import com.gail.model.ContractText;
import com.gail.model.Nominations;
import com.gail.responseData.DownloadNominationDTO;
import com.gail.responseData.NominationDataDTO;
import com.gail.utility.Constants;

@Repository("nominationDao")
public class NominationDaoImpl extends GenericDaoImpl<Nominations, Long> implements NominationDao {

	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private static final Logger logger = LoggerFactory.getLogger(NominationDaoImpl.class);

	@Override
	public String getQueryForDisplay(Nominations nominationData) {

		StringBuffer sqlQuery = new StringBuffer();
		sqlQuery.append(
				"SELECT n.nomination_id,n.nominated_by,n.contract_id,n.gas_date,n.seller_redel_point,n.seller_updated_date,p.payer_name,u.user_id,p.payer_key,");
		sqlQuery.append("n.revision_no,m.contract_type,c.unit_of_measurements ,");
		sqlQuery.append("m.material_id ,m.material_code,m.material_desc,");
		sqlQuery.append("n.redel_point,n.del_point,n.updated_date,c.start_date ,c.end_date,");
		sqlQuery.append("c.customer_code,c.customer_description,c.contract_ref");
		sqlQuery.append(" FROM nominations n INNER JOIN contracts c");
		sqlQuery.append(" ON n.contract_id = c.contract_id");
		sqlQuery.append(" INNER JOIN material m ON c.material_id = m.material_id");
		sqlQuery.append("  INNER JOIN payer p ON c.payer_id = p.payer_id");
		sqlQuery.append("  INNER JOIN user u ON p.payer_id = u.payer_id");
		sqlQuery.append(" WHERE m.contract_type in ('" + nominationData.getContractType() + "')");
		if (nominationData.getContractRef() != null)
			sqlQuery.append(" AND c.contract_ref = " + nominationData.getContractRef());
		sqlQuery.append(" AND u.user_id = " + nominationData.getUserId());
		sqlQuery.append(" AND c.status = " + Constants.ACTIVE_STATUS);
		sqlQuery.append(" AND n.status = " + Constants.ACTIVE_STATUS);
		// sqlQuery.append(" AND
		if (nominationData.getGasDate() != null) {
			sqlQuery.append(" AND n.gas_date = '" + format.format(nominationData.getGasDate()) + "'");
			sqlQuery.append(
					" And '" + format.format(nominationData.getGasDate()) + "' between  c.start_date  and c.end_date");
		} else {
			sqlQuery.append(" AND n.gas_date between " + "'" + format.format(nominationData.getStartDate()) + "'");
			sqlQuery.append(" AND '" + format.format(nominationData.getEndDate()) + "'");
			sqlQuery.append(" AND n.gas_date between  c.start_date  and c.end_date");
		}
		sqlQuery.append(" AND n.nomination_id not in ( select n1.nomination_id from nominations n1, nominations n2");
		sqlQuery.append("  where n1.status = n2.status AND n1.status = 0 AND  n1.nomination_id < n2.nomination_id");
		sqlQuery.append("  AND n1.contract_id = n2.contract_id AND  n1.gas_date = n2.gas_date)");
		sqlQuery.append(
				" GROUP BY n.`status` , n.contract_id , n.gas_date, n.nominated_by, n.revision_no, n.redel_point,n.del_point, n.updated_date");
		// sqlQuery.append(" AND m.material_code in(select material_code from
		// seller where user_id="+ nominationBO.getUserId().toString()+")" );

		sqlQuery.append(" Union ");

		sqlQuery.append(
				"SELECT n.nomination_id,n.nominated_by,n.contract_id,n.gas_date,n.seller_redel_point,n.seller_updated_date,p.payer_name, u.user_id,p.payer_key,");
		sqlQuery.append("n.revision_no,m.contract_type,c.unit_of_measurements ,");
		sqlQuery.append("m.material_id ,m.material_code,m.material_desc,");
		sqlQuery.append("n.redel_point,n.del_point,n.updated_date,c.start_date ,c.end_date,");
		sqlQuery.append("c.customer_code,c.customer_description,c.contract_ref");
		sqlQuery.append(" FROM nominations n INNER JOIN contracts c");
		sqlQuery.append(" ON n.contract_id = c.contract_id");
		sqlQuery.append(" INNER JOIN material m ON c.material_id = m.material_id");
		sqlQuery.append("  INNER JOIN payer p ON c.payer_id = p.payer_id");
		sqlQuery.append("  INNER JOIN user u ON p.payer_id = u.payer_id");

		sqlQuery.append(" WHERE m.contract_type in ('" + nominationData.getContractType() + "')");
		if (nominationData.getContractRef() != null)
			sqlQuery.append(" AND c.contract_ref = " + nominationData.getContractRef());

		sqlQuery.append(" AND c.status = " + Constants.ACTIVE_STATUS);
		sqlQuery.append(" AND n.status = " + Constants.ACTIVE_STATUS);

		if (nominationData.getGasDate() != null) {
			sqlQuery.append(" AND n.gas_date = " + "'" + format.format(nominationData.getGasDate()) + "'");

		}

		else {
			sqlQuery.append(" AND n.gas_date between " + "'" + format.format(nominationData.getStartDate()) + "'");
			sqlQuery.append(" AND " + "'" + format.format(nominationData.getEndDate()) + "'");
		}
		sqlQuery.append(" AND m.material_code in(select material_code from seller where user_id="
				+ nominationData.getUserId().toString() + ")");

		String query = sqlQuery.toString();
		logger.info("First Query for getting nomination details  - {}", query);
		return sqlQuery.toString();

	}

	@Transactional
	@Override
	public List<NominationDataDTO> getNomination(String query) {
		SQLQuery queryto = currentSession().createSQLQuery(query);
		queryto.addScalar("user_id", BigIntegerType.INSTANCE);
		queryto.addScalar("seller_redel_point", BigDecimalType.INSTANCE);
		queryto.addScalar("gas_date", DateType.INSTANCE);
		queryto.addScalar("payer_name", StringType.INSTANCE);
		queryto.addScalar("payer_key", StringType.INSTANCE);
		queryto.addScalar("redel_point", BigDecimalType.INSTANCE);
		queryto.addScalar("del_point", BigDecimalType.INSTANCE);
		queryto.addScalar("customer_code", StringType.INSTANCE);
		queryto.addScalar("contract_ref", StringType.INSTANCE);
		// queryto.addScalar("contract_name", StringType.INSTANCE);
		queryto.addScalar("material_id", BigIntegerType.INSTANCE);
		queryto.addScalar("material_code", StringType.INSTANCE);
		queryto.addScalar("material_desc", StringType.INSTANCE);
		queryto.addScalar("nomination_id", BigIntegerType.INSTANCE);
		queryto.addScalar("revision_no", IntegerType.INSTANCE);
		queryto.addScalar("unit_of_measurements", StringType.INSTANCE);
		queryto.addScalar("contract_type", StringType.INSTANCE);
		queryto.addScalar("customer_description", StringType.INSTANCE);
		queryto.addScalar("contract_id", BigIntegerType.INSTANCE);
		queryto.addScalar("updated_Date", TimestampType.INSTANCE);
		queryto.addScalar("nominated_by", BigIntegerType.INSTANCE);
		queryto.addScalar("start_date", DateType.INSTANCE);
		queryto.addScalar("end_date", DateType.INSTANCE);
		queryto.addScalar("seller_updated_date", TimestampType.INSTANCE);

		System.out.println(query);
		@SuppressWarnings("unchecked")
		List<NominationDataDTO> nominationDataDTO = queryto
				.setResultTransformer(Transformers.aliasToBean(NominationDataDTO.class)).list();

		return nominationDataDTO;
	}

	@Override
	public String getQueryForNomination(Nominations nominationData) {

		StringBuffer sqlQuery = new StringBuffer();
		sqlQuery.append(
				"SELECT n.nomination_id,n.nominated_by,n.contract_id,n.gas_date,n.seller_redel_point,p.payer_name,u.user_id,p.payer_key,n.seller_updated_date,");
		sqlQuery.append("n.revision_no,m.contract_type,c.unit_of_measurements ,");
		sqlQuery.append("m.material_id ,m.material_code,m.material_desc,");
		sqlQuery.append("n.redel_point,n.del_point,n.updated_date,c.start_date ,c.end_date,");
		sqlQuery.append("c.customer_code,c.customer_description,c.contract_ref");
		sqlQuery.append(" FROM nominations n INNER JOIN contracts c");
		sqlQuery.append(" ON n.contract_id = c.contract_id");
		sqlQuery.append(" INNER JOIN material m ON c.material_id = m.material_id");
		sqlQuery.append("  INNER JOIN payer p ON c.payer_id = p.payer_id");
		sqlQuery.append("  INNER JOIN user u ON p.payer_id = u.payer_id");
		sqlQuery.append(" WHERE m.contract_type in ('" + nominationData.getContractType() + "')");
		if (nominationData.getContractRef() != null)
			sqlQuery.append(" AND c.contract_ref = " + nominationData.getContractRef());
		sqlQuery.append(" AND u.user_id = " + nominationData.getUserId());
		sqlQuery.append(" AND c.status = " + Constants.ACTIVE_STATUS);
		sqlQuery.append(" AND n.status = " + Constants.ACTIVE_STATUS);
		// sqlQuery.append(" AND
		if (nominationData.getGasDate() != null) {
			sqlQuery.append(" AND n.gas_date = '" + format.format(nominationData.getGasDate()) + "'");
			sqlQuery.append(
					" And '" + format.format(nominationData.getGasDate()) + "' between  c.start_date  and c.end_date");
		} else {
			sqlQuery.append(" AND n.gas_date between " + "'" + format.format(nominationData.getStartDate()) + "'");
			sqlQuery.append(" AND '" + format.format(nominationData.getEndDate()) + "'");
			sqlQuery.append(" AND n.gas_date between  c.start_date  and c.end_date");
		}
		sqlQuery.append(" AND n.nomination_id not in ( select n1.nomination_id from nominations n1, nominations n2");
		sqlQuery.append("  where n1.status = n2.status AND n1.status = 0 AND  n1.nomination_id < n2.nomination_id");
		sqlQuery.append("  AND n1.contract_id = n2.contract_id AND  n1.gas_date = n2.gas_date)");
		sqlQuery.append(
				" GROUP BY n.`status` , n.contract_id , n.gas_date, n.nominated_by, n.revision_no, n.redel_point,n.del_point, n.updated_date");
		// sqlQuery.append(" AND m.material_code in(select material_code from
		// seller where user_id="+ nominationBO.getUserId().toString()+")" );

		String query = sqlQuery.toString();
		logger.info("First Query for getting nomination details  - {}", query);
		return sqlQuery.toString();
	}

	@SuppressWarnings("unused")
	@Transactional
	@Override
	public String getContractText(BigInteger contractRef) {
		ContractText contractTextModel = null;
		Criteria cr = currentSession().createCriteria(ContractText.class);
		cr.add(Restrictions.eq("contractRef", contractRef));
		contractTextModel = (ContractText) cr.uniqueResult();
		String contractName = contractTextModel.getContractName();
		if (contractTextModel != null) {
			logger.info(" nomination Found for  nominationId {} ", contractRef);
		} else
			logger.info(" No nomination Found for  nominationId {} ", contractRef);

		return contractName;

	}

	public String getQuery(Nominations nomination) {

		StringBuffer sqlQuery = new StringBuffer();
		sqlQuery.append("Select ct.customer_code,ct.contract_ref,mt.contract_type,");
		sqlQuery.append("ct.unit_of_measurements,mt.material_code,ct.contract_id,");
		sqlQuery.append("nm.nomination_id,nm.gas_date,nm.del_point,");
		sqlQuery.append(
				" nm.redel_point,nm.updated_date,r.region_name,mt.material_id,nm.seller_redel_point,nm.seller_updated_date ");
		sqlQuery.append(" from material mt inner join contracts ct");
		sqlQuery.append(" on mt.material_id=ct.material_id");
		sqlQuery.append(" inner join nominations nm on nm.contract_id=ct.contract_id");
		sqlQuery.append(" inner join payer p on ct.payer_id=p.payer_id");
		sqlQuery.append(" inner join region r on p.region_id=r.region_id ");
		sqlQuery.append(" where nm.status = " + Constants.ACTIVE_STATUS);
		if (!nomination.getMaterialCode().equals(Constants.ALL_MATERIAL)) {
			sqlQuery.append(" AND mt.material_code = '" + nomination.getMaterialCode() + "'");
		}
		if (nomination.getContractType() != null) {
			sqlQuery.append(" and mt.contract_type = '" + nomination.getContractType() + "'");
		}
		if (nomination.getRegionId() != null) {
			StringBuilder region = new StringBuilder(nomination.getRegionId().toString());
			int length = region.length();
			region.replace(0, 1, "(");
			region.replace(length - 1, length, ")");

			sqlQuery.append(" and r.region_id IN " + region);
		}
		sqlQuery.append(
				" and nm.gas_date between '" + nomination.getStartDate() + "' AND '" + nomination.getEndDate() + "'");
		sqlQuery.append(" AND nm.gas_date between ct.start_date and ct.end_date ");

		sqlQuery.append(" AND nm.nomination_id not in ( select n1.nomination_id from nominations n1, nominations n2");
		sqlQuery.append(" where n1.status = n2.status AND n1.status = 0 AND  n1.nomination_id < n2.nomination_id");
		sqlQuery.append(" AND n1.contract_id = n2.contract_id AND  n1.gas_date = n2.gas_date)");
		sqlQuery.append(" GROUP BY nm.`status` , nm.contract_id , nm.gas_date, nm.redel_point,nm.del_point");

		String query = sqlQuery.toString();
		logger.info("First Query for getting user details for recommended list - {}", query);
		return query;

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<DownloadNominationDTO> getNominationReport(String query) {
		SQLQuery queryto = currentSession().createSQLQuery(query);
		queryto.addScalar("gas_date", DateType.INSTANCE);
		queryto.addScalar("redel_point", BigDecimalType.INSTANCE);
		queryto.addScalar("seller_redel_point", BigDecimalType.INSTANCE);
		queryto.addScalar("del_point", BigDecimalType.INSTANCE);
		queryto.addScalar("customer_code", StringType.INSTANCE);
		queryto.addScalar("contract_ref", StringType.INSTANCE);
		queryto.addScalar("material_id", BigIntegerType.INSTANCE);
		queryto.addScalar("material_code", StringType.INSTANCE);
		queryto.addScalar("unit_of_measurements", StringType.INSTANCE);
		queryto.addScalar("contract_type", StringType.INSTANCE);
		queryto.addScalar("contract_id", BigIntegerType.INSTANCE);
		queryto.addScalar("updated_Date", TimestampType.INSTANCE);
		queryto.addScalar("nomination_id", BigIntegerType.INSTANCE);
		queryto.addScalar("region_name", StringType.INSTANCE);
		queryto.addScalar("seller_updated_date", TimestampType.INSTANCE);

		List<DownloadNominationDTO> downloadNominationDTOList = queryto
				.setResultTransformer(Transformers.aliasToBean(DownloadNominationDTO.class)).list();

		return downloadNominationDTOList;
	}

	@Override
	public String getQueryForSeller(Nominations sellerData) {
		StringBuffer sqlQuery = new StringBuffer();
		sqlQuery.append(
				"SELECT n.nomination_id,n.nominated_by,n.contract_id,n.gas_date,n.seller_redel_point,n.seller_updated_date,p.payer_name, u.user_id,p.payer_key,");
		sqlQuery.append("n.revision_no,m.contract_type,c.unit_of_measurements ,");
		sqlQuery.append("m.material_id ,m.material_code,m.material_desc,");
		sqlQuery.append("n.redel_point,n.del_point,n.updated_date,c.start_date ,c.end_date,");
		sqlQuery.append("c.customer_code,c.customer_description,c.contract_ref");
		sqlQuery.append(" FROM nominations n INNER JOIN contracts c");
		sqlQuery.append(" ON n.contract_id = c.contract_id");
		sqlQuery.append(" INNER JOIN material m ON c.material_id = m.material_id");
		sqlQuery.append("  INNER JOIN payer p ON c.payer_id = p.payer_id");
		sqlQuery.append("  INNER JOIN user u ON p.payer_id = u.payer_id");

		sqlQuery.append(" WHERE m.contract_type in ('" + sellerData.getContractType() + "')");
		if (sellerData.getContractRef() != null)
			sqlQuery.append(" AND c.contract_ref = " + sellerData.getContractRef());

		sqlQuery.append(" AND c.status = " + Constants.ACTIVE_STATUS);
		sqlQuery.append(" AND n.status = " + Constants.ACTIVE_STATUS);

		if (sellerData.getGasDate() != null) {
			sqlQuery.append(" AND n.gas_date = " + "'" + format.format(sellerData.getGasDate()) + "'");

		}

		else {
			sqlQuery.append(" AND n.gas_date between " + "'" + format.format(sellerData.getStartDate()) + "'");
			sqlQuery.append(" AND " + "'" + format.format(sellerData.getEndDate()) + "'");
		}
		sqlQuery.append(" AND m.material_code in(select material_code from seller where payer_id="
				+ sellerData.getPayerId().toString() + ")");

		String query = sqlQuery.toString();
		logger.info("First Query for getting nomination details for seller  - {}", query);
		return sqlQuery.toString();
	}

	@Transactional
	@Override
	public Nominations getNominationModel(BigInteger nominationId) {
		Nominations nominationsModel = null;
		Criteria cr = currentSession().createCriteria(Nominations.class);
		cr.add(Restrictions.eq("nominationId", nominationId));
		nominationsModel = (Nominations) cr.uniqueResult();
		if (nominationsModel != null) {
			logger.info(" nomination Found for  nominationId {} ", nominationId);
		} else
			logger.info(" No nomination Found for  nominationId {} ", nominationId);

		return nominationsModel;

	}

}
