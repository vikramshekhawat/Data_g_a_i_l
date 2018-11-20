package com.gail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gail.responseData.TickerDataList;
import com.gail.restURIConstants.GailNominationURI;
import com.gail.service.TickerService;
import com.gail.utility.GailNominationServiceException;
import com.gail.utility.ResponseBuilder;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class TickerController {

	@Autowired
	TickerService tickerService;

	@GetMapping(value = GailNominationURI.GET_TICKER, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getTickerList() {
		ResponseEntity<Object> response = null;
		TickerDataList tickerList = null;
		try {
			tickerList = tickerService.getTickerDetail();
			response = ResponseBuilder.getSuccessResponse(tickerList);
		} catch (GailNominationServiceException ex) {
			response = ResponseBuilder.getErrorResponse(ex.getErrorDetails());
		}
		return response;
	}

	@PostMapping(value = GailNominationURI.ADD_TICKER, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addTicker(@RequestBody TickerDataList tickerList) {
		ResponseEntity<Object> response = null;
		try {
			String gson = tickerService.saveTickerData(tickerList);
			response = ResponseBuilder.getSuccessResponse(gson);
		} catch (GailNominationServiceException ex) {
			response = ResponseBuilder.getErrorResponse(ex.getErrorDetails());
		}
		return response;
	}

	@PostMapping(value = GailNominationURI.UPDATE_TICKER, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateTicker(@RequestBody @Validated() TickerDataList tickerList)
			throws GailNominationServiceException {
		ResponseEntity<Object> response = null;
		try {
			String gson = tickerService.saveTickerData(tickerList);
			response = ResponseBuilder.getSuccessResponse(gson);
		} catch (GailNominationServiceException ex) {
			response = ResponseBuilder.getErrorResponse(ex.getErrorDetails());
		}
		return response;

	}

}
