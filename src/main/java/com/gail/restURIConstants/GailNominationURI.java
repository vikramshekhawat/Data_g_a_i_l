package com.gail.restURIConstants;

public class GailNominationURI {
	
	//User related APIs
	public static final String LOGIN = "/loginuser";
	public static final String VENDOR_STATUS = "/vendorstatus";
	public static final String BUILD_USER_REPORT = "/builduserreport";
	public static final String CHANGE_PASSWORD = "/changepassword";
	public static final String FORGET_PASSWORD = "/forgetpassword";
	public static final String UPLOAD_USER = "/uploaduser";
	public static final String LOGOUT = "/logout";
	
	//Material APIs
	public static final String GET_MATERIAL = "/getmaterial";
	public static final String UPLOAD_MATERIAL = "/uploadmaterial";
	
	//Region APIs
	public static final String GET_REGION = "/getregion";
	public static final String GET_REGION_NAME = "/getRegioName";
	
	//Nomination APIs
	public static final String GETNOMINATION="/getnomination";
	public static final String GET_CONTRACTS_OR_NOMINATION ="/getcontractsornominations";
	public static final String BUILD_NOMINATION_REPORT = "/buildnominationreport";
	public static final String DOWNLOAD_NOMINATION_REPORT = "/downloadnominationreport/{filename}";
	public static final String GET_CONTRACTS_FOR_SELLER="/getcontractsforseller";
	public static final String UPDATE_NOMINATION_BY_SELLER="/updatenominationbyseller";
	public static final String SAVE_NOMINATION="/savenomination";
	public static final String UPDATE_NOMINATION="/updatenomination";
	public static final String  SAVE_CONTRACT_TEXT="/savecontracttext";
	public static final String UPDATE_CONTRACT_TEXT="/updatecontracttext";
	
	//Contract APIs
	public static final String UPLOAD_CONTRACT = "/uploadcontract";
	public static final String BUILD_CONTRACT_REPORT = "/buildcontractreport";
	public static final String GET_CONTRACT_BY_PAYER_ID= "/getContractsByPayerId/{payerId}/{contractType}";
	
	//Ticker APIs
	public static final String GET_TICKER="/getTickers";
	public static final String ADD_TICKER="/addTicker";
	public static final String UPDATE_TICKER="/updateTicker";

}
