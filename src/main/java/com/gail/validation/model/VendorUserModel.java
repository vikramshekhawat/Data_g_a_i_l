package com.gail.validation.model;

import java.math.BigInteger;

public class VendorUserModel {
	private BigInteger user_id;
	private String material_code;
	private BigInteger material_id;
	private  BigInteger contract_id;
	public BigInteger getUser_id() {
		return user_id;
	}
	public void setUser_id(BigInteger user_id) {
		this.user_id = user_id;
	}
	public String getMaterial_code() {
		return material_code;
	}
	public void setMaterial_code(String material_code) {
		this.material_code = material_code;
	}
	public BigInteger getMaterial_id() {
		return material_id;
	}
	public void setMaterial_id(BigInteger material_id) {
		this.material_id = material_id;
	}
	public BigInteger getContract_id() {
		return contract_id;
	}
	public void setContract_id(BigInteger contract_id) {
		this.contract_id = contract_id;
	}
	@Override
	public String toString() {
		return "VendorUserModel [user_id=" + user_id + ", material_code=" + material_code + ", material_id="
				+ material_id + ", contract_id=" + contract_id + "]";
	}
	
	
	
	
}