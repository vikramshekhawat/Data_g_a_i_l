package com.gail.serviceImpl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gail.dao.ContractTextDao;
import com.gail.dao.ContractsDao;
import com.gail.dao.NominationDao;
import com.gail.dao.PayerDao;
import com.gail.dao.UserContactsDao;
import com.gail.dao.UserDao;
import com.gail.model.ContractText;
import com.gail.model.Nominations;
import com.gail.model.Payer;
import com.gail.model.User;
import com.gail.responseData.ContractDataDTO;
import com.gail.responseData.DownloadNominationDTO;
import com.gail.responseData.NominationData;
import com.gail.responseData.NominationDataDTO;
import com.gail.responseData.NominationResponseData;
import com.gail.responseData.NominationResponseDataList;
import com.gail.service.EmailSenderService;
import com.gail.service.NominationService;
import com.gail.utility.Constants;
import com.gail.utility.ErrorDetails;
import com.gail.utility.ExceptionUtil;
import com.gail.utility.GailNominationServiceException;
import com.gail.utility.JsonParseUtil;
import com.gail.utility.UTCDate;
import com.gail.utility.Util;

import jersey.repackaged.com.google.common.collect.Lists;

@Service("nominationService")
public class NominationServiceImpl extends GenericServiceImpl<Nominations, Long> implements NominationService {

	@Autowired
	ContractsDao contractsDao;

	@Autowired
	NominationDao nominationDao;

	@Autowired
	ContractTextDao contractTextDao;

	@Autowired
	UserDao userDao;

	@Autowired
	PayerDao payerDao;

	@Autowired
	UserContactsDao userContactsDao;
	
	@Autowired
	EmailSenderService emailSenderService;

	private static SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat v_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = LoggerFactory.getLogger(NominationServiceImpl.class);

	@Override
	public NominationResponseDataList getNominationDetail(Nominations data) {
		logger.info("fetching nomination details");
		List<NominationDataDTO> nominationDataList;
		// List<>
		NominationResponseDataList Nominations = new NominationResponseDataList();
		NominationResponseData nominationResponseData = null;
		String query = nominationDao.getQueryForDisplay(data);
		nominationDataList = nominationDao.getNomination(query);
		List<NominationResponseData> list = Lists.newArrayList();
		if (nominationDataList != null && nominationDataList.size() > 0) {

			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			for (NominationDataDTO nominatioData : nominationDataList) {
				nominationResponseData = new NominationResponseData();
				nominationResponseData.setContractId(nominatioData.getContract_id().toString());
				nominationResponseData.setContractRef(nominatioData.getContract_ref());
				try {
					// ************************************************************************
					String contractName = nominationDao
							.getContractText(new BigInteger(nominatioData.getContract_ref()));
					// ********************************************************************************
					nominationResponseData.setContractName(contractName);
				} catch (Exception e) {

				}

				nominationResponseData.setContractType(nominatioData.getContract_type());
				nominationResponseData.setDelPoint(nominatioData.getDel_point().toString());
				nominationResponseData.setRedelPoint(nominatioData.getRedel_point().toString());
				nominationResponseData.setCustomerCode(nominatioData.getCustomer_code());
				nominationResponseData.setCustomerDescription(nominatioData.getCustomer_description());
				nominationResponseData.setMaterialCode(nominatioData.getMaterial_code());
				nominationResponseData.setMaterialDescription(nominatioData.getMaterial_desc());
				nominationResponseData.setUnitOfMeasurements(nominatioData.getUnit_of_measurements());
				nominationResponseData.setGasDate(format.format(nominatioData.getGas_date()));
				nominationResponseData.setUpdatedDate(
						nominatioData.getUpdated_Date() == null ? "" : nominatioData.getUpdated_Date().toString());
				nominationResponseData.setRevisionNo(String.valueOf(nominatioData.getRevision_no()));
				nominationResponseData.setNominationId(nominatioData.getNomination_id().toString());
				if (nominatioData.getSeller_redel_point() != null
						&& !(nominatioData.getSeller_redel_point().doubleValue() == 0.000)) {
					nominationResponseData.setSeller_redel_point(nominatioData.getSeller_redel_point().toString());
					nominationResponseData.setSeller_updated_time(nominatioData.getSeller_updated_date().toString());
				}
				list.add(nominationResponseData);
			}
			Nominations.setNominationData(list);
		}

		return Nominations;
	}

