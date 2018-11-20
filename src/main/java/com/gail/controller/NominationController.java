package com.gail.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gail.model.ContractText;
import com.gail.model.Nominations;
import com.gail.responseData.NominationResponseDataList;
import com.gail.restURIConstants.GailNominationURI;
import com.gail.service.NominationService;
import com.gail.utility.Constants;
import com.gail.utility.ErrorDetails;
import com.gail.utility.FileManager;
import com.gail.utility.GailNominationServiceException;
import com.gail.utility.ResponseBuilder;

@RestController
@RequestMapping(value = "/")
public class NominationController {

	public static final Logger logger = LoggerFactory.getLogger(NominationController.class);

	@Autowired
	ServletContext context;

	@Autowired
	NominationService nominationService;

	@PostMapping(value = GailNominationURI.GETNOMINATION, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getNomination(
			@RequestBody @Validated(Nominations.NominationReq.class) Nominations nominationData)
			throws MethodArgumentNotValidException {
		ResponseEntity<Object> response = null;
		try {
			NominationResponseDataList nominationDataList = nominationService.getNominationDetail(nominationData);
			response = ResponseBuilder.getSuccessResponse(nominationDataList);

			return response;
		} catch (Exception ex) {
			ErrorDetails err = new ErrorDetails(Constants.NOMINATION_GETTING_ERROR, Constants.ERROR_TYPE_CODE_INTERNAL,
					Constants.ERROR_TYPE_ERROR, "Error while getting nomination.");
			response = ResponseBuilder.getErrorResponse(err);
			return response;
		}

	}

	@PostMapping(value = GailNominationURI.GET_CONTRACTS_OR_NOMINATION, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getContractOrNomination(
			@RequestBody @Validated(Nominations.GetContractNominationRequest.class) Nominations nominationData)
			throws GailNominationServiceException {
		ResponseEntity<Object> response = null;
		try {
			NominationResponseDataList nominationDataList = nominationService.getNominationOrcontracts(nominationData);
			response = ResponseBuilder.getSuccessResponse(nominationDataList);
			return response;
		} catch (GailNominationServiceException ex) {
			logger.error(this.getClass().getName() + ex.getMessage());
			response = ResponseBuilder.getErrorResponse(ex.getErrorDetails());
			return response;
		}

	}

	@PostMapping(value = GailNominationURI.GET_CONTRACTS_FOR_SELLER, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getContractForSeller(
			@RequestBody @Validated(Nominations.GetContractSellerReq.class) Nominations sellerData)
			throws GailNominationServiceException {
		ResponseEntity<Object> response = null;
		try {

			NominationResponseDataList nominationDataList = nominationService
					.getcontractOrNominationForSeller(sellerData);
			response = ResponseBuilder.getSuccessResponse(nominationDataList);
		} catch (GailNominationServiceException ex) {
			response = ResponseBuilder.getErrorResponse(ex.getErrorDetails());

		}
		return response;
	}

	@PostMapping(value = GailNominationURI.UPDATE_NOMINATION_BY_SELLER, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateNominationBySeller(
			@RequestBody @Validated(Nominations.UpdateSellerNominate.class) Nominations updatedData)
			throws ParseException {
		ResponseEntity<Object> response = null;
		try {

			List<Nominations> gson = nominationService.updateNominationDataBySeller(updatedData);
			response = ResponseBuilder.getSuccessResponse(gson);
		} catch (GailNominationServiceException ex) {
			response = ResponseBuilder.getErrorResponse(ex.getErrorDetails());

		}
		return response;

	}

	@PostMapping(value = GailNominationURI.BUILD_NOMINATION_REPORT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getNominationReport(
			@RequestBody @Validated(Nominations.BuildNominationReport.class) Nominations nomination)
			throws MethodArgumentNotValidException {
		ResponseEntity<Object> response = null;
		logger.info("login processing");
		try {
			NominationResponseDataList loginResponse = nominationService.buildNominationReport(nomination);
			response = ResponseBuilder.getSuccessResponse(loginResponse);
			return response;
		} catch (GailNominationServiceException e) {
			response = ResponseBuilder.getErrorResponse(e.getErrorDetails());
			return response;
		}
	}

	@GetMapping(value = GailNominationURI.DOWNLOAD_NOMINATION_REPORT, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<Object> downloadReport(@PathVariable("filename") String fileName)
			throws GailNominationServiceException {
		ResponseEntity<Object> response = null;
		try {
			File file = FileManager.getFileImage(fileName, Constants.FILE_PATH);
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.xlsx");
			headers.set("Content-Type", "application/vnd.ms-excel;");

			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			response = ResponseEntity.ok().headers(headers).contentLength(file.length())
					.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);

		} catch (GailNominationServiceException ex) {
			response = ResponseBuilder.getErrorResponse(ex.getErrorDetails());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return response;
	}

	@PostMapping(value = GailNominationURI.SAVE_NOMINATION, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getSaveNomination(
			@RequestBody @Validated(Nominations.SaveNomination.class) Nominations saveNomData)
			throws ParseException, IOException {
		ResponseEntity<Object> response = null;
		try {
			List<Nominations> gson = nominationService.saveNomination(saveNomData);
			response = ResponseBuilder.getSuccessResponse(gson);
		} catch (GailNominationServiceException ex) {
			logger.error(this.getClass().getName() + ex.getMessage());
			response = ResponseBuilder.getErrorResponse(ex.getErrorDetails());
		}
		return response;
	}

	@PostMapping(value = GailNominationURI.UPDATE_NOMINATION, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateNominations(
			@RequestBody @Validated(Nominations.UpdateNomination.class) Nominations updateNomData)
			throws ParseException, IOException {
		ResponseEntity<Object> response = null;
		try {
			List<Nominations> gson = nominationService.updateNomination(updateNomData);
			response = ResponseBuilder.getSuccessResponse(gson);
		} catch (GailNominationServiceException ex) {
			logger.error(this.getClass().getName() + ex.getMessage());
			response = ResponseBuilder.getErrorResponse(ex.getErrorDetails());
		}
		return response;
	}

	@PostMapping(value = GailNominationURI.SAVE_CONTRACT_TEXT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> saveOrUpdateContractText(
			@RequestBody @Validated(ContractText.ContractReq.class) ContractText contractTextData) {

		ResponseEntity<Object> response = null;

		try {

			String gson = nominationService.saveContractText(contractTextData);
			response = ResponseBuilder.getSuccessResponse(gson);
		} catch (GailNominationServiceException ex) {
			response = ResponseBuilder.getErrorResponse(ex.getErrorDetails());

		}
		return response;

	}

	@PostMapping(value = GailNominationURI.UPDATE_CONTRACT_TEXT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getUpdateContractText(
			@RequestBody @Validated(ContractText.ContractReq.class) ContractText contractTextData) {
		ResponseEntity<Object> response = null;
		try {

			String gson = nominationService.updateContractText(contractTextData);
			response = ResponseBuilder.getSuccessResponse(gson);
		} catch (GailNominationServiceException ex) {
			response = ResponseBuilder.getErrorResponse(ex.getErrorDetails());

		}
		return response;

	}

}
