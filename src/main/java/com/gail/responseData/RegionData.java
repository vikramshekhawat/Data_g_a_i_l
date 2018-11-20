package com.gail.responseData;

public class RegionData {

	private String regionId;
	private String regionName;

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	@Override
	public String toString() {
		return "RegionData [regionId=" + regionId + ", regionName=" + regionName + "]";
	}

}
