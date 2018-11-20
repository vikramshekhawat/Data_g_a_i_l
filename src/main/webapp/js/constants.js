//Local Server
var apiServerIp = 'http://localhost';
var apiServerPort = ':8080';

//Staging Server
//var apiServerIp = 'http://net.site4demo.com';
//var apiServerPort = ':8111';
  
//Production Server
//var apiServerIp='https://gailnominations';
//var apiServerPort = '.com';

var constants = {
	// api url
	loginUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/loginuser',
	changePasswordUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/changepassword',
	getNominationDetailUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/getnomination',
	forgotPasswordUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/forgetpassword',
	getContractsOrNominationsUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/getcontractsornominations',
	saveNominationsUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/savenomination',
	updateNominationUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/updatenomination',
	downloadNominationReportUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/downloadnominationreport',
	getmaterialUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/getmaterial',
	getregionUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/getregion',
	getregionIdUrl :apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/getRegioIdByRegionType/',
	getRegionName :apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/getRegioName',
	buildUserReportUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/builduserreport',
	buildContractReportUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/buildcontractreport',
	buildReportUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/buildnominationreport',
	downloadReportUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/downloadnominationreport/',
	getContractsByPayerIdUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/getContractsByPayerId/',
	uploadUserUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/uploaduser',
	uploadContractUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/uploadcontract',
	uploadPayerId: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/uploadPayer',
	uploadMaterialUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/uploadmaterial',
	uploadVendorUrl:apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/uploadvendor',
	uploadCGDUrl:apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/uploadcgd',
	addContactDetailsUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/addcontactdetails',
	deleteContactDetailsUrl : apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/deletecontactsdetail',
	getPayersUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/getPayers',
	getPayersId: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/getPayersIdByRegionId',
	createTickerUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/addTicker',
	sendPushNotificationUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/sendPushNotification',
	getNotificationsUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/getNotifications',
	getTickersUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/getTickers',
	updateTickerUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/updateTicker',
	saveContractText: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/savecontracttext',
	UpdateContractText: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/updatecontracttext',
	UpdateSellerRedelPoint: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/updatenominationbyseller',
	getcontractsforsellerUrl : apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/getcontractsforseller',
	getVendorStatusUrl : apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/vendorstatus', 
	buildCgdUserReportUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/buildcgduserreport',
	cgdNominationUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/savecgdnomination',
	updateCgdNominationUrl: apiServerIp+apiServerPort+'/gailNom/v1/gailNomination/updatecgdnomination',
	
	
	
	
	// Status code
	internalServerError: 500,
	success: 0,
	unknownServerError: 108,
	errorDBConnection: 109,
	runtimeException: 112,
	
	// Email ID regx
	emailFilter: /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/,
	mobileFilter: /^\d{10}$/,
	imeiFilter: /^\d{15}$/,
	pinCodeFilter: /^\d{6}$/,
	numberFilter: /([0-9])$/,
	
	numberFilterWithDot:/^(?!\.?$)\d{0,7}(\.\d{0,3})?$/,
	alphaFilter: /^[a-zA-Z]*$/,
	
	//userAPI status code
	
	USER_DOES_NOT_EXIST: 1002,
	USER_LOGGING_ERROR:  1003,
	PASSWORD_DOES_NOT_MATCHED: 1015,
	CHANGE_PASSWORD_ERROR: 1013,
	USER_ID_NOT_MATCHED: 1001,
    FORGET_PASSWORD_ERROR: 1014,
	CONTRACT_NOT_FOUND: 1019,
	NOMINATION_GETTING_ERROR: 1011,
	SAVE_NOMINATION_ERROR : 1016,
	CONTRACT_LIST_NOT_FOUND: 1017,
	CUT_OFF_TIME_EXPIRED: 1025,
	CUT_OFF_EXPIRED: 1026,
	GET_MATERIAL_ERROR: 1028,
	GET_REGION_ERROR: 1029,
	DOWNLOAD_NOMINATION_DATA_NOT_FOUND: 1023,
    DOWNLOAD_NOMINATION_ERROR: 1024,
	FILE_DOWNLOAD_ERROR: 1030,
    FILE_NOT_FOUND: 1031,
	USER_TOKEN_DEACTIVATED: 1033,
	UPLOADING_ERROR: 1037,
	INVALID_TEMPLATES: 1038,
	UPLOAD_USER_DATA_ERROR: 1039,
	UPLOADING_PAYER_DATA_ERROR: 1040,
	UPLOADING_USER_CONTACT_ERROR: 1041,
	
	CONTRACT_UPLOAD_ERROR: 1043,
	PAYER_NOT_FOUND: 1044,
	MATERIAL_UPLOAD_ERROR: 1045,
	INVALID_CUTOFF_TIME_FORMAT: 1050,
	
	MATERIAL_CODE_NOT_FOUND :1042,
	INVALID_FILE_FORMAT : 1046,
	PAYER_ID_NOT_FOUND : 1047,
	PAYER_DETAIL_NOT_FOUND : 1048,
	ADD_PROFILE_PARSING_ERROR : 1049,
	UPLOAD_USER_CONTACT_DATA_ERROR : 1051,
	CONTACT_LIMIT_EXCEED : 1052,
	UPDATE_PROFILE_PARSING_ERROR : 1053,
	NO_CONTACT_FOUND : 1054,
    DELETING_USER_CONTACT_ERROR : 1055,
    NO_CONTACT_FOUNDS_FOR_DELETION : 1056,
    DOWNLOAD_USER_DATA_NOT_FOUND : 1058,
    DOWNLOAD_USER_ERROR : 1059,
    DOWNLOAD_CONTRACTS_DATA_NOT_FOUND : 1060,
    DOWNLOAD_CONTRACTS_ERROR : 1061,
    SAVING_TICKER_DATA_ERROR : 1062,
    SAVING_PUSH_NOTIFICATION_ERROR : 1063,
    SENDING_PUSH_NOTIFICATION_ERROR : 1064,
    GET_TICKER_ERROR : 1065,
    GET_PUSH_NOTIFICATION_ERROR : 1066,
    UPLOAD_SELLER_DATA_ERROR :1067,
    UPLOADING_CGD_DATA_ERROR :1068,
    CGD_NOMINATION_DETAIL_PARSING_ERROR : 1069,
    SAVE_CGD_NOMINATION_ERROR : 1070,
    CGD_NOMINATION_DATA_NOT_FOUND:1071,
    GET_SELLER_USER_ID_BY_CONTRACT_ID:1072,
    GET_USER_ID_BY_USER_NAME : 1073,
    GETTING_USER_CONTACT_ERROR : 1074
	
};