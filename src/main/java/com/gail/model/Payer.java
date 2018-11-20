package com.gail.model;

import java.math.BigInteger;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "payer")
public class Payer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payer_id", nullable = false)
	private BigInteger payerId;

	@Column(name = "region_id", nullable = false)
	private BigInteger regionId;

	@Column(name = "payer_key", nullable = false)
	private String payerKey;

	@Column(name = "payer_name", nullable = false)
	private String payerName;

	@Column(name = "cutofftime", nullable = false)
	private Time cutOffTime;

	@Column(name = "status", nullable = false)
	private int status;

	@Column(name = "created_date", nullable = false)
	private Date createdDate;

	@Column(name = "updated_date", nullable = false)
	private Date updateDate;

	public BigInteger getPayerId() {
		return payerId;
	}

	public void setPayerId(BigInteger payerId) {
		this.payerId = payerId;
	}

	public BigInteger getRegionId() {
		return regionId;
	}

	public void setRegionId(BigInteger regionId) {
		this.regionId = regionId;
	}

	public String getPayerKey() {
		return payerKey;
	}

	public void setPayerKey(String payerKey) {
		this.payerKey = payerKey;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public Time getCutOffTime() {
		return cutOffTime;
	}

	public void setCutOffTime(Time cutOffTime) {
		this.cutOffTime = cutOffTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
	
	
	
	
}