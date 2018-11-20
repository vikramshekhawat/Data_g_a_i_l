package com.gail.responseData;

import java.util.Date;

public class DownloadContractsDTO {

	private String contract_reference;
	private String material_code;
	private Date start_date;
	private Date end_date;
	private String uom;
	private String payer_key;
	private String customer_code;
	private String customer_description;
	private Date created_date;
	private Date updated_date;

	public String getContract_reference() {
		return contract_reference;
	}

	public void setContract_reference(String contract_reference) {
		this.contract_reference = contract_reference;
	}

	public String getMaterial_code() {
		return material_code;
	}

	public void setMaterial_code(String material_code) {
		this.material_code = material_code;
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

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getPayer_key() {
		return payer_key;
	}

	public void setPayer_key(String payer_key) {
		this.payer_key = payer_key;
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

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Date getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(Date updated_date) {
		this.updated_date = updated_date;
	}

	@Override
	public String toString() {
		return "DownloadUserDTO [contract_reference=" + contract_reference + ", material_id=" + material_code
				+ ", start_date=" + start_date + ", end_date=" + end_date + ", uom=" + uom + ", payer_key=" + payer_key
				+ ", customer_code=" + customer_code + ", customer_description=" + customer_description
				+ ", created_date=" + created_date + ", updated_date=" + updated_date + "]";
	}

}
