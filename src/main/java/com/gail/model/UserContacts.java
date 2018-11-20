package com.gail.model;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_contacts")
public class UserContacts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_contacts_id", nullable = false)
	private BigInteger userContactsId;

	@Column(name = "user_id", nullable = false)
	private BigInteger userId;

	@Column(name = "device_id")
	private String deviceToken;

	@Column(name = "device_type")
	private String deviceType;

	@Column(name = "contact_type", nullable = false)
	private String contactType;

	@Column(name = "contact_value", nullable = false)
	private String contactValue;

	@Column(name = "priority", nullable = false)
	private int priority;

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

	public BigInteger getUserContactsId() {
		return userContactsId;
	}

	public void setUserContactsId(BigInteger userContactsId) {
		this.userContactsId = userContactsId;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
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

	public String getContactType() {
		return contactType;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getContactValue() {
		return contactValue;
	}

	public void setContactValue(String contactValue) {
		this.contactValue = contactValue;
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
		return "UserContactModel [userContactsId=" + userContactsId + ", userId=" + userId + ", contactType="
				+ contactType + ", contactValue=" + contactValue + ", priority=" + priority + ", deviceToken="
				+ deviceToken + ", deviceType=" + deviceType + ", status=" + status + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", updatedBy=" + updatedBy + "]";
	}

}
