var resionData;
var materialData;
var nominationtype = "GSA";
var uploadtype = "user";
var downloadtype = "user";
var uploadedFile;
var userData;
var payerId=[];
var uploadPayerId = []
var tickerUploadedFile;

function activeGSA() {
	$('.gsaType').addClass("activeBtn2");
	$('.gtaType').removeClass("activeBtn2");
	$('.bothType').removeClass("activeBtn2");
	nominationtype = "GSA";
}

function activeGTA() {
	$('.gsaType').removeClass("activeBtn2");
	$('.gtaType').addClass("activeBtn2");
	$('.bothType').removeClass("activeBtn2");
	nominationtype = "GTA";
}

function activeBoth() {
	$('.gsaType').removeClass("activeBtn2");
	$('.gtaType').removeClass("activeBtn2");
	$('.bothType').addClass("activeBtn2");
	nominationtype = null;
}

// Upload Report
function activeUser() {
	$('.userData').addClass("activeDetails");
	$('.contractData').removeClass("activeDetails");
	$('.materialData').removeClass("activeDetails");
	$('.vendortData').removeClass("activeDetails");
	$('.uploadtype').html("add/update User");
	$('.box label span').text("Browse");
	$('#file-1').val("");
	uploadtype = "user";
}

function activeContract() {
	$('.userData').removeClass("activeDetails");
	$('.contractData').addClass("activeDetails");
	$('.materialData').removeClass("activeDetails");
	$('.vendorData').removeClass("activeDetails");
	$('.uploadtype').html("add Contract");
	$('.box label span').text("Browse");
	$('#file-1').val("");
	uploadtype = "contract";
}

function activeMaterial() {
	$('.userData').removeClass("activeDetails");
	$('.contractData').removeClass("activeDetails");
	$('.materialData').addClass("activeDetails");
	$('.vendorData').removeClass("activeDetails");
	$('.uploadtype').html("add Material");
	$('.box label span').text("Browse");
	$('#file-1').val("");
	uploadtype = "material";
}

function activeVendor() {
	$('.userData').removeClass("activeDetails");
	$('.contractData').removeClass("activeDetails");
	$('.materialData').removeClass("activeDetails");
	$('.vendorData').addClass("activeDetails");
	$('.uploadtype').html("add vendor");
	$('.box label span').text("Browse");
	$('#file-1').val("");
	uploadtype = "vendor";
}





// Download Report
function activeDownloadUser() {
	$('.userData').addClass("activeDetails");
	$('.contractData').removeClass("activeDetails");
	$('.materialData').removeClass("activeDetails");
	$('.box label span').text("Browse");
	$('#file-1').val("");
	$("#userTab").show();
	
	$("#TickerData").hide();
	$('#PushNotificationData').hide();
	
	$("#contractTab").hide();
	$("#nominationTab").hide();
	document.getElementById("resetBtn").style.display = 'none';
	downloadtype = "user";
}

function activeDownloadContract() {
	$('.userData').removeClass("activeDetails");
	$('.contractData').addClass("activeDetails");
	$('.materialData').removeClass("activeDetails");
	$('.box label span').text("Browse");
	$('#file-1').val("");
	$("#userTab").hide();
	$("#contractTab").show();
	$("#nominationTab").hide();
	document.getElementById("resetBtn").style.display = 'block';
	downloadtype = "contract";
}

function activeDownloadMaterial() {
	$('.userData').removeClass("activeDetails");
	$('.contractData').removeClass("activeDetails");
	$('.materialData').addClass("activeDetails");
	$('.box label span').text("Browse");
	$('#file-1').val("");
	$("#userTab").hide();
	$("#contractTab").hide();
	$("#nominationTab").show();
	document.getElementById("resetBtn").style.display = 'block';
	downloadtype = "nomination";
}

$(document).ready(function() {
	$('#tab1').trigger("click");
});


