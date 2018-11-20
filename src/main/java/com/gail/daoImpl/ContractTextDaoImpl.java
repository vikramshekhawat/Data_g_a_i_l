package com.gail.daoImpl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gail.dao.ContractTextDao;
import com.gail.model.ContractText;
import com.gail.utility.QueryConstants;

@Repository("contractTextDao")
public class ContractTextDaoImpl extends GenericDaoImpl<ContractText, Long> implements ContractTextDao {
	
	@Transactional
	@Override
	public ContractText updateData(ContractText updatedData) {

		Query query = currentSession().createQuery(QueryConstants.updateContractText)
				.setParameter("contractName", updatedData.getContractName())
				.setParameter("contractRef", updatedData.getContractRef());
		if (query.executeUpdate()>0) {
			return (updatedData) ;
		} else {
			return null;
		}

	}

}
