package com.gail.service;

import java.util.List;

import com.gail.responseData.EmailData;
import com.gail.utility.GailNominationServiceException;

public interface EmailService {

	public void sendBulkMail(List<EmailData> emailList, String templatePath) throws GailNominationServiceException;
	
	public void sendReminderMail() throws GailNominationServiceException;
	
	public void sendEmail(List<EmailData> emailDataList, String emailType) throws GailNominationServiceException;

}
