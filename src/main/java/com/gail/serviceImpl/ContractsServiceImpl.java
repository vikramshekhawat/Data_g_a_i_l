package com.gail.serviceImpl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gail.dao.ContractsDao;
import com.gail.dao.MaterialDao;
import com.gail.dao.PayerDao;
import com.gail.model.Contracts;
import com.gail.model.Material;
import com.gail.model.Payer;
import com.gail.responseData.ContactData;
import com.gail.responseData.ContractDataDTO;
import com.gail.responseData.ContractUploadData;
import com.gail.responseData.ContractsData;
import com.gail.responseData.ContractsDataList;
import com.gail.responseData.DownloadContractsDTO;
import com.gail.responseData.EmailData;
import com.gail.responseData.ReminderContractDto;
import com.gail.service.ContractsService;
import com.gail.service.EmailService;
import com.gail.service.UserContactsService;
import com.gail.service.VendorService;
import com.gail.utility.Constants;
import com.gail.utility.ErrorDetails;
import com.gail.utility.ExceptionUtil;
import com.gail.utility.GailNominationServiceException;
import com.gail.utility.UTCDate;

import jersey.repackaged.com.google.common.collect.Lists;

@Service("contractsService")
public class ContractsServiceImpl extends GenericServiceImpl<Contracts, Long> implements ContractsService {

	@Autowired
	ContractsDao contractsDao;

	@Autowired
	MaterialDao materialDao;

	@Autowired
	PayerDao payerDao;

	@Autowired
	UserContactsService userContactsService;

	@Autowired
	VendorService vendorService;
	
	@Autowired
	EmailService emailService;

	private static final Logger logger = LoggerFactory.getLogger(ContractsServiceImpl.class);

	@SuppressWarnings("rawtypes")
	@Override
	public Contracts downloadContractReport(String contractEndDate) throws GailNominationServiceException {
		logger.info("inside downloadContractReport method of ContractsServiceImpl");
		String filePath = null;
		String query = contractsDao.getQuery(contractEndDate);
		List<List> listsOfResponse = Lists.newArrayList();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		List<DownloadContractsDTO> downloadContractsDTO;
		Contracts contracts = null;
		try {
			downloadContractsDTO = contractsDao.getContractsDownloadData(query);
			contracts = new Contracts();
			if (downloadContractsDTO != null && !downloadContractsDTO.isEmpty()) {
				for (DownloadContractsDTO downloadContractsDTOs : downloadContractsDTO) {
					List<String> responseList = Lists.newArrayList();
					responseList.add(downloadContractsDTOs.getContract_reference());
					responseList.add(downloadContractsDTOs.getMaterial_code());
					responseList.add(dateFormat.format(downloadContractsDTOs.getStart_date()));
					responseList.add(dateFormat.format(downloadContractsDTOs.getEnd_date()));
					responseList.add(downloadContractsDTOs.getUom());
					responseList.add(downloadContractsDTOs.getPayer_key());
					responseList.add(downloadContractsDTOs.getCustomer_code());
					responseList.add(downloadContractsDTOs.getCustomer_description());
					responseList.add(dateFormat.format(downloadContractsDTOs.getCreated_date()));
					responseList.add(dateFormat.format(downloadContractsDTOs.getUpdated_date()));
					listsOfResponse.add(responseList);
				}
			} else {
				throw new GailNominationServiceException(new ErrorDetails(Constants.DOWNLOAD_CONTRACTS_DATA_NOT_FOUND,
						Constants.ERROR_TYPE_CODE_VALIDATION, Constants.ERROR_TYPE_VALIDATION,
						"No Contracts detail  found for downloading"));
			}

			filePath = generateCSVFile(listsOfResponse);
			contracts.setFileName(filePath);
		} catch (Throwable ex) {
			ExceptionUtil.handleException(
					new ErrorDetails(Constants.DOWNLOAD_CONTRACTS_ERROR, Constants.ERROR_TYPE_CODE_INTERNAL,
							Constants.ERROR_TYPE_ERROR, "Error while downloading contracts."),
					ex, "");
		}
		return contracts;
	}

