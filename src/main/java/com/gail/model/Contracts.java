package com.gail.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "contracts")
public class Contracts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contract_id", nullable = false)
	private BigInteger contractId;

	@Column(name = "material_id", nullable = false)
	private BigInteger materialId;

	@Column(name = "payer_id", nullable = false)
	private BigInteger payerId;

	@Column(name = "customer_code", nullable = false)
	private String customerCode;

	@Column(name = "contract_ref", nullable = false)
	private String contractRef;

	@Column(name = "start_date", nullable = false)
	private Date startDate;

	@Column(name = "end_date", nullable = false)
	private Date endDate;

	@Column(name = "unit_of_measurements", nullable = false)
	private String unitOfMeasurements;

	@Column(name = "customer_description", nullable = false)
	private String customerDescription;

	@Column(name = "status", nullable = false)
	private int status;

	@Column(name = "created_by", nullable = false)
	private BigInteger createdBy;

	@Column(name = "created_date", nullable = false)
	private Date createdDate;

	@Column(name = "updated_date", nullable = false)
	private Date updatedDate;

	@Column(name = "updated_by", nullable = false)
	private BigInteger updatedBy;

	@Transient
	private String userId;

	@Transient
	private String contractType;

	@NotEmpty(groups = { Contracts.DownloadContracts.class })
	@Transient
	private String contractEndDate;

	@Transient
	private String materialCode;

	@Transient
	private List<String> regionId;

	@Transient
	private String fileName;

	public BigInteger getContractId() {
		return contractId;
	}

	public void setContractId(BigInteger contractId) {
		this.contractId = contractId;
	}

	public BigInteger getMaterialId() {
		return materialId;
	}

	public void setMaterialId(BigInteger materialId) {
		this.materialId = materialId;
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

	public String getContractRef() {
		return contractRef;
	}

	public void setContractRef(String contractRef) {
		this.contractRef = contractRef;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
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

	public String getCustomerDescription() {
		return customerDescription;
	}

	public void setCustomerDescription(String customerDescription) {
		this.customerDescription = customerDescription;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public List<String> getRegionId() {
		return regionId;
	}

	public void setRegionId(List<String> regionId) {
		this.regionId = regionId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "Contracts [contractId=" + contractId + ", materialId=" + materialId + ", payerId=" + payerId
				+ ", customerCode=" + customerCode + ", contractRef=" + contractRef + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", unitOfMeasurements=" + unitOfMeasurements + ", customerDescription="
				+ customerDescription + ", status=" + status + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", updatedDate=" + updatedDate + ", updatedBy=" + updatedBy + ", userId=" + userId
				+ ", contractType=" + contractType + ", contractEndDate=" + contractEndDate + ", materialCode="
				+ materialCode + ", regionId=" + regionId + ", fileName=" + fileName +  "]";
	}

	public interface DownloadContracts {

	}
}
