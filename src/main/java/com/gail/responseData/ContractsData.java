package com.gail.responseData;

import java.math.BigInteger;
import java.util.Date;

public class ContractsData {

	private BigInteger contractId;
	private BigInteger payerId;
	private String customerCode;
	private BigInteger regionId;
	private String contractType;
	private String contractRef;
	private String materialCode;
	private String materialDiscription;
	private String startDate;
	private Date endDate;
	private String unitOfMeasurements;
	private int status;
	private BigInteger createdBy;
	private Date createdDate;
	private Date updatedDate;
	private BigInteger updatedBy;
	public BigInteger getContractId() {
		return contractId;
	}
	public void setContractId(BigInteger contractId) {
		this.contractId = contractId;
	}
	public BigInteger getPayerId() {
		return payerId;
	}
	public void setPayerId(BigInteger payerId) {
		this.payerId = payerId;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public BigInteger getRegionId() {
		return regionId;
	}
	public void setRegionId(BigInteger regionId) {
		this.regionId = regionId;
	}
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public String getContractRef() {
		return contractRef;
	}
	public void setContractRef(String contractRef) {
		this.contractRef = contractRef;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getMaterialDiscription() {
		return materialDiscription;
	}
	public void setMaterialDiscription(String materialDiscription) {
		this.materialDiscription = materialDiscription;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getUnitOfMeasurements() {
		return unitOfMeasurements;
	}
	public void setUnitOfMeasurements(String unitOfMeasurements) {
		this.unitOfMeasurements = unitOfMeasurements;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public BigInteger getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(BigInteger createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public BigInteger getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(BigInteger updatedBy) {
		this.updatedBy = updatedBy;
	}
	@Override
	public String toString() {
		return "ContractsData [contractId=" + contractId + ", payerId=" + payerId + ", customerCode=" + customerCode
				+ ", regionId=" + regionId + ", contractType=" + contractType + ", contractRef=" + contractRef
				+ ", materialCode=" + materialCode + ", materialDiscription=" + materialDiscription + ", startDate="
				+ startDate + ", endDate=" + endDate + ", unitOfMeasurements=" + unitOfMeasurements + ", status="
				+ status + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate
				+ ", updatedBy=" + updatedBy + "]";
	}
	
	
	
}