	@Override
	public NominationResponseDataList getNominationOrcontracts(Nominations data) throws GailNominationServiceException {
		List<NominationDataDTO> nominationDataList;
		boolean nominationFilled = false;
		NominationResponseDataList nominations = new NominationResponseDataList();
		NominationResponseData nominationResponseData = null;
		String query = nominationDao.getQueryForNomination(data);
		nominationDataList = nominationDao.getNomination(query);
		List<NominationResponseData> list = Lists.newArrayList();
		if (nominationDataList != null && !nominationDataList.isEmpty()) {
			nominationResponseData = new NominationResponseData();

			for (NominationDataDTO nominatioData : nominationDataList) {
				nominationResponseData = new NominationResponseData();
				nominationResponseData.setContractId(nominatioData.getContract_id().toString());
				nominationResponseData.setContractRef(nominatioData.getContract_ref());

				if (nominatioData.getSeller_redel_point() != null
						&& !(nominatioData.getSeller_redel_point().doubleValue() == 0.000)) {
					nominationResponseData.setSeller_redel_point(nominatioData.getSeller_redel_point().toString());

					nominationResponseData.setSeller_updated_time(nominatioData.getSeller_updated_date().toString());
				}

				nominationResponseData.setContractName(getContractData(nominatioData.getContract_ref()));
				nominationResponseData.setContractType(nominatioData.getContract_type());
				nominationResponseData.setDelPoint(nominatioData.getDel_point().toString());
				nominationResponseData.setRedelPoint(nominatioData.getRedel_point().toString());
				nominationResponseData.setCustomerCode(nominatioData.getCustomer_code());
				nominationResponseData.setCustomerDescription(nominatioData.getCustomer_description());
				nominationResponseData.setMaterialCode(nominatioData.getMaterial_code());
				nominationResponseData.setMaterialDescription(nominatioData.getMaterial_desc());
				nominationResponseData.setUnitOfMeasurements(nominatioData.getUnit_of_measurements());
				nominationResponseData.setGasDate(nominatioData.getGas_date().toString());
				if (nominatioData.getUpdated_Date() != null) {
					nominationResponseData.setUpdatedDate(nominatioData.getUpdated_Date().toString());
				}
				nominationResponseData.setRevisionNo(String.valueOf(nominatioData.getRevision_no()));
				nominationResponseData.setNominationId(nominatioData.getNomination_id().toString());
				nominationResponseData.setStartDate(format.format(nominatioData.getStart_date()));
				nominationResponseData.setEndDate(format.format(nominatioData.getEnd_date()));
				list.add(nominationResponseData);
			}
			nominations.setNominationData(list);
		}
		if (nominations.getNominationData() == null) {
			NominationResponseDataList nominationDataLists = getContracts(data);
			return nominationDataLists;
		} else {

			String query1 = contractsDao.getQuery(data);
			List<ContractDataDTO> contractDataDTOList = contractsDao.getContractsList(query1);
			if (contractDataDTOList != null && !contractDataDTOList.isEmpty()) {
				if (contractDataDTOList.size() > nominations.getNominationData().size()) {

					List<NominationResponseData> list1 = Lists.newArrayList();
					list1.addAll(nominations.getNominationData());
					for (ContractDataDTO contractDataDTO : contractDataDTOList) {
						nominationFilled = checkNominationFilled(contractDataDTO, nominations);
						if (!nominationFilled) {
							nominationResponseData = new NominationResponseData();
							nominationResponseData.setContractId(contractDataDTO.getContract_id().toString());
							nominationResponseData.setContractRef(contractDataDTO.getContract_ref());
							nominationResponseData.setContractType(contractDataDTO.getContract_type());
							nominationResponseData.setMaterialCode(contractDataDTO.getMaterial_code());
							nominationResponseData.setMaterialDescription(contractDataDTO.getMaterial_desc());
							nominationResponseData.setMaterialId(contractDataDTO.getMaterial_id().toString());
							nominationResponseData.setCustomerCode(contractDataDTO.getCustomer_code());
							nominationResponseData.setCustomerDescription(contractDataDTO.getCustomer_description());
							if (contractDataDTO.getSeller_redel_point() != null) {
								nominationResponseData
										.setSeller_redel_point(contractDataDTO.getSeller_redel_point().toString());
							}

							nominationResponseData.setContractName(getContractData(contractDataDTO.getContract_ref()));
							nominationResponseData.setUnitOfMeasurements(contractDataDTO.getUnit_of_measurements());
							nominationResponseData.setStartDate(format.format(contractDataDTO.getStart_date()));
							nominationResponseData.setEndDate(format.format(contractDataDTO.getEnd_date()));
							list.add(nominationResponseData);
						}
					}
					nominations.setNominationData(list);
				}
			} else {
				throw new GailNominationServiceException(
						new ErrorDetails(Constants.CONTRACT_NOT_FOUND, Constants.ERROR_TYPE_CODE_VALIDATION,
								Constants.ERROR_TYPE_VALIDATION, "Contracts list not found"));

			}
		}
		return nominations;
	}

	public String getContractData(String Contract_ref) {
		String contractName = null;

		try {

			contractName = nominationDao.getContractText(new BigInteger(Contract_ref));

		} catch (Exception e) {

		}
		return contractName;

	}

