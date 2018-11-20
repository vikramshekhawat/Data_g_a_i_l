package com.gail.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.gail.model.Contracts;
import com.gail.responseData.ContractsDataList;
import com.gail.responseData.ReminderContractDto;
import com.gail.utility.GailNominationServiceException;

public interface ContractsService extends GenericService<Contracts, Long> {

	public Contracts downloadContractReport(String contractEndDate) throws GailNominationServiceException;

	public Map<BigInteger, List<ReminderContractDto>> getUnfilledContractList(String nominationDate);

	public ContractsDataList getContractDetail(BigInteger payerId, String contractType)
			throws GailNominationServiceException;

	public void readFile(InputStream inputFile, String userId) throws GailNominationServiceException, IOException;

}
