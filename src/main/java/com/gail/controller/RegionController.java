package com.gail.controller;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gail.responseData.RegionDataList;
import com.gail.restURIConstants.GailNominationURI;
import com.gail.service.RegionService;
import com.gail.utility.GailNominationServiceException;
import com.gail.utility.ResponseBuilder;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegionController {

	public static final Logger logger = LoggerFactory.getLogger(RegionController.class);

	@Autowired
	ServletContext context;

	@Autowired
	RegionService regionService;

	@GetMapping(value = GailNominationURI.GET_REGION, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.ALL_VALUE})
	public ResponseEntity<Object> getRegion() throws MethodArgumentNotValidException {
		ResponseEntity<Object> response = null;
		logger.info("getting region");
		try {
			RegionDataList regionList = regionService.getRegions();
			response = ResponseBuilder.getSuccessResponse(regionList);
			return response;
		} catch (GailNominationServiceException e) {
			response = ResponseBuilder.getErrorResponse(e.getErrorDetails());
			return response;
		}
	}

}