	@SuppressWarnings("rawtypes")
	private String generateCSVFile(List<List> responseDataList) throws IOException {

		String fileName = String.valueOf(UTCDate.getCurrentUTCDate().getTime());
		String outputFilePath = Constants.FILE_PATH + fileName + Constants.FILE_EXT;
		int rowCount = 0;
		// boolean alreadyExists = new File(outputFile).exists();
		try (FileOutputStream outputStream = new FileOutputStream(outputFilePath, false);) {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("report");
			Row row = sheet.createRow(0);

			(row.createCell(0)).setCellValue("CONTRACT REFERENCE");
			(row.createCell(1)).setCellValue("MATERIAL CODE");
			(row.createCell(2)).setCellValue("START DATE");
			(row.createCell(3)).setCellValue("END DATE");
			(row.createCell(4)).setCellValue("UOM");
			(row.createCell(5)).setCellValue("PAYER ID");
			(row.createCell(6)).setCellValue("CUSTOMER CODE");
			(row.createCell(7)).setCellValue("CUSTOMER DESCRIPTION");
			(row.createCell(8)).setCellValue("CREATED DATE");
			(row.createCell(9)).setCellValue("UPDATED DATE");

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

	public Map<BigInteger, List<ReminderContractDto>> getUnfilledContractList(String nominationDate) {
		List<ReminderContractDto> contractDtoList = null;
		Map<BigInteger, List<ReminderContractDto>> contractMap = new HashMap<BigInteger, List<ReminderContractDto>>();
		List<ReminderContractDto> contractDtos = contractsDao.getUnfilledCcontractList(nominationDate);
		logger.info("UnfilledContracts List without grouping with PayerId fetched with size {} on {}",
				contractDtos.size(), nominationDate);
		for (ReminderContractDto contractsDto : contractDtos) {
			contractDtoList = contractMap.get(contractsDto.getPayer_id());
			if (contractDtoList != null && !contractDtoList.isEmpty()) {
				contractDtoList.add(contractsDto);
				contractMap.put(contractsDto.getPayer_id(), contractDtoList);
				logger.info("ContractDto object added to existing contractDtoList with payerId: {}",
						contractsDto.getPayer_id());
			} else {
				contractDtoList = Lists.newArrayList();
				contractDtoList.add(contractsDto);
				contractMap.put(contractsDto.getPayer_id(), contractDtoList);
				logger.info("New contractDtoList initialized with payerId: {}", contractsDto.getPayer_id());
			}
		}
		return contractMap;
	}

	@Override
	public ContractsDataList getContractDetail(BigInteger payerId, String contractType)
			throws GailNominationServiceException {
		ContractsDataList contractsList = new ContractsDataList();
		List<ContractsData> list = Lists.newArrayList();
		try {
			String query = contractsDao.getContractsDetailforDisplay(payerId, contractType);
			List<ContractDataDTO> contractsModel = contractsDao.getContractsListForDisplay(query);
			for (ContractDataDTO model : contractsModel) {
				ContractsData contractsData = new ContractsData();
				contractsData.setContractId(model.getContract_id());
				contractsData.setContractRef(model.getContract_ref());
				contractsData.setContractType(model.getContract_type());
				list.add(contractsData);
			}
			contractsList.setContractsDataList(list);
		} catch (Throwable ex) {

			ExceptionUtil
					.handleException(
							new ErrorDetails(Constants.GETTING_CONTRACT_ERROR, Constants.ERROR_TYPE_CODE_INTERNAL,
									Constants.ERROR_TYPE_ERROR, "Error while getting contract."),
							ex, contractsList.toString());

		}
		return contractsList;
	}

	public void readFile(InputStream inputFile, String userId) throws GailNominationServiceException, IOException {
		int count = 0;
		Map<String, List<ContractUploadData>> contractMap = new HashMap<String, List<ContractUploadData>>();
		try {
			boolean emptyCell = false;
			// Workbook workbook = new XSSFWorkbook(inputFile);
			XSSFWorkbook workbook = new XSSFWorkbook(inputFile);
			XSSFSheet firstSheet = workbook.getSheetAt(0);

			Iterator<Row> iterator = firstSheet.iterator();
			List<String> headerName = Lists.newArrayList();
			Row nextRow1 = iterator.next();
			Iterator<Cell> cellIterator1 = nextRow1.cellIterator();
			while (cellIterator1.hasNext()) {
				Cell cell = cellIterator1.next();

				headerName.add(cell.getStringCellValue());
			}
			System.out.println(headerName);
			if (headerName.get(0).equals("PAYER_ID") && headerName.get(1).equals("CUSTOMER_CODE")
					&& headerName.get(2).equals("CUSTOMER_DESCRIPTION")
					&& headerName.get(3).equals("CONTRACT_REFERENCE") && headerName.get(4).equals("MATERIAL_ID")
					&& headerName.get(5).equals("START_DATE") && headerName.get(6).equals("END_DATE")
					&& headerName.get(7).equals("UOM")) {
				while (iterator.hasNext() && emptyCell == false) {
					Row nextRow = iterator.next();
					Iterator<Cell> cellIterator = nextRow.cellIterator();
					List<String> dataList = Lists.newArrayList();

					while (cellIterator.hasNext() && count < 8) {
						Cell cell = cellIterator.next();
						if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_NUMERIC:
								dataList.add(String.valueOf((int) cell.getNumericCellValue()));
								count++;
								break;
							case Cell.CELL_TYPE_STRING:
								dataList.add(cell.getStringCellValue());
								count++;
								break;
							}
						} else {
							emptyCell = true;
							break;
						}
					}
					count = 0;
					if (dataList != null && !dataList.isEmpty()) {
						ContractUploadData contractUpload = new ContractUploadData();
						contractUpload.setAdminId(userId);
						contractUpload.setPayerKey(dataList.get(0));
						contractUpload.setCustomerCode(dataList.get(1));
						contractUpload.setCustomerDescription(dataList.get(2));
						contractUpload.setContractReference(dataList.get(3));
						contractUpload.setMaterialCode(dataList.get(4));
						contractUpload.setStartDate(dataList.get(5));
						contractUpload.setEndDate(dataList.get(6));
						contractUpload.setUOM(dataList.get(7));
						saveContracts(contractUpload);
						contractMap = makeContractDataToMail(contractUpload, contractMap);
					}
				}
			} else {
				throw new GailNominationServiceException(new ErrorDetails(Constants.INVALID_TEMPLATES,
						Constants.ERROR_TYPE_CODE_VALIDATION, Constants.ERROR_TYPE_VALIDATION, "Invalid Template "));
			}
			sendContractUploadMail(contractMap);
		} catch (Throwable ex) {

			ExceptionUtil
					.handleException(
							new ErrorDetails(Constants.UPLOADING_ERROR, Constants.ERROR_TYPE_CODE_INTERNAL,
									Constants.ERROR_TYPE_ERROR, "Error while reading excel file."),
							ex, inputFile.toString());
		}

		inputFile.close();
	}

