package com.gail.dao;

import java.math.BigInteger;
import java.util.List;

import com.gail.model.Contracts;
import com.gail.model.Nominations;
import com.gail.responseData.ContractDataDTO;
import com.gail.responseData.DownloadContractsDTO;
import com.gail.responseData.ReminderContractDto;

public interface ContractsDao extends GenericDao<Contracts, Long> {

	public String getQuery(Nominations nominationData);

	public List<ContractDataDTO> getContractsList(String query);

	public String getQuery(String contractEndDate);

	public List<DownloadContractsDTO> getContractsDownloadData(String query);

	public String getQueryforsellerContract(Nominations nominationData);

	public String getQueryForSeller(Nominations nominationData);

	public List<ReminderContractDto> getUnfilledCcontractList(String nominationDate);

	public String getContractsDetailforDisplay(BigInteger payerId, String contractType);

	public List<ContractDataDTO> getContractsListForDisplay(String query);

	public Contracts getContarctByRef(String contractReference);
	
	public Contracts getContactRef(BigInteger contractId) ;

}
