package com.gail.serviceImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.gail.responseData.ContactData;
import com.gail.responseData.EmailData;
import com.gail.responseData.ReminderContractDto;
import com.gail.service.ContractsService;
import com.gail.service.EmailService;
import com.gail.service.UserContactsService;
import com.gail.utility.Constants;
import com.gail.utility.ErrorDetails;
import com.gail.utility.ExceptionUtil;
import com.gail.utility.GailNominationServiceException;
import com.gail.utility.UTCDate;

import jersey.repackaged.com.google.common.collect.Lists;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

	public static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private UserContactsService userContactsService;

	@Autowired
	private ContractsService contractsService;

	@Value("${from.email}")
	private String fromEmail;

	@Value("${CHECK_MAX_LIMIT_OF_EMAILS}")
	private boolean checkMaxLimitOfEmails;

	@Value("${MAX_LIMIT_OF_EMAILS}")
	private int maxEmailsLimit;

	@Value("${line.separator}")
	private String lineSeparator;

	@Async
	public void sendEmail(List<EmailData> emailDataList, String emailType) throws GailNominationServiceException {
		if (!emailDataList.isEmpty() && emailType != null) {
			if (Constants.CHANGE_PASSWORD.equals(emailType)) {

				sendEmailForChangePassword(emailDataList);
			} else if (Constants.FORGET_PASSWORD.equals(emailType)) {

				sendEmailForForgetPassword(emailDataList);
			} else if (Constants.SAVE_NOMINATION.equals(emailType)) {

				sendEmailForSaveNomination(emailDataList);
			} else if (Constants.NEW_USER.equals(emailType)) {

				sendEmailForNewUser(emailDataList);
			} else if (Constants.ADD_CONTRACT.equals(emailType)) {

				sendEmailForNewContract(emailDataList);
			} else if (Constants.NOMINATION_REMINDER.equals(emailType)) {

				sendEmailForNominationReminder(emailDataList);
			} else if (Constants.SAVE_NOMINATION_SELLER.equals(emailType)) {

				sendEmailNominationUpdateBySeller(emailDataList);
			}
		}
	}

	// Send n mails with n connections
	// public void sendEmail(EmailData emailData, String templatePath) throws
	// MessagingException {
	//
	// MimeMessage message = emailSender.createMimeMessage();
	// MimeMessageHelper messageHelper = new MimeMessageHelper(message, true,
	// "UTF-8");
	//
	// InternetAddress[] addressTo = new
	// InternetAddress[emailData.getEmail().length];
	// for (int i = 0; i < emailData.getEmail().length; i++) {
	// addressTo[i] = new InternetAddress(emailData.getEmail()[i]);
	// }
	//
	// messageHelper.setFrom(fromEmail);
	// messageHelper.setTo(addressTo);
	// messageHelper.setSubject(emailData.getSubject());
	//
	// Map<String, String> inputValue = new HashMap<String, String>();
	// inputValue.put("TableContentData", emailData.getEmailValues());
	//
	// // HTML mail content
	// String htmlText = readEmailFromHtml(templatePath, inputValue);
	// messageHelper.setText(htmlText, true);
	// emailSender.send(message);
	//
	// }

	@Async
	public void sendBulkMail(List<EmailData> emailList, String templatePath) throws GailNominationServiceException {
		MimeMessagePreparator[] preparators = new MimeMessagePreparator[emailList.size()];
		int i = 0;
		try {
			for (final EmailData emailData : emailList) {

				InternetAddress[] addressTo = new InternetAddress[emailData.getEmail().length];
				for (int j = 0; j < emailData.getEmail().length; j++) {
					addressTo[j] = new InternetAddress(emailData.getEmail()[j]);
				}

				Map<String, String> inputValue = new HashMap<String, String>();
				inputValue.put("TableContentData", emailData.getEmailValues());
				
				if(Constants.FORGET_PASSWORD.equals(emailData.getType())) {
					inputValue = new HashMap<String, String>();
					inputValue.put("passwordParam", emailData.getEmailValues());
				}

				if(Constants.NEW_USER.equals(emailData.getType())) {
					inputValue = new HashMap<String, String>();
					inputValue.put("passwordParam", emailData.getEmailValues());
		            inputValue.put("userNameParam", emailData.getUserName());
				}
				
				// HTML mail content
				String htmlText = readEmailFromHtml(templatePath, inputValue);

				preparators[i++] = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage) throws MessagingException, IOException {
						final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
						message.setSubject(emailData.getSubject());
						message.setTo(addressTo);
						message.setFrom(fromEmail);
						message.setText(htmlText, true);
					}
				};
				logger.info("Bulk Mail sent");
			}
			this.emailSender.send(preparators);
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Throwable ex) {
			ExceptionUtil.handleException(new ErrorDetails(Constants.MAIL_ERROR, Constants.ERROR_TYPE_CODE_INTERNAL,
					Constants.ERROR_TYPE_ERROR, "Error while sending reminder mail."), ex, "");
		}
	}

	@Scheduled(cron = "00 30 09 * * *", zone = "Asia/Calcutta")
	public void sendReminderMail() throws GailNominationServiceException {
		try {
			logger.info("sendReminderMail called for bulk mailing");
			String templatePath = Constants.EMAIL_TEMPLATE_PATH + "nomination-reminder-template.html";
			String currentDate = format.format(UTCDate.getDateInISTFormats(UTCDate.getCurrentUTCDate()));
			Map<BigInteger, List<ReminderContractDto>> unfilledContractsMap = contractsService
					.getUnfilledContractList(currentDate);
			logger.info("Number of UnfilledcontractMap are {} fetched on {}.", unfilledContractsMap.size(),
					currentDate);
			if (!unfilledContractsMap.isEmpty()) {
				List<EmailData> emailDataList = Lists.newArrayList();
				int count = 0;
				try {
					for (Map.Entry<BigInteger, List<ReminderContractDto>> entry : unfilledContractsMap.entrySet()) {
						if (Boolean.valueOf(checkMaxLimitOfEmails) && count > maxEmailsLimit) {
							logger.error("Maximum limit of {} mails reached", maxEmailsLimit);
							break;
						}
						StringBuffer tableContent = new StringBuffer();
						for (ReminderContractDto contract : entry.getValue()) {
							// materialModel =
							// materialDao.getMaterialByCode(contract.getMaterialCode());
							tableContent.append(
									"<tr>" + "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
											+ currentDate + "</td>"
											+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
											+ contract.getCustomer_code() + "</td>"
											+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
											+ contract.getContract_ref() + "</td>"
											+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
											+ contract.getContract_type() + "</td>"
											+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
											+ contract.getMaterial_code() + "</td>" +
											/*
											 * "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
											 * +contract.getUOM() +"</td>"+
											 */
											"</tr>");
						}
						List<String> emailList = Lists.newArrayList();

						// Android & iOS Notification
						List<String> androidTokenList = Lists.newArrayList();
						List<String> iosTokenList = Lists.newArrayList();

						List<ContactData> contactDataList = userContactsService.getContactListByPayerId(entry.getKey());// Get
																														// user-contact
																														// data

						logger.info("EmailIds associated with payerId {} fetched", entry.getKey());

						for (ContactData contactData : contactDataList) {
							String email = contactData.getContactValue();
							emailList.add(email);

							// Android & iOS Notification
							if ("android".equalsIgnoreCase(contactData.getDeviceType()))
								androidTokenList.add(contactData.getDeviceToken());
							else if ("ios".equalsIgnoreCase(contactData.getDeviceType()))
								iosTokenList.add(contactData.getDeviceToken());
						}

						// Android & iOS Notification
						// if(!androidTokenList.isEmpty())
						// PushNotificationHelper.sendAndroidNotification(androidTokenList,
						// Constants.SUBJECT_NOMINATION_REMINDER,
						// Constants.SUBJECT_NOMINATION_REMINDER);
						// if(!iosTokenList.isEmpty())
						// PushNotificationHelper.sendIOSNotification(iosTokenList,
						// Constants.SUBJECT_NOMINATION_REMINDER);

						String[] emailArr = emailList.toArray(new String[emailList.size()]);
						EmailData emailData = new EmailData();
						emailData.setEmail(emailArr);

						emailData.setSubject(Constants.SUBJECT_NOMINATION_REMINDER);
						emailData.setType(Constants.NOMINATION_REMINDER);
						emailData.setEmailValues(tableContent.toString());

						count++;
						emailDataList.add(emailData);
					}
				} catch (Throwable ex) {
					ExceptionUtil.handleException(
							new ErrorDetails(Constants.MAIL_ERROR, Constants.ERROR_TYPE_CODE_INTERNAL,
									Constants.ERROR_TYPE_ERROR, "Error while sending reminder mail."),
							ex, unfilledContractsMap.toString());
				}
				sendBulkMail(emailDataList, templatePath);
			} else {
				logger.info("UnfilledContractsMap list is empty");
			}

		} catch (Throwable e) {
			logger.error("Error occured while fetching contracts", e);
			e.printStackTrace();
		}
	}

	protected String readEmailFromHtml(String filePath, Map<String, String> input) {
		String msg = readContentFromFile(filePath);
		if (input != null) {
			try {
				Set<Entry<String, String>> entries = input.entrySet();
				for (Map.Entry<String, String> entry : entries) {
					msg = msg.replace(entry.getKey().trim(), entry.getValue().trim());
				}
			} catch (Exception exception) {
				logger.error("Error occured with readEmailFromHtml " + exception.getStackTrace());
			}
		}
		return msg;
	}

	private String readContentFromFile(String fileName) {
		StringBuffer contents = new StringBuffer();

		try {
			// use buffering, reading one line at a time
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			try {
				String line = null;
				while ((line = reader.readLine()) != null) {
					contents.append(line);
					contents.append(lineSeparator);
				}
			} finally {
				reader.close();
			}
		} catch (IOException ex) {
			logger.error("Error occured with readContentFromFile " + ex.getStackTrace());
		}
		return contents.toString();
	}

	private void sendEmailForNominationReminder(List<EmailData> emailData) throws GailNominationServiceException {
		String nominationReminderTemplatePath = Constants.EMAIL_TEMPLATE_PATH + "nomination-reminder-template.html";
		sendBulkMail(emailData, nominationReminderTemplatePath);
	}

	private void sendEmailForNewContract(List<EmailData> emailData) throws GailNominationServiceException {
		String addContractTemplatePath = Constants.EMAIL_TEMPLATE_PATH + "contract-added-template.html";
		sendBulkMail(emailData, addContractTemplatePath);
	}

	private void sendEmailForNewUser(List<EmailData> emailData) throws GailNominationServiceException {
		String uploadUserTemplatePath = Constants.EMAIL_TEMPLATE_PATH + "upload-new-user-template.html";
		sendBulkMail(emailData, uploadUserTemplatePath);
	}

	private void sendEmailForSaveNomination(List<EmailData> emailData) throws GailNominationServiceException {
		String saveNominationTemplatePath = Constants.EMAIL_TEMPLATE_PATH + "nomination-submission-template.html";
		sendBulkMail(emailData, saveNominationTemplatePath);
	}

	private void sendEmailForForgetPassword(List<EmailData> emailData) throws GailNominationServiceException {
		String forgetPasswordTemplatePath = Constants.EMAIL_TEMPLATE_PATH + "forget-password-template.html";
		sendBulkMail(emailData, forgetPasswordTemplatePath);
	}

	private void sendEmailForChangePassword(List<EmailData> emailData) throws GailNominationServiceException {
		String changePasswordTemplatePath = Constants.EMAIL_TEMPLATE_PATH + "change-password-template.html";
		sendBulkMail(emailData, changePasswordTemplatePath);
	}

	private void sendEmailNominationUpdateBySeller(List<EmailData> emailData) throws GailNominationServiceException {
		String updateNominationBySellerTemplatePath = Constants.EMAIL_TEMPLATE_PATH
				+ "nomination-updated-by-seller.html";
		sendBulkMail(emailData, updateNominationBySellerTemplatePath);
	}
}