	public void saveContracts(ContractUploadData contractUpload) throws GailNominationServiceException {

		SimpleDateFormat dateFormate = new SimpleDateFormat("dd.MM.yyyy");
		Material material = null;
		Payer payer = null;
		Contracts contracts = null;
		try {
			material = materialDao.getMaterialByCode(contractUpload.getMaterialCode());
			payer = payerDao.getPayerByKey(contractUpload.getPayerKey());
			contracts = contractsDao.getContarctByRef(contractUpload.getContractReference());

			if (material != null) {
				if (payer != null) {
					if (contracts != null) {
						contracts.setStartDate(dateFormate.parse(contractUpload.getStartDate()));
						contracts.setEndDate(dateFormate.parse(contractUpload.getEndDate()));
						contracts.setUpdatedBy(new BigInteger(contractUpload.getAdminId()));
						contracts.setPayerId(payer.getPayerId());
						contracts.setCustomerCode(contractUpload.getCustomerCode());
						contracts.setCustomerDescription(contractUpload.getCustomerDescription());
						contracts.setUpdatedDate(UTCDate.getCurrentUTCDate());
						contractsDao.saveOrUpdate(contracts);

					} else {
						Contracts contract = new Contracts();
						contract.setMaterialId(material.getMaterialId());
						contract.setPayerId(payer.getPayerId());
						contract.setCustomerCode(contractUpload.getCustomerCode());
						contract.setContractRef(contractUpload.getContractReference());
						contract.setCustomerDescription(contractUpload.getCustomerDescription());
						contract.setStartDate(dateFormate.parse(contractUpload.getStartDate()));
						contract.setEndDate(dateFormate.parse(contractUpload.getEndDate()));
						contract.setUnitOfMeasurements(contractUpload.getUOM());
						contract.setStatus(Constants.ACTIVE_STATUS);
						contract.setCreatedBy(new BigInteger(contractUpload.getAdminId()));
						contract.setUpdatedBy(new BigInteger(contractUpload.getAdminId()));
						contract.setCreatedDate(UTCDate.getCurrentUTCDate());
						contract.setUpdatedDate(UTCDate.getCurrentUTCDate());
						// contractModel.setContractName(contractName);
						contractsDao.saveOrUpdate(contract);
					}
				} else {
					throw new GailNominationServiceException(
							new ErrorDetails(Constants.PAYER_NOT_FOUND, Constants.ERROR_TYPE_CODE_VALIDATION,
									Constants.ERROR_TYPE_VALIDATION, "Payer Id not found"));
				}

			} else {
				throw new GailNominationServiceException(
						new ErrorDetails(Constants.MATERIAL_CODE_NOT_FOUND, Constants.ERROR_TYPE_CODE_VALIDATION,
								Constants.ERROR_TYPE_VALIDATION, "Material code not found"));
			}
		} catch (Throwable ex) {
			ExceptionUtil.handleException(
					new ErrorDetails(Constants.CONTRACT_UPLOAD_ERROR, Constants.ERROR_TYPE_CODE_INTERNAL,
							Constants.ERROR_TYPE_ERROR, "Error while uploading contracts"),
					ex, contractUpload.toString());
		}
	}

