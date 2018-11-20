function numericFilter(object) {
	var val = Number($(object).val());
	if (!constants.numberFilter.test(val)) {
		$(object).val('');
	}
}

function alphaFilter(object) {
	var val = $(object).val();
	if (!constants.alphaFilter.test(val)) {
		$(object).val('');
	}
}

function returnNumericChar(e) {
	var k;
	k = e.which;
	if (!k) {
		k = e.keyCode;
	} else if ((k > 34 && k < 38) || k == 39 || k == 46) {
		k = 0;
	} else {
	}
	return ((k > 47 && k < 58) || k == 8 || k == 32 || k == 9 || k == 37 || k == 39 || k == 46);
}


function clearField(object) {
	$($(object)[0].previousElementSibling).val('');
	$(object).hide();
}

function showClearIcon(object) {
	if ($(object).val().length != 0) {
		$($(object)[0].nextElementSibling).show();
	}
}

function commonError(status) {
	if (status == constants.unknownServerError
			|| status == constants.errorDBConnection || status == constants.runtimeException
			|| status == constants.runtimeException || status == constants.internalServerError) {
		showErrorModal(messages.msgProcessingError);
		
	} else if (status == constants.USER_DOES_NOT_EXIST) {
		showErrorModal(messages.msgInvalidUser);
		
	} else if (status == constants.USER_LOGGING_ERROR) {
		showErrorModal(messages.msgLoggingError);
		
	} else if (status == constants.PASSWORD_DOES_NOT_MATCHED) {
		showErrorModal(messages.msgInvalidOldPassword);
		
	} else if (status == constants.CHANGE_PASSWORD_ERROR) {
		showErrorModal(messages.msgChangedPasswordError);
		
	} else if (status == constants.USER_ID_NOT_MATCHED) {
		showErrorModal(messages.msgInvalidUserName);
		
	} else if (status == constants.FORGET_PASSWORD_ERROR) {
		showErrorModal(messages.msgForgotPasswordError);
		
	} else if (status == constants.CONTRACT_NOT_FOUND) {
		showErrorModal(messages.msgContractNotFound);
		
	} else if (status == constants.NOMINATION_GETTING_ERROR) {
		showErrorModal(messages.msgContractGettingError);
		
	} else if (status == constants.SAVE_NOMINATION_ERROR) {
		showErrorModal(messages.msgNominationError);
		
	} else if (status == constants.CONTRACT_LIST_NOT_FOUND) {
		showErrorModal(messages.msgNominationNotFound);
		
	} else if (status == constants.CUT_OFF_TIME_EXPIRED) {
		showErrorModal(messages.msgCutOffTimeExpire);
		
	} else if (status == constants.CUT_OFF_EXPIRED) {
		showErrorModal(messages.msgCutOffTimeExpire);
		
	} else if (status == constants.GET_MATERIAL_ERROR) {
		showErrorModal(messages.msgMaterialGettingError);
		
	} else if (status == constants.GET_REGION_ERROR) {
		showErrorModal(messages.msgRegionGettingError);
		
	} else if (status == constants.DOWNLOAD_NOMINATION_DATA_NOT_FOUND) {
		showErrorModal(messages.msgNoNominationData);
		
	} else if (status == constants.DOWNLOAD_NOMINATION_ERROR) {
		showErrorModal(messages.msgNominationDownloadError);
		
	} else if (status == constants.FILE_DOWNLOAD_ERROR) {
		showErrorModal(messages.msgNominationDownloadError);
		
	} else if (status == constants.FILE_NOT_FOUND) {
		showErrorModal(messages.msgNoNominationData);
		
	} else if (status == constants.USER_TOKEN_DEACTIVATED) {
		showLougoutModal(messages.msgForLOugout);
		
	} else if (status == constants.MATERIAL_UPLOAD_ERROR) {
		showErrorModal(messages.msgMaterialUploadError);
		
	} else if (status == constants.UPLOADING_USER_CONTACT_ERROR) {
		showErrorModal(messages.msgContactUploadError);
		
	} else if (status == constants.CONTRACT_UPLOAD_ERROR) {
		showErrorModal(messages.msgContractUploadError);
		
	} else if (status == constants.PAYER_NOT_FOUND) {
		showErrorModal(messages.msgPayerNotFound);
		
	} else if (status == constants.UPLOADING_ERROR) {
		showErrorModal(messages.msgUploadError);
		
	} else if (status == constants.INVALID_TEMPLATES) {
		showErrorModal(messages.msgInvalidTemplate);
		
	} else if (status == constants.UPLOADING_PAYER_DATA_ERROR) {
		showErrorModal(messages.msgUploadPayerError);
		
	} else if (status == constants.UPLOAD_USER_DATA_ERROR) {
		showErrorModal(messages.msgUploadUserError);
		
	} else if (status == constants.INVALID_CUTOFF_TIME_FORMAT) {
		showErrorModal(messages.invalidCutOfTimeFormat);
		
	} else if (status == constants.MATERIAL_CODE_NOT_FOUND) {
		showErrorModal(messages.msgMaterialNotFound);
		
	} else if (status == constants.INVALID_FILE_FORMAT) {
		showErrorModal(messages.msgInvalidFileFormat);
		
	} else if (status == constants.PAYER_ID_NOT_FOUND) {
		showErrorModal(messages.msgPayerNotFound);
		
	} else if (status == constants.PAYER_DETAIL_NOT_FOUND) {
		showErrorModal(messages.msgPayerDetailNotFound);
		
	} else if (status == constants.ADD_PROFILE_PARSING_ERROR) {
		showErrorModal(messages.msgAddProfileParsingError);
		
	} else if (status == constants.UPLOAD_USER_CONTACT_DATA_ERROR) {
		showErrorModal(messages.msgUploadContactError);
		
	} else if (status == constants.CONTACT_LIMIT_EXCEED) {
		showErrorModal(messages.msgContactLimitExced);
		
	} else if (status == constants.UPDATE_PROFILE_PARSING_ERROR) {
		showErrorModal(messages.msgUpdateProfileParsingError);
		
	} else if (status == constants.NO_CONTACT_FOUND) {
		showErrorModal(messages.msgNoContactFound);
		
	} else if (status == constants.DELETING_USER_CONTACT_ERROR) {
		showErrorModal(messages.msgDeleteUserContact);
		
	} else if (status == constants.NO_CONTACT_FOUNDS_FOR_DELETION) {
		showErrorModal(messages.msgNoContactFoundDeletion);
		
	} else if (status == constants.DOWNLOAD_USER_DATA_NOT_FOUND) {
		showErrorModal(messages.msgNoUserData);
		
	} else if (status == constants.DOWNLOAD_USER_ERROR) {
		showErrorModal(messages.msgUserDownloadError);
		
	} else if (status == constants.DOWNLOAD_CONTRACTS_DATA_NOT_FOUND) {
		showErrorModal(messages.msgNoContractsData);
		
	} else if (status == constants.DOWNLOAD_CONTRACTS_ERROR) {
		showErrorModal(messages.msgContractsDownloadError);
		
	} else if (status == constants.SAVING_TICKER_DATA_ERROR) {
		showErrorModal(messages.msgSavingTickerError);
		
	} else if (status == constants.SAVING_PUSH_NOTIFICATION_ERROR) {
		showErrorModal(messages.msgSavingPushNotificationError);

	} else if (status == constants.SENDING_PUSH_NOTIFICATION_ERROR) {
		showErrorModal(messages.msgSendPushNotificationError);
		
	} else if (status == constants.GET_TICKER_ERROR) {
		showErrorModal(messages.msgGetTickerError);

	} else if (status == constants.GET_PUSH_NOTIFICATION_ERROR) {
		showErrorModal(messages.msgGetPushNotificationError);
		
	}
	else if (status == constants.UPLOAD_SELLER_DATA_ERROR) {
		showErrorModal(messages.msgUploadSellerError);
		
	}
	else if (status == constants.GET_SELLER_USER_ID_BY_CONTRACT_ID) {
		showErrorModal(messages.msgSellerUserIdError);
		
	}
	else if (status == constants.GET_USER_ID_BY_USER_NAME) {
		showErrorModal(messages.msgGetUserId);
		
	}
	
	
}

