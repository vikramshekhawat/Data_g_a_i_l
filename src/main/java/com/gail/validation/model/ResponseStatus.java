package com.gail.validation.model;

public class ResponseStatus {

	private int statusCode;
	private String message;
	private Integer errorTypeCode;
	private String errorType = null;

	public ResponseStatus() {

	}

	public ResponseStatus(int statusCode, String message, Integer errorTypeCode, String errorType) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.errorTypeCode = errorTypeCode;
		this.errorType = errorType;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getErrorTypeCode() {
		return errorTypeCode;
	}

	public void setErrorTypeCode(Integer errorTypeCode) {
		this.errorTypeCode = errorTypeCode;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

}
