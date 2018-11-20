package com.gail.serviceImpl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gail.dao.PayerDao;
import com.gail.dao.TickerDao;
import com.gail.dao.UserAuthTokenDao;
import com.gail.dao.UserContactsDao;
import com.gail.dao.UserDao;
import com.gail.dao.VendorDao;
import com.gail.model.Payer;
import com.gail.model.Ticker;
import com.gail.model.User;
import com.gail.responseData.DownloadUserDTO;
import com.gail.responseData.EmailData;
import com.gail.responseData.UserUploadData;
import com.gail.service.EmailService;
import com.gail.service.PayerService;
import com.gail.service.UserContactsService;
import com.gail.service.UserService;
import com.gail.utility.Constants;
import com.gail.utility.ErrorDetails;
import com.gail.utility.ExceptionUtil;
import com.gail.utility.GailNominationServiceException;
import com.gail.utility.RandomNumberGenerator;
import com.gail.utility.UTCDate;
import com.gail.utility.Util;

import jersey.repackaged.com.google.common.collect.Lists;

@Service("userService")
public class UserServiceImpl extends GenericServiceImpl<User, Long> implements UserService {

	@Autowired
	UserDao userDao;

	@Autowired
	PayerDao payerDao;

	@Autowired
	VendorDao vendorDao;

	@Autowired
	UserContactsDao userContactsDao;

	@Autowired
	TickerDao tickerDao;

	@Autowired
	UserAuthTokenDao userAuthTokenDao;

	@Autowired
	UserContactsService userContactsService;

	@Autowired
	UserService userService;

	@Autowired
	PayerService payerService;

	@Autowired
	EmailService emailService;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User login(User login) throws GailNominationServiceException {

		User user = null;
		logger.info("Started login");
		try {
			user = userDao.fetchUserByUserName(login.getUserName());
			if (user != null) {
				Payer payer = payerDao.fetchUserByPayerId(user.getPayerId());
				List<Ticker> tickerList = tickerDao.getTickersByKey(user.getUserName());
				// UserTokenMapping token = new UserTokenMapping();
				if (login.getPassword().length() < 50 || Util.checkPassword(login.getPassword(), user.getPassword())) {
					try {
						if (user.getPassword().equals(login.getPassword())
								|| Util.checkPassword(login.getPassword(), user.getPassword())) { // This
																									// line
																									// has
																									// to
																									// be
																									// removed
																									// once
																									// all
																									// the
																									// passwords
																									// are
																									// encrypted
							if (payer == null) {
								throw new GailNominationServiceException(
										new ErrorDetails(Constants.PAYER_DETAIL_NOT_FOUND,
												Constants.ERROR_TYPE_CODE_VALIDATION, Constants.ERROR_TYPE_VALIDATION,
												"Payer detail not found for corrosponding userId"));
							}

							// token.setToken(Util.tokenGenerator(Constants.TOKEN_GENERATOR_VALUE));
							// token.setUserId(user.getUserId());
							// userAuthTokenDao.saveOrUpdate(token);
							if (user.getPassword().equals(login.getPassword())) {
								user.setPassword(Util.encryptPassword(user.getPassword()));
								userDao.update(user);
							}

							user.setPayerName(payer.getPayerName());
							user.setCutOffTime(payer.getCutOffTime());
							user.setPasswordChanged(user.isPasswordChanged());
							user.setUserId(user.getUserId());
							user.setStatus(user.getStatus());
							// user.setVendorStatus();
							user.setContactList(userContactsDao.getContactsByUserId(user.getUserId()));
							user.setPayerId(user.getPayerId());
							String tickerText = "";
							if (tickerList != null && !tickerList.isEmpty()) {
								for (Ticker ticker : tickerList)
									tickerText = tickerText + ticker.getTickerText() + ", ";
								user.setTickerText(tickerText.substring(0, tickerText.lastIndexOf(",")));
							}
						} else { // This else block has to be removed once all
									// the
									// passwords are encrypted
							if (user.getPassword().equals(login.getPassword())) {
								user.setPassword(Util.encryptPassword(user.getPassword()));
								userDao.update(user);
							}

							throw new GailNominationServiceException(new ErrorDetails(Constants.USER_DOES_NOT_EXIST,
									Constants.ERROR_TYPE_CODE_VALIDATION, Constants.ERROR_TYPE_VALIDATION,
									"Invalid username or password."));
						}
					} catch (IllegalArgumentException ex) {
						if (ex.getMessage().equals("Invalid salt version"))
							throw new GailNominationServiceException(new ErrorDetails(Constants.USER_DOES_NOT_EXIST,
									Constants.ERROR_TYPE_CODE_VALIDATION, Constants.ERROR_TYPE_VALIDATION,
									"Invalid username or password."));
					}
				} else {
					throw new GailNominationServiceException(
							new ErrorDetails(Constants.USER_DOES_NOT_EXIST, Constants.ERROR_TYPE_CODE_VALIDATION,
									Constants.ERROR_TYPE_VALIDATION, "Invalid username or password."));
				}
			} else {
				throw new GailNominationServiceException(
						new ErrorDetails(Constants.USER_DOES_NOT_EXIST, Constants.ERROR_TYPE_CODE_VALIDATION,
								Constants.ERROR_TYPE_VALIDATION, "Invalid username or password."));
			}

		} catch (Throwable ex) {
			ExceptionUtil.handleException(new ErrorDetails(Constants.USER_LOGGING_ERROR,
					Constants.ERROR_TYPE_CODE_INTERNAL, Constants.ERROR_TYPE_ERROR, "Error while logging user."), ex,
					login.toString());
		}

		return user;
	}

