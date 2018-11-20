package com.gail.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gail.model.User;
import com.gail.restURIConstants.GailNominationURI;
import com.gail.service.UserService;
import com.gail.utility.Constants;
import com.gail.utility.GailNominationServiceException;
import com.gail.utility.ResponseBuilder;
import com.gail.utility.UTCDate;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	ServletContext context;

	@Autowired
	UserService userService;

	@PostMapping(value = GailNominationURI.LOGIN, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> login(@RequestBody @Validated(User.Login.class) User loginData)
			throws MethodArgumentNotValidException {
		ResponseEntity<Object> response = null;
		logger.info("login processing");
		try {
			User loginResponse = userService.login(loginData);
			response = ResponseBuilder.getSuccessResponse(loginResponse);
			return response;
		} catch (GailNominationServiceException e) {
			response = ResponseBuilder.getErrorResponse(e.getErrorDetails());
			return response;
		} catch (Exception ex) {
			logger.error(this.getClass().getName() + ex.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping(value = GailNominationURI.VENDOR_STATUS, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> vendorStatus(@RequestBody @Validated(User.VendorStatus.class) User userData)
			throws MethodArgumentNotValidException {
		ResponseEntity<Object> response = null;
		logger.info("Vendor Status processing");
		try {
			User user = userService.isVendor(userData.getUserId());
			response = ResponseBuilder.getSuccessResponse(user);
			return response;
		} catch (GailNominationServiceException e) {
			response = ResponseBuilder.getErrorResponse(e.getErrorDetails());
			return response;
		}
	}

	@GetMapping(value = GailNominationURI.BUILD_USER_REPORT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getUserReport() throws MethodArgumentNotValidException {
		ResponseEntity<Object> response = null;
		logger.info("login processing");
		try {
			User userList = userService.downloadUserReport();
			response = ResponseBuilder.getSuccessResponse(userList);
			return response;
		} catch (GailNominationServiceException e) {
			response = ResponseBuilder.getErrorResponse(e.getErrorDetails());
			return response;
		}
	}

	@PostMapping(value = GailNominationURI.UPLOAD_USER, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadUser(@RequestParam("userId") String userId,
			@RequestParam("file") MultipartFile inputFile) {
		ResponseEntity<Object> response = null;
		try {
			String fileName = Constants.FILE_PATH + UTCDate.getCurrentUTCDate().getTime() + Constants.FILE_EXT;
			File tempFile = new File(fileName);
			inputFile.transferTo(tempFile);
			InputStream file = new FileInputStream(tempFile);
			userService.readFile(file, userId);
			response = ResponseBuilder.getSuccessResponse();
			tempFile.delete();
		} catch (GailNominationServiceException ex) {
			response = ResponseBuilder.getErrorResponse(ex.getErrorDetails());
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

}
