package com.gail.responseData;

import java.math.BigInteger;

public class MaterialData {
    
    private BigInteger materialId;
    private String materialCode;
    public BigInteger getMaterialId() {
        return materialId;
    }
    public void setMaterialId(BigInteger materialId) {
        this.materialId = materialId;
    }
    public String getMaterialCode() {
        return materialCode;
    }
    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }
    @Override
    public String toString() {
        return "MaterialData [materialId=" + materialId + ", materialCode="
                + materialCode + "]";
    }
    
    
    

}