function showAlertModal(message) {
	$('.alertModel2 p').html(message);
	$('.alertModel1').fadeIn(300);
	$('.alertModel2').fadeIn(300);
}

function showNominationModal(message) {
	$('.successNominationModel2 p').html(message);
	$('.successNominationModel1').fadeIn(300);
	$('.successNominationModel2').fadeIn(300);
}

function showTickerModal(message) {
	$('.successTickerModel2 p').html(message);
	$('.successTickerModel1').fadeIn(300);
	$('.successTickerModel2').fadeIn(300);
}

function showErrorModal(message) {
	$('.errorModel2 p').html(message);
	$('.errorModel1').fadeIn(300);
	$('.errorModel2').fadeIn(300);
}

function showSuccessModal(message) {
	$('.successModel2 p').html(message);
	$('.successModel1').fadeIn(300);
	$('.successModel2').fadeIn(300);
}

function showMaterialDescModel(message) {
	$('.materialDescModel2 p').html(message);
	$('.materialDescModel1').fadeIn(300);
	$('.materialDescModel2').fadeIn(300);
}

function showCustomerDescModel(message) {
	$('.customerDescModel2 p').html(message);
	$('.customerDescModel1').fadeIn(300);
	$('.customerDescModel2').fadeIn(300);
}

function showContractRefModel(message) {
	$('.contractValidity2 p').html(message);
	$('.contractValidity1').fadeIn(300); 
	$('.contractValidity2').fadeIn(300);
}

