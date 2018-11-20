package com.gail.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gail.validation.model.GailResponse;
import com.gail.validation.model.ResponseStatus;

public class ResponseBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseBuilder.class);

	public static ResponseEntity<Object> getErrorResponse(ErrorDetails errorDetails) {
		LOGGER.info("Setting Error Response");
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setMessage(errorDetails.getMessage());
		responseStatus.setStatusCode(errorDetails.getErrorCode());
		responseStatus.setErrorTypeCode(errorDetails.getErrorTypeCode());
		responseStatus.setErrorType(errorDetails.getErrorType());
		GailResponse response = new GailResponse();
		response.setResponseStatus(responseStatus);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	public static ResponseEntity<Object> getSuccessResponse() throws GailNominationServiceException {
		return getSuccessResponse(null);
	}

	public static ResponseEntity<Object> getSuccessResponse(Object responseData) {
		LOGGER.info("Setting Success Response");
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setStatusCode(Constants.API_SUCCESS);
		GailResponse response = new GailResponse();
		response.setResponseStatus(responseStatus);
		response.setResponseData(responseData);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
}
