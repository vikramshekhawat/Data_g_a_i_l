package com.gail.serviceImpl;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gail.dao.ContractsDao;
import com.gail.dao.VendorDao;
import com.gail.model.Contracts;
import com.gail.model.Nominations;
import com.gail.responseData.ContactData;
import com.gail.responseData.EmailData;
import com.gail.responseData.NominationData;
import com.gail.service.EmailSenderService;
import com.gail.service.EmailService;
import com.gail.service.UserContactsService;
import com.gail.utility.Constants;
import com.gail.utility.ErrorDetails;
import com.gail.utility.ExceptionUtil;
import com.gail.utility.GailNominationServiceException;

import jersey.repackaged.com.google.common.collect.Lists;

@Service("emailSenderService")
public class EmailSenderServiceImpl implements EmailSenderService {

	@Autowired
	ContractsDao contractsDao;
	@Autowired
	VendorDao vendorDao;
	@Autowired
	UserContactsService userContactsService;
	@Autowired
	EmailService emailService;

	@Override
	public void sendNominationEmail(Nominations nominationData) throws GailNominationServiceException, IOException {
		List<EmailData> emailDataList = new ArrayList<>();
		StringBuffer tableContent = new StringBuffer();
		StringBuffer tableContentForSeller = new StringBuffer();
		SimpleDateFormat dateFormate = new SimpleDateFormat("dd.MM.yyyy");
		int revisionNo = 0;
		List<String> emailList = Lists.newArrayList();
		List<String> androidTokenList = Lists.newArrayList();
		List<String> iosTokenList = Lists.newArrayList();
		List<NominationData> nominationDataList = null;
		Map<BigInteger, StringBuffer> sellerData = new HashMap<>();
		nominationDataList = nominationData.getNominationData();
		if (nominationDataList!=null) {
			for (NominationData data : nominationDataList) {
				Contracts contracts = contractsDao.getContactRef(new BigInteger(data.getContractId()));
				if (nominationData.getContractType().equalsIgnoreCase("GTA")) {
					BigInteger SellerUserId = vendorDao.getVendorUserId(data.getContractId().toString());
					if (SellerUserId != null) {
						if (!sellerData.containsKey(SellerUserId)) {
							tableContentForSeller = new StringBuffer();
							tableContentForSeller = tableContentCreator(tableContentForSeller, nominationData, data);
							sellerData.put(SellerUserId, tableContentForSeller);
						} else {
							sellerData.put(SellerUserId,
									tableContentCreator(sellerData.get(SellerUserId), nominationData, data));
						}

					}

				}

				tableContent.append(
						"<tr>" + "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
								+ dateFormate.format(nominationData.getGasDate()) + "</td>"
								+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
								+ nominationData.getContractType() + "</td>"
								+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
								+ contracts.getContractRef() + "</td>"
								+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
								+ data.getDelPoint() + "</td>"
								+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
								+ data.getRedelPoint() + "</td>"
								+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
								+ revisionNo + "</td>" + "</tr>");

			}

		} else {

			nominationDataList = nominationData.getUpdateNominationData();

			for (NominationData data : nominationDataList) {
				Contracts contracts = contractsDao.getContactRef(new BigInteger(data.getContractId()));
				if (nominationData.getContractType().equalsIgnoreCase("GTA")) {
					try{
					BigInteger SellerUserId = vendorDao.getVendorUserId(data.getContractId().toString());
					if (SellerUserId != null) {
						if (!sellerData.containsKey(SellerUserId)) {
							tableContentForSeller = new StringBuffer();
							tableContentForSeller = tableContentCreator(tableContentForSeller, nominationData, data);
							sellerData.put(SellerUserId, tableContentForSeller);
						} else {
							sellerData.put(SellerUserId,
									tableContentCreator(sellerData.get(SellerUserId), nominationData, data));
						}

					}
					}catch(Exception e){
						System.out.println("Vendor Id not not found");
					}

				}

				revisionNo = Integer.valueOf(data.getRevisionNo() + 1);
				tableContent.append(
						"<tr>" + "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
								+ dateFormate.format(nominationData.getGasDate()) + "</td>"
								+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
								+ nominationData.getContractType() + "</td>"
								+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
								+ contracts.getContractRef() + "</td>"
								+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
								+ data.getDelPoint() + "</td>"
								+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
								+ data.getRedelPoint() + "</td>"
								+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
								+ revisionNo + "</td>" + "</tr>");
			}

		}

		if (!sellerData.isEmpty()) {
			sendUpdateMailToSeller(sellerData, nominationData);
		}

		List<ContactData> contactDatalist = userContactsService
				.getContactListByPayerId(new BigInteger(nominationData.getUserId()));// Get
		// user-contact
		// data
		for (ContactData contactData : contactDatalist) {
			String email = contactData.getContactValue();
			emailList.add(email);

			// Android & iOS Notification
			if ("android".equalsIgnoreCase(contactData.getDeviceType()))
				androidTokenList.add(contactData.getDeviceToken());
			else if ("ios".equalsIgnoreCase(contactData.getDeviceType()))
				iosTokenList.add(contactData.getDeviceToken());
		}

		// Android & iOS Notification
		// if (!androidTokenList.isEmpty())
		// PushNotificationHelper.sendAndroidNotification(androidTokenList,
		// GailNominationServiceConstant.SUBJECT_SAVE_NOMINATION,
		// GailNominationServiceConstant.SUBJECT_SAVE_NOMINATION);
		// if (!iosTokenList.isEmpty())
		// PushNotificationHelper.sendIOSNotification(iosTokenList,
		// GailNominationServiceConstant.SUBJECT_SAVE_NOMINATION);

		String[] emailArr = emailList.toArray(new String[emailList.size()]);
		EmailData emailData = new EmailData();
		emailData.setEmail(emailArr);

		emailData.setSubject(Constants.SUBJECT_SAVE_NOMINATION);
		emailData.setType(Constants.SAVE_NOMINATION);
		emailData.setEmailValues(tableContent.toString());
		emailDataList.add(emailData);
		emailService.sendEmail(emailDataList, emailData.getType());

	}

