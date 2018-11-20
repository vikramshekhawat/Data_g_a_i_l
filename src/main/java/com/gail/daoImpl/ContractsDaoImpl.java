package com.gail.daoImpl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Criteria;
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

import com.gail.dao.ContractsDao;
import com.gail.model.Contracts;
import com.gail.model.Nominations;
import com.gail.responseData.ContractDataDTO;
import com.gail.responseData.DownloadContractsDTO;
import com.gail.responseData.ReminderContractDto;
import com.gail.utility.Constants;
import com.gail.utility.QueryConstants;

@Repository("contractsDao")
public class ContractsDaoImpl extends GenericDaoImpl<Contracts, Long> implements ContractsDao {

	public static final Logger logger = LoggerFactory.getLogger(ContractsDaoImpl.class);

	@Override
	public String getQuery(Nominations nominationData) {
		StringBuffer sqlQuery = new StringBuffer();
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		sqlQuery.append(
				"SELECT c.contract_id,c.start_date,c.end_date,c.contract_ref,m.material_desc ,p.payer_name,u.user_id,p.payer_key,");
		sqlQuery.append("c.customer_code,c.customer_description,m.material_code,m.contract_type,m.material_id ,");
		sqlQuery.append("c.unit_of_measurements FROM contracts c");
		sqlQuery.append(" INNER JOIN material m ON");
		sqlQuery.append(" c.material_id = m.material_id");
		sqlQuery.append(" INNER JOIN payer p ON c.payer_id=p.payer_id ");
		sqlQuery.append(" INNER JOIN user u ON u.payer_id=p.payer_id ");
		sqlQuery.append(" WHERE m.contract_type = " + "'" + nominationData.getContractType() + "'");
		sqlQuery.append(" AND c.payer_Id = " + nominationData.getPayerId());
		sqlQuery.append(" AND c.status = " + Constants.ACTIVE_STATUS);
		sqlQuery.append(" AND " + "'" + formate.format(nominationData.getGasDate()) + "'"
				+ " BETWEEN c.start_date  AND c.end_date");
		System.out.println(sqlQuery);
		return sqlQuery.toString();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<ContractDataDTO> getContractsList(String query) {
		SQLQuery queryto = currentSession().createSQLQuery(query);
		queryto.addScalar("payer_key", StringType.INSTANCE);
		queryto.addScalar("user_id", BigIntegerType.INSTANCE);
		queryto.addScalar("customer_code", StringType.INSTANCE);
		queryto.addScalar("payer_name", StringType.INSTANCE);
		queryto.addScalar("contract_ref", StringType.INSTANCE);
		// queryto.addScalar("contract_name", StringType.INSTANCE);
		queryto.addScalar("material_id", BigIntegerType.INSTANCE);
		queryto.addScalar("material_code", StringType.INSTANCE);
		queryto.addScalar("customer_description", StringType.INSTANCE);
		queryto.addScalar("material_desc", StringType.INSTANCE);
		queryto.addScalar("unit_of_measurements", StringType.INSTANCE);
		queryto.addScalar("contract_type", StringType.INSTANCE);
		queryto.addScalar("contract_id", BigIntegerType.INSTANCE);
		queryto.addScalar("start_date", DateType.INSTANCE);
		queryto.addScalar("end_date", DateType.INSTANCE);
		System.out.println(query);
		List<ContractDataDTO> contractDataDTO = queryto
				.setResultTransformer(Transformers.aliasToBean(ContractDataDTO.class)).list();
		return contractDataDTO;
	}

	@Override
	public String getQuery(String contractEndDate) {
		StringBuffer sqlQuery = new StringBuffer();
		logger.info("Query to fetch contractDetails on the basics of contractDate");
		sqlQuery.append("select contract_ref as 'contract_reference', m.material_code, start_date, end_date, ");
		sqlQuery.append("c.unit_of_measurements as 'uom', p.payer_key, c.customer_code, c.customer_description,");
		sqlQuery.append(" c.created_date, c.updated_date from contracts c, material m, payer p");
		sqlQuery.append(" WHERE c.material_id = m.material_id AND c.payer_id = p.payer_id AND c.status = "
				+ Constants.ACTIVE_STATUS);
		sqlQuery.append(" AND c.end_date >= '" + contractEndDate + "'");
		logger.info("Query to fetch to contract detail complete");
		System.out.println(sqlQuery);
		return sqlQuery.toString();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<DownloadContractsDTO> getContractsDownloadData(String query) {
		SQLQuery queryto = currentSession().createSQLQuery(query);
		queryto.addScalar("contract_reference", StringType.INSTANCE);
		queryto.addScalar("material_code", StringType.INSTANCE);
		queryto.addScalar("start_date", DateType.INSTANCE);
		queryto.addScalar("end_date", DateType.INSTANCE);
		queryto.addScalar("uom", StringType.INSTANCE);
		queryto.addScalar("payer_key", StringType.INSTANCE);
		queryto.addScalar("customer_code", StringType.INSTANCE);
		queryto.addScalar("customer_description", StringType.INSTANCE);
		queryto.addScalar("created_date", DateType.INSTANCE);
		queryto.addScalar("updated_date", DateType.INSTANCE);
		System.out.println(query);

		List<DownloadContractsDTO> downloadContractsDTOList = queryto
				.setResultTransformer(Transformers.aliasToBean(DownloadContractsDTO.class)).list();

		return downloadContractsDTOList;
	}

	@Override
	public String getQueryforsellerContract(Nominations nominationData) {

		StringBuffer sqlQuery = new StringBuffer();
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		sqlQuery.append(
				"SELECT c.contract_id,c.start_date,c.end_date,c.contract_ref,m.material_desc ,p.payer_name,u.user_id,p.payer_key,");
		sqlQuery.append("c.customer_code,c.customer_description,m.material_code,m.contract_type,m.material_id ,");
		sqlQuery.append("c.unit_of_measurements FROM contracts c");
		sqlQuery.append(" INNER JOIN material m ON");

		sqlQuery.append(" c.material_id = m.material_id");
		sqlQuery.append(" INNER JOIN payer p ON c.payer_id=p.payer_id ");
		sqlQuery.append(" INNER JOIN user u ON u.payer_id=p.payer_id ");
		sqlQuery.append(" WHERE m.contract_type = " + "'" + nominationData.getContractType() + "'");
		sqlQuery.append(" AND c.status = " + Constants.ACTIVE_STATUS);
		sqlQuery.append(" AND " + "'" + formate.format(nominationData.getGasDate()) + "'"
				+ " BETWEEN c.start_date  AND c.end_date");
		sqlQuery.append(" AND m.material_code in(select material_code from seller where payer_id="
				+ nominationData.getPayerId().toString() + ")");
		// sqlQuery.append(" AND contract_id not in(select contract_id from
		// nominations)");

		System.out.println(sqlQuery);
		return sqlQuery.toString();

	}

	@Override
	public String getQueryForSeller(Nominations nominationData) {
		StringBuffer sqlQuery = new StringBuffer();
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		sqlQuery.append(
				"SELECT c.contract_id,c.start_date,c.end_date,c.contract_ref,m.material_desc ,p.payer_name,u.user_id,p.payer_key,");
		sqlQuery.append("c.customer_code,c.customer_description,m.material_code,m.contract_type,m.material_id ,");
		sqlQuery.append("c.unit_of_measurements FROM contracts c");
		sqlQuery.append(" INNER JOIN material m ON");

		sqlQuery.append(" c.material_id = m.material_id");
		sqlQuery.append(" INNER JOIN payer p ON c.payer_id=p.payer_id ");
		sqlQuery.append(" INNER JOIN user u ON u.payer_id=p.payer_id ");
		sqlQuery.append(" WHERE m.contract_type = " + "'" + nominationData.getContractType() + "'");
		sqlQuery.append(" AND c.status = " + Constants.ACTIVE_STATUS);
		sqlQuery.append(" AND " + "'" + formate.format(nominationData.getGasDate()) + "'"
				+ " BETWEEN c.start_date  AND c.end_date");
		sqlQuery.append(" AND m.material_code in(select material_code from seller where payer_id="
				+ nominationData.getPayerId().toString() + ")");
		sqlQuery.append(
				" AND contract_id not in(select n.contract_id from nominations n where n.seller_redel_point  is not null");
		sqlQuery.append(" AND n.status = " + Constants.ACTIVE_STATUS);
		sqlQuery.append(" AND n.gas_date=" + "'" + formate.format(nominationData.getGasDate()) + "')");

		System.out.println(sqlQuery);
		return sqlQuery.toString();

	}

	@Override
	public String getContractsDetailforDisplay(BigInteger payerId, String contractType) {
		StringBuffer sqlQuery = new StringBuffer("select c.contract_id, c.contract_ref,");
		sqlQuery.append(" c.material_id, m. material_code, m.material_desc, c.customer_code,");
		sqlQuery.append(" c.customer_description, c.start_date, c.end_date, c.unit_of_measurements,");
		sqlQuery.append("  m.contract_type from material m,contracts c");
		sqlQuery.append("  WHERE c.material_id = m.material_id");
		sqlQuery.append(" AND c.payer_id = " + payerId);
		if (contractType != null && !contractType.equals("Both"))
			sqlQuery.append(" AND m.contract_type in ('" + contractType + "')");
		else
			sqlQuery.append(" AND m.contract_type in ('GSA', 'GTA')");

		return sqlQuery.toString();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<ReminderContractDto> getUnfilledCcontractList(String nominationDate) {
		logger.info("Query intialized to fetch unfilledContract list");
		SQLQuery queryto = (SQLQuery) currentSession().createSQLQuery(QueryConstants.GET_UNFILLED_CONTRACTS)
				.setParameter("gasDate", nominationDate);
		queryto.addScalar("customer_code", StringType.INSTANCE);
		queryto.addScalar("contract_ref", StringType.INSTANCE);
		// queryto.addScalar("contract_name", StringType.INSTANCE);
		queryto.addScalar("material_code", StringType.INSTANCE);
		queryto.addScalar("contract_type", StringType.INSTANCE);
		queryto.addScalar("payer_id", BigIntegerType.INSTANCE);
		System.out.println(queryto);
		List<ReminderContractDto> contractDataDTO = queryto
				.setResultTransformer(Transformers.aliasToBean(ReminderContractDto.class)).list();
		logger.info("Query completed to fetch unfilledContract list");
		return contractDataDTO;

	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<ContractDataDTO> getContractsListForDisplay(String query) {

		SQLQuery queryto = currentSession().createSQLQuery(query);
		// queryto.addScalar("user_id", BigIntegerType.INSTANCE);
		queryto.addScalar("customer_code", StringType.INSTANCE);
		// queryto.addScalar("payer_name", StringType.INSTANCE);
		queryto.addScalar("contract_ref", StringType.INSTANCE);
		// queryto.addScalar("contract_name", StringType.INSTANCE);
		queryto.addScalar("material_id", BigIntegerType.INSTANCE);
		queryto.addScalar("material_code", StringType.INSTANCE);
		queryto.addScalar("customer_description", StringType.INSTANCE);
		queryto.addScalar("material_desc", StringType.INSTANCE);
		queryto.addScalar("unit_of_measurements", StringType.INSTANCE);
		queryto.addScalar("contract_type", StringType.INSTANCE);
		queryto.addScalar("contract_id", BigIntegerType.INSTANCE);
		queryto.addScalar("start_date", DateType.INSTANCE);
		queryto.addScalar("end_date", DateType.INSTANCE);
		System.out.println(query);
		List<ContractDataDTO> contractDataDTO = queryto
				.setResultTransformer(Transformers.aliasToBean(ContractDataDTO.class)).list();
		return contractDataDTO;

	}

	@Transactional
	@Override
	public Contracts getContarctByRef(String contractReference) {
		Contracts contract = null;
		Criteria cr = currentSession().createCriteria(Contracts.class);
		cr.add(Restrictions.eq("contractRef", contractReference));
		cr.add(Restrictions.eq("status", Constants.ACTIVE_STATUS));
		contract = (Contracts) cr.uniqueResult();
		return contract;
	}
	
	@Transactional
	@Override
	public Contracts getContactRef(BigInteger contractId) {

		Contracts contract = null;
		try {
			Criteria cr = currentSession().createCriteria(Contracts.class);
			cr.add(Restrictions.eq("contractId", contractId));
			contract = (Contracts) cr.uniqueResult();
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		return contract;
	}

}
