package com.gail.dao;

import com.gail.model.ContractText;

public interface ContractTextDao extends GenericDao<ContractText, Long> {
	
	public ContractText updateData(ContractText updatedData);

}
