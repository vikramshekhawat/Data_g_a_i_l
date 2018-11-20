package com.gail.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gail.model.Contracts;
import com.gail.responseData.ContractsDataList;
import com.gail.restURIConstants.GailNominationURI;
import com.gail.service.ContractsService;
import com.gail.utility.Constants;
import com.gail.utility.GailNominationServiceException;
import com.gail.utility.ResponseBuilder;
import com.gail.utility.UTCDate;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContractController {

	public static final Logger logger = LoggerFactory.getLogger(ContractController.class);

	@Autowired
	ServletContext context;

	@Autowired
	ContractsService contractsService;

	@PostMapping(value = GailNominationURI.BUILD_CONTRACT_REPORT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getContracts(
			@RequestBody @Validated(Contracts.DownloadContracts.class) Contracts contractData)
			throws MethodArgumentNotValidException {
		ResponseEntity<Object> response = null;
		logger.info("downloading contracts");
		try {
			Contracts contracts = contractsService.downloadContractReport(contractData.getContractEndDate());
			response = ResponseBuilder.getSuccessResponse(contracts);
			return response;
		} catch (GailNominationServiceException e) {
			response = ResponseBuilder.getErrorResponse(e.getErrorDetails());
			return response;
		}
	}

	@GetMapping(value = GailNominationURI.GET_CONTRACT_BY_PAYER_ID, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<Object> getContractsByPayerId(@PathVariable("payerId") String payerId,
			@PathVariable("contractType") String contractType) {
		ResponseEntity<Object> response = null;
		ContractsDataList contractsList = null;
		try {
			contractsList = contractsService.getContractDetail(BigInteger.valueOf(Long.parseLong(payerId)),
					contractType);
			response = ResponseBuilder.getSuccessResponse(contractsList);
		} catch (GailNominationServiceException ex) {
			response = ResponseBuilder.getErrorResponse(ex.getErrorDetails());
		}
		return response;
	}

	@PostMapping(value = GailNominationURI.UPLOAD_CONTRACT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadUser(@RequestParam("userId") String userId,
			@RequestParam("file") MultipartFile inputFile) {
		ResponseEntity<Object> response = null;
		try {
			String fileName = Constants.FILE_PATH + UTCDate.getCurrentUTCDate().getTime() + Constants.FILE_EXT;
			File tempFile = new File(fileName);
			inputFile.transferTo(tempFile);
			InputStream file = new FileInputStream(tempFile);
			contractsService.readFile(file, userId);
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