function configureAdminPage() {
	
	showProgress('processing data.. please wait..');
	if (sessionStorage.getItem('userId') == null && sessionStorage.getItem('userId') == undefined) {
		location.replace('sign-in.html');
	}
	$('.loginUserName').html(sessionStorage.getItem("payerName"));
	$('.title').html('(admin)');
	getMaterialData();
	gerResionData();
	gerRegionData();
	
	//getRegionNomination();
	//getUserData();
	
	// put the function name here
}
	
	
function gerResionData() {
	//creating header
	var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
		
	$('.regionDropdown').empty();
	resionData = null;
	$.ajax({
		type : "GET",
		url : constants.getregionUrl,
		dataType : "json",
		headers : headerParam,
		crossDomain : true,
		success : function(data) {
			//hideProgress();
			
			var responseStatus = data.responseStatus;
			if (responseStatus.statusCode == constants.success) {
				$('.regionDropdown').append('<option > All</option>');
				regionData = data.responseData;
				for (var i = 0; i < regionData.dataList.length; i++) {
					$('.regionDropdown').append('<option value="' + regionData.dataList[i].regionId + '">' + regionData.dataList[i].regionName + '</option>');
				}
				$("#multipleRegions").trigger("chosen:updated");
				
				
			} 
			
			
			
/*			var responseStatus = data.responseStatus;
			if (responseStatus.statusCode == constants.success) {
				$('.regionDropdown').append('<option>All</option>');
				$('.regionDropdown').append('<option>All</option>');
				resionData = data.responseData;
				for (var i = 0; i < resionData.dataList.length; i++) {
					$(".regionDropdown").append('<option>' + resionData.dataList[i].regionName + '</option>');
					$(".regionDropdown").append('<option>' + resionData.dataList[i].regionName + '</option>');
				}
			} */
			
			
			
			
			
			
			else {
				commonError(responseStatus.statusCode);
			}
		},
		error : function(e) {
			hideProgress();
			commonError(constants.internalServerError);
		}
	});
}


function gerRegionData() {
	
	//creating header
	var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
		
	$('.regionMultidropdown').empty();
	var regionData = null;
	$.ajax({
		type : "GET",
		url : constants.getregionUrl,
		dataType : "json",
		headers : headerParam,
		crossDomain : true,
		success : function(data) {
			hideProgress();
			
			var responseStatus = data.responseStatus;
			if (responseStatus.statusCode == constants.success) {
				$('.regionMultidropdown').append('<option > All</option>');
				regionData = data.responseData;
				for (var i = 0; i < regionData.dataList.length; i++) {
					$('.regionMultidropdown').append('<option value="' + regionData.dataList[i].regionId + '">' + regionData.dataList[i].regionName + '</option>');
				}
				
				$("#multipleRegionsPushNotification").trigger("chosen:updated");
			} 
			
/*			var responseStatus = data.responseStatus;
			if (responseStatus.statusCode == constants.success) {
				$('.regionDropdown').append('<option>All</option>');
				$('.regionDropdown').append('<option>All</option>');
				resionData = data.responseData;
				for (var i = 0; i < resionData.dataList.length; i++) {
					$(".regionDropdown").append('<option>' + resionData.dataList[i].regionName + '</option>');
					$(".regionDropdown").append('<option>' + resionData.dataList[i].regionName + '</option>');
				}
			} */
			
			
			else {
				commonError(responseStatus.statusCode);
			}
		},
		error : function(e) {
			hideProgress();
			commonError(constants.internalServerError);
		}
	});
}

function getRegionNomination() {
	
	//creating header
	var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
		
	$('.regionMultidropdown').empty();
	var regionData = null;
	$.ajax({
		type : "GET",
		url : constants.getregionUrl,
		dataType : "json",
		headers : headerParam,
		crossDomain : true,
		success : function(data) {
			hideProgress();
			
			var responseStatus = data.responseStatus;
			if (responseStatus.statusCode == constants.success) {
				$('.regionMultidropdown').append('<option > All</option>');
				regionData = data.responseData;
				for (var i = 0; i < regionData.dataList.length; i++) {
					$('.regionMultidropdown').append('<option value="' + regionData.dataList[i].regionId + '">' + regionData.dataList[i].regionName + '</option>');
				}
				
				$("#multipleRegionsPushNotification").trigger("chosen:updated");
			} 
			
/*			var responseStatus = data.responseStatus;
			if (responseStatus.statusCode == constants.success) {
				$('.regionDropdown').append('<option>All</option>');
				$('.regionDropdown').append('<option>All</option>');
				resionData = data.responseData;
				for (var i = 0; i < resionData.dataList.length; i++) {
					$(".regionDropdown").append('<option>' + resionData.dataList[i].regionName + '</option>');
					$(".regionDropdown").append('<option>' + resionData.dataList[i].regionName + '</option>');
				}
			} */
			
			
			else {
				commonError(responseStatus.statusCode);
			}
		},
		error : function(e) {
			hideProgress();
			commonError(constants.internalServerError);
		}
	});
}






















function getMaterialData() {
	// creating header
	var headerParam = {'clientdata' : sessionStorage.getItem("userId") + "|" + sessionStorage.getItem("token")};
	$('.materialDropdown').empty();
	materialData = null;
	$.ajax({
		type : "GET",
		url : constants.getmaterialUrl,
		dataType : "json",
		crossDomain : true,
		headers : headerParam,
		success : function(data) {
			hideProgress();
			var responseStatus = data.responseStatus;
			if (responseStatus.statusCode == constants.success) {
				$('.materialDropdown').append('<option > All</option>');
				materialData = data.responseData;
				for (var i = 0; i < materialData.materialDataList.length; i++) {
					$('.materialDropdown').append('<option >' + materialData.materialDataList[i].materialCode + '</option>');
				}
			} else {
				commonError(responseStatus.statusCode);
			}
		},
		error : function(e) {
			hideProgress();
			commonError(constants.internalServerError);
		}
	});
}

