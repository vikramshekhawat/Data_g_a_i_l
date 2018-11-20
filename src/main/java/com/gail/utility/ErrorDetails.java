package com.gail.utility;

public class ErrorDetails  {

    private int errorCode;

    private int errorTypeCode;

    private String errorType;

    private String message;
    
    
    

	public ErrorDetails(int errorCode, int errorTypeCode, String errorType, String message) {
		super();
		this.errorCode = errorCode;
		this.errorTypeCode = errorTypeCode;
		this.errorType = errorType;
		this.message = message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getErrorTypeCode() {
		return errorTypeCode;
	}

	public void setErrorTypeCode(int errorTypeCode) {
		this.errorTypeCode = errorTypeCode;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ErrorDetails [errorCode=" + errorCode + ", errorTypeCode=" + errorTypeCode + ", errorType=" + errorType
				+ ", message=" + message + "]";
	}
    
    
    
}
