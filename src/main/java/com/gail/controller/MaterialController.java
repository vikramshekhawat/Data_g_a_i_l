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

import com.gail.responseData.MaterialDataList;
import com.gail.restURIConstants.GailNominationURI;
import com.gail.service.MaterialService;
import com.gail.utility.GailNominationServiceException;
import com.gail.utility.ResponseBuilder;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class MaterialController {

	public static final Logger logger = LoggerFactory.getLogger(MaterialController.class);

	@Autowired
	ServletContext context;

	@Autowired
	MaterialService materialService;

	@GetMapping(value = GailNominationURI.GET_MATERIAL, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.ALL_VALUE})
	public ResponseEntity<Object> getMaterial() throws MethodArgumentNotValidException {
		ResponseEntity<Object> response = null;
		logger.info("getting material");
		try {
			MaterialDataList materialList = materialService.getMaterials();
			response = ResponseBuilder.getSuccessResponse(materialList);
			return response;
		} catch (GailNominationServiceException e) {
			response = ResponseBuilder.getErrorResponse(e.getErrorDetails());
			return response;
		}
	}

}
