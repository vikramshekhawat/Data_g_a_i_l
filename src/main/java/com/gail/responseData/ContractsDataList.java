package com.gail.responseData;

import java.util.List;

public class ContractsDataList {
	

    private List<ContractsData> contractsDataList ;

    public List<ContractsData> getContractsDataList() {
        return contractsDataList;
    }

    public  void setContractsDataList(List<ContractsData> contractsDataList) {
        this.contractsDataList = contractsDataList;
    }

    @Override
    public String toString() {
        return "ContractsDataList [contractsDataList=" + contractsDataList
                + "]";
    }





}
