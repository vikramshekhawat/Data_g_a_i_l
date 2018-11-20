package com.gail.responseData;

import java.math.BigInteger;
import java.util.Date;

public class DownloadUserDTO {

	private BigInteger payer_id;
	private String payer_name;
	private String primary_email_id;
	private String secondary_email_id1;
	private String secondary_email_id2;
	private String secondary_email_id3;
	private String secondary_email_id4;
	private String cutoff_time;
	private String region;
	private Date created_date;

	public BigInteger getPayer_id() {
		return payer_id;
	}

	public void setPayer_id(BigInteger payer_id) {
		this.payer_id = payer_id;
	}

	public String getPayer_name() {
		return payer_name;
	}

	public void setPayer_name(String payer_name) {
		this.payer_name = payer_name;
	}

	public String getPrimary_email_id() {
		return primary_email_id;
	}

	public void setPrimary_email_id(String primary_email_id) {
		this.primary_email_id = primary_email_id;
	}

	public String getSecondary_email_id1() {
		return secondary_email_id1;
	}

	public void setSecondary_email_id1(String secondary_email_id1) {
		this.secondary_email_id1 = secondary_email_id1;
	}

	public String getSecondary_email_id2() {
		return secondary_email_id2;
	}

	public void setSecondary_email_id2(String secondary_email_id2) {
		this.secondary_email_id2 = secondary_email_id2;
	}

	public String getSecondary_email_id3() {
		return secondary_email_id3;
	}

	public void setSecondary_email_id3(String secondary_email_id3) {
		this.secondary_email_id3 = secondary_email_id3;
	}

	public String getSecondary_email_id4() {
		return secondary_email_id4;
	}

	public void setSecondary_email_id4(String secondary_email_id4) {
		this.secondary_email_id4 = secondary_email_id4;
	}

	public String getCutoff_time() {
		return cutoff_time;
	}

	public void setCutoff_time(String cutoff_time) {
		this.cutoff_time = cutoff_time;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	@Override
	public String toString() {
		return "DownloadUserDTO [payer_id=" + payer_id + ", payer_name=" + payer_name + ", primary_email_id="
				+ primary_email_id + ", secondary_email_id1=" + secondary_email_id1 + ", secondary_email_id2="
				+ secondary_email_id2 + ", secondary_email_id3=" + secondary_email_id3 + ", secondary_email_id4="
				+ secondary_email_id4 + ", cutoff_time=" + cutoff_time + ", region=" + region + ", created_date="
				+ created_date + "]";
	}

}
