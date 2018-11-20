package com.gail.utility;

public class Constants {

	public static final String EMAIL_TEMPLATE_PATH;
	public static final String CERTIFICATE_PATH;
	public static final String FILE_PATH;

	static {
		if (System.getProperty("os.name").contains("Windows")) {
			EMAIL_TEMPLATE_PATH = "D:\\Wildnet Project\\GAIL\\Server\\server_properties\\Email-template\\";
			CERTIFICATE_PATH = "D:\\Wildnet Project\\GAIL\\Server\\server_properties\\sanbox_push_nomination.p12";
			FILE_PATH = "D:\\tmp\\";
		} else {
			EMAIL_TEMPLATE_PATH = "/mnt/gailnomination/server_properties/email_template/";
			CERTIFICATE_PATH = "/mnt/gailnomination/server_properties/push_notification/sanbox_push_nomination.p12";
			FILE_PATH = "/mnt/gailnomination/gail_report_files/";
		}
	}

	public static final String FILE_EXT = ".xlsx";

	public static final int PAYER_DETAIL_NOT_FOUND = 1048;
	public static final int ERROR_TYPE_CODE_VALIDATION = 2;
	public static final int ERROR_TYPE_CODE_INTERNAL = 3;
	public static final String ERROR_TYPE_VALIDATION = "validation";
	public static final int USER_ID_NOT_MATCHED = 1001;
	public static final int USER_DOES_NOT_EXIST = 1002;
	public static final int USER_LOGGING_ERROR = 1003;
	public static final int USER_MAIL_AUTHENTICATION = 1004;
	public static final int PARSING_EMPTY_FIELD_ERROR = 1005;
	public static final int USER_LOGIN_DETAIL_PARSING_ERROR = 1006;
	public static final int CHANGE_PASSWORD_DETAIL_PARSING_ERROR = 1007;
	public static final int FORGET_PASSWORD_DETAIL_PARSING_ERROR = 1008;
	public static final int NOMINATION_DETAIL_PARSING_ERROR = 1009;
	public static final int OBJECT_FROM_JSON_PARSE_EXCEPTION = 3001;
	public static final int OBJECT_FROM_JSON_MAPPING_EXCEPTION = 3002;
	public static final int OBJECT_FROM_JSON_IO_EXCEPTION = 3003;
	public static final int OBJECT_FROM_JSON_EXCEPTION = 3004;
	public static final int JSON_FROM_OBJECT_PROCESSING_EXCEPTION = 3005;
	public static final int JSON_FROM_OBJECT_EXCEPTION = 3006;
	public static final String ERROR_TYPE_ERROR = "ERROR";
	public static final String ALL_MATERIAL = "All";
	// Length of token and default password generators
	public static final int TOKEN_GENERATOR_VALUE = 6;
	public static final int PASSWORD_GENERATOR_VALUE = 6;
	public static final int PHONE_VERIFIER_CODE_LENGTH = 6;
	// Default values of Serve Status
	public static final int PHYSICAL_HELP = 1;
	public static final int VIRTUAL_HELP = 2;

	// status

	public static final int MINUTES = 330;
	public static final int SECOND = 60;
	public static final int MILLISECONDS = 1000;
	public static final String GTA = "GTA";

	public static final int API_SUCCESS = 0;
	public static final int USER_ACTIVE_STATUS = 0;
	public static final int ACTIVE_USER_CONTACTS = 0;
	public static final int ACTIVE_STATUS_FOR_REGION = 0;
	public static final int ACTIVE_STATUS_FOR_PAYER = 0;
	public static final int ACTIVE_STATUS_FOR_TICKER = 0;
	public static final int ACTIVE_STATUS = 0;
	public static final int DEACTIVE_STATUS = 1;

	// Default values of Normal Status
	public static final int REMOVED = 0;
	public static final int CREATED = 1;
	public static final int ACCEPTED = 2;
	public static final int STARTED = 3;
	public static final int FINISHED = 4;
	public static final int CANCELED = 5;
	public static final int PAUSED = 6;

	// Twilio Account Details
	public static final String ACCOUNT_SID = "ACffbdfcdbd62f6aba542f69abb26e0a36";
	public static final String AUTH_TOKEN = "ef5f47ef5c1082f7afeb21fd848942e8";
	public static final String MOBILE_NUMBER = "+18457623142";

	// Folder Related Details
	public static final String FOLDER_PATH = "D:\\MyImages\\";
	public static final String FORGOT_PASSWORD_FILE_PATH = "WEB-INF/templates/email/forgotten_password.html";