//***************************************************************************************************
//function getPayerIdByRegionId(valueA){

 // var regionList = [];
//	  var regionId = $(valueA).val();
	   
//	  var formData = {};
//	  formData['region_id'] = regionId;
	//  finalData = JSON.stringify(formData);
		//getUserData(finalData);
//	  var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
//	  console.log(constants.getPayersId);
//	  $.ajax({
//	 		type : "POST",
//	 		url : constants.getPayersId,
//	 		dataType : "json",
//	 		headers : headerParam,
//	 		data : finalData,
//	 		contentType:'application/json',
//	 		crossDomain : true,
//	 		success : function(data) {
//	
//	 			
//	 			
//	 			
//			//hideProgress();

//	 			var responseStatus = data.responseStatus;
//				if (responseStatus.statusCode == constants.success) {
//					$('#multiplePayers').append('<option>All</option>');
//					$('#multipleUsers').append('<option>All</option>');
//					userData = data.responseData;
//					for (var i = 0; i < userData.dataList.length; i++) {
//						$("#multiplePayers").append('<option>' + userData.dataList[i].payerKey + '</option>');
//						$("#multipleUsers").append('<option>' + userData.dataList[i].payerKey + '</option>');
//					}
//					$("#multiplePayers").trigger("chosen:updated");
//					$("#multipleUsers").trigger("chosen:updated");
//					
//					
//				//	console.log(data);
//					
//				//	getUserData(finalData);
//					
//				} else {
//					commonError(responseStatus.statusCode);
//				}
//			},
//			error : function(e) {
//				hideProgress();
//				commonError(constants.internalServerError);
//				console.log(e);
//			}
//		});
//	
	
	
	
	
	
	
	
	
//}




















$("#multipleRegions").change(function(evt, params){
	
	 if (params.selected && params.selected == "All") {
		 $('#file-ticker').prop('disabled', true);
		 
	 }
	 else{
		 $('#file-ticker').prop('disabled', false);
	getUserData(this);
	 }
	
});

$("#multipleRegionsPushNotification").change(function(evt, params){
	
	if (params.selected && params.selected == "All") {
	
		 $('#file-ticker').prop('disabled', true);
		
	 }
	else{
		 $('#file-ticker').prop('disabled', false);
	getUserData(this);

	}
});

//************************************************************************************


function getUserData(valueA) {

	var regionList = [];
    this.payerId = [];

	  var regionId = $(valueA).val();
	   if(regionId !=null){
	  var formData = {};
	  formData['region_id'] = regionId;
	  finalData = JSON.stringify(formData);
		
	//creating header
	var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};

	$('#multiplePayers').empty();
	$('#multipleUsers').empty();
	

	  console.log(constants.getPayersId);
	  $.ajax({
	 		type : "POST",
	 		url : constants.getPayersId,
	 		dataType : "json",
	 		headers : headerParam,
	 		data : finalData,
	 		contentType:'application/json',
	 		crossDomain : true,
	 		success : function(data) {
	 			
			//hideProgress();
	
	 			var responseStatus = data.responseStatus;
				if (responseStatus.statusCode == constants.success) {
				//	$('#multiplePayers').append('<option>All</option>');
					//$('#multipleUsers').append('<option>All</option>');
					userData = data.responseData;
					for (var i = 0; i < userData.dataList.length; i++) {
						//$("#multiplePayers").append('<option>' + userData.dataList[i].payerKey + '</option>');
					//	$("#multipleUsers").append('<option>' + userData.dataList[i].payerKey + '</option>');
						payerId.push(userData.dataList[i].payerKey);
					}
					//$("#multiplePayers").trigger("chosen:updated");
				//	$("#multipleUsers").trigger("chosen:updated");
					
					
					console.log(payerId);
					
				//
					
				} else {
					commonError(responseStatus.statusCode);
				}
			},
			error : function(e) {
				hideProgress();
				commonError(constants.internalServerError);
				console.log(e);
			}
		});
	   }
}
	//*************User Data ********************

function getUser() {
	this.payerId = [];
	
	//creating header
	var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};

	//$('#multiplePayers').empty();
	//$('#multipleUsers').empty();
	userData = null;
	$.ajax({
		type : "GET",
		url : constants.getPayersUrl,
		dataType : "json",
		headers : headerParam,
		crossDomain : true,
		success : function(data) {
//			hideProgress();
			var responseStatus = data.responseStatus;
			if (responseStatus.statusCode == constants.success) {
				//$('#multiplePayers').append('<option>All</option>');
			//	$('#multipleUsers').append('<option>All</option>');
				userData = data.responseData;
				for (var i = 0; i < userData.dataList.length; i++) {
				//	$("#multiplePayers").append('<option>' + userData.dataList[i].payerKey + '</option>');
				//	$("#multipleUsers").append('<option>' + userData.dataList[i].payerKey + '</option>');
					payerId.push(userData.dataList[i].payerKey);
				}
				//$("#multiplePayers").trigger("chosen:updated");
			//	$("#multipleUsers").trigger("chosen:updated");
				
			} else {
				commonError(responseStatus.statusCode);
			}
		},
		error : function(e) {
			hideProgress();
			commonError(constants.internalServerError);
		}
	});
}