function editContractName(message) {// $('.contractValidity2 p').html(message);

	$('.addContractInfo2 input').html(message);
	$('.addContractInfo1').fadeIn(300);
	$('.addContractInfo2').fadeIn(300);
}


function editSellerPointInPopUp(message) {// $('.contractValidity2 p').html(message);

	$('.addSellerPoint2 input').html(message);
	$('.addSellerPoint1').fadeIn(300);
	$('.addSellerPoint2').fadeIn(300);
}



function showContractNameModel(message) {
	$('.contractName2 p').html(message);
	$('.contractName1').fadeIn(300);
	$('.contractName2').fadeIn(300);
}

function showLougoutModal(message) {
	$('.logoutModel2 p').html(message);
	$('.logoutModel1').fadeIn(300);
	$('.logoutModel2').fadeIn(300);
}

function dateFromater(dateString) {
	var yyyy = dateString.substring(6, 10);
	var mm = dateString.substring(3, 5);
	var dd = dateString.substring(0, 2);
	var dateStr = yyyy + "-" + mm + "-" + dd;
	return dateStr;
}

function datetimeFromater(dateString) {
	var mm = dateString.substring(14, 16);
	var hh = dateString.substring(11, 13);
	var yyyy = dateString.substring(6, 10);
	var mm = dateString.substring(3, 5);
	var dd = dateString.substring(0, 2);
	var dateStr = dd + "-" + mm + "-" + yyyy + " " + hh + ":" + mm + ":00";
	return dateStr;
}


function datetimeFromater2(dateString) {
	var min = dateString.substring(14, 16);
	var hh = dateString.substring(11, 13);
	var yyyy = dateString.substring(8, 10);
	var mm = dateString.substring(5, 7);
	var dd = dateString.substring(0, 4);
	var dateStr = dd + "-" + mm + "-" + yyyy + " " + hh + ":" + min + ":00";
	return dateStr;
}

function dateFromater2(dateString) {
	var yyyy = dateString.substring(6, 10);
	var mm = dateString.substring(3, 5);
	var dd = dateString.substring(0, 2);
	// var dateStr = yyyy+"-"+mm+"-"+dd;
	var dateStr = dd + "-" + mm + "-" + yyyy;
	return dateStr;
}
function loginValidation(username, password, usernameField, passwordField) {
	var validationResult = true;
	if (!username || username == '') {
		showAlertModal(messages.msgEnterUsername);
		validationResult = false;
		setFocusOnInput(usernameField);
	} else if (!password || password == '') {
		showAlertModal(messages.msgEnterPassword);
		validationResult = false;
		setFocusOnInput(passwordField);
	} else {
	}
	return validationResult;
}

function passwordValidation(oldPassword, newPassword, reNewPassword) {
	var validationResult = true;
	var re = /[0-9]/;
	if (!oldPassword || oldPassword == '') {
		showAlertModal(messages.msgEnterOldPassword);
		validationResult = false;
		
	} else if (!newPassword || newPassword == '') {
		showAlertModal(messages.msgEnterNewPassword);
		validationResult = false;
		
	} else if (!reNewPassword || reNewPassword == '') {
		showAlertModal(messages.msgEnterRePassword);
		validationResult = false;
		
	} else if (newPassword != reNewPassword) {
		showAlertModal(messages.msgPasswordMismatch);
		validationResult = false;
		
	} else if (newPassword.length < 8 && reNewPassword.length < 8) {
		showAlertModal("Password must contain at least eight characters!");
		return false;
		
	} else if (!re.test(newPassword) || !re.test(newPassword)) {
		showAlertModal("password must contain at least one number (0-9)!");
		return false;
	}
	return validationResult;
}

function includes(container, value) {
	var returnValue = false;
	var pos = container.indexOf(value);
	if (pos >= 0) {
		returnValue = true;
	}
	return returnValue;
}

function nominationValidation(nominationDate, nominationType) {
	
	var validationResult = true;
	if (!nominationDate || nominationDate == '--' || includes(nominationDate,'Dat')) {
		showAlertModal(messages.msgEnterNominationDate);
		validationResult = false;
		
	} else if (!nominationType || nominationType == '-Please Select-') {
		showAlertModal(messages.msgEnterNominationType);
		validationResult = false;
		
	} else {
	}
	return validationResult;
}


// Logout function
function logout() {
	sessionStorage.clear();
	localStorage.clear();
	location.replace("sign-in.html");
}

function numericFilter(object) {
	var val = $(object).val();
	if (!constants.numberFilterWithDot.test(val)) {
		$(object).val('');
	}
}