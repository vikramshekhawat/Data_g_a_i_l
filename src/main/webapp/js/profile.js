var emailValue;
var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
var re1 = /[0-9]/;
      
function configureProfilePage() 
	{
		showProgress('processing data.. please wait..');
		if(sessionStorage.getItem('userId')== null && sessionStorage.getItem('userId') == undefined)
			{
				location.replace('sign-in.html');
			}
		$('.loginUserName').html(sessionStorage.getItem("payerName"));
		if (sessionStorage.getItem("userRole")=='payer')
		{
			$('.title').html('(user)');
		}
		else
		{
			$('.title').html('(admin)');
		}
		fillEmailData(JSON.parse(sessionStorage.getItem("contactData")));
		hideProgress();
	}
	

function fillEmailData(dataList) {
	
	for(var i=0;i<dataList.contactDataList.length;i++){
		
		if(dataList.contactDataList[i].contactValue != "" && dataList.contactDataList[i].priority == 1 ){
			$('.primaryEmail').val(dataList.contactDataList[i].contactValue);
			emailValue = dataList.contactDataList[i].contactValue;
		}
		if(dataList.contactDataList[i].contactValue != "" && dataList.contactDataList[i].priority == 2  ){
			$('.firstSecEmail').val(dataList.contactDataList[i].contactValue);
		}
		if(dataList.contactDataList[i].contactValue != "" && dataList.contactDataList[i].priority == 3 ){
			$('.secondSecEmail').val(dataList.contactDataList[i].contactValue);
		}
		if(dataList.contactDataList[i].contactValue != "" && dataList.contactDataList[i].priority == 4  ){
			$('.thirdSecEmail').val(dataList.contactDataList[i].contactValue);
		}
		if(dataList.contactDataList[i].contactValue != "" && dataList.contactDataList[i].priority == 5  ){
			$('.fourSecEmail').val(dataList.contactDataList[i].contactValue);
		}
	}
}

	
function changePasswordOnEditProfile()
{
 	
	
	// Get values
	var oldPassword = $('.oldPassword').val().trim();
	var newPassword = $('.newPassword').val().trim();
	var reNewPassword = $('.reNewPassword').val().trim();
	
	// Validation function call commom.js
	var validationResult = passwordValidation(oldPassword, newPassword, reNewPassword);
	if(validationResult)
	{	
		showProgress('processing data.. please wait..');
		var formData = {};
		formData['userId'] = sessionStorage.getItem("userId");
		formData['oldPassword'] = oldPassword;
		formData['newPassword'] = newPassword;
		
		var finalData = JSON.stringify(formData);
		
		//creating header
		var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
		
		$.ajax({
			type: "POST",
			url: constants.changePasswordUrl,       
			data: finalData,
			headers: headerParam,
			dataType: "json",
			crossDomain: true,
			contentType: 'application/json',
			success: function(data){
				hideProgress();
				var responseStatus = data.responseStatus;
				if(responseStatus.statusCode == constants.success)
				{
					clearPassword();
					showSuccessModal("Password change successfully.");
				}
				else
				{
					commonError(responseStatus.statusCode);
				} 
			
			},
			error: function(e)
			{
				hideProgress();
				commonError(constants.internalServerError);
			}
		});
		
		
	} 
}

function editPrimaryEmail() {

	$(".primaryEmail").prop("readonly", false);
	$(".primaryEmail").focus();
}

function setDefaultEmail() {
	if(emailValue != undefined || emailValue != "") {
		$(".primaryEmail").val(emailValue);
	}
	$(".primaryEmail").prop("readonly", true);
	
}

function clearPassword() {
	$('.oldPassword').val("");
	$('.newPassword').val("");
	$('.reNewPassword').val("");
	$(".oldPassword").focus();
	
}

function clearText(object) {
	$("."+object).val("");
	$("."+object).focus();
}

