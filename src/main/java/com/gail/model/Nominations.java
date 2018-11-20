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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.gail.responseData.NominationData;

@Entity
@Table(name = "nominations")
@DynamicInsert
public class Nominations {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "nomination_id", nullable = false)
	private BigInteger nominationId;

	@Column(name = "nominated_by", nullable = false)
	private BigInteger nominatedBy;

	@NotNull(groups = { Nominations.NominationReq.class, Nominations.GetContractNominationRequest.class,
			Nominations.GetContractSellerReq.class, Nominations.UpdateSellerNominate.class,
			Nominations.SaveNomination.class, Nominations.UpdateNomination.class })
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "gas_date", nullable = false)
	private Date gasDate;

	@Column(name = "contract_id", nullable = false)
	private BigInteger contractId;

	@Column(name = "contract_name", nullable = false)
	private String contractName;

	@Column(name = "redel_point", nullable = false)
	private Double redelPoint;

	@Column(name = "del_point", nullable = false)
	private Double delPoint;

	@Column(name = "revision_no", nullable = false)
	private int revision_no;

	@Column(name = "status", nullable = false)
	private int status;

	@Column(name = "created_by", nullable = false)
	private BigInteger createdBy;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "created_date", nullable = false)
	private Date createdDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "updated_date", nullable = true)
	private Date updateDate;

	@Column(name = "updated_by", nullable = false)
	private BigInteger updatedBy;

	@Column(name = "seller_redel_point", nullable = false)
	private Double sellerredelPoint;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "seller_updated_date", nullable = true)
	private Date sellerUpdatedDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotEmpty(groups = { Nominations.BuildNominationReport.class })
	@Transient
	private String startDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotEmpty(groups = { Nominations.BuildNominationReport.class })
	@Transient
	private String endDate;

	@Transient
	private String regionId;

	@NotEmpty(groups = { Nominations.NominationReq.class, Nominations.GetContractNominationRequest.class,
			Nominations.GetContractSellerReq.class, Nominations.UpdateSellerNominate.class,
			Nominations.SaveNomination.class, Nominations.UpdateNomination.class })
	@Transient
	private String contractType;

	@Transient
	private String contractRef;

	@NotEmpty(groups = { Nominations.NominationReq.class, Nominations.GetContractNominationRequest.class,
			Nominations.UpdateSellerNominate.class, Nominations.SaveNomination.class,
			Nominations.UpdateNomination.class })
	@Transient
	private String userId;

	@Transient
	private String fileName;

	@Transient
	private String regionName;

	@Transient
	private String customerCode;

	@Transient
	private String customerDescription;

	@NotEmpty(groups = { Nominations.BuildNominationReport.class })
	@Transient
	private String materialCode;

	@Transient
	private String materialDesc;

	@Transient
	private String unitOfMeasurements;

	@NotEmpty(groups = { Nominations.GetContractNominationRequest.class, Nominations.GetContractSellerReq.class })
	@Transient
	private String payerId;

	@NotEmpty(groups = { Nominations.UpdateSellerNominate.class })
	@Transient
	private String clientUserId;

	@NotEmpty(groups = { Nominations.UpdateSellerNominate.class })
	@Transient
	private String contract_ref;

	@NotEmpty(groups = { Nominations.UpdateSellerNominate.class })
	@Transient
	private String contract_id;

	@NotEmpty(groups = { Nominations.UpdateSellerNominate.class })
	@Transient
	private String nomination_id;

	@NotEmpty(groups = { Nominations.UpdateSellerNominate.class })
	@Transient
	private String seller_redel_point;

	@NotEmpty(groups = { Nominations.SaveNomination.class })
	@Transient
	private List<NominationData> nominationData;

	@NotEmpty(groups = { Nominations.UpdateNomination.class })
	@Transient
	private List<NominationData> updateNominationData;

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public Double getSellerredelPoint() {
		return sellerredelPoint;
	}

	public void setSellerredelPoint(Double sellerredelPoint) {
		this.sellerredelPoint = sellerredelPoint;
	}

	public Date getSellerUpdatedDate() {
		return sellerUpdatedDate;
	}

	public void setSellerUpdatedDate(Date sellerUpdatedDate) {
		this.sellerUpdatedDate = sellerUpdatedDate;
	}

	public BigInteger getNominationId() {
		return nominationId;
	}

	public void setNominationId(BigInteger nominationId) {
		this.nominationId = nominationId;
	}

	public BigInteger getNominatedBy() {
		return nominatedBy;
	}

	public void setNominatedBy(BigInteger nominatedBy) {
		this.nominatedBy = nominatedBy;
	}

	public Date getGasDate() {
		return gasDate;
	}

	public int getRevision_no() {
		return revision_no;
	}

	public void setRevision_no(int revision_no) {
		this.revision_no = revision_no;
	}

	public void setGasDate(Date gasDate) {
		this.gasDate = gasDate;
	}

	public BigInteger getContractId() {
		return contractId;
	}

	public void setContractId(BigInteger contractId) {
		this.contractId = contractId;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public Double getRedelPoint() {
		return redelPoint;
	}

	public void setRedelPoint(Double redelPoint) {
		this.redelPoint = redelPoint;
	}

	public Double getDelPoint() {
		return delPoint;
	}

	public void setDelPoint(Double delPoint) {
		this.delPoint = delPoint;
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

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public List<NominationData> getNominationData() {
		return nominationData;
	}

	public void setNominationData(List<NominationData> nominationData) {
		this.nominationData = nominationData;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getUnitOfMeasurements() {
		return unitOfMeasurements;
	}

	public void setUnitOfMeasurements(String unitOfMeasurements) {
		this.unitOfMeasurements = unitOfMeasurements;
	}

	public String getContractRef() {
		return contractRef;
	}

	public void setContractRef(String contractRef) {
		this.contractRef = contractRef;
	}

	public String getCustomerDescription() {
		return customerDescription;
	}

	public void setCustomerDescription(String customerDescription) {
		this.customerDescription = customerDescription;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public String getClientUserId() {
		return clientUserId;
	}

	public void setClientUserId(String clientUserId) {
		this.clientUserId = clientUserId;
	}

	public String getContract_ref() {
		return contract_ref;
	}

	public void setContract_ref(String contract_ref) {
		this.contract_ref = contract_ref;
	}

	public String getContract_id() {
		return contract_id;
	}

	public void setContract_id(String contract_id) {
		this.contract_id = contract_id;
	}

	public String getNomination_id() {
		return nomination_id;
	}

	public void setNomination_id(String nomination_id) {
		this.nomination_id = nomination_id;
	}

	public String getSeller_redel_point() {
		return seller_redel_point;
	}

	public void setSeller_redel_point(String seller_redel_point) {
		this.seller_redel_point = seller_redel_point;
	}

	public List<NominationData> getUpdateNominationData() {
		return updateNominationData;
	}

	public void setUpdateNominationData(List<NominationData> updateNominationData) {
		this.updateNominationData = updateNominationData;
	}

	@Override
	public String toString() {
		return "Nominations [nominationId=" + nominationId + ", nominatedBy=" + nominatedBy + ", gasDate=" + gasDate
				+ ", contractId=" + contractId + ", contractName=" + contractName + ", redelPoint=" + redelPoint
				+ ", delPoint=" + delPoint + ", revision_no=" + revision_no + ", status=" + status + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", updateDate=" + updateDate + ", updatedBy=" + updatedBy
				+ ", sellerredelPoint=" + sellerredelPoint + ", sellerUpdatedDate=" + sellerUpdatedDate + ", startDate="
				+ startDate + ", endDate=" + endDate + ", regionId=" + regionId + ", contractType=" + contractType
				+ ", contractRef=" + contractRef + ", userId=" + userId + ", fileName=" + fileName + ", nominationData="
				+ nominationData + ", updateNominationData=" + updateNominationData + ", regionName=" + regionName
				+ ", customerCode=" + customerCode + ", customerDescription=" + customerDescription + ", materialCode="
				+ materialCode + ", materialDesc=" + materialDesc + ", unitOfMeasurements=" + unitOfMeasurements
				+ ", payerId=" + payerId + ", clientUserId=" + clientUserId + ", contract_ref=" + contract_ref
				+ ", contract_id=" + contract_id + ", nomination_id=" + nomination_id + ", seller_redel_point="
				+ seller_redel_point + "]";
	}

	public interface NominationReq {

	}

	public interface GetContractNominationRequest {

	}

	public interface GetContractSellerReq {

	}

	public interface UpdateSellerNominate {

	}

	public interface SaveNomination {

	}

	public interface BuildNominationReport {

	}

	public interface UpdateNomination {

	}
}
