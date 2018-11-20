package com.gail.serviceImpl;

import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gail.dao.UserContactsDao;
import com.gail.model.User;
import com.gail.model.UserContacts;
import com.gail.responseData.ContactData;
import com.gail.responseData.UserUploadData;
import com.gail.service.UserContactsService;
import com.gail.utility.Constants;
import com.gail.utility.ErrorDetails;
import com.gail.utility.ExceptionUtil;
import com.gail.utility.GailNominationServiceException;
import com.gail.utility.UTCDate;

import jersey.repackaged.com.google.common.collect.Lists;

@Service("userContactsService")
public class UserContactsServiceImpl extends GenericServiceImpl<UserContacts, Long> implements UserContactsService {

	@Autowired
	UserContactsDao userContactsDao;

	private static final Logger logger = LoggerFactory.getLogger(UserContactsServiceImpl.class);

	@Override
	public List<ContactData> getContactListByPayerId(BigInteger payerId) throws GailNominationServiceException {
		logger.info("fetching contacts based on payer id");
		List<ContactData> contactList = Lists.newArrayList();
		try {
			List<UserContacts> userContacts = userContactsDao.getContactListByPayerId(payerId);
			if (!userContacts.isEmpty()) {
				for (UserContacts model : userContacts) {
					ContactData contactData = new ContactData();
					contactData.setContactType(model.getContactType());
					contactData.setContactValue(model.getContactValue());
					contactData.setDeviceToken(model.getDeviceToken());
					contactData.setDeviceType(model.getDeviceType());
					contactData.setPriority(model.getPriority());
					contactList.add(contactData);
				}
			}
		} catch (Throwable ex) {
			ExceptionUtil.handleException(
					new ErrorDetails(Constants.GETTING_USER_CONTACT_ERROR, Constants.ERROR_TYPE_CODE_INTERNAL,
							Constants.ERROR_TYPE_ERROR, "Error while getting user contacts."),
					ex, String.valueOf(payerId));
		}
		return contactList;
	}

	@Override
	public void saveUserContact(UserUploadData uploadData, User user) throws GailNominationServiceException {
		UserContacts userContact = null;
		try {
			List<UserContacts> contractList = userContactsDao.getContactDetail(user.getUserId());
			if (contractList.isEmpty()) {
				userContact = new UserContacts();
				userContact.setCreatedBy(new BigInteger(uploadData.getAdminId()));
				userContact.setCreatedDate(UTCDate.getCurrentUTCDate());
			} else {
				userContact = contractList.get(0);
			}

			userContact.setContactType(Constants.CONTACT_TYPE_MAIL);
			userContact.setContactValue(uploadData.getEmailId());
			userContact.setPriority(Constants.CONTACT_PRIORITY);
			userContact.setUserId(user.getUserId());
			userContact.setStatus(Constants.ACTIVE_STATUS);
			userContact.setUpdatedDate(UTCDate.getCurrentUTCDate());
			userContact.setUpdatedBy(new BigInteger(uploadData.getAdminId()));
			userContactsDao.saveOrUpdate(userContact);
		} catch (Throwable ex) {
			ExceptionUtil
					.handleException(
							new ErrorDetails(Constants.UPLOADING_USER_CONTACT_ERROR, Constants.ERROR_TYPE_CODE_INTERNAL,
									Constants.ERROR_TYPE_ERROR, "Error while uploading contacts."),
							ex, uploadData.toString());
		}
	}

	@Override
	public List<ContactData> getContactList(BigInteger userId) throws GailNominationServiceException {
		List<ContactData> contactDataList = Lists.newArrayList();
		try {
			List<UserContacts> contactList = userContactsDao.getContactDetail(userId);
			if (!contactList.isEmpty()) {
				for (UserContacts userContacts : contactList) {
					ContactData contactData = new ContactData();
					contactData.setContactType(userContacts.getContactType());
					contactData.setContactValue(userContacts.getContactValue());
					contactData.setDeviceToken(userContacts.getDeviceToken());
					contactData.setDeviceType(userContacts.getDeviceType());
					contactData.setPriority(userContacts.getPriority());
					contactDataList.add(contactData);
				}
			}
		} catch (Throwable ex) {
			ExceptionUtil
					.handleException(
							new ErrorDetails(Constants.GETTING_CONTRACT_ERROR, Constants.ERROR_TYPE_CODE_INTERNAL,
									Constants.ERROR_TYPE_ERROR, "Error while getting contract."),
							ex, userId.toString());
		}
		return contactDataList;

	}

	@Override
	public List<ContactData> getContactListByPayerKey(String key) throws GailNominationServiceException {
		List<ContactData> list = null;
		try {
			List<UserContacts> contactModel = userContactsDao.getContactDetailByPayerKey(key);
			if (!contactModel.isEmpty()) {
				list = Lists.newArrayList();
				for (UserContacts model : contactModel) {
					ContactData contactData = new ContactData();
					contactData.setContactType(model.getContactType());
					contactData.setContactValue(model.getContactValue());
					contactData.setDeviceToken(model.getDeviceToken());
					contactData.setDeviceType(model.getDeviceType());
					contactData.setPriority(model.getPriority());
					list.add(contactData);
				}
			}
		} catch (Throwable ex) {
			ExceptionUtil.handleException(new ErrorDetails(Constants.GETTING_CONTRACT_ERROR,
					Constants.ERROR_TYPE_CODE_INTERNAL, Constants.ERROR_TYPE_ERROR, "Error while getting contract."),
					ex, key);
		}
		return list;

	}

}