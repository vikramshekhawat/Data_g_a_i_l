package com.gail.utility;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonParseUtil {

	public static Object getObjectFromJSON(String jsonString, Class<? extends Object> classInstance)
			throws GailNominationServiceException {
		Object object = null;

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			object = mapper.readValue(jsonString, classInstance);
		} catch (JsonParseException exception) {
			ExceptionUtil.handleException(new ErrorDetails(Constants.OBJECT_FROM_JSON_PARSE_EXCEPTION,
					Constants.ERROR_TYPE_CODE_VALIDATION, Constants.ERROR_TYPE_VALIDATION,
					"JsonParseException occured while creating object from json"), exception, jsonString);
		} catch (JsonMappingException exception) {
			ExceptionUtil.handleException(new ErrorDetails(Constants.OBJECT_FROM_JSON_MAPPING_EXCEPTION,
					Constants.ERROR_TYPE_CODE_VALIDATION, Constants.ERROR_TYPE_VALIDATION,
					"JsonMappingException occured while creating object from json"), exception, jsonString);
		} catch (IOException exception) {
			ExceptionUtil.handleException(
					new ErrorDetails(Constants.OBJECT_FROM_JSON_IO_EXCEPTION, Constants.ERROR_TYPE_CODE_VALIDATION,
							Constants.ERROR_TYPE_VALIDATION, "IOException occured while creating object from json"),
					exception, jsonString);
		} catch (Exception exception) {
			ExceptionUtil.handleException(
					new ErrorDetails(Constants.OBJECT_FROM_JSON_EXCEPTION, Constants.ERROR_TYPE_CODE_VALIDATION,
							Constants.ERROR_TYPE_VALIDATION, "Exception occured while creating object from json"),
					exception, jsonString);
		}

		return object;
	}

	// Create JSON String
	public static String getJsonStringFromObject(Object responseObject) throws GailNominationServiceException {
		String jsonString = "";

		try {
			ObjectMapper mapper = new ObjectMapper();
			jsonString = mapper.writeValueAsString(responseObject);
		} catch (JsonProcessingException exception) {
			ExceptionUtil.handleException(new ErrorDetails(Constants.JSON_FROM_OBJECT_PROCESSING_EXCEPTION,
					Constants.ERROR_TYPE_CODE_VALIDATION, Constants.ERROR_TYPE_VALIDATION,
					"JsonProcessingException occured while creating json from object"), exception, jsonString);
		} catch (Exception exception) {
			ExceptionUtil.handleException(
					new ErrorDetails(Constants.JSON_FROM_OBJECT_EXCEPTION, Constants.ERROR_TYPE_CODE_VALIDATION,
							Constants.ERROR_TYPE_VALIDATION, "Exception occured while creating json from object"),
					exception, jsonString);
		}

		return jsonString;
	}
}