	private Map<String, List<ContractUploadData>> makeContractDataToMail(ContractUploadData contractUpload,
			Map<String, List<ContractUploadData>> contractMap) {
		List<ContractUploadData> contractList = contractMap.get(contractUpload.getPayerKey());
		if (contractList != null) {
			contractList.add(contractUpload);
			contractMap.put(contractUpload.getPayerKey(), contractList);
		} else {
			contractList = Lists.newArrayList();
			contractList.add(contractUpload);
			contractMap.put(contractUpload.getPayerKey(), contractList);
		}
		return contractMap;
	}

	private void sendContractUploadMail(Map<String, List<ContractUploadData>> contractMap)
			throws GailNominationServiceException {
		StringBuffer tableContent = null;
		Material material = null;
		List<BigInteger> selleruserIdList = new ArrayList<BigInteger>();
		List<EmailData> emailDataList = Lists.newArrayList();
		String emailType = Constants.ADD_CONTRACT;
		try {
			for (Map.Entry<String, List<ContractUploadData>> entry : contractMap.entrySet()) {
				tableContent = new StringBuffer();
				for (ContractUploadData contract : entry.getValue()) {
					material = materialDao.getMaterialByCode(contract.getMaterialCode());
					BigInteger SellerId = vendorService.getVendorUserId(contract.getMaterialCode());
					if (SellerId != null) {
						selleruserIdList.add(SellerId);
					}

					tableContent.append(
							"<tr>" + "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
									+ contract.getCustomerCode() + "</td>"
									+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
									+ contract.getContractReference() + "</td>"
									+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
									+ material.getContractType() + "</td>"
									+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
									+ contract.getMaterialCode() + "</td>"
									+ "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
									+ contract.getEndDate() + "</td>" +
									/*
									 * "<td align='center' style='border:1px solid #999;color:#3f3f3f;font-size:12px;margin:0 0 20px 0;padding:0;font-weight:bold; font-family:Arial, Helvetica, sans-serif; line-height:20px'>"
									 * +contract.getUOM() +"</td>"+
									 */
									"</tr>");
				}

				List<String> emailList = Lists.newArrayList();

				// Android & iOS Notification
				List<String> androidTokenList = Lists.newArrayList();
				List<String> iosTokenList = Lists.newArrayList();
				if (selleruserIdList.size() > 0) {
					for (int i = 0; i < selleruserIdList.size(); i++) {

						List<ContactData> contactDatalist = userContactsService.getContactList(selleruserIdList.get(i));
						for (ContactData contactData : contactDatalist) {
							String email = contactData.getContactValue();
							emailList.add(email);

							// Android & iOS Notification
							if ("android".equalsIgnoreCase(contactData.getDeviceType()))
								androidTokenList.add(contactData.getDeviceToken());
							else if ("ios".equalsIgnoreCase(contactData.getDeviceType()))
								iosTokenList.add(contactData.getDeviceToken());
						}

					}
				}

				List<ContactData> contactDatalist = userContactsService.getContactListByPayerKey(entry.getKey());// Get
																													// user-contact
																													// data
				for (ContactData contactData : contactDatalist) {
					String email = contactData.getContactValue();
					emailList.add(email);

					// Android & iOS Notification
					if ("android".equalsIgnoreCase(contactData.getDeviceType()))
						androidTokenList.add(contactData.getDeviceToken());
					else if ("ios".equalsIgnoreCase(contactData.getDeviceType()))
						iosTokenList.add(contactData.getDeviceToken());
				}

				// Android & iOS Notification
				// if (!androidTokenList.isEmpty())
				// PushNotificationHelper.sendAndroidNotification(androidTokenList,
				// Constants.SUBJECT_ADD_CONTRACT,
				// Constants.SUBJECT_ADD_CONTRACT);
				// if (!iosTokenList.isEmpty())
				// PushNotificationHelper.sendIOSNotification(iosTokenList,
				// Constants.SUBJECT_ADD_CONTRACT);

				String[] emailArr = emailList.toArray(new String[emailList.size()]);
				EmailData emailData = new EmailData();
				emailData.setEmail(emailArr);

				emailData.setSubject(Constants.SUBJECT_ADD_CONTRACT);
				emailData.setType(Constants.ADD_CONTRACT);
				emailData.setEmailValues(tableContent.toString());
				emailDataList.add(emailData);
			}
			emailService.sendEmail(emailDataList, emailType);
		} catch (Throwable ex) {
			ExceptionUtil.handleException(new ErrorDetails(Constants.MAIL_ERROR, Constants.ERROR_TYPE_CODE_INTERNAL,
					Constants.ERROR_TYPE_ERROR, "Error while sending mail."), ex, contractMap.toString());
		}

	}

}