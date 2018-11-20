package com.gail.service;

import java.math.BigInteger;
import java.util.List;

import com.gail.model.User;
import com.gail.model.UserContacts;
import com.gail.responseData.ContactData;
import com.gail.responseData.UserUploadData;
import com.gail.utility.GailNominationServiceException;

public interface UserContactsService extends GenericService<UserContacts, Long> {

	public List<ContactData> getContactListByPayerId(BigInteger payerId) throws GailNominationServiceException;

	public void saveUserContact(UserUploadData uploadData, User user) throws GailNominationServiceException;

	public List<ContactData> getContactList(BigInteger userId) throws GailNominationServiceException;

	public List<ContactData> getContactListByPayerKey(String key) throws GailNominationServiceException;

}