function buildNominationReport() {
	
	var URL;
	var formData = {};
	
	formData['userId'] = sessionStorage.getItem("userId");
	formData['contractType'] = nominationtype;
	

	if ($('.regionDropdown').val() == "All") {
		formData['regionId'] = null;
	} else {
		var regionIdList = [];
		//var regionId = findRegionId($('.regionDropdown').val());
		var regionId = $('.regionDropdown').val();
		regionIdList.push(regionId);
		formData['regionId'] = regionIdList;
	}
	
	formData['materialCode'] = $('.materialDropdown').val().trim();
	

	var finalData = "";
	var requestType = "POST";
	if (downloadtype == "user") {
		URL = constants.buildUserReportUrl;
		requestType = "GET";
		finalData = null;
	} else if (downloadtype == "contract") {
		URL = constants.buildContractReportUrl;
		requestType = "POST";
		if (dateFromater($('.contractEndDate').val().trim()).length != 10) {
			showAlertModal(messages.msgEnterContractEndDate);
			return false;
		}
		formData['contractEndDate'] = dateFromater($('.contractEndDate').val().trim());
		finalData = JSON.stringify(formData);
		console.log(finalData);
	} else if (downloadtype == "nomination") {
		
		if($(".SingleDate").is(":checked"))
		{
			formData['startDate'] = dateFromater($('.singleSelectedDate').val().trim());
			formData['endDate'] = dateFromater($('.singleSelectedDate').val().trim());
		
		}
		if($(".MultipleDate").is(":checked"))
		{
			formData['startDate'] = dateFromater($('.fristMulSelectedDate').val().trim());
			formData['endDate'] = dateFromater($('.secondMulSelectedDate').val().trim());
		
		}
		
		URL = constants.buildReportUrl;
		requestType = "POST";
		finalData = JSON.stringify(formData);
		console.log(finalData);
	}
	showProgress('processing data.. please wait..');

	//creating header
		var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};

	$.ajax({
		type : requestType,
		url : URL,
		data : finalData,
		headers : headerParam,
		dataType : "json",
		crossDomain : true,
		contentType : 'application/json',
		success : function(data) {
			hideProgress();
			var responseStatus = data.responseStatus;
			console.log("responseStatus" + responseStatus);
			if (responseStatus.statusCode == constants.success) {
				window.location = constants.downloadReportUrl + data.responseData.fileName;
			}
			else {
				commonError(responseStatus.statusCode);
			}
		},
		error : function(e) {
			hideProgress();
			commonError(constants.internalServerError);
		}
	});
}

function createTicker() {
	var URL;
//	this.payerId = [];
	var formData = {};
	formData['userId'] = sessionStorage.getItem("userId");
	
	if($('#multipleRegions').val()=='All'){
		formData['payerId'] = "All";
	}
	else{
	
	if($('#multipleRegions').val()){
		
			if(uploadPayerId.length > 0){
				for(var i = 0 ; i < uploadPayerId.length; i++){
				    this.payerId.push(uploadPayerId[i].payerKey);
				 }
			}
			formData['payerId'] = this.payerId;

	}else { 
		if(uploadPayerId.length > 0){
		for(var i = 0 ; i < uploadPayerId.length; i++){
			   this.payerId.push(uploadPayerId[i].payerKey);
			 }
			formData['payerId'] = this.payerId;
		}
	} 
	}
	
	
	//******************************************************************************
//	formData['regionId'] = sessionStorage.getItem("regionId");
//
//	if ($('#multipleRegions').val() == "All") {
//		formData['regionId'] = "All";
//	} else {
//		var payerIdList = [];
//		var payerId = $('#multipleRegions').val();
//		payerIdList.push(payerId);
//		formData['regionId'] = payerIdList;
//	}
	//***********************************************************************************************
	debugger;
	formData['startDate'] = datetimeFromater($('.startDate').val().trim());
	formData['endDate'] = datetimeFromater($('.endDate').val().trim());
	formData['tickerText'] = $('.tickerText').val().trim();
	
	showProgress('saving data.. please wait..');
	var finalData = "";
	var requestType = "POST";
	URL = constants.createTickerUrl;
	finalData = JSON.stringify(formData);
	console.log(finalData);

	//creating header
	var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};

	$.ajax({
		type : requestType,
		url : URL,
		data : finalData,
		headers : headerParam,
		dataType : "json",
		crossDomain : true,
		contentType : 'application/json',
		success : function(data) {
			hideProgress();
			this.payerId = [];
			uploadPayerId = [];
			var responseStatus = data.responseStatus;
			console.log("responseStatus" + JSON.stringify(responseStatus));
			if (responseStatus.statusCode == constants.success) {
				hideProgress();
				showTickerModal(messages.msgTickerSave);
				clearTickerData();
			}
			else {
				commonError(responseStatus.statusCode);
//				this.payerId = [];
			}
			
		},
		error : function(e) {
			hideProgress();
			commonError(constants.internalServerError);
		}
	});
	payerId = [];
	
}