	// Mail Related Details
	public static final String DEFAULT_FROM_EMAIL = "GoodKarms Support <no-reply@goodkarms.com>";
	public static final String FORGOTTEN_PASSWORD_EMAIL_SUBJECT = "Forgotten Password";
	public static final String HOST = "mail.localserver.com";
	public static final String USER = "master@mail.localserver.com";
	public static final String PASSWORD = "password";

	// Success Message
	public static final String SUCCESS = "success";
	public static final String SUCCESS_REGISTERED = "Registered Successfully";
	public static final String SUCCESS_PASSWORD_CREATED = "New Password created and mailed to you successfully";
	public static final String SUCCESS_PASSWORD_CHANGED = "Password changed successfully";

	// Error Message
	public static final String ERROR_WRONG_EMAIL = "Please provide valid Email Id";
	public static final String ERROR_INVALID_CREDENTIALS = "Invalid Credentials";
	public static final String ERROR_EMAIL_REGISTERED = "This field must be unique";
	public static final String ERROR_INTERNAL_SERVER = "Internal Server Error";
	public static final String ERROR_PASSWORD_NOT_MATCH = "New passwords do not match";
	public static final String ERROR_WRONG_CURRENT_PASSWORD = "Incorrect current password";
	public static final String ERROR_WRONG_PASSWORD = "Incorrect password";
	public static final String ERROR_INVALID_TOKEN = "Invalid token";
	public static final String ERROR_USER_NOT_EXIST = "This user doesn't exist";
	public static final String ERROR_FIELD_BLANK = "This field may not be blank";
	public static final String ERROR_MISSING_TOKEN = "You must provide a token";
	public static final String ERROR_SESSION_ALREADY_ACTIVE = "There is another session active in another device";
	public static final String ERROR_CLIENT_FIELD_NOT_CHANGE = "Social users can't change email or password";
	public static final String ERROR_WRONG_DATE_FORMAT = "Date has wrong format. Use one of these formats instead: YYYY[-MM[-DD]]";
	public static final String ERROR_WRONG_EMAIL_ADDRESS = "Enter a valid email address";
	public static final String ERROR_MALFORMED_REQUEST = "Malformed Request";
	public static final String ERROR_MEDIA_TYPE = "MediaType Not Supported";
	public static final String ERROR_METHOD_NOT_ALLOWED = "Method Not Allowed";
	public static final String ERROR_WRONG_PHONE_NUMBER = "Enter a correct phone number";

