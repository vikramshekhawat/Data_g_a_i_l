package com.gail.responseData;

public class UserUploadData {

	private String payerName;
	private String password;
	private String payerId;
	private String emailId;
	private String cutOffTime;
	private String region;
	private String customerCode;
	private String customerDescription;
	private String adminId;

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerDescription() {
		return customerDescription;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getCutOffTime() {
		return cutOffTime;
	}

	public void setCutOffTime(String cutOffTime) {
		this.cutOffTime = cutOffTime;
	}

	public void setCustomerDescription(String customerDescription) {
		this.customerDescription = customerDescription;
	}

	@Override
	public String toString() {
		return "UserUploadData [payerName=" + payerName + ", password=" + password + ", payerId=" + payerId
				+ ", emailId=" + emailId + ", cutOffTime=" + cutOffTime + ", region=" + region + ", customerCode="
				+ customerCode + ", customerDescription=" + customerDescription + ", adminId=" + adminId + "]";
	}

}