	@Override
	public NominationResponseDataList getContracts(Nominations data) throws GailNominationServiceException {

		List<ContractDataDTO> contractDataDTOList = null;
		NominationResponseDataList nominationDataList = new NominationResponseDataList();
		NominationResponseData nominationResponseData = null;
		List<NominationResponseData> list = Lists.newArrayList();
		String query1 = contractsDao.getQuery(data);
		contractDataDTOList = contractsDao.getContractsList(query1);
		if (contractDataDTOList != null && !contractDataDTOList.isEmpty()) {

			for (ContractDataDTO contractDataDTO : contractDataDTOList) {
				nominationResponseData = new NominationResponseData();
				nominationResponseData.setContractId(contractDataDTO.getContract_id().toString());
				nominationResponseData.setContractRef(contractDataDTO.getContract_ref());
				nominationResponseData.setContractName(getContractData(contractDataDTO.getContract_ref()));
				nominationResponseData.setContractType(contractDataDTO.getContract_type());
				nominationResponseData.setMaterialCode(contractDataDTO.getMaterial_code());
				nominationResponseData.setMaterialDescription(contractDataDTO.getMaterial_desc());
				nominationResponseData.setMaterialId(contractDataDTO.getMaterial_id().toString());
				nominationResponseData.setCustomerCode(contractDataDTO.getCustomer_code());
				nominationResponseData.setCustomerDescription(contractDataDTO.getCustomer_description());
				nominationResponseData.setUnitOfMeasurements(contractDataDTO.getUnit_of_measurements());
				nominationResponseData.setStartDate(format.format(contractDataDTO.getStart_date()));
				nominationResponseData.setEndDate(format.format(contractDataDTO.getEnd_date()));
				list.add(nominationResponseData);
			}
		} else {
			throw new GailNominationServiceException(new ErrorDetails(Constants.CONTRACT_NOT_FOUND,
					Constants.ERROR_TYPE_CODE_VALIDATION, Constants.ERROR_TYPE_VALIDATION, "Contracts list not found"));
		}
		nominationDataList.setNominationData(list);

		return nominationDataList;

	}