	public static final int CONTRACT_GETTING_ERROR = 1010;
	public static final int NOMINATION_GETTING_ERROR = 1011;
	public static final int NOMINATION_OR_CONTRACT_DETAIL_PARSING_ERROR = 1012;
	public static final int CHANGE_PASSWORD_ERROR = 1013;
	public static final int FORGET_PASSWORD_ERROR = 1014;
	public static final int PASSWORD_DOES_NOT_MATCHED = 1015;
	public static final int SAVE_NOMINATION_ERROR = 1016;
	public static final int CONTRACT_LIST_NOT_FOUND = 1017;
	public static final int NOMINATION_NOT_FOUND = 1018;
	public static final int CONTRACT_NOT_FOUND = 1019;
	public static final int NOMINATION_DATA_NOT_FOUND = 1020;
	public static final int UPDATE_NOMINATION_ERROR = 1021;
	public static final int NOMINATION_REPORT_PARSING_ERROR = 1022;
	public static final int DOWNLOAD_NOMINATION_DATA_NOT_FOUND = 1023;
	public static final int DOWNLOAD_NOMINATION_ERROR = 1024;
	public static final int CUT_OFF_TIME_EXPIRED = 1025;
	public static final int CUT_OFF_EXPIRED = 1026;
	public static final int INITIAL_REVISION_NO = 0;
	public static final int MAIL_ERROR = 1027;
	public static final int GET_MATERIAL_ERROR = 1028;
	public static final int GET_REGION_ERROR = 1029;
	public static final int FILE_DOWNLOAD_ERROR = 1030;
	public static final int FILE_NOT_FOUND = 1031;
	public static final int GETTING_CONTRACT_ERROR = 1031;
	public static final String CLIENT_DATA = "clientdata";
	public static final String LOGIN_API = "loginuser";
	public static final int CLIENT_DATA_NOT_EXIST = 1032;
	public static final int USER_TOKEN_DEACTIVATED = 1033;
	public static final int REQUEST_HEADER_EMPTY = 1034;
	public static final int REQUEST_HEADER_KEY_EMPTY = 1035;
	public static final int TOKEN_NOT_FOUND = 1036;
	public static final int UPLOADING_ERROR = 1037;
	public static final int INVALID_TEMPLATES = 1038;
	public static final int UPLOAD_USER_DATA_ERROR = 1039;
	public static final int UPLOADING_PAYER_DATA_ERROR = 1040;
	public static final int UPLOADING_USER_CONTACT_ERROR = 1041;
	public static final String CONTACT_TYPE_MAIL = "mail";
	public static final int CONTACT_PRIORITY = 1;
	public static final String USER_ROLE = "payer";
	public static final int MATERIAL_CODE_NOT_FOUND = 1042;
	public static final int CONTRACT_UPLOAD_ERROR = 1043;
	public static final int PAYER_NOT_FOUND = 1044;
	public static final int MATERIAL_UPLOAD_ERROR = 1045;
	public static final int INVALID_FILE_FORMAT = 1046;
	public static final int PAYER_ID_NOT_FOUND = 1047;
	public static final int ADD_PROFILE_PARSING_ERROR = 1049;
	public static final int INVALID_CUTOFF_TIME_FORMAT = 1050;
	public static final int UPLOAD_USER_CONTACT_DATA_ERROR = 1051;
	public static final int CONTACT_LIMIT_EXCEED = 1052;
	public static final int UPDATE_PROFILE_PARSING_ERROR = 1053;
	public static final int NO_CONTACT_FOUND = 1054;
	public static final int DELETING_USER_CONTACT_ERROR = 1055;
	public static final int NO_CONTACT_FOUNDS_FOR_DELETION = 1056;
	public static final int GETTING_MATERIAL_ERROR = 1057;
	public static final int DOWNLOAD_USER_DATA_NOT_FOUND = 1058;
	public static final int DOWNLOAD_USER_ERROR = 1059;
	public static final int DOWNLOAD_CONTRACTS_DATA_NOT_FOUND = 1060;
	public static final int DOWNLOAD_CONTRACTS_ERROR = 1061;
	public static final int SAVING_TICKER_DATA_ERROR = 1062;
	public static final int SAVING_PUSH_NOTIFICATION_ERROR = 1063;
	public static final int SENDING_PUSH_NOTIFICATION_ERROR = 1064;
	public static final int GET_TICKER_ERROR = 1065;
	public static final int GET_PUSH_NOTIFICATION_ERROR = 1066;
	public static final int UPLOAD_SELLER_DATA_ERROR = 1067;
	public static final int UPLOADING_CGD_DATA_ERROR = 1068;
	public static final int CGD_NOMINATION_DETAIL_PARSING_ERROR = 1069;
	public static final int SAVE_CGD_NOMINATION_ERROR = 1070;
	public static final int CGD_NOMINATION_DATA_NOT_FOUND = 1071;
	public static final int GET_SELLER_USER_ID_BY_CONTRACT_ID = 1072;
	public static final int GET_USER_ID_BY_USER_NAME = 1073;
	public static final int GETTING_USER_CONTACT_ERROR = 1074;

	public static final String SUBJECT_CHANGE_PASSWORD = "Password changed successfully";
	public static final String SUBJECT_FORGET_PASSWORD = "Forgot Password link";
	public static final String SUBJECT_SAVE_NOMINATION = "Nomination submitted successfully!";
	public static final String SUBJECT_SAVE_NOMINATION_BY_SELLER = "Quantity Scheduled By Seller!";

	public static final String NEW_USER_CREATED = "New user created";
	public static final String SUBJECT_ADD_CONTRACT = "Contract added/modified";
	public static final String SUBJECT_NOMINATION_REMINDER = "Nomination reminder";

	public static final String CHANGE_PASSWORD = "changepassword";
	public static final String FORGET_PASSWORD = "forgotpassword";
	public static final String SAVE_NOMINATION = "savenomination";
	public static final String NEW_USER = "newuser";
	public static final String ADD_CONTRACT = "addcontract";
	public static final String NOMINATION_REMINDER = "nominationreminder";
	public static final String SAVE_NOMINATION_SELLER = "updatenominationbyseller";
	
	public static final boolean PASSWORD_CHANGED = true;
	public static final boolean PASSWORD_NOT_CHANGED = false;

}
