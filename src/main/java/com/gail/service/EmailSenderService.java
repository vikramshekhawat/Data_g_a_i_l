package com.gail.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.gail.model.Nominations;
import com.gail.responseData.NominationData;
import com.gail.utility.GailNominationServiceException;

public interface EmailSenderService {
	
	
	public void sendNominationEmail(Nominations nominationData) throws GailNominationServiceException, IOException;
	public StringBuffer tableContentCreator(StringBuffer tableContent, Nominations nominationBO,
			NominationData saveNomination);

	public void sendUpdateMailToSeller(Map<BigInteger, StringBuffer> sellerData, Nominations nominationBO)
			throws GailNominationServiceException, IOException;
	
	public void sendNominationEmailForSeller(Nominations nominationBO, List data)
			throws GailNominationServiceException;
}
