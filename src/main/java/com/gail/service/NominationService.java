package com.gail.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.gail.model.ContractText;
import com.gail.model.Nominations;
import com.gail.responseData.ContractDataDTO;
import com.gail.responseData.NominationResponseDataList;
import com.gail.utility.GailNominationServiceException;

public interface NominationService extends GenericService<Nominations, Long> {

	public NominationResponseDataList getNominationDetail(Nominations data);

	public NominationResponseDataList getNominationOrcontracts(Nominations data) throws GailNominationServiceException;

	public NominationResponseDataList getContracts(Nominations data) throws GailNominationServiceException;

	public boolean checkNominationFilled(ContractDataDTO contractDataDTO, NominationResponseDataList nominationResponseDataList);

	public NominationResponseDataList buildNominationReport(Nominations nomination) throws GailNominationServiceException;

	public NominationResponseDataList getcontractOrNominationForSeller(Nominations sellerData) throws GailNominationServiceException;

	public NominationResponseDataList getContractsForSeller(Nominations sellerData) throws GailNominationServiceException;

	public List<Nominations> updateNominationDataBySeller(Nominations updatedData)
			throws GailNominationServiceException, ParseException;

	public List<Nominations> saveNomination(Nominations updatedData) throws GailNominationServiceException, ParseException, IOException;

	public String saveContractText(ContractText contractTextData) throws GailNominationServiceException;

	public String updateContractText(ContractText contractTextData) throws GailNominationServiceException;

	public List<Nominations> updateNomination(Nominations updatedData) throws GailNominationServiceException, ParseException, IOException;	

}