	public StringBuffer tableContentCreator(StringBuffer tableContent, Nominations nominationBO,
			NominationData saveNomination) {

		SimpleDateFormat dateFormate = new SimpleDateFormat("dd.MM.yyyy");
		int revisionNo = Integer.valueOf(nominationBO.getRevision_no() + 1);
		Contracts contract = contractsDao.getContactRef(new BigInteger(saveNomination.getContractId()));

		tableContent.append(
				"<tr>" + "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
						+ dateFormate.format(nominationBO.getGasDate()) + "</td>"
						+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
						+ nominationBO.getContractType() + "</td>"
						+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
						+ contract.getContractRef() + "</td>"
						+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
						+ saveNomination.getDelPoint() + "</td>"
						+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
						+ saveNomination.getRedelPoint() + "</td>"
						+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
						+ revisionNo + "</td>" + "</tr>");

		return tableContent;
	}

	@Override
	public void sendUpdateMailToSeller(Map<BigInteger, StringBuffer> sellerData, Nominations nominationBO)
			throws GailNominationServiceException, IOException {

		List<EmailData> emailDataList = new ArrayList<>();
		Iterator<Map.Entry<BigInteger, StringBuffer>> itr = sellerData.entrySet().iterator();
		while (itr.hasNext()) {

			Map.Entry<BigInteger, StringBuffer> entry = itr.next();

			List<String> emailList = Lists.newArrayList();

			// Android & iOS Notification
			List<String> androidTokenList = Lists.newArrayList();
			List<String> iosTokenList = Lists.newArrayList();

			List<ContactData> contactDatalist = userContactsService.getContactListByPayerId((entry.getKey()));

			for (ContactData contactData : contactDatalist) {
				String email = contactData.getContactValue();
				emailList.add(email);

				// Android & iOS Notification
				if ("android".equalsIgnoreCase(contactData.getDeviceType()))
					androidTokenList.add(contactData.getDeviceToken());
				else if ("ios".equalsIgnoreCase(contactData.getDeviceType()))
					iosTokenList.add(contactData.getDeviceToken());
			}

			// if (!androidTokenList.isEmpty())
			// PushNotificationHelper.sendAndroidNotification(androidTokenList,
			// GailNominationServiceConstant.SUBJECT_SAVE_NOMINATION,
			// GailNominationServiceConstant.SUBJECT_SAVE_NOMINATION);
			// if (!iosTokenList.isEmpty())
			// PushNotificationHelper.sendIOSNotification(iosTokenList,
			// GailNominationServiceConstant.SUBJECT_SAVE_NOMINATION);

			String[] emailArr = emailList.toArray(new String[emailList.size()]);
			EmailData emailData = new EmailData();
			emailData.setEmail(emailArr);

			emailData.setSubject(Constants.SUBJECT_SAVE_NOMINATION);
			emailData.setType(Constants.SAVE_NOMINATION);
			emailData.setEmailValues(entry.getValue().toString());
			emailDataList.add(emailData);

		}
		emailService.sendEmail(emailDataList, Constants.SAVE_NOMINATION);

	}

