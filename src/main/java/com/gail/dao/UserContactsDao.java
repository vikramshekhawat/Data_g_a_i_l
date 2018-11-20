package com.gail.dao;

import java.math.BigInteger;
import java.util.List;

import com.gail.model.UserContacts;

public interface UserContactsDao extends GenericDao<UserContacts, Long> {

	public List<UserContacts> getContactsByUserId(BigInteger userId);

	public List<UserContacts> getContactListByPayerId(BigInteger payerId);

	public List<UserContacts> getContactDetail(BigInteger userId);

	public List<UserContacts> getContactDetailByPayerKey(String payerKey);

}
