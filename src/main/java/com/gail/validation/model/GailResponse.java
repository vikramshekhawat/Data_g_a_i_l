package com.gail.validation.model;

public class GailResponse {

	private ResponseStatus responseStatus;
	private Object responseData;

	public GailResponse() {
	}

	public GailResponse(ResponseStatus responseStatus, Object responseData) {
		this.responseStatus = responseStatus;
		this.responseData = responseData;
	}

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public Object getResponseData() {
		return responseData;
	}

	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}

}
