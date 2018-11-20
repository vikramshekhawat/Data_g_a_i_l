package com.gail.responseData;

import java.util.List;



public class NominationResponseDataList {

	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private List<NominationResponseData> nominationData;

	public List<NominationResponseData> getNominationData() {
		return nominationData;
	}

	public void setNominationData(List<NominationResponseData> nominationData) {
		this.nominationData = nominationData;
	}

	@Override
	public String toString() {
		return "NominationResponseDataList [filePath=" + fileName + ", nominationData=" + nominationData + "]";
	}

}
