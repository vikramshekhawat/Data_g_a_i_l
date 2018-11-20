package com.gail.dao;

import java.math.BigInteger;
import java.util.List;

import com.gail.model.Nominations;
import com.gail.responseData.DownloadNominationDTO;
import com.gail.responseData.NominationDataDTO;

public interface NominationDao extends GenericDao<Nominations, Long> {

	public String getQueryForDisplay(Nominations nominationData);

	public List<NominationDataDTO> getNomination(String query);

	public String getQueryForNomination(Nominations nominationData);

	public String getContractText(BigInteger Contract_ref);

	public String getQuery(Nominations nomination);

	public List<DownloadNominationDTO> getNominationReport(String query);
	
	public String getQueryForSeller(Nominations sellerData);
	
	public Nominations getNominationModel(BigInteger nominationId);

}