	public void sendNominationEmailForSeller(Nominations nominationData, List data)
			throws GailNominationServiceException {
		List<EmailData> emailDataList = new ArrayList<>();
		List<String> emailList = Lists.newArrayList();
		List<String> androidTokenList = Lists.newArrayList();
		List<String> iosTokenList = Lists.newArrayList();
		StringBuffer tableContent = new StringBuffer();
		SimpleDateFormat dateFormate = new SimpleDateFormat("dd.MM.yyyy");
		// int revisionNo = 0;
		try {
			tableContent.append(
					"<tr>" + "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
							+ dateFormate.format(nominationData.getGasDate()) + "</td>"
							+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
							+ nominationData.getContractType() + "</td>"
							+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
							+ nominationData.getContract_ref() + "</td>"
							+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
							+ nominationData.getSeller_redel_point() + "</td>" + "</tr>");

			if (nominationData.getUserId() != null) {

				List<ContactData> contactDatalist = userContactsService
						.getContactListByPayerId(new BigInteger(nominationData.getUserId()));

				for (ContactData contactData : contactDatalist) {
					String email = contactData.getContactValue();
					emailList.add(email);

					// Android & iOS Notification
					if ("android".equalsIgnoreCase(contactData.getDeviceType()))
						androidTokenList.add(contactData.getDeviceToken());
					else if ("ios".equalsIgnoreCase(contactData.getDeviceType()))
						iosTokenList.add(contactData.getDeviceToken());
				}
			}

			if (nominationData.getClientUserId() != null) {

				List<ContactData> contactDatalist = userContactsService
						.getContactListByPayerId(new BigInteger(nominationData.getUserId()));

				for (ContactData contactData : contactDatalist) {
					String email = contactData.getContactValue();
					emailList.add(email);

					// Android & iOS Notification
					if ("android".equalsIgnoreCase(contactData.getDeviceType()))
						androidTokenList.add(contactData.getDeviceToken());
					else if ("ios".equalsIgnoreCase(contactData.getDeviceType()))
						iosTokenList.add(contactData.getDeviceToken());
				}

			}
			// Android & iOS Notification
			// if (!androidTokenList.isEmpty())
			// PushNotificationHelper.sendAndroidNotification(androidTokenList,
			// GailNominationServiceConstant.SUBJECT_SAVE_NOMINATION_BY_SELLER,
			// GailNominationServiceConstant.SUBJECT_SAVE_NOMINATION_BY_SELLER);
			// if (!iosTokenList.isEmpty())
			// PushNotificationHelper.sendIOSNotification(iosTokenList,
			// GailNominationServiceConstant.SUBJECT_SAVE_NOMINATION_BY_SELLER);

			String[] emailArr = emailList.toArray(new String[emailList.size()]);
			EmailData emailData = new EmailData();
			emailData.setEmail(emailArr);

			emailData.setSubject(Constants.SUBJECT_SAVE_NOMINATION_BY_SELLER);
			emailData.setType(Constants.SAVE_NOMINATION_SELLER);
			emailData.setEmailValues(tableContent.toString());
			emailDataList.add(emailData);
			emailService.sendEmail(emailDataList, emailData.getType());
		} catch (Throwable ex) {
			ExceptionUtil.handleException(new ErrorDetails(Constants.MAIL_ERROR, Constants.ERROR_TYPE_CODE_INTERNAL,
					Constants.ERROR_TYPE_ERROR, "Error while sending mail."), ex, nominationData.toString());

		}

	}

}
