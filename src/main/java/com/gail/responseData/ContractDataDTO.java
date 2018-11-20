package com.gail.responseData;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

public class ContractDataDTO {
	
	private BigInteger contract_id;
	private BigInteger material_id;
	private String contract_ref;
//	private String contract_name;
	private String material_code;
	private String material_desc;
	private String unit_of_measurements;
	private String customer_code;
	private String customer_description;
	private String contract_type;
	private BigInteger nomination_id;
	private Date start_date;
	private Date end_date;
	private Timestamp seller_updated_date;
	private BigDecimal seller_redel_point;
	private String payer_name;
	private BigInteger user_id;
	private String payer_key;
	public BigInteger getContract_id() {
		return contract_id;
	}
	public void setContract_id(BigInteger contract_id) {
		this.contract_id = contract_id;
	}
	public BigInteger getMaterial_id() {
		return material_id;
	}
	public void setMaterial_id(BigInteger material_id) {
		this.material_id = material_id;
	}
	public String getContract_ref() {
		return contract_ref;
	}
	public void setContract_ref(String contract_ref) {
		this.contract_ref = contract_ref;
	}
	public String getMaterial_code() {
		return material_code;
	}
	public void setMaterial_code(String material_code) {
		this.material_code = material_code;
	}
	public String getMaterial_desc() {
		return material_desc;
	}
	public void setMaterial_desc(String material_desc) {
		this.material_desc = material_desc;
	}
	public String getUnit_of_measurements() {
		return unit_of_measurements;
	}
	public void setUnit_of_measurements(String unit_of_measurements) {
		this.unit_of_measurements = unit_of_measurements;
	}
	public String getCustomer_code() {
		return customer_code;
	}
	public void setCustomer_code(String customer_code) {
		this.customer_code = customer_code;
	}
	public String getCustomer_description() {
		return customer_description;
	}
	public void setCustomer_description(String customer_description) {
		this.customer_description = customer_description;
	}
	public String getContract_type() {
		return contract_type;
	}
	public void setContract_type(String contract_type) {
		this.contract_type = contract_type;
	}
	public BigInteger getNomination_id() {
		return nomination_id;
	}
	public void setNomination_id(BigInteger nomination_id) {
		this.nomination_id = nomination_id;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public Timestamp getSeller_updated_date() {
		return seller_updated_date;
	}
	public void setSeller_updated_date(Timestamp seller_updated_date) {
		this.seller_updated_date = seller_updated_date;
	}
	public BigDecimal getSeller_redel_point() {
		return seller_redel_point;
	}
	public void setSeller_redel_point(BigDecimal seller_redel_point) {
		this.seller_redel_point = seller_redel_point;
	}
	public String getPayer_name() {
		return payer_name;
	}
	public void setPayer_name(String payer_name) {
		this.payer_name = payer_name;
	}
	public BigInteger getUser_id() {
		return user_id;
	}
	public void setUser_id(BigInteger user_id) {
		this.user_id = user_id;
	}
	public String getPayer_key() {
		return payer_key;
	}
	public void setPayer_key(String payer_key) {
		this.payer_key = payer_key;
	}
	
	

}
