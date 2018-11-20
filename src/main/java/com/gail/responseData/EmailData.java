package com.gail.responseData;

import java.util.Arrays;


public class EmailData {

    String email[];
    String message;
    String subject;
    String type;
    String emailValues ;
    String userName;
    
	public String[] getEmail() {
		return email;
	}
	public void setEmail(String[] email) {
		this.email = email;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEmailValues() {
		return emailValues;
	}
	public void setEmailValues(String emailValues) {
		this.emailValues = emailValues;
	}
	
	public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Override
    public String toString() {
        return "EmailData [email=" + Arrays.toString(email) + ", message="
                + message + ", subject=" + subject + ", type=" + type
                + ", emailValues=" + emailValues + ", userName=" + userName
                + "]";
    }
	
    
}