	@Override
	public boolean checkNominationFilled(ContractDataDTO contractDataDTO,
			NominationResponseDataList nominationResponseDataList) {

		List<NominationResponseData> nominationDataLists = nominationResponseDataList.getNominationData();
		for (NominationResponseData nominationResponseData : nominationDataLists) {
			if (nominationResponseData.getContractId().equals(contractDataDTO.getContract_id().toString())) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public NominationResponseDataList buildNominationReport(Nominations nomination)
			throws GailNominationServiceException {
		String filePath = null;
		String query = nominationDao.getQuery(nomination);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		NominationResponseDataList nominationDataList = null;
		List<DownloadNominationDTO> downloadNominationDTO;
		try {
			List<List> listsOfResponse = Lists.newArrayList();
			downloadNominationDTO = nominationDao.getNominationReport(query);

			if (downloadNominationDTO != null && !downloadNominationDTO.isEmpty()) {
				nominationDataList = new NominationResponseDataList();
				List<NominationResponseData> list = Lists.newArrayList();

				/* List<String> responseList =Lists.newArrayList(); */

				for (DownloadNominationDTO downloadNominationDTOs : downloadNominationDTO) {
					List<String> responseList = Lists.newArrayList();
					responseList.add(downloadNominationDTOs.getNomination_id().toString());
					responseList.add(downloadNominationDTOs.getRegion_name());
					responseList.add(downloadNominationDTOs.getCustomer_code());
					responseList.add(dateFormat.format(downloadNominationDTOs.getGas_date()));
					responseList.add(downloadNominationDTOs.getMaterial_code());
					responseList.add(downloadNominationDTOs.getContract_ref());
					responseList.add(downloadNominationDTOs.getDel_point().toString());
					responseList.add(downloadNominationDTOs.getRedel_point().toString());
					if (downloadNominationDTOs.getSeller_redel_point() != null) {
						responseList.add(downloadNominationDTOs.getSeller_redel_point().toString());
					} else {
						responseList.add(" -- ");
					}
					responseList.add(downloadNominationDTOs.getUnit_of_measurements());

					if (downloadNominationDTOs.getUpdated_Date() != null) {
						responseList.add(UTCDate.getDateInISTFormat(downloadNominationDTOs.getUpdated_Date()));
					} else {
						responseList.add(" -- ");
					}
					if (downloadNominationDTOs.getSeller_redel_point() != null
							&& !(downloadNominationDTOs.getSeller_redel_point().doubleValue() == 0.000)) {

						responseList.add(UTCDate.getDateInISTFormat(downloadNominationDTOs.getSeller_updated_date()));
					} else {
						responseList.add(" -- ");
					}

					listsOfResponse.add(responseList);
				}

				nominationDataList.setNominationData(list);
			} else {
				throw new GailNominationServiceException(new ErrorDetails(Constants.DOWNLOAD_NOMINATION_DATA_NOT_FOUND,
						Constants.ERROR_TYPE_CODE_VALIDATION, Constants.ERROR_TYPE_VALIDATION,
						"No Nomination detail  found for downloading"));
			}

			filePath = generateCSVFile(listsOfResponse);
			nominationDataList.setFileName(filePath);
		} catch (Throwable ex) {
			ExceptionUtil.handleException(
					new ErrorDetails(Constants.DOWNLOAD_NOMINATION_ERROR, Constants.ERROR_TYPE_CODE_INTERNAL,
							Constants.ERROR_TYPE_ERROR, "Error while downloading nomination."),
					ex, nomination.toString());
		}
		return nominationDataList;
	}

	@SuppressWarnings("rawtypes")
	private String generateCSVFile(List<List> responseDataList) throws IOException {

		String fileName = String.valueOf(UTCDate.getCurrentUTCDate().getTime());
		String outputFilePath = Constants.FILE_PATH + fileName + Constants.FILE_EXT;
		int rowCount = 0;
		try (FileOutputStream outputStream = new FileOutputStream(outputFilePath, false);) {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("report");
			Row row = sheet.createRow(0);

			(row.createCell(0)).setCellValue("NOMINATION ID");
			(row.createCell(1)).setCellValue("REGION NAME");
			(row.createCell(2)).setCellValue("CUSTOMER CODE");
			(row.createCell(3)).setCellValue("GAS DATE");
			(row.createCell(4)).setCellValue("MATERIAL CODE");
			(row.createCell(5)).setCellValue("CONTRACT REFERENCE");
			(row.createCell(6)).setCellValue("DEL POINT");
			(row.createCell(7)).setCellValue("REDEL POINT");
			(row.createCell(8)).setCellValue("DEL POINT BY SELLER");
			(row.createCell(9)).setCellValue("UNIT OF MEASUREMENTS");
			(row.createCell(10)).setCellValue("UPDATED DATE BY CLIENT");
			(row.createCell(11)).setCellValue("UPDATED DATE BY SELLER");

			for (List nominationResponseDatas : responseDataList) {
				row = sheet.createRow(++rowCount);

				for (int i = 0; i < nominationResponseDatas.size(); i++) {
					Cell cell = row.createCell(i);
					cell.setCellValue((String) nominationResponseDatas.get(i));
				}
			}
			workbook.write(outputStream);

		} catch (IOException e) {
			e.printStackTrace();

		}
		return fileName;
	}

	@Override
	public NominationResponseDataList getcontractOrNominationForSeller(Nominations sellerData)
			throws GailNominationServiceException {
		List<ContractDataDTO> contractDataDTOList = null;
		List<NominationDataDTO> nominationDataDTO;
		NominationResponseDataList nominationsModel = new NominationResponseDataList();
		NominationResponseData nominationResponseData = null;
		String query = nominationDao.getQueryForSeller(sellerData);
		nominationDataDTO = nominationDao.getNomination(query);
		List<NominationResponseData> list = Lists.newArrayList();
		if (nominationDataDTO != null && !nominationDataDTO.isEmpty()) {
			for (NominationDataDTO nominatioData : nominationDataDTO) {
				nominationResponseData = new NominationResponseData();
				nominationResponseData.setContractId(nominatioData.getContract_id().toString());
				nominationResponseData.setContractRef(nominatioData.getContract_ref());
				nominationResponseData.setContractName(getContractData(nominatioData.getContract_ref()));
				nominationResponseData.setContractType(nominatioData.getContract_type());
				nominationResponseData.setDelPoint(nominatioData.getDel_point().toString());
				nominationResponseData.setRedelPoint(nominatioData.getRedel_point().toString());
				if (nominatioData.getUser_id() != null) {
					nominationResponseData.setClientUserId(nominatioData.getUser_id().toString());
				}
				nominationResponseData.setPayerId(nominatioData.getPayer_key());
				nominationResponseData.setCustomerCode(nominatioData.getPayer_name());
				nominationResponseData.setCustomerDescription(nominatioData.getCustomer_description());
				nominationResponseData.setMaterialCode(nominatioData.getMaterial_code());
				nominationResponseData.setMaterialDescription(nominatioData.getMaterial_desc());
				nominationResponseData.setUnitOfMeasurements(nominatioData.getUnit_of_measurements());
				nominationResponseData.setGasDate(nominatioData.getGas_date().toString());
				nominationResponseData.setUpdatedDate(
						nominatioData.getUpdated_Date() == null ? "" : nominatioData.getUpdated_Date().toString());
				nominationResponseData.setRevisionNo(String.valueOf(nominatioData.getRevision_no()));
				nominationResponseData.setNominationId(nominatioData.getNomination_id().toString());
				nominationResponseData.setStartDate(format.format(nominatioData.getStart_date()));
				nominationResponseData.setEndDate(format.format(nominatioData.getEnd_date()));
				if (nominatioData.getSeller_redel_point() != null
						&& !(nominatioData.getSeller_redel_point().doubleValue() == 0.000)) {
					nominationResponseData.setSeller_redel_point(nominatioData.getSeller_redel_point().toString());

					nominationResponseData.setSeller_updated_time(nominatioData.getSeller_updated_date().toString());
				}
				list.add(nominationResponseData);
			}
			nominationsModel.setNominationData(list);
		}
		if (nominationsModel.getNominationData() == null) {
			NominationResponseDataList nominationDataLists = getContractsForSeller(sellerData);

			return nominationDataLists;
		}

		String query1 = contractsDao.getQueryForSeller(sellerData);
		contractDataDTOList = contractsDao.getContractsList(query1);
		if (contractDataDTOList != null && !contractDataDTOList.isEmpty()) {
			for (ContractDataDTO contractDataDTO : contractDataDTOList) {
				nominationResponseData = new NominationResponseData();
				nominationResponseData.setPayerId(contractDataDTO.getPayer_key());
				nominationResponseData.setContractId(contractDataDTO.getContract_id().toString());
				nominationResponseData.setClientUserId(contractDataDTO.getUser_id().toString());
				nominationResponseData.setContractRef(contractDataDTO.getContract_ref());
				nominationResponseData.setContractType(contractDataDTO.getContract_type());
				nominationResponseData.setMaterialCode(contractDataDTO.getMaterial_code());
				nominationResponseData.setMaterialDescription(contractDataDTO.getMaterial_desc());
				nominationResponseData.setMaterialId(contractDataDTO.getMaterial_id().toString());
				nominationResponseData.setCustomerCode(contractDataDTO.getPayer_name());
				nominationResponseData.setCustomerDescription(contractDataDTO.getCustomer_description());
				nominationResponseData.setContractName(getContractData(contractDataDTO.getContract_ref()));
				nominationResponseData.setUnitOfMeasurements(contractDataDTO.getUnit_of_measurements());
				nominationResponseData.setStartDate(format.format(contractDataDTO.getStart_date()));
				nominationResponseData.setEndDate(format.format(contractDataDTO.getEnd_date()));
				if (contractDataDTO.getSeller_redel_point() != null
						&& !(contractDataDTO.getSeller_redel_point().doubleValue() == 0.000)) {
					nominationResponseData.setSeller_redel_point(contractDataDTO.getSeller_redel_point().toString());

				}
				list.add(nominationResponseData);
			}
			nominationsModel.setNominationData(list);
		}
		return nominationsModel;

	}

	@Override
	public NominationResponseDataList getContractsForSeller(Nominations sellerData) {
		List<ContractDataDTO> contractDataDTOList = null;
		NominationResponseDataList nominationsModel = new NominationResponseDataList();
		NominationResponseData nominationResponseData = null;
		List<NominationResponseData> list = Lists.newArrayList();
		String query1 = contractsDao.getQueryforsellerContract(sellerData);
		contractDataDTOList = contractsDao.getContractsList(query1);
		if (contractDataDTOList != null && !contractDataDTOList.isEmpty()) {
			for (ContractDataDTO contractDataDTO : contractDataDTOList) {
				nominationResponseData = new NominationResponseData();
				nominationResponseData.setContractId(contractDataDTO.getContract_id().toString());
				nominationResponseData.setContractRef(contractDataDTO.getContract_ref());
				if (contractDataDTO.getUser_id() != null) {
					nominationResponseData.setClientUserId(contractDataDTO.getUser_id().toString());
				}
				nominationResponseData.setPayerId(contractDataDTO.getPayer_key());
				nominationResponseData.setContractName(getContractData(contractDataDTO.getContract_ref()));
				nominationResponseData.setContractType(contractDataDTO.getContract_type());
				nominationResponseData.setMaterialCode(contractDataDTO.getMaterial_code());
				nominationResponseData.setMaterialDescription(contractDataDTO.getMaterial_desc());
				nominationResponseData.setMaterialId(contractDataDTO.getMaterial_id().toString());
				nominationResponseData.setCustomerCode(contractDataDTO.getPayer_name());
				nominationResponseData.setCustomerDescription(contractDataDTO.getCustomer_description());
				nominationResponseData.setUnitOfMeasurements(contractDataDTO.getUnit_of_measurements());
				nominationResponseData.setStartDate(format.format(contractDataDTO.getStart_date()));
				nominationResponseData.setEndDate(format.format(contractDataDTO.getEnd_date()));
				list.add(nominationResponseData);
			}
			nominationsModel.setNominationData(list);
		}

		return nominationsModel;

	}

	@Override
	public List<Nominations> updateNominationDataBySeller(Nominations updatedData)
			throws GailNominationServiceException, ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Nominations nominationsModel;
		Boolean isNominationSaveOrUpdate = false;
		List<Nominations> nominationModelsList = new ArrayList<>();
		User userModel = userDao.getPayerByUserId(new BigInteger(updatedData.getClientUserId()));
		Payer payerModel = payerDao.fetchUserByPayerId(userModel.getPayerId());
		if (payerModel == null) {
			throw new GailNominationServiceException(
					new ErrorDetails(Constants.PAYER_ID_NOT_FOUND, Constants.ERROR_TYPE_CODE_VALIDATION,
							Constants.ERROR_TYPE_VALIDATION, "Payer detail not found for corrosponding userId"));
		}
		Time cutoffTime = payerModel.getCutOffTime();
		TimeZone tz1 = TimeZone.getTimeZone("IST");
		SimpleDateFormat inputParser = new SimpleDateFormat("HH:mm:ss");
		inputParser.setTimeZone(tz1);
		Calendar now = Calendar.getInstance(tz1);
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int minute = now.get(Calendar.MINUTE);
		int second = now.get(Calendar.SECOND);
		Date date = inputParser.parse((hour - 1) + ":" + minute + ":" + second);
		Date cuttoffTime = inputParser.parse(cutoffTime.toString());

		if ((cuttoffTime.after(date))) {

			if (!updatedData.getNomination_id().equals("null") && !updatedData.getNomination_id().isEmpty()) {
				// for (UpdateNominationBySellerBo
				// updateNominationDatasBo : nominationBO) {
				nominationsModel = new Nominations();
				nominationsModel = nominationDao.getNominationModel(new BigInteger(updatedData.getNomination_id()));
				nominationsModel.setSellerredelPoint(Double.parseDouble(updatedData.getSeller_redel_point()));
				nominationsModel.setSellerUpdatedDate(Util.getCurrentUTCDate());
				nominationDao.saveOrUpdate(nominationsModel);
				nominationModelsList.add(nominationsModel);
				isNominationSaveOrUpdate = true;

			} else {

				nominationsModel = new Nominations();
				nominationsModel.setContractId(new BigInteger(updatedData.getContract_id()));
				nominationsModel.setDelPoint(0.0);
				nominationsModel.setRedelPoint(0.0);
				nominationsModel.setContractName(null);
				nominationsModel.setCreatedBy(new BigInteger(updatedData.getUserId()));
				String gasDateFormat = simpleDateFormat.format(updatedData.getGasDate());
				nominationsModel.setGasDate(new java.sql.Date(simpleDateFormat.parse(gasDateFormat).getTime()));
				nominationsModel.setStatus(Constants.ACTIVE_STATUS);
				nominationsModel.setCreatedDate(Util.getCurrentUTCDate());

				nominationsModel.setUpdatedBy(new BigInteger(updatedData.getUserId()));
				nominationsModel.setNominatedBy(new BigInteger(updatedData.getUserId()));
				nominationsModel.setRevision_no(Constants.INITIAL_REVISION_NO);
				nominationsModel.setSellerredelPoint(Double.parseDouble(updatedData.getSeller_redel_point()));
				nominationsModel.setSellerUpdatedDate(Util.getCurrentUTCDate());

				nominationDao.saveOrUpdate(nominationsModel);
				nominationModelsList.add(nominationsModel);
				isNominationSaveOrUpdate = true;

			}

			if (isNominationSaveOrUpdate) {
				emailSenderService.sendNominationEmailForSeller(updatedData, nominationModelsList);
			}

		}

		return nominationModelsList;
	}

	// Save NominationData
	@Override
	public List<Nominations> saveNomination(Nominations saveNomData)
			throws GailNominationServiceException, ParseException, IOException {

		List<NominationData> saveNominationDataBoList = saveNomData.getNominationData();
		List<Nominations> nominationModelList = Lists.newArrayList();
		Nominations nominationsModel = null;
		boolean isNominationSave = false;
		User userModel = userDao.getPayerByUserId(new BigInteger(saveNomData.getUserId()));
		Payer payerModel = payerDao.fetchUserByPayerId(userModel.getPayerId());
		if (payerModel == null) {
			throw new GailNominationServiceException(
					new ErrorDetails(Constants.PAYER_ID_NOT_FOUND, Constants.ERROR_TYPE_CODE_VALIDATION,
							Constants.ERROR_TYPE_VALIDATION, "Payer detail not found for corrosponding userId"));
		}

		Time cutoffTime = payerModel.getCutOffTime();
		TimeZone tz1 = TimeZone.getTimeZone("IST");
		SimpleDateFormat inputParser = new SimpleDateFormat("HH:mm:ss");
		inputParser.setTimeZone(tz1);
		Calendar now = Calendar.getInstance(tz1);
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int minute = now.get(Calendar.MINUTE);
		int second = now.get(Calendar.SECOND);
		Date date = inputParser.parse((hour - 1) + ":" + minute + ":" + second);
		Date cuttoffTime = inputParser.parse(cutoffTime.toString());
		Date gasDate = saveNomData.getGasDate();
		SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = dateFormate.parse(dateFormate.format(Util.getDateInISTFormats(Util.getCurrentUTCDate())));
		if ((gasDate.equals(currentDate) && (cuttoffTime.after(date))) || gasDate.after(currentDate)) {
			if (saveNominationDataBoList != null && !saveNominationDataBoList.isEmpty()) {
				for (NominationData data : saveNominationDataBoList) {

					if (data.getNominationId() != null && !data.getNominationId().isEmpty()) {

						nominationsModel = new Nominations();
						nominationsModel = nominationDao.getNominationModel(new BigInteger(data.getNominationId()));

						nominationsModel.setStatus(Constants.DEACTIVE_STATUS);
						nominationsModel.setUpdateDate(Util.getCurrentUTCDate());
						nominationsModel.setUpdatedBy(new BigInteger(saveNomData.getUserId()));
						nominationsModel.setNominatedBy(new BigInteger(saveNomData.getUserId()));
						nominationsModel.setContractName(saveNomData.getContractName());
						nominationDao.saveOrUpdate(nominationsModel);
						saveNomData.setRevision_no(nominationsModel.getRevision_no() + 1);
						Nominations updatedNomination = setNomData(data, saveNomData);

						nominationDao.saveOrUpdate(updatedNomination);
						isNominationSave = true;
						nominationModelList.add(updatedNomination);

					} else {
						Nominations saveNominationData = setNomData(data, saveNomData);
						nominationDao.saveOrUpdate(saveNominationData);
						isNominationSave = true;
						nominationModelList.add(saveNominationData);

					}

				}

			} else {
				throw new GailNominationServiceException(
						new ErrorDetails(Constants.CONTRACT_LIST_NOT_FOUND, Constants.ERROR_TYPE_CODE_VALIDATION,
								Constants.ERROR_TYPE_VALIDATION, "Contract detail not fount"));
			}

			if (isNominationSave) {
				emailSenderService.sendNominationEmail(saveNomData);
			}
		} else {
			throw new GailNominationServiceException(
					new ErrorDetails(Constants.CUT_OFF_TIME_EXPIRED, Constants.ERROR_TYPE_CODE_VALIDATION,
							Constants.ERROR_TYPE_VALIDATION, "cut off time has been expired"));
		}

		return nominationModelList;

	}

	// Update NominationData
	@Override
	public List<Nominations> updateNomination(Nominations saveNomData)
			throws GailNominationServiceException, ParseException, IOException {

		List<NominationData> saveNominationDataBoList = saveNomData.getUpdateNominationData();
		List<Nominations> nominationModelList = Lists.newArrayList();
		Nominations nominationsModel = null;
		boolean isNominationSave = false;
		User userModel = userDao.getPayerByUserId(new BigInteger(saveNomData.getUserId()));
		Payer payerModel = payerDao.fetchUserByPayerId(userModel.getPayerId());
		if (payerModel == null) {
			throw new GailNominationServiceException(
					new ErrorDetails(Constants.PAYER_ID_NOT_FOUND, Constants.ERROR_TYPE_CODE_VALIDATION,
							Constants.ERROR_TYPE_VALIDATION, "Payer detail not found for corrosponding userId"));
		}

		Time cutoffTime = payerModel.getCutOffTime();
		TimeZone tz1 = TimeZone.getTimeZone("IST");
		SimpleDateFormat inputParser = new SimpleDateFormat("HH:mm:ss");
		inputParser.setTimeZone(tz1);
		Calendar now = Calendar.getInstance(tz1);
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int minute = now.get(Calendar.MINUTE);
		int second = now.get(Calendar.SECOND);
		Date date = inputParser.parse((hour - 1) + ":" + minute + ":" + second);
		Date cuttoffTime = inputParser.parse(cutoffTime.toString());
		Date gasDate = saveNomData.getGasDate();
		SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = dateFormate.parse(dateFormate.format(Util.getDateInISTFormats(Util.getCurrentUTCDate())));
		if ((gasDate.equals(currentDate) && (cuttoffTime.after(date))) || gasDate.after(currentDate)) {
			if (saveNominationDataBoList != null && !saveNominationDataBoList.isEmpty()) {
				for (NominationData data : saveNominationDataBoList) {

					if (data.getNominationId() != null && !data.getNominationId().isEmpty()) {

						nominationsModel = new Nominations();
						nominationsModel = nominationDao.getNominationModel(new BigInteger(data.getNominationId()));

						nominationsModel.setStatus(Constants.DEACTIVE_STATUS);
						nominationsModel.setUpdateDate(Util.getCurrentUTCDate());
						nominationsModel.setUpdatedBy(new BigInteger(saveNomData.getUserId()));
						nominationsModel.setNominatedBy(new BigInteger(saveNomData.getUserId()));
						nominationsModel.setContractName(saveNomData.getContractName());
						nominationDao.saveOrUpdate(nominationsModel);
						saveNomData.setRevision_no(nominationsModel.getRevision_no() + 1);
						Nominations updatedNomination = setNomData(data, saveNomData);

						nominationDao.saveOrUpdate(updatedNomination);
						isNominationSave = true;
						nominationModelList.add(updatedNomination);

					}

				}

			} else {
				throw new GailNominationServiceException(
						new ErrorDetails(Constants.CONTRACT_LIST_NOT_FOUND, Constants.ERROR_TYPE_CODE_VALIDATION,
								Constants.ERROR_TYPE_VALIDATION, "Contract detail not fount"));
			}

			if (isNominationSave) {
				emailSenderService.sendNominationEmail(saveNomData);
			}
		} else {
			throw new GailNominationServiceException(
					new ErrorDetails(Constants.CUT_OFF_TIME_EXPIRED, Constants.ERROR_TYPE_CODE_VALIDATION,
							Constants.ERROR_TYPE_VALIDATION, "cut off time has been expired"));
		}

		return nominationModelList;

	}

	//Save data for save and update nominations
	public Nominations setNomData(NominationData saveNominationDatasBo, Nominations saveNomData) throws ParseException {

		Nominations nominationsModel = new Nominations();
		nominationsModel.setContractId(new BigInteger(saveNominationDatasBo.getContractId()));
		nominationsModel.setDelPoint(Double.parseDouble(saveNominationDatasBo.getDelPoint()));
		nominationsModel.setContractName(saveNominationDatasBo.getContractName());
		nominationsModel.setRedelPoint(Double.parseDouble(saveNominationDatasBo.getRedelPoint()));
		// nominationsModel.setNominationId(nominationBO.getUserId());
		nominationsModel.setCreatedBy(new BigInteger(saveNomData.getUserId()));
		nominationsModel.setGasDate(saveNomData.getGasDate());
		nominationsModel.setStatus(Constants.ACTIVE_STATUS);
		nominationsModel.setCreatedDate(Util.getCurrentUTCDate());
		nominationsModel.setUpdateDate(Util.getCurrentUTCDate());
		nominationsModel.setUpdatedBy(new BigInteger(saveNomData.getUserId()));
		nominationsModel.setNominatedBy(new BigInteger(saveNomData.getUserId()));
		nominationsModel.setRevision_no(Constants.INITIAL_REVISION_NO);
		if (saveNominationDatasBo.getSellerRedelpoint() != null
				&& !saveNominationDatasBo.getSellerRedelpoint().equals("null")
				&& !saveNominationDatasBo.getSellerRedelpoint().isEmpty()) {
			nominationsModel.setSellerredelPoint(Double.parseDouble(saveNominationDatasBo.getSellerRedelpoint()));

			Date v_date = v_format.parse(saveNominationDatasBo.getSellerUpdateTime().toString());

			nominationsModel.setSellerUpdatedDate(v_date);
		}

		return nominationsModel;

	}

	@Override
	public String saveContractText(ContractText contractTextData) throws GailNominationServiceException {

		ContractText contractModel = new ContractText();

		try {

			if (contractTextData != null) {

				contractModel.setContractRef(contractTextData.getContractRef());
				contractModel.setContractName(contractTextData.getContractName());
				contractModel.setPayerId(contractTextData.getPayerId());
				contractTextDao.saveOrUpdate(contractModel);

			} else {
				throw new GailNominationServiceException(
						new ErrorDetails(Constants.NOMINATION_DATA_NOT_FOUND, Constants.ERROR_TYPE_CODE_VALIDATION,
								Constants.ERROR_TYPE_VALIDATION, " Issue in ContractText "));
			}
		} catch (Throwable ex) {
			ExceptionUtil.handleException(
					new ErrorDetails(Constants.UPDATE_NOMINATION_ERROR, Constants.ERROR_TYPE_CODE_INTERNAL,
							Constants.ERROR_TYPE_ERROR, "Error while adding contract Text."),
					ex, contractTextData.toString());
		}

		String json = JsonParseUtil.getJsonStringFromObject(contractModel);

		return json;

	}

	@Override
	public String updateContractText(ContractText contractTextData) throws GailNominationServiceException {
		ContractText contractUpdatedData = null;
		try {
			if (contractTextData != null) {
				contractUpdatedData = contractTextDao.updateData(contractTextData);
			} else {
				throw new GailNominationServiceException(
						new ErrorDetails(Constants.NOMINATION_DATA_NOT_FOUND, Constants.ERROR_TYPE_CODE_VALIDATION,
								Constants.ERROR_TYPE_VALIDATION, "contract text detail not found"));
			}
		} catch (Throwable ex) {

			ExceptionUtil.handleException(
					new ErrorDetails(Constants.UPDATE_NOMINATION_ERROR, Constants.ERROR_TYPE_CODE_INTERNAL,
							Constants.ERROR_TYPE_ERROR, "contract text detail not found."),
					ex, contractTextData.toString());
		}

		String json = JsonParseUtil.getJsonStringFromObject(contractUpdatedData);

		return json;
	}

}