package com.gail.responseData;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class DownloadNominationDTO {
	private Date gas_date;

	private BigInteger nomination_id;
	private BigInteger material_id;
	private BigDecimal del_point;
	private BigDecimal redel_point;
	private String contract_ref;
	private String contract_type;
	private String material_code;
	private String region_name;
	private String customer_code;
	private String unit_of_measurements;
	private Date updated_Date;
	private BigInteger contract_id;
	private BigDecimal seller_redel_point;
	private Date seller_updated_date;
	
	

	public Date getSeller_updated_date() {
		return seller_updated_date;
	}

	public void setSeller_updated_date(Date seller_updated_date) {
		this.seller_updated_date = seller_updated_date;
	}

	public BigDecimal getSeller_redel_point() {
		return seller_redel_point;
	}

	public void setSeller_redel_point(BigDecimal seller_redel_point) {
		this.seller_redel_point = seller_redel_point;
	}

	public BigInteger getContract_id() {
		return contract_id;
	}

	public void setContract_id(BigInteger contract_id) {
		this.contract_id = contract_id;
	}

	public Date getGas_date() {
		return gas_date;
	}

	public BigInteger getMaterial_id() {
		return material_id;
	}

	public void setMaterial_id(BigInteger material_id) {
		this.material_id = material_id;
	}

	public String getContract_type() {
		return contract_type;
	}

	public void setContract_type(String contract_type) {
		this.contract_type = contract_type;
	}

	public void setGas_date(Date gas_date) {
		this.gas_date = gas_date;
	}

	public BigInteger getNomination_id() {
		return nomination_id;
	}

	public void setNomination_id(BigInteger nomination_id) {
		this.nomination_id = nomination_id;
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

	public Date getUpdated_Date() {
		return updated_Date;
	}

	public void setUpdated_Date(Date updated_Date) {
		this.updated_Date = updated_Date;
	}

	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	@Override
	public String toString() {
		return "DownloadNominationDTO [gas_date=" + gas_date + ", nomination_id=" + nomination_id + ", material_id="
				+ material_id + ", del_point=" + del_point + ", redel_point=" + redel_point + ", contract_ref="
				+ contract_ref + ", contract_type=" + contract_type + ", material_code=" + material_code
				+ ", region_name=" + region_name + ", customer_code=" + customer_code + ", unit_of_measurements="
				+ unit_of_measurements + ", updated_Date=" + updated_Date + ", contract_id=" + contract_id
				+ ", seller_redel_point=" + seller_redel_point + ", seller_updated_date=" + seller_updated_date + "]";
	}

}
