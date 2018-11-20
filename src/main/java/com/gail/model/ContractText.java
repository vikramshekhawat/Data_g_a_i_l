package com.gail.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "contract_text")
public class ContractText {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contract_id", nullable = false)
	private BigInteger contractTextId;
    @NotNull(groups={ContractText.ContractReq.class})
	@Column(name = "contract_ref", nullable = false)
	private BigInteger contractRef;
    @NotNull(groups={ContractText.ContractReq.class})
	@Column(name = "contract_name")
	private String contractName;
    @NotNull(groups={ContractText.ContractReq.class})
	@Column(name = "payer_id")
	private String payerId;

	public BigInteger getContractTextId() {
		return contractTextId;
	}

	public void setContractTextId(BigInteger contractTextId) {
		this.contractTextId = contractTextId;
	}

	public BigInteger getContractRef() {
		return contractRef;
	}

	public void setContractRef(BigInteger contractRef) {
		this.contractRef = contractRef;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}
	
	public interface ContractReq{
		
	}

	@Override
	public String toString() {
		return "ContractText [contractTextId=" + contractTextId + ", contractRef=" + contractRef + ", contractName="
				+ contractName + ", payerId=" + payerId + "]";
	}
	
}
