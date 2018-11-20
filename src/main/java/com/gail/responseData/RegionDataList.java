package com.gail.responseData;

import java.util.List;

public class RegionDataList {

	private List<RegionData> dataList;

	public List<RegionData> getDataList() {
		return dataList;
	}

	public void setDataList(List<RegionData> dataList) {
		this.dataList = dataList;
	}

	@Override
	public String toString() {
		return "RegionDataList [dataList=" + dataList + "]";
	}

}
