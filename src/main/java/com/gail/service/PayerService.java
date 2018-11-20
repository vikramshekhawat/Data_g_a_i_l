package com.gail.service;

import com.gail.model.Payer;
import com.gail.responseData.UserUploadData;
import com.gail.utility.GailNominationServiceException;

public interface PayerService extends GenericService<Payer, Long> {

	public Payer savePayerData(UserUploadData uploadData) throws GailNominationServiceException;

}
