package com.gail.responseData;

import java.math.BigInteger;

public class ReminderContractDto {
    private String contract_ref;
    private String material_code;
    private String customer_code;
    private String contract_type;
    private BigInteger payer_id;
    //private BigInteger contract_id;
    
    
//    public BigInteger getContract_id() {
//		return contract_id;
//	}
//	public void setContract_id(BigInteger contract_id) {
//		this.contract_id = contract_id;
//	}
	public BigInteger getPayer_id() {
        return payer_id;
    }
    public void setPayer_id(BigInteger payer_id) {
        this.payer_id = payer_id;
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
    public String getContract_type() {
        return contract_type;
    }
    public void setContract_type(String contract_type) {
        this.contract_type = contract_type;
    }
    @Override
    public String toString() {
        return "ReminderContractDto [contract_ref=" + contract_ref
                + ", material_code=" + material_code + ", customer_code="
                + customer_code + ", contract_type=" + contract_type
                + ", payer_id=" + payer_id + "]";
    }
    
    
}
