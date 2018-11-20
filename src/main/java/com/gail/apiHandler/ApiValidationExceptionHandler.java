package com.gail.apiHandler;

import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gail.utility.Constants;
import com.gail.validation.model.GailResponse;
import com.gail.validation.model.ApiFieldError;
import com.gail.validation.model.ResponseStatus;

@ControllerAdvice
public class ApiValidationExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(ApiValidationExceptionHandler.class);

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.info("Started handleMethodArgumentNotValid");
		BindingResult bindingResult = ex.getBindingResult();
		Map<String, String> map = new HashMap<String, String>();
		
		ResponseStatus resStatus = new ResponseStatus();
		resStatus.setErrorType(Constants.ERROR_TYPE_VALIDATION);
		resStatus.setErrorTypeCode(Constants.ERROR_TYPE_CODE_VALIDATION);

		List<ApiFieldError> apiFieldErrors = bindingResult.getFieldErrors().stream()
				.map(fieldError -> new ApiFieldError(fieldError.getField(), fieldError.getCode(),
						fieldError.getRejectedValue()))
				.collect(toList());
		for (ApiFieldError api : apiFieldErrors) {
			if (!api.getField().equals("token")) {
				switch (api.getCode().toString()) {
				case "NotEmpty":
				case "NotNull":
					map.put(api.getField(), Constants.ERROR_FIELD_BLANK);
					resStatus.setStatusCode(status.value());
					resStatus.setMessage(api.getField() + " " + Constants.ERROR_FIELD_BLANK);
					break;
				case "DateFormat":
					map.put(api.getField(), Constants.ERROR_WRONG_DATE_FORMAT);
					resStatus.setStatusCode(status.value());
					resStatus.setMessage(api.getField() + " " + Constants.ERROR_WRONG_DATE_FORMAT);
					break;
				case "Email":
					map.put(api.getField(), Constants.ERROR_WRONG_EMAIL);
					resStatus.setStatusCode(status.value());
					resStatus.setMessage(api.getField() + " " + Constants.ERROR_WRONG_EMAIL);
					break;
				default:
					map.put(api.getField(), Constants.ERROR_MALFORMED_REQUEST);
					resStatus.setStatusCode(status.value());
					resStatus.setMessage(api.getField() + " " + Constants.ERROR_MALFORMED_REQUEST);
				}
			}
		}

		if (!map.isEmpty()) {
			GailResponse apiErrorsView = new GailResponse(resStatus, map);
			return new ResponseEntity<Object>(apiErrorsView, status);
		}

		for (ApiFieldError api : apiFieldErrors) {
			switch (api.getCode().toString()) {
			case "NotEmpty":
			case "NotNull":
				map.put("token", Constants.ERROR_MISSING_TOKEN);
				resStatus.setStatusCode(status.value());
				resStatus.setMessage(api.getField() + " " + Constants.ERROR_MISSING_TOKEN);
				break;
			case "InvalidToken":
				map.put("token", Constants.ERROR_INVALID_TOKEN);
				resStatus.setStatusCode(status.value());
				resStatus.setMessage(api.getField() + " " + Constants.ERROR_INVALID_TOKEN);
				break;
			case "InvalidTokenSession":
				map.put("token", Constants.ERROR_SESSION_ALREADY_ACTIVE);
				resStatus.setStatusCode(status.value());
				resStatus.setMessage(api.getField() + " " + Constants.ERROR_SESSION_ALREADY_ACTIVE);
				break;
			default:
				map.put(api.getField(), Constants.ERROR_MALFORMED_REQUEST);
				resStatus.setStatusCode(status.value());
				resStatus.setMessage(api.getField() + " " + Constants.ERROR_MALFORMED_REQUEST);
			}
			GailResponse apiErrorsView = new GailResponse(resStatus, map);
			return new ResponseEntity<Object>(apiErrorsView, status);
		}
		return null;

	}

/*	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.info("Started handleHttpMediaTypeNotSupported");
		String object = Constants.ERROR_MEDIA_TYPE;
		ApiErrorsView apiErrorsView = new ApiErrorsView(status.value(), object, false);
		return new ResponseEntity<>(apiErrorsView, status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.info("Started handleHttpMessageNotReadable");
		String object = Constants.ERROR_MALFORMED_REQUEST;
		ApiErrorsView apiErrorsView = new ApiErrorsView(status.value(), object, false);
		return new ResponseEntity<>(apiErrorsView, status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.info("Started handleHttpMediaTypeNotAcceptable");
		String object = "Could not find acceptable representation";
		ApiErrorsView apiErrorsView = new ApiErrorsView(status.value(), object, false);
		return new ResponseEntity<>(apiErrorsView, status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.info("Started handleHttpRequestMethodNotSupported");
		String object = Constants.ERROR_METHOD_NOT_ALLOWED;
		ApiErrorsView apiErrorsView = new ApiErrorsView(status.value(), object, false);
		return new ResponseEntity<>(apiErrorsView, status);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		logger.info("Started handleNoHandlerFoundException");
		String object = Constants.ERROR_METHOD_NOT_ALLOWED;
		ApiErrorsView apiErrorsView = new ApiErrorsView(status.value(), object, false);
		return new ResponseEntity<Object>(apiErrorsView, status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.info("Started handleHttpMessageNotWritable");
		String object = Constants.ERROR_METHOD_NOT_ALLOWED;
		ApiErrorsView apiErrorsView = new ApiErrorsView(status.value(), object, false);
		return new ResponseEntity<Object>(apiErrorsView, status);
	}*/

}