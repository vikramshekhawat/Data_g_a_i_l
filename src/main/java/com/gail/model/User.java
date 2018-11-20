package com.gail.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Time;
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

@Entity
@Table(name = "user")
@DynamicInsert
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4116447726963817157L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false)
	@NotNull(groups = { User.VendorStatus.class })
	private BigInteger userId;

	@NotEmpty(groups = { User.Login.class })
	@Column(name = "user_name", nullable = false)
	private String userName;

	@Column(name = "role", nullable = false)
	private String role;

	@NotEmpty(groups = { User.Login.class })
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "payer_id", nullable = true)
	private BigInteger payerId;

	@Column(name = "is_password_changed")
	private boolean isPasswordChanged;

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
	private String payerName;

	@Transient
	private Time cutOffTime;

	@Transient
	private String tickerText;

	@Transient
	private String token;

	@Transient
	private String deviceToken;

	@Transient
	private String deviceType;

	@Transient
	private Boolean vendorStatus;

	@Transient
	private List<UserContacts> contactList;

	@Transient
	private String fileName;

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BigInteger getPayerId() {
		return payerId;
	}

	public void setPayerId(BigInteger payerId) {
		this.payerId = payerId;
	}

	public boolean isPasswordChanged() {
		return isPasswordChanged;
	}

	public void setPasswordChanged(boolean isPasswordChanged) {
		this.isPasswordChanged = isPasswordChanged;
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

	public String getTickerText() {
		return tickerText;
	}

	public void setTickerText(String tickerText) {
		this.tickerText = tickerText;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public Boolean getVendorStatus() {
		return vendorStatus;
	}

	public void setVendorStatus(Boolean vendorStatus) {
		this.vendorStatus = vendorStatus;
	}

	public List<UserContacts> getContactList() {
		return contactList;
	}

	public void setContactList(List<UserContacts> contactList) {
		this.contactList = contactList;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "UserModel [userId=" + userId + ", userName=" + userName + ", role=" + role + ", password=" + password
				+ ", payerId=" + payerId + ", isPasswordChanged=" + isPasswordChanged + ", status=" + status
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate
				+ ", updatedBy=" + updatedBy + ", fileName=" + fileName + "]";
	}

	public interface Login {
	}

	public interface VendorStatus {
	}
}
