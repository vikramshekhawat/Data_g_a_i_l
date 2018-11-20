package com.gail.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

import com.gail.model.Payer;
import com.gail.model.User;
import com.gail.responseData.UserUploadData;
import com.gail.utility.GailNominationServiceException;

public interface UserService extends GenericService<User, Long> {

	public User login(User login) throws GailNominationServiceException;

	public User isVendor(BigInteger userId) throws GailNominationServiceException;

	public User downloadUserReport() throws GailNominationServiceException;

	public void readFile(InputStream inputFile, String userId) throws GailNominationServiceException, IOException;

	public User saveUserData(UserUploadData uploadData, Payer payer) throws GailNominationServiceException;
	
	public void sendMail(List<UserUploadData> uploadList) throws GailNominationServiceException;

}
