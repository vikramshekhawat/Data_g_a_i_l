package com.gail.responseData;

public class ContractUploadData {

	private String payerKey;
	private String customerCode;
	private String customerDescription;
	private String contractReference;
	private String materialCode;
	private String startDate;
	private String endDate;
	private String UOM;
	private String adminId;

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getPayerKey() {
		return payerKey;
	}

	public void setPayerKey(String payerKey) {
		this.payerKey = payerKey;
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

	public void setCustomerDescription(String customerDescription) {
		this.customerDescription = customerDescription;
	}

	public String getContractReference() {
		return contractReference;
	}

	public void setContractReference(String contractReference) {
		this.contractReference = contractReference;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getUOM() {
		return UOM;
	}

	public void setUOM(String uOM) {
		UOM = uOM;
	}

	@Override
	public String toString() {
		return "ContractUploadData [payerKey=" + payerKey + ", customerCode=" + customerCode + ", customerDescription="
				+ customerDescription + ", contractReference=" + contractReference + ", materialCode=" + materialCode
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", UOM=" + UOM + ", adminId=" + adminId + "]";
	}

}
