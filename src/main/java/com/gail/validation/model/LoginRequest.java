package com.gail.validation.model;



import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
public class LoginRequest {
	@NotEmpty
	@NotNull
	private String userName;

	@NotEmpty
	@NotNull
	private String password;

	private String deviceToken;

	private String deviceType;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	
	

}
