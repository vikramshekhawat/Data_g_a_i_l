package com.gail.responseData;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

public class NominationDataDTO {
    private String payer_key;
	private Date gas_date;
	private String payer_name;
	private String contract_type;
	private BigInteger contract_id;
	private BigInteger material_id;
	private BigDecimal del_point;
	private BigDecimal redel_point;
	private String contract_ref;
	// private String contract_name;
	private String material_code;
	private String material_desc;
	private String customer_description;
	private String customer_code;
	private String unit_of_measurements;
	private Timestamp updated_Date;
	private BigInteger nomination_id;
	private BigInteger nominated_by;
	private int revision_no;
	private Date start_date;
	private Date end_date;
	private BigDecimal seller_redel_point;
	private Timestamp seller_updated_date;
	private BigInteger user_id;
	public String getPayer_key() {
		return payer_key;
	}
	public void setPayer_key(String payer_key) {
		this.payer_key = payer_key;
	}
	public Date getGas_date() {
		return gas_date;
	}
	public void setGas_date(Date gas_date) {
		this.gas_date = gas_date;
	}
	public String getPayer_name() {
		return payer_name;
	}
	public void setPayer_name(String payer_name) {
		this.payer_name = payer_name;
	}
	public String getContract_type() {
		return contract_type;
	}
	public void setContract_type(String contract_type) {
		this.contract_type = contract_type;
	}
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
	public BigDecimal getDel_point() {
		return del_point;
	}
	public void setDel_point(BigDecimal del_point) {
		this.del_point = del_point;
	}
	public BigDecimal getRedel_point() {
		return redel_point;
	}
	public void setRedel_point(BigDecimal redel_point) {
		this.redel_point = redel_point;
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
	public String getCustomer_description() {
		return customer_description;
	}
	public void setCustomer_description(String customer_description) {
		this.customer_description = customer_description;
	}
	public String getCustomer_code() {
		return customer_code;
	}
	public void setCustomer_code(String customer_code) {
		this.customer_code = customer_code;
	}
	public String getUnit_of_measurements() {
		return unit_of_measurements;
	}
	public void setUnit_of_measurements(String unit_of_measurements) {
		this.unit_of_measurements = unit_of_measurements;
	}
	public Timestamp getUpdated_Date() {
		return updated_Date;
	}
	public void setUpdated_Date(Timestamp updated_Date) {
		this.updated_Date = updated_Date;
	}
	public BigInteger getNomination_id() {
		return nomination_id;
	}
	public void setNomination_id(BigInteger nomination_id) {
		this.nomination_id = nomination_id;
	}
	public BigInteger getNominated_by() {
		return nominated_by;
	}
	public void setNominated_by(BigInteger nominated_by) {
		this.nominated_by = nominated_by;
	}
	public int getRevision_no() {
		return revision_no;
	}
	public void setRevision_no(int revision_no) {
		this.revision_no = revision_no;
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
	public BigDecimal getSeller_redel_point() {
		return seller_redel_point;
	}
	public void setSeller_redel_point(BigDecimal seller_redel_point) {
		this.seller_redel_point = seller_redel_point;
	}
	public Timestamp getSeller_updated_date() {
		return seller_updated_date;
	}
	public void setSeller_updated_date(Timestamp seller_updated_date) {
		this.seller_updated_date = seller_updated_date;
	}
	public BigInteger getUser_id() {
		return user_id;
	}
	public void setUser_id(BigInteger user_id) {
		this.user_id = user_id;
	}
	
	
	
	
}
