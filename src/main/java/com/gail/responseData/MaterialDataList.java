package com.gail.responseData;

import java.util.List;

public class MaterialDataList {
    
    private List<MaterialData> materialDataList ;

    public List<MaterialData> getMaterialDataList() {
        return materialDataList;
    }

    public void setMaterialDataList(List<MaterialData> materialDataList) {
        this.materialDataList = materialDataList;
    }

    @Override
    public String toString() {
        return "MaterialDataList [materialDataList=" + materialDataList + "]";
    }
    

}