function sendPushNotification() {
	var URL;
	var formData = {};
	formData['userId'] = sessionStorage.getItem("userId");
	if($('#multipleRegionsPushNotification').val()=='All'){
		formData['payerId'] = "All";
	}

	
	else{
		
		if($('#multipleRegionsPushNotification').val()){
			
				if(uploadPayerId.length > 0){
					for(var i = 0 ; i < uploadPayerId.length; i++){
					    this.payerId.push(uploadPayerId[i].payerKey);
					 }
				}
				formData['payerId'] = this.payerId;

		}else { 
			if(uploadPayerId.length > 0){
			for(var i = 0 ; i < uploadPayerId.length; i++){
				   this.payerId.push(uploadPayerId[i].payerKey);
				 }
				formData['payerId'] = this.payerId;
			}
		} 
		}
/*	if ($('#multipleUsers').val() == "All") {
		formData['payerId'] = this.payerId;
	} else {
		var payerIdList = [];
		var payerId = $('#multipleUsers').val();
		//payerIdList.push(payerId);
		formData['payerId'] = payerId;
	}*/
	
//	//****************************************************************************
//	formData['regionId'] = sessionStorage.getItem("regionId");
//
//	if ($('#multipleRegionsPushNotification').val() == "All") {
//		formData['regionId'] = "All";
//	} else {
//		var payerIdList = [];
//		var payerId = $('#multipleRegionsPushNotification').val();
//		payerIdList.push(payerId);
//		formData['regionId'] = payerIdList;
//	}
	
	//**********************************************************************************
	
	formData['pushNotificationText'] = $('.pushNotificationText').val().trim();
	
	showProgress('sending data.. please wait..');
	var finalData = "";
	var requestType = "POST";
	URL = constants.sendPushNotificationUrl;
	finalData = JSON.stringify(formData);
	console.log(finalData);

	//creating header
	var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};

	$.ajax({
		type : requestType,
		url : URL,
		data : finalData,
		headers : headerParam,
		dataType : "json",
		crossDomain : true,
		contentType : 'application/json',
		success : function(data) {
			hideProgress();
			payerId = [];
			uploadPayerId = [];
			var responseStatus = data.responseStatus;
			console.log("responseStatus" + JSON.stringify(responseStatus));
			if (responseStatus.statusCode == constants.success) {
				hideProgress();
				showTickerModal(messages.msgNotificationSent);
				clearNotificationData();
			} else {
				commonError(responseStatus.statusCode);
			}
		},
		error : function(e) {
			hideProgress();
			commonError(constants.internalServerError);
		}
	});
	
}

function getNotificationHistory() {
	
	// Clearing values
	$('.notificationHistoryDisplayTable').empty();
	
	// Validation function call commom.js

	showProgress('getting data.. please wait..');
	var formData = {};
	formData['userId'] = sessionStorage.getItem("userId");
	
	var finalData = JSON.stringify(formData);
	console.log("finalData" +finalData);
	//creating header
	var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
	
	$.ajax({
		type: "GET",
		url: constants.getNotificationsUrl,
        headers: headerParam,			
		dataType: "json",
		crossDomain: true,
		contentType: 'application/json',
		success: function(data){
			hideProgress();
			var responseStatus = data.responseStatus;
			var notificationData = data.responseData; 
			$('.notificationHistoryDisplayTable').append(htmlConstants.notificationDataHeader)
			$('.displayNotificationDetailTable').removeClass( "displayNone");
			$('.notificationDetail').addClass("displayNone");
			if(notificationData == null) {
				showAlertModal("No notification data found.");
			} else {
				for(var i=0;i<notificationData.notificationData.length;i++) {
					$('.notificationHistoryDisplayTable').append(htmlConstants.notificationDataRow);
					var dlist = $('.notificationHistoryDisplayTable').find('.notificationDataRowParent')[i];
					$(dlist).find('.pushNotificationText').html(notificationData.notificationData[i].pushNotificationText);
					$(dlist).find('.sendTo').html(notificationData.notificationData[i].sendTo);
				}
				$('.notificationHistoryDisplayTable').append('</tbody>');
		   }
		},
		error: function(e)
		{
			hideProgress();
			commonError(constants.internalServerError);
		}
	}); 
		
}

