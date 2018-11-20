package com.gail.responseData;

public class NominationData {
    private String contractId;
	private String delPoint;
	private String redelPoint;
	private String contractName;
	private String sellerRedelpoint;
	private String sellerUpdateTime;
	private String nominationId;
	private String revisionNo;
	
	
	public String getNominationId() {
		return nominationId;
	}
	public void setNominationId(String nominationId) {
		this.nominationId = nominationId;
	}
	public String getRevisionNo() {
		return revisionNo;
	}
	public void setRevisionNo(String revisionNo) {
		this.revisionNo = revisionNo;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getDelPoint() {
		return delPoint;
	}
	public void setDelPoint(String delPoint) {
		this.delPoint = delPoint;
	}
	public String getRedelPoint() {
		return redelPoint;
	}
	public void setRedelPoint(String redelPoint) {
		this.redelPoint = redelPoint;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getSellerRedelpoint() {
		return sellerRedelpoint;
	}
	public void setSellerRedelpoint(String sellerRedelpoint) {
		this.sellerRedelpoint = sellerRedelpoint;
	}
	public String getSellerUpdateTime() {
		return sellerUpdateTime;
	}
	public void setSellerUpdateTime(String sellerUpdateTime) {
		this.sellerUpdateTime = sellerUpdateTime;
	}
	@Override
	public String toString() {
		return "NominationData [contractId=" + contractId + ", delPoint=" + delPoint + ", redelPoint=" + redelPoint
				+ ", contractName=" + contractName + ", sellerRedelpoint=" + sellerRedelpoint + ", sellerUpdateTime="
				+ sellerUpdateTime + ", nominationId=" + nominationId + ", revisionNo=" + revisionNo + "]";
	}
	
	
	
}