	@Override
	public User isVendor(BigInteger userId) throws GailNominationServiceException {

		User user = new User();
		Boolean vendorStatus = false;
		logger.info("Started login");
		try {
			vendorStatus = vendorDao.isVendor(userId);
			user.setDeviceToken(null);
			user.setUserId(userId);
			user.setVendorStatus(vendorStatus);

		} catch (Throwable ex) {
			ExceptionUtil.handleException(
					new ErrorDetails(Constants.USER_LOGGING_ERROR, Constants.ERROR_TYPE_CODE_INTERNAL,
							Constants.ERROR_TYPE_ERROR, "Exception in getVendorData by materialCode"),
					ex, vendorStatus.toString());
		}

		return user;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public User downloadUserReport() throws GailNominationServiceException {
		String filePath = null;
		String query = userDao.getQuery();
		User user = null;
		List<List> listsOfResponse = Lists.newArrayList();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		List<DownloadUserDTO> downloadUserDTO;
		try {
			downloadUserDTO = userDao.getUserReport(query);
			if (downloadUserDTO != null && !downloadUserDTO.isEmpty()) {
				user = new User();
				for (DownloadUserDTO downloadUserDTOs : downloadUserDTO) {
					List<String> responseList = Lists.newArrayList();
					responseList.add(downloadUserDTOs.getPayer_id().toString());
					responseList.add(downloadUserDTOs.getPayer_name());
					responseList.add(downloadUserDTOs.getPrimary_email_id());
					responseList.add(downloadUserDTOs.getSecondary_email_id1());
					responseList.add(downloadUserDTOs.getSecondary_email_id2());
					responseList.add(downloadUserDTOs.getSecondary_email_id3());
					responseList.add(downloadUserDTOs.getSecondary_email_id4());
					responseList.add(downloadUserDTOs.getCutoff_time());
					responseList.add(downloadUserDTOs.getRegion());
					responseList.add(dateFormat.format(downloadUserDTOs.getCreated_date()));
					listsOfResponse.add(responseList);

				}
			} else {
				throw new GailNominationServiceException(
						new ErrorDetails(Constants.DOWNLOAD_USER_DATA_NOT_FOUND, Constants.ERROR_TYPE_CODE_VALIDATION,
								Constants.ERROR_TYPE_VALIDATION, "No User detail  found for downloading"));
			}
			filePath = generateCSVFile(listsOfResponse);
			user.setFileName(filePath);
		} catch (Throwable ex) {
			ExceptionUtil.handleException(new ErrorDetails(Constants.DOWNLOAD_USER_ERROR,
					Constants.ERROR_TYPE_CODE_INTERNAL, Constants.ERROR_TYPE_ERROR, "Error while downloading user."),
					ex, "");
		}
		return user;
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
			(row.createCell(0)).setCellValue("PAYER ID");
			(row.createCell(1)).setCellValue("PAYER NAME");
			(row.createCell(2)).setCellValue("PRIMARY EMAIL ID");
			(row.createCell(3)).setCellValue("SECONDARY EMAIL ID 1");
			(row.createCell(4)).setCellValue("SECONDARY EMAIL ID 2");
			(row.createCell(5)).setCellValue("SECONDARY EMAIL ID 3");
			(row.createCell(6)).setCellValue("SECONDARY EMAIL ID 4");
			(row.createCell(7)).setCellValue("CUTOFF TIME");
			(row.createCell(8)).setCellValue("REGION");
			(row.createCell(9)).setCellValue("CREATED DATE");

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

	public void readFile(InputStream inputFile, String userId) throws GailNominationServiceException, IOException {
		Payer payer = null;
		User user = null;
		String psw;
		int count = 0;
		try {

			List<UserUploadData> uploadDataList = Lists.newArrayList();
			boolean emptyCell = false;
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
			if (headerName.get(0).equals("PAYER_NAME") && headerName.get(1).equals("PAYER_ID") &&
			// headerName.get(2).equals("PASSWORD") &&
					headerName.get(2).equals("EMAIL_ID") && headerName.get(3).equals("CUTOFF_TIME")
					&& headerName.get(4).equals("REGION")
			/*
			 * //&&headerName.get(5).equals("CUSTOMMER_CODE")&&
			 * headerName.get(6).equals("CUSTOMER_DESCRIPTION")
			 */) {

				while (iterator.hasNext() && emptyCell == false) {
					Row nextRow = iterator.next();
					Iterator<Cell> cellIterator = nextRow.cellIterator();
					List<String> dataList = Lists.newArrayList();

					while (cellIterator.hasNext() && count < 5) {
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
						psw = RandomNumberGenerator.getNewRandomNumber();
						UserUploadData uploadData = new UserUploadData();
						uploadData.setAdminId(userId);
						uploadData.setPayerName(dataList.get(0));
						uploadData.setPayerId(dataList.get(1));
						// uploadData.setPassword(dataList.get(2));
						uploadData.setPassword(psw);
						uploadData.setEmailId(dataList.get(2));
						uploadData.setCutOffTime(dataList.get(3));
						uploadData.setRegion(dataList.get(4));
						/*
						 * uploadData.setCustomerCode(dataList.get(5));
						 * uploadData.setCustomerDescription(dataList.get(6));
						 */
						payer = payerService.savePayerData(uploadData);
						user = saveUserData(uploadData, payer);
						userContactsService.saveUserContact(uploadData, user);
						uploadDataList.add(uploadData);
					}
				}
			} else {
				throw new GailNominationServiceException(new ErrorDetails(Constants.INVALID_TEMPLATES,
						Constants.ERROR_TYPE_CODE_VALIDATION, Constants.ERROR_TYPE_VALIDATION, "Invalid Template "));
			}
			sendMail(uploadDataList);
		} catch (Throwable ex) {

			ExceptionUtil
					.handleException(
							new ErrorDetails(Constants.UPLOADING_ERROR, Constants.ERROR_TYPE_CODE_INTERNAL,
									Constants.ERROR_TYPE_ERROR, "Error while reading excel file."),
							ex, inputFile.toString());
		}

		inputFile.close();
	}

	public User saveUserData(UserUploadData uploadData, Payer payer) throws GailNominationServiceException {
		User user = null;
		try {
			user = userDao.getUserByUserName(uploadData.getPayerId());
			{
				if (user == null) {
					user = new User();
					user.setCreatedDate(UTCDate.getCurrentUTCDate());
					user.setCreatedBy(new BigInteger(uploadData.getAdminId()));
				}
			}
			user.setUserName(uploadData.getPayerId());
			user.setPassword(Util.encryptPassword(uploadData.getPassword()));
			user.setRole(Constants.USER_ROLE); // to be changed
			user.setPayerId(payer.getPayerId());
			user.setPasswordChanged(Constants.PASSWORD_NOT_CHANGED);
			user.setStatus(Constants.ACTIVE_STATUS);
			user.setUpdatedDate(UTCDate.getCurrentUTCDate());
			user.setUpdatedBy(new BigInteger(uploadData.getAdminId()));
			userDao.saveOrUpdate(user);
		} catch (Throwable ex) {
			ExceptionUtil
					.handleException(
							new ErrorDetails(Constants.UPLOAD_USER_DATA_ERROR, Constants.ERROR_TYPE_CODE_INTERNAL,
									Constants.ERROR_TYPE_ERROR, "Error while uploading user data."),
							ex, uploadData.toString());
		}
		return user;
	}

	public void sendMail(List<UserUploadData> uploadList) throws GailNominationServiceException {

		List<EmailData> emailDataList = Lists.newArrayList();
		for (UserUploadData uploadData : uploadList) {
			List<String> emailList = Lists.newArrayList();
			emailList.add(uploadData.getEmailId());
			String[] emailArr = emailList.toArray(new String[emailList.size()]);
			EmailData emailData = new EmailData();
			emailData.setEmail(emailArr);
			emailData.setMessage(null);
			emailData.setEmailValues(uploadData.getPassword());
			emailData.setUserName(uploadData.getPayerId());
			emailData.setSubject(Constants.NEW_USER_CREATED);
			emailData.setType(Constants.NEW_USER);
			emailDataList.add(emailData);
		}
		emailService.sendEmail(emailDataList, Constants.NEW_USER);

	}

}