function getTickerHistory(){

	// Clearing values
	$('.tickerHistoryDisplayTable').empty();
	
	// Validation function call commom.js
	showProgress('getting data.. please wait..');
	var formData = {};
	formData['userId'] = sessionStorage.getItem("userId");
	
	var finalData = JSON.stringify(formData);
	console.log("finalData" +finalData);
	//creating header
	var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
	
	$.ajax({
		type: "GET",
		url: constants.getTickersUrl,
        headers: headerParam,			
		dataType: "json",
		crossDomain: true,
		contentType: 'application/json',
		success: function(data){
			hideProgress();
			var responseStatus = data.responseStatus;
			var tickerData = data.responseData; 
			$('.tickerHistoryDisplayTable').append(htmlConstants.tickerDataHeader)
			$('.displayTickerDetailTable').removeClass( "displayNone");
			$('.tickerDetail').addClass("displayNone");
			debugger;
			if(tickerData == null) {
				showAlertModal("No ticker data found.");
			} else {
				debugger;
				for(var i=0;i<tickerData.dataList.length;i++) {
					$('.tickerHistoryDisplayTable').append(htmlConstants.tickerDataRow);
					var dlist = $('.tickerHistoryDisplayTable').find('.tickerDataRowParent')[i];
					$(dlist).find('.tickerId').html(tickerData.dataList[i].tickerId).hide();
					$(dlist).find('.tickerText').html(tickerData.dataList[i].tickerText);
					$(dlist).find('.start').html(tickerData.dataList[i].start);
					$(dlist).find('.end').html(tickerData.dataList[i].end);
					$(dlist).find('.btnEdit').html('Edit');
					$(dlist).find('.btnDelete').html('Delete');
				}
				$(".btnEdit").bind("click", Edit);		
				$(".btnDelete").bind("click", Delete);
				$('.tickerHistoryDisplayTable').append('</tbody>');
		   }
		},
		error: function(e)
		{
			hideProgress();
			commonError(constants.internalServerError);
		}
	}); 
}

function Save(){
	var par = $(this).parent().parent(); //tr
	var tickerId = par.children("td:nth-child(1)");
	var tickerText = par.children("td:nth-child(2)");
	var start = par.children("td:nth-child(3)");
	var end = par.children("td:nth-child(4)");
	var tdButtons = par.children("td:nth-child(25)");

	tickerId.html(tickerId.children("input[type=text]").val()).hide();
	tickerText.html(tickerText.children("input[type=text]").val());
	start.html(start.children("input[type=text]").val());
	end.html(end.children("input[type=text]").val());
	tdButtons.html("<div class='btnEdit'>Edit</div> <div class='btnDelete'>Delete</div>");
	
	$(".btnEdit").bind("click", Edit);
	$(".btnDelete").bind("click", Delete);
	
	// Validation function call commom.js
	showProgress('saving data.. please wait..');
	var formData = {};
	formData['userId'] = sessionStorage.getItem("userId");
	formData['tickerId'] = tickerId.html();
	formData['tickerText'] = tickerText.html();
	formData['startDate'] = datetimeFromater2(start.html().trim());
	formData['endDate'] = datetimeFromater2(end.html().trim());
	
	var finalData = JSON.stringify(formData);
	console.log("finalData" +finalData);
	//creating header
	var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
	
	$.ajax({
		type: "POST",
		url: constants.updateTickerUrl,
		data: finalData,
        headers: headerParam,			
		dataType: "json",
		crossDomain: true,
		contentType: 'application/json',
		success: function(data){
			hideProgress();
			var responseStatus = data.responseStatus;
			var tickerData = data.responseData; 
			if(tickerData == null) {
				showAlertModal("No ticker data found.");
			}
		},
		error: function(e) {
			hideProgress();
			commonError(constants.internalServerError);
		}
	}); 

	
};

function Edit(){
	var par = $(this).parent().parent(); //tr
	var tickerId = par.children("td:nth-child(1)");
	var tickerText = par.children("td:nth-child(2)");
	var start = par.children("td:nth-child(3)");
	var end = par.children("td:nth-child(4)");
	var tdButtons = par.children("td:nth-child(5)");

	tickerId.html(tickerId.children("input[type=text]").val()).hide();
	tickerText.html("<input class='editable' type='text' id='tickerText' value='"+tickerText.html()+"'/>");
	start.html("<input class='editable' type='text' id='start' readonly='readonly' value='"+start.html()+"'/>");
	end.html("<input class='editable' type='text' id='end' readonly='readonly' value='"+end.html()+"'/>");
	tdButtons.html("<div class='btnSave'>Save</div>");
	
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!
	var yyyy = today.getFullYear();
	var hh = today.getHours();
	var mm = today.getMinutes();
	var ss = today.getSeconds();
	today = mm + '/' + dd + '/' + yyyy + ' ' + hh + ':' + mm;
	
	$('#start').datetimepicker({
		lang:'en',
		timepicker:true,
		format:'d-m-Y H:m',
		formatDate:'d-m-Y',
		formatTime:'H:m',
		minDate: today
	});
	debugger;
	$('#end').datetimepicker({
		lang:'en',
		timepicker:true,
		format:'d-m-Y H:m',
		formatDate:'d-m-Y',
		minDate: datetimeFromater2($('#start').val().trim()),
		formatTime:'H:m'
	});
	
	$(".btnSave").bind("click", Save);
	$(".btnEdit").bind("click", Edit);
	$(".btnDelete").bind("click", Delete);
};

function Delete(){
	var par = $(this).parent().parent(); //tr

	var tickerId = par.children("td:nth-child(1)");
	var tickerText = par.children("td:nth-child(2)");
	var start = par.children("td:nth-child(3)");
	var end = par.children("td:nth-child(4)");
	var tdButtons = par.children("td:nth-child(5)");
	
	// Validation function call commom.js
	showProgress('saving data.. please wait..');
	var formData = {};
	formData['userId'] = sessionStorage.getItem("userId");
	formData['tickerId'] = tickerId.html();
	formData['tickerText'] = tickerText.html();
	formData['startDate'] = start.html();
	formData['endDate'] = end.html();
	formData['status'] = 1;
	
	var finalData = JSON.stringify(formData);
	console.log("finalData" +finalData);
	//creating header
	var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
	
	$.ajax({
		type: "POST",
		url: constants.updateTickerUrl,
		data: finalData,
        headers: headerParam,			
		dataType: "json",
		crossDomain: true,
		contentType: 'application/json',
		success: function(data){
			hideProgress();
			var responseStatus = data.responseStatus;
			var tickerData = data.responseData; 
			if(tickerData == null) {
				showAlertModal("No ticker data found.");
			}
		},
		error: function(e) {
			hideProgress();
			commonError(constants.internalServerError);
		}
	}); 
	par.remove();
	
};

function uploadFile() {
	showProgress('uploading data.. please wait..');
	var URL;
	if (uploadtype == "user") {
		URL = constants.uploadUserUrl;
	} else if (uploadtype == "contract") {
		URL = constants.uploadContractUrl;
	} else if (uploadtype == "material") {
		URL = constants.uploadMaterialUrl;
	}
	else if (uploadtype == "vendor") {
		URL = constants.uploadVendorUrl;
	}
	
	var uploadFile = $('.uploadedFile')[0].files[0]
	if(uploadFile){
		if(uploadFile.size > 5242880) {
			hideProgress();
			showAlertModal(messages.msgFileSize);
			return false;
		}
	} else {
		hideProgress();
		showErrorModal(messages.msgFileNotFound);
		return false;
	}
	
	var data = new FormData();
	data.append('userId', sessionStorage.getItem("userId"));
	data.append('file', uploadFile);
	
	//creating header
		var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
		
	$.ajax({
		url: URL,
        data: data,
        cache: false,
        contentType: false,
        processData: false,
        type: 'POST',
		headers: headerParam,	
		success: function(data){
			hideProgress();
			console.log(data);
			var responseStatus = data.responseStatus;
			if(responseStatus.statusCode == constants.success)
			{
				showSuccessModal(messages.msgUploadSuccess);			
			}
			else
			{
				commonError(responseStatus.statusCode);
			}
			
		},
		error: function(e) { // if error occured
		hideProgress();
			commonError(constants.internalServerError);
		}
	});				
		
		
}

function findRegionId(data) {
	var regionId;
	for (var i = 0; i < resionData.dataList.length; i++) {
		if (resionData.dataList[i].regionName == data) {
			regionId = resionData.dataList[i].regionId;
			break;
		}
	}
	return regionId;
}

function goBackToPushNotification() {

	$('.displayNotificationDetailTable').addClass("displayNone");
	$('.notificationDetail').removeClass("displayNone");

}

function goBackToTicker() {

	$('.displayTickerDetailTable').addClass("displayNone");
	$('.tickerDetail').removeClass("displayNone");

}

function clearData() {
	configureAdminPage();
	$('.gsaType').addClass("activeBtn2");
	$('.gtaType').removeClass("activeBtn2");
	$('.bothType').removeClass("activeBtn2");
	nominationtype = "GSA";
	$('.singleSelectedDate').val("Single Date");
	$('.fristMulSelectedDate').val("From");
	$('.secondMulSelectedDate').val("To");
	$('.contractEndDate').val("End Date");
	
}
function clearTickerData(){
	
	$('.tickerText').val('');
	$('.startDate').val('Start Date');
	$('.endDate').val('End Date');
	$('#multipleRegions').val('');
	$('#multipleRegions').trigger('chosen:updated');
	$('#multiplePayers').val('');
	$('#multiplePayers').prop('disabled', true);
	$("#file-ticker").val('');
	$("label[for*='file-ticker']").html("Browse");
}

function clearNotificationData(){
	// debugger
	$('.pushNotificationText').val('');
	$('#multipleRegionsPushNotification').val('');
	$('#multipleRegionsPushNotification').trigger('chosen:updated');
	$('#multipleUsers').val('');
	$('#multipleUsers').prop('disabled', true);
	$("#multipleUsers").trigger("chosen:updated");
	$("#file-ticker").val('');
	$("label[for*='file-notification']").html("Browse");
}

function clearBrowse() {
	$('.box label span').text("Browse");
	$('#file-1').val("");
	$('#file_ticker').val("");
}

function clearDropDownData() {
	$('#multiplePayers').val('');
	$('#multiplePayers').prop('disabled', true);
	$("#multiplePayers").trigger("chosen:updated");
	payerId = [];
}

function clearNotificationDropDownData() {
	$('#multipleUsers').val('');
	$('#multipleUsers').prop('disabled', true);
	$("#multipleUsers").trigger("chosen:updated");
	payerId = [];
}

/*******************************************/
function uploadTickerFile(event) {
//	$('#file-ticker label span') = ($('.tickerUploadedFile')[0].files[0].name);
	uploadPayerId = [];
	 // debugger
	 showProgress('uploading data.. please wait..');
	 var URL;
	  URL = constants.uploadPayerId;
	
	 var uploadFile = $('.tickerUploadedFile')[0].files[0]
	 if(uploadFile){
	  if(uploadFile.size > 5242880) {
	   hideProgress();
	   showAlertModal(messages.msgFileSize);
	   return false;
	  }
	 } else {
	  hideProgress();
	  showErrorModal(messages.msgFileNotFound);
	  return false;
	 }
	 
	 var data = new FormData();
	 data.append('userId', sessionStorage.getItem("userId"));
	 data.append('file', uploadFile);
	 
	 //creating header
	  var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
	  
	 $.ajax({
	  url: URL,
	        data: data,
	        cache: false,
	        contentType: false,
	        processData: false,
	        type: 'POST',
	  headers: headerParam, 
	  success: function(data){
//		  debugger;
	   hideProgress();
	   console.log(data);
	   var responseStatus = data.responseStatus;
	   if(responseStatus.statusCode == constants.success)
	   {
		   uploadPayerId = data.responseData.dataList;
//		   debugger
		   $("label[for*='file-ticker']").html($('.tickerUploadedFile')[0].files[0].name);
	       showSuccessModal(messages.msgUploadSuccess);   
	   }
	   else
	   {
		   $("label[for*='file-ticker']").html("Browse");
	       commonError(responseStatus.statusCode);
	   }
	   
	  },
	  error: function(e) { // if error occured
	  hideProgress();
	   commonError(constants.internalServerError);
	  }
	 });    
	  
	  
	}

/*******************************************/
function uploadNotificationFile(event) {
//	$('#file-ticker label span') = ($('.tickerUploadedFile')[0].files[0].name);
	uploadPayerId = [];
	 // debugger
	 showProgress('uploading data.. please wait..');
	 var URL;
	  URL = constants.uploadPayerId;
	
	 var uploadFile = $('.notificationUploadedFile')[0].files[0]
	 if(uploadFile){
	  if(uploadFile.size > 5242880) {
	   hideProgress();
	   showAlertModal(messages.msgFileSize);
	   return false;
	  }
	 } else {
	  hideProgress();
	  showErrorModal(messages.msgFileNotFound);
	  return false;
	 }
	 
	 var data = new FormData();
	 data.append('userId', sessionStorage.getItem("userId"));
	 data.append('file', uploadFile);
	 
	 //creating header
	  var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
	  
	 $.ajax({
	  url: URL,
	        data: data,
	        cache: false,
	        contentType: false,
	        processData: false,
	        type: 'POST',
	  headers: headerParam, 
	  success: function(data){
//		  debugger;
	   hideProgress();
	   console.log(data);
	   var responseStatus = data.responseStatus;
	   if(responseStatus.statusCode == constants.success)
	   {
		   uploadPayerId = data.responseData.dataList;
//		   debugger
		   $("label[for*='file-notification']").html($('.notificationUploadedFile')[0].files[0].name);
	    showSuccessModal(messages.msgUploadSuccess);   
	   }
	   else
	   {
		   $("label[for*='file-notification']").html("Browse");
	    commonError(responseStatus.statusCode);
	   }
	   
	  },
	  error: function(e) { // if error occured
	  hideProgress();
	   commonError(constants.internalServerError);
	  }
	 });    
	  
	  
	}