function saveEmails() {
	
	var addContactRequestList = [];
	var deleteContactRequestList = [];
	var finalDataToRequest ={};
	var finalDataToDeleteRequest ={};
	if($('.primaryEmail').val() == "" ) {
		showAlertModal("Primary emial will not blanck.");
		$('.primaryEmail').focus();
		return;
	} else {
		if (!re.test($('.primaryEmail').val())) {
		
			showAlertModal("Please enter a valid email address.");
			return false;
		}
		var formData = {};
			formData['userId'] = sessionStorage.getItem("userId");
			formData['contactType'] = "mail";
			formData['contactValue'] = $('.primaryEmail').val().trim();
			formData['priority'] = "1";
			addContactRequestList.push(formData);
	}
	
	if($('.firstSecEmail').val() != "" ) {
		if (!re.test($('.firstSecEmail').val())) {
		
			showAlertModal("Please enter a valid email address.");
			return false;
		}
		var formData = {};
			formData['userId'] = sessionStorage.getItem("userId");
			formData['contactType'] = "mail";
			formData['contactValue'] = $('.firstSecEmail').val().trim();
			formData['priority'] = "2";
			addContactRequestList.push(formData);
	} else {
		var formData = {};
			formData['userId'] = sessionStorage.getItem("userId");
			formData['priority'] = "2";
		deleteContactRequestList.push(formData);	
	}
	
	if($('.secondSecEmail').val() != "" ) {
		if (!re.test($('.secondSecEmail').val())) {
		
			showAlertModal("Please enter a valid email address.");
			return false;
		}
		var formData = {};
			formData['userId'] = sessionStorage.getItem("userId");
			formData['contactType'] = "mail";
			formData['contactValue'] = $('.secondSecEmail').val().trim();
			formData['priority'] = "3";
			addContactRequestList.push(formData);
	} else {
		var formData = {};
			formData['userId'] = sessionStorage.getItem("userId");
			formData['priority'] = "3";
		deleteContactRequestList.push(formData);	
	}
	
	if($('.thirdSecEmail').val() != "" ) {
		if (!re.test($('.thirdSecEmail').val())) {
		
			showAlertModal("Please enter a valid email address.");
			return false;
		}
		var formData = {};
			formData['userId'] = sessionStorage.getItem("userId");
			formData['contactType'] = "mail";
			formData['contactValue'] = $('.thirdSecEmail').val().trim();
			formData['priority'] = "4";
			addContactRequestList.push(formData);
	} else {
		var formData = {};
			formData['userId'] = sessionStorage.getItem("userId");
			formData['priority'] = "4";
		deleteContactRequestList.push(formData);	
	}
	
	if($('.fourSecEmail').val() != "" ) {
		if (!re.test($('.fourSecEmail').val())) {
		
			showAlertModal("Please enter a valid email address.");
			return false;
		}
		var formData = {};
			formData['userId'] = sessionStorage.getItem("userId");
			formData['contactType'] = "mail";
			formData['contactValue'] = $('.fourSecEmail').val().trim();
			formData['priority'] = "5";
			addContactRequestList.push(formData);
	} else {
		var formData = {};
			formData['userId'] = sessionStorage.getItem("userId");
			formData['priority'] = "5";
		deleteContactRequestList.push(formData);	
	}
	
	//deleteing email data
	var object = {"addContactRequestList": deleteContactRequestList};
		
		
		
	finalDataToDeleteRequest = JSON.stringify(object);
		console.log(finalDataToDeleteRequest);
		
		//creating header
		var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
		
		$.ajax({
			type: "POST",
			url: constants.deleteContactDetailsUrl,       
			data: finalDataToDeleteRequest,
			headers: headerParam,
			dataType: "json",
			crossDomain: true,
			contentType: 'application/json',
			success: function(data){
				hideProgress();
				var responseData = data.responseData;
				var responseStatus = data.responseStatus;
				if(responseStatus.statusCode == constants.success)
				{
					
				}
				else
				{
					commonError(responseStatus.statusCode);
				} 
			
			},
			error: function(e)
			{
				hideProgress();
				commonError(constants.internalServerError);
			}
		});
	
	//adding email data
		var object = {"addContactRequestList": addContactRequestList};
		finalDataToRequest = JSON.stringify(object);
		console.log(finalDataToRequest);
		showProgress('saving email data.. please wait..');
		//creating header
		var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
		
		$.ajax({
			type: "POST",
			url: constants.addContactDetailsUrl,       
			data: finalDataToRequest,
			headers: headerParam,
			dataType: "json",
			crossDomain: true,
			contentType: 'application/json',
			success: function(data){
				hideProgress();
				var responseData = data.responseData;
				var responseStatus = data.responseStatus;
				if(responseStatus.statusCode == constants.success)
				{
					sessionStorage.setItem("contactData",responseData);
					showSuccessModal("Email saved successfully.");
				}
				else
				{
					commonError(responseStatus.statusCode);
				} 
			
			},
			error: function(e)
			{
				hideProgress();
				commonError(constants.internalServerError);
			}
		});
		
}

