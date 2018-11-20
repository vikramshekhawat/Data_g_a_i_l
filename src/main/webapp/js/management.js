var contractData;
var contractAndNominationData;
var sellerData;
var nominationData;
var nominationType;
var isSaveNomination = false;
var isSaveSellerRedelPoint = false;
var rowIndex;
var contactRefrenceGlobalVar;
var contractId;
var nominationId;
var editClientUserId;
var nominationUpdateData = [];
var nominationSaveData = [];
var sessionStorageData;

$(document).ready(function() {
	
localStorage.setItem('login','false');
sessionStorageData = JSON.parse(sessionStorage.getItem(''));

   getVendorStatus();



});

 function getVendorStatus(){
	
	var sessionStorageData = JSON.parse(sessionStorage.getItem(''));
	var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
	
	let vendorData = {
			userId:sessionStorageData.userId,
				deviceToken:sessionStorageData.token,
				vendorStatus:sessionStorageData.vendorStatus
	}

	
 $.ajax({
									type : "Post",
									url : constants.getVendorStatusUrl,
									headers : headerParam,
									data: JSON.stringify(vendorData),		
									dataType: "json",
									crossDomain: true,
									contentType: 'application/json',

			success : function(data) {

				var responseStatus = data.responseStatus;
				var responseData = data.responseData;
				if (responseStatus.statusCode == constants.success) {
					
					if (responseData.vendorStatus == true) {
					    $('#SellerTab').show();
					} else {
					    $('#SellerTab').hide();
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


$(document).on('click','.gsa',function(){
		 $('.selectedType').val("GSA");
});
$(document).on('click','.gta',function(){
		 $('.selectedType').val("GTA");
});
$(document).on('click','.gta1',function(){
		 $('.dinplayselectedType').val("GTA");
});
$(document).on('click','.gsa1',function(){
		  $('.dinplayselectedType').val("GSA");
});
$(document).on('click','.both',function(){
	  $('.dinplayselectedType').val("Both");
});

/*  data ajax */

function gerContractData(contractType) {
	//creating header
	var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
	$('.contractDropdown').empty();
	contractData = null; 
	$.ajax({
		type : "GET",
		url : constants.getContractsByPayerIdUrl + sessionStorage.getItem('userId') + '/' + contractType,
		dataType : "json",
		headers : headerParam,
		crossDomain : true,
		success : function(data) {
//			hideProgress();
			var responseStatus = data.responseStatus;
			if (responseStatus.statusCode == constants.success) {
				$('.contractDropdown').append('<option > All</option>');
				contractData = data.responseData;
				for (var i = 0; i < contractData.contractsDataList.length; i++) {
					$('.contractDropdown').append('<option >' + contractData.contractsDataList[i].contractRef + '</option>');
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

/* Agax request for select payerId corresponding to regionId */

//function getRegionIdPayerId(regionType) {
//	//creating header
//	var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
//		debugger;
//	$('.chosen-search-input').empty();
//	payerData = null; 
//	$.ajax({
//		type : "GET",
//		url : constants.getContractsByPayerIdUrl + sessionStorage.getItem('userId') + '/' + regionId,
//		dataType : "json",
//		headers : headerParam,
//		crossDomain : true,
//		success : function(data) {
////			hideProgress();
//			var responseStatus = data.responseStatus;
//			if (responseStatus.statusCode == constants.success) {
//				$('.chosen-search-input').append('<option > All</option>');
//				payerData = data.responseData;
//				for (var i = 0; i < payerData.contractsDataList.length; i++) {
//					$('.chosen-search-input').append('<option >' + payerData.contractsDataList[i].contractRef + '</option>');
//				}
//			} else {
//				commonError(responseStatus.statusCode);
//			}
//		},
//		error : function(e) {
//			hideProgress();
//			commonError(constants.internalServerError);
//		}
//	});
//}
//
//





function gerContractData(contractType) {
	//creating header
	var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
		
	$('.contractDropdown').empty();
	contractData = null; 
	$.ajax({
		type : "GET",
		url : constants.getContractsByPayerIdUrl + sessionStorage.getItem('userId') + '/' + contractType,
		dataType : "json",
		headers : headerParam,
		crossDomain : true,
		success : function(data) {
//			hideProgress();
			var responseStatus = data.responseStatus;
			if (responseStatus.statusCode == constants.success) {
				$('.contractDropdown').append('<option > All</option>');
				contractData = data.responseData;
				for (var i = 0; i < contractData.contractsDataList.length; i++) {
					$('.contractDropdown').append('<option >' + contractData.contractsDataList[i].contractRef + '</option>');
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

function configureManagementPage() {
	if(sessionStorage.getItem('userId')== null && sessionStorage.getItem('userId') == undefined)
		{
			location.replace('sign-in.html');
		}
	$('.loginUserName').html(sessionStorage.getItem("payerName"));
	$('.gtaName').html(sessionStorage.getItem("payerName"));
	$('.comName').html(sessionStorage.getItem("payerName"));
}

function goToSelectNominationDetail() {
	$('.nominationDetailTable').addClass( "displayNone");
	$('.selectNominationDetail').removeClass("displayNone");
	$('.gtaNominationDetailTable').addClass("displayNone");
	$('.gsaNominationDetailTable').addClass("displayNone");
}

/*For nominations*/
function getContractsOrNominationsUrl()
{
	// Get values
	var nominationDate = dateFromater($('.nominationDate').val().trim());
	var nominationDate2 = dateFromater2($('.nominationDate').val().trim());
	 nominationType = $('.selectedType').val().trim();
	
	//clear the table data
	$('.gtaDataTable').empty();
	$('.gsaDataTable').empty();
	$('.gtaDate span').html(nominationDate2);
	
	// Validation function call commom.js
	var validationResult = nominationValidation(nominationDate, nominationType);
	if(validationResult)
	{	
		showProgress('processing data.. please wait..');
		var formData = {};
		formData['userId'] = sessionStorage.getItem("userId");
		formData['payerId'] = sessionStorage.getItem("payerId");
		formData['gasDate'] = nominationDate;
		formData['contractType'] = nominationType;
			
		var finalData = JSON.stringify(formData);
		//creating header
		var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
				
		$.ajax({
			type: "POST",
			url: constants.getContractsOrNominationsUrl,       
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
					if(data.responseData.nominationData.length > 0)
					{
						contractAndNominationData = data.responseData; 
						if(contractAndNominationData.nominationData[0].redelPoint == "" || contractAndNominationData.nominationData[0].redelPoint == null || contractAndNominationData.nominationData[0].redelPoint == undefined)
						{
							isSaveNomination = true;
						}
							
						if(nominationType == "GSA" ) 
						{
							$('.gsaDataTable').append(htmlConstants.gsaDataHeader)
							$('.gsaNominationDetailTable').removeClass( "displayNone");
							$('.gtaNominationDetailTable').addClass("displayNone");
							$('.selectNominationDetail').addClass("displayNone");
							
							for(var i=0;i<contractAndNominationData.nominationData.length;i++) {
								
									$('.gsaDataTable').append(htmlConstants.gsaDataRow);
									var dlist = $('.gsaDataTable').find('.gsaDataRowParent')[i];
									$(dlist).find('.contactRefrence').html(contractAndNominationData.nominationData[i].contractRef + htmlConstants.contractValidityInfo2);
									
									if(contractAndNominationData.nominationData[i].contractName == null || contractAndNominationData.nominationData[i].contractName == ''){
										$(dlist).find('.unEditcontactName').html(htmlConstants.addcontractName);
									} else {
										var append  = contractAndNominationData.nominationData[i].contractName.length > 15 ? '....' : '' ;
										// debugger
										$(dlist).find('.unEditcontactName').html((contractAndNominationData.nominationData[i].contractName).slice(0,15) + append + htmlConstants.contractNameInfo2 + htmlConstants.editcontractName);
									}
									// $(dlist).find('.contactName').val(contractAndNominationData.nominationData[i].contractName);
									$(dlist).find('.materialCode').html(contractAndNominationData.nominationData[i].materialCode + htmlConstants.materialDescInfo);
									$(dlist).find('.customerCode').html(contractAndNominationData.nominationData[i].customerCode + htmlConstants.customerDescInfo);
									$(dlist).find('.redelPoint').val(contractAndNominationData.nominationData[i].redelPoint);
									$(dlist).find('.uom').html(contractAndNominationData.nominationData[i].unitOfMeasurements);
									$(dlist).find('.customerDesc').html(contractAndNominationData.nominationData[i].customerDescription);
									$(dlist).find('.materialDesc').html(contractAndNominationData.nominationData[i].materialDescription);
									$(dlist).find('.contractValidity').html('Start Date:' + contractAndNominationData.nominationData[i].startDate + '<br/>' +'End Date:' +contractAndNominationData.nominationData[i].endDate);
									$(dlist).find('.contractId').html(contractAndNominationData.nominationData[i].contractId);
									if(contractAndNominationData.nominationData[i].nominationId)
									{
										$(dlist).find('.nominationId').html(contractAndNominationData.nominationData[i].nominationId);	
										$(dlist).find('.revesionNo').html(contractAndNominationData.nominationData[i].revisionNo);	
									}
							}	
							$('.gsaDataTable').append('</tbody>');
						}
						else {
							$('.gtaDataTable').append(htmlConstants.gtaDataHeader)
							$('.gtaNominationDetailTable').removeClass( "displayNone");
							$('.gsaNominationDetailTable').addClass("displayNone");
							$('.selectNominationDetail').addClass("displayNone");
							
							for(var i=0;i<contractAndNominationData.nominationData.length;i++) {
							$('.gtaUOM').text("UOM: "+contractAndNominationData.nominationData[0].unitOfMeasurements);	
									$('.gtaDataTable').append(htmlConstants.gtaDataRow);
									var dlist = $('.gtaDataTable').find('.gtaDataRowParent')[i];
									$(dlist).find('.contactRefrence').html(contractAndNominationData.nominationData[i].contractRef + htmlConstants.contractValidityInfo2);
									if(contractAndNominationData.nominationData[i].contractName == null || contractAndNominationData.nominationData[i].contractName == ''){
										$(dlist).find('.unEditcontactName').html(htmlConstants.addcontractName);
									} else {
										// debugger
										var append  = contractAndNominationData.nominationData[i].contractName.length > 15 ? '....' : '' ;
										$(dlist).find('.unEditcontactName').html((contractAndNominationData.nominationData[i].contractName).slice(0,15) + append + htmlConstants.contractNameInfo2 + htmlConstants.editcontractName);
									}
								//	let disableStatus = contractAndNominationData.nominationData[i].seller_redel_point ? true : false;
									
									// $(dlist).find('.contactName').val(contractAndNominationData.nominationData[i].contractName);
									$(dlist).find('.materialCode').html(contractAndNominationData.nominationData[i].materialCode + htmlConstants.materialDescInfo);
									$(dlist).find('.customerCode').html(contractAndNominationData.nominationData[i].customerCode + htmlConstants.customerDescInfo);
									$(dlist).find('.redelPoint').val(contractAndNominationData.nominationData[i].redelPoint);
									$(dlist).find('.delPoint').val(contractAndNominationData.nominationData[i].delPoint);
									$(dlist).find('.customerDesc').html(contractAndNominationData.nominationData[i].customerDescription);
									$(dlist).find('.materialDesc').html(contractAndNominationData.nominationData[i].materialDescription);
									$(dlist).find('.contractValidity').html('Start Date:' + contractAndNominationData.nominationData[i].startDate + '<br/>' +'End Date:' +contractAndNominationData.nominationData[i].endDate);
									$(dlist).find('.contractId').html(contractAndNominationData.nominationData[i].contractId);
									$(dlist).find('.sellerRedelPoint').html(contractAndNominationData.nominationData[i].seller_redel_point);
									$(dlist).find('.sellerRedelPoint').val(contractAndNominationData.nominationData[i].seller_redel_point);
									$(dlist).find('.sellerUpdateDateClass').html(contractAndNominationData.nominationData[i].seller_updated_time);
									if(contractAndNominationData.nominationData[i].nominationId)
									{
										$(dlist).find('.nominationId').html(contractAndNominationData.nominationData[i].nominationId);	
										$(dlist).find('.revesionNo').html(contractAndNominationData.nominationData[i].revisionNo);	
									}
							}
							$('.gtaDataTable').append('</tbody>');	
						}
					} else {
						showAlertModal("No contract data found.");
					}
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




// For seller
function getContractsSeller()
{
	// Get values
	var nominationDate = dateFromater($('.sellerDate').val().trim());
	var nominationDate1 = dateFromater2($('.sellerDate').val().trim());
	$('.sellerDate span').html(nominationDate1);
	nominationType = "GTA";
	$('.gtasellerDataTable').empty();
	
	// Validation function call commom.js
	var validationResult = nominationValidation(nominationDate, nominationType);
	if(validationResult)
	{	
		showProgress('processing data.. please wait..');
		var formData = {};
		formData['payerId'] = JSON.parse(sessionStorage.getItem("")).userName;
		formData['gasDate'] = nominationDate;
		formData['contractType'] = "GTA";
			
		var finalData = JSON.stringify(formData);
		//creating header
		var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
				
		$.ajax({
			type: "POST",
			url: constants.getcontractsforsellerUrl,       
			data: finalData,
			headers: headerParam,			
			dataType: "json",
			crossDomain: true,
			contentType: 'application/json',
			success: function(data){
				hideProgress();
				$('.gtasellerDataTable').append(htmlConstants.sellergtaDataHeader);
				$('.gtaSellerDetailTable').removeClass("displayNone");
				$('.sellerselectNominationDetail').addClass("displayNone");
//				$('.gtasellerDataTable').append(htmlConstants.sellergtaDataHeader)
				if(data.responseData) {
					if(data.responseData.nominationData.length > 0) {
						  sellerData = data.responseData;
						}
				

				for(var i=0;i<sellerData.nominationData.length;i++) {
					$('.gtasellerDataTable').append(htmlConstants.sellergtaDataRow);
					var dlist = $('.gtasellerDataTable').find('.gtaDataRowParent')[i];
						$(dlist).find('.contactRefrence').html(sellerData.nominationData[i].contractRef + htmlConstants.contractValidityInfo2);
//						$(dlist).find('.unEditcontactName').html(nominationData.nominationData[i].contractName + htmlConstants.contractNameInfo);
						$(dlist).find('.materialCode').html(sellerData.nominationData[i].materialCode + htmlConstants.materialDescInfo);
						$(dlist).find('.unEditcontactName').html(sellerData.nominationData[i].customerCode + htmlConstants.customerDescInfo);
						$(dlist).find('.customerPayerId').html(sellerData.nominationData[i].payerId + htmlConstants.customerDescInfo);
						
//						$(dlist).find('.redelPoint').val(sellerData.nominationData[i].seller_redel_point);

						if(sellerData.nominationData[i].seller_redel_point == null || sellerData.nominationData[i].seller_redel_point == ''){
							$(dlist).find('.unEditredelPoint ').html(htmlConstants.addSellerRedelPointButton);
						} else {
							$(dlist).find('.unEditredelPoint ').html(sellerData.nominationData[i].seller_redel_point   + htmlConstants.editSellerRedelPoint);
							
						}
						$(dlist).find('.clientUserId').html(sellerData.nominationData[i].clientUserId);
						$(dlist).find('.unEditdelPoint').html(sellerData.nominationData[i].delPoint);
						$(dlist).find('.unEditdelPoint').html(sellerData.nominationData[i].delPoint);
						$(dlist).find('.materialDesc').html(sellerData.nominationData[i].materialDescription);
						$(dlist).find('.contractValidity').html('Start Date:' + sellerData.nominationData[i].startDate + '<br/>' +'End Date:' +sellerData.nominationData[i].endDate);
						$(dlist).find('.contractId').html(sellerData.nominationData[i].contractId);
						if(!isSaveSellerRedelPoint) {													
							$(dlist).find('.nominationId').html(sellerData.nominationData[i].nominationId);	
							$(dlist).find('.revesionNo').html(sellerData.nominationData[i].revisionNo);	
						}
				}	
				
			}
				$('gtasellerDataTable').append('</tbody>');

			} 
		})
		
	}			
}

//go back to seller detail
function goToSellerMainPage() {
	$('.gtaSellerDetailTable').addClass( "displayNone");
	$('.sellerselectNominationDetail').removeClass("displayNone");
}

/*get data for display*/	
/*get data for display*/	
function getNominationsData() {
	
	// Get values
	var nominationDate = dateFromater($('.singleSelectedDate').val().trim());
	var startDate = dateFromater($('.fristMulSelectedDate').val().trim());
	var endDate = dateFromater($('.secondMulSelectedDate').val().trim());
	nominationType = $('.dinplayselectedType').val().trim();
	var validationResult;
	//clear the table data
	$('.gsaDataDisplayTable').empty();
	$('.gtaDataDisplayTable').empty();
	$('.bothDataDisplayTable').empty();
	
	if($(".SingleDate").is(":checked")){
		validationResult = nominationValidation(nominationDate, nominationType);
		$('.gtaDate span').html(nominationDate);
		$('.gtaDate').show();
	} else if($(".MultipleDate").is(":checked")) {
		$('.gtaDate span').html(nominationDate);
		$('.gtaDate').hide();
		if (!startDate || startDate == '--' || includes(startDate,'Fr')) {
			showAlertModal(messages.msgEnterNominationDate);
			validationResult = false;
			
		} else if (!endDate || endDate == '--' || includes(endDate,'To')) {
			showAlertModal(messages.msgEnterNominationDate);
			validationResult = false;
			
		} else if (!nominationType || nominationType == '-Please Select-') {
			showAlertModal(messages.msgEnterNominationType);
			validationResult = false;
			
		} else {
			validationResult = true;
		}
	}
		
	// Validation function call commom.js
	
	if(validationResult)
	{	
		showProgress('getting data.. please wait..');
		var formData = {};
		formData['userId'] = sessionStorage.getItem("userId");
		if(nominationType == 'GSA' || nominationType == 'GTA')
			formData['contractType'] = nominationType;
		else
			formData['contractType'] = "GSA','GTA";
		
		if($(".SingleDate").is(":checked"))
		{
			formData['gasDate'] = nominationDate;
		}
		if($(".MultipleDate").is(":checked"))
		{
			formData['gasDate'] = null;
			formData['startDate'] = dateFromater($('.fristMulSelectedDate').val().trim());
			formData['endDate'] = dateFromater($('.secondMulSelectedDate').val().trim());
		}
		
		if ($('.contractDropdown').val() == "All") {
			formData['contractRef'] = null;
		} else {
			var contractRef = $('.contractDropdown').val();
			formData['contractRef'] = contractRef;
		}
		
		var finalData = JSON.stringify(formData);
		console.log("finalData" +finalData);
		//creating header
		var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
		
		$.ajax({
			type: "POST",
			url: constants.getNominationDetailUrl,       
			data: finalData, 
            headers: headerParam,			
			dataType: "json",
			crossDomain: true,
			contentType: 'application/json',
			success: function(data){
				hideProgress();
				var responseStatus = data.responseStatus;
				if(responseStatus.statusCode == constants.success && $(".SingleDate").is(":checked"))
				{
					nominationData = data.responseData; 
					if(nominationType == "GSA") 
					{
						$('.gsaDataDisplayTable').append(htmlConstants.gsaDataHeader)
						$('.displayGsaNominationDetailTable').removeClass( "displayNone");
						$('.displayNominationDetail').addClass("displayNone");
						if(nominationData == null) {
							showAlertModal("No nomination data found.");
						} else {
							for(var i=0;i<nominationData.nominationData.length;i++) {
								$('.gsaDataDisplayTable').append(htmlConstants.gsaDataRow);
								var dlist = $('.gsaDataDisplayTable').find('.gsaDataRowParent')[i];
								$(dlist).find('.contactRefrence').html(nominationData.nominationData[i].contractRef);
								// $(dlist).find('.unEditcontactName').html(nominationData.nominationData[i].contractName + htmlConstants.contractNameInfo);
								if(nominationData.nominationData[i].contractName == null || nominationData.nominationData[i].contractName == ''){
									$(dlist).find('.unEditcontactName').html(nominationData.nominationData[i].contractName);
								} else {
									var append  = nominationData.nominationData[i].contractName.length > 15 ? '....' : '' ;
									$(dlist).find('.unEditcontactName').html(nominationData.nominationData[i].contractName.slice(0,15) + append + htmlConstants.contractNameInfo);
								}
								$(dlist).find('.materialCode').html(nominationData.nominationData[i].materialCode + htmlConstants.materialDescInfo);
								$(dlist).find('.customerCode').html(nominationData.nominationData[i].customerCode + htmlConstants.customerDescInfo);
								$(dlist).find('.unEditredelPoint').html(nominationData.nominationData[i].redelPoint + '<span class="displayNone customerDesc"></span><span class="displayNone materialDesc"></span><span class="displayNone contractId"></span>');
								$(dlist).find('.uom').html(nominationData.nominationData[i].unitOfMeasurements);
								$(dlist).find('.customerDesc').html(nominationData.nominationData[i].customerDescription);
								$(dlist).find('.materialDesc').html(nominationData.nominationData[i].materialDescription);
								if(contractAndNominationData != null)
									$(dlist).find('.contractValidity').html('Start Date:' + contractAndNominationData.nominationData[i].startDate + '<br/>' +'End Date:' +contractAndNominationData.nominationData[i].endDate);
								$(dlist).find('.contractId').html(nominationData.nominationData[i].contractId);
							}
							$('.gsaDataDisplayTable').append('</tbody>');
					   }
					}
					else if(nominationType == "GTA") {
						$('.gtaDataDisplayTable').append(htmlConstants.gtaDataHeader)
						$('.displayGtaNominationDetailTable').removeClass( "displayNone");
						$('.displayNominationDetail').addClass("displayNone");
						
						if(nominationData == null)
						{
							showAlertModal("No nomination data found.");
						} else {
						for(var i=0;i<nominationData.nominationData.length;i++) {
								$('.gtaUOM').text("UOM: "+nominationData.nominationData[0].unitOfMeasurements);	
								$('.gtaDataDisplayTable').append(htmlConstants.gtaDataRow);
								var dlist = $('.gtaDataDisplayTable').find('.gtaDataRowParent')[i];
								$(dlist).find('.contactRefrence').html(nominationData.nominationData[i].contractRef);
								if(nominationData.nominationData[i].contractName == null || nominationData.nominationData[i].contractName == ''){
									$(dlist).find('.unEditcontactName').html(nominationData.nominationData[i].contractName);
								} else {
									var append  = nominationData.nominationData[i].contractName.length > 15 ? '....' : '' ;
									$(dlist).find('.unEditcontactName').html(nominationData.nominationData[i].contractName.slice(0,15) + append + htmlConstants.contractNameInfo);
								}
								// $(dlist).find('.unEditcontactName').html(nominationData.nominationData[i].contractName + htmlConstants.contractNameInfo);
								$(dlist).find('.materialCode').html(nominationData.nominationData[i].materialCode + htmlConstants.materialDescInfo);
								$(dlist).find('.customerCode').html(nominationData.nominationData[i].customerCode + htmlConstants.customerDescInfo);
								$(dlist).find('.unEditredelPoint').html(nominationData.nominationData[i].redelPoint);
								$(dlist).find('.unEditdelPoint').html(nominationData.nominationData[i].delPoint + '<span class="displayNone customerDesc"></span><span class="displayNone materialDesc"></span><span class="displayNone contractId"></span>');
								$(dlist).find('.customerDesc').html(nominationData.nominationData[i].customerDescription);
								$(dlist).find('.materialDesc').html(nominationData.nominationData[i].materialDescription);
								$(dlist).find('.sellerRedelPoint').html(nominationData.nominationData[i].seller_redel_point);
								$(dlist).find('.sellerUpdateDateClass').html(nominationData.nominationData[i].seller_updated_time);
								
								if(contractAndNominationData != null)
									$(dlist).find('.contractValidity').html('Start Date:' + contractAndNominationData.nominationData[i].startDate + '<br/>' +'End Date:' +contractAndNominationData.nominationData[i].endDate);
								$(dlist).find('.contractId').html(nominationData.nominationData[i].contractId);
						}	
						$('.gtaDataDisplayTable').append('</tbody>');
					  }
					} else {
						$('.bothDataDisplayTable').append(htmlConstants.bothDataHeader)
						$('.displayBothNominationDetailTable').removeClass( "displayNone");
						$('.displayNominationDetail').addClass("displayNone");
						if(nominationData == null)
						{
							showAlertModal("No nomination data found.");
						} else {
						for(var i=0;i<nominationData.nominationData.length;i++) {
						$('.gtaUOM').text("UOM: "+nominationData.nominationData[0].unitOfMeasurements);	
								$('.bothDataDisplayTable').append(htmlConstants.bothDataRow);
								var dlist = $('.bothDataDisplayTable').find('.bothDataRowParent')[i];
								$(dlist).find('.contactRefrence').html(nominationData.nominationData[i].contractRef);
								if(nominationData.nominationData[i].contractName == null || nominationData.nominationData[i].contractName == ''){
									$(dlist).find('.unEditcontactName').html(nominationData.nominationData[i].contractName);
								} else {
									var append  = nominationData.nominationData[i].contractName.length > 15 ? '....' : '' ;
									$(dlist).find('.unEditcontactName').html(nominationData.nominationData[i].contractName.slice(0,15) + append + htmlConstants.contractNameInfo);
								}
								// $(dlist).find('.unEditcontactName').html(nominationData.nominationData[i].contractName + htmlConstants.contractNameInfo);
								$(dlist).find('.materialCode').html(nominationData.nominationData[i].materialCode + htmlConstants.materialDescInfo);
								$(dlist).find('.customerCode').html(nominationData.nominationData[i].customerCode + htmlConstants.customerDescInfo);
								$(dlist).find('.unEditredelPoint').html(nominationData.nominationData[i].redelPoint);
								$(dlist).find('.unEditdelPoint').html(nominationData.nominationData[i].delPoint + '<span class="displayNone customerDesc"></span><span class="displayNone materialDesc"></span><span class="displayNone contractId"></span>');
								$(dlist).find('.customerDesc').html(nominationData.nominationData[i].customerDescription);
								$(dlist).find('.materialDesc').html(nominationData.nominationData[i].materialDescription);
								$(dlist).find('.sellerRedelPoint').html(nominationData.nominationData[i].seller_redel_point);
								if(contractAndNominationData != null)
									$(dlist).find('.contractValidity').html('Start Date:' + contractAndNominationData.nominationData[i].startDate + '<br/>' +'End Date:' +contractAndNominationData.nominationData[i].endDate);
								$(dlist).find('.contractId').html(nominationData.nominationData[i].contractId);
								$(dlist).find('.uom').html(nominationData.nominationData[i].unitOfMeasurements);
						}	
						$('.bothDataDisplayTable').append('</tbody>');
					  }
					}
				} else if(responseStatus.statusCode == constants.success && $(".MultipleDate").is(":checked"))
					{
						nominationData = data.responseData; 
						console.log(JSON.stringify(nominationData.nominationData));
						if(nominationType == "GSA") 
						{
							$('.gsaDataDisplayTable').append(htmlConstants.mulGsaDataHeader)
							$('.displayGsaNominationDetailTable').removeClass( "displayNone");
							$('.displayNominationDetail').addClass("displayNone");
							if(nominationData == null) {
								showAlertModal("No nomination data found.");
							} else {
								for(var i=0;i<nominationData.nominationData.length;i++) {
									$('.gsaDataDisplayTable').append(htmlConstants.mulGsaDataRow);
									var dlist = $('.gsaDataDisplayTable').find('.gsaDataRowParent')[i];
									$(dlist).find('.contactRefrence').html(nominationData.nominationData[i].contractRef);
									// $(dlist).find('.unEditcontactName').html(nominationData.nominationData[i].contractName + htmlConstants.contractNameInfo);
									if(nominationData.nominationData[i].contractName == null || nominationData.nominationData[i].contractName == ''){
									$(dlist).find('.unEditcontactName').html(nominationData.nominationData[i].contractName);
									} else {
										var append  = nominationData.nominationData[i].contractName.length > 15 ? '....' : '' ;
									    $(dlist).find('.unEditcontactName').html(nominationData.nominationData[i].contractName.slice(0,15) + append + htmlConstants.contractNameInfo);
									}
									$(dlist).find('.materialCode').html(nominationData.nominationData[i].materialCode + htmlConstants.materialDescInfo);
									$(dlist).find('.customerCode').html(nominationData.nominationData[i].customerCode + htmlConstants.customerDescInfo);
									$(dlist).find('.gasDate').html(nominationData.nominationData[i].gasDate);
									$(dlist).find('.unEditredelPoint').html(nominationData.nominationData[i].redelPoint + '<span class="displayNone customerDesc"></span><span class="displayNone materialDesc"></span><span class="displayNone contractId"></span>');
									$(dlist).find('.uom').html(nominationData.nominationData[i].unitOfMeasurements);
									$(dlist).find('.customerDesc').html(nominationData.nominationData[i].customerDescription);
									$(dlist).find('.materialDesc').html(nominationData.nominationData[i].materialDescription);
									if(contractAndNominationData != null)
										$(dlist).find('.contractValidity').html('Start Date:' + contractAndNominationData.nominationData[i].startDate + '<br/>' +'End Date:' +contractAndNominationData.nominationData[i].endDate);
									$(dlist).find('.contractId').html(nominationData.nominationData[i].contractId);
								}
								$('.gsaDataDisplayTable').append('</tbody>');
						   }
						}
						else if(nominationType == "GTA") {
							$('.gtaDataDisplayTable').append(htmlConstants.mulGtaDataHeader)
							$('.displayGtaNominationDetailTable').removeClass( "displayNone");
							$('.displayNominationDetail').addClass("displayNone");
							if(nominationData == null)
							{
								showAlertModal("No nomination data found.");
							} else {
							for(var i=0;i<nominationData.nominationData.length;i++) {
									$('.gtaUOM').text("UOM: "+nominationData.nominationData[0].unitOfMeasurements);	
									$('.gtaDataDisplayTable').append(htmlConstants.mulGtaDataRow);
									var dlist = $('.gtaDataDisplayTable').find('.gtaDataRowParent')[i];
									$(dlist).find('.contactRefrence').html(nominationData.nominationData[i].contractRef);
									if(nominationData.nominationData[i].contractName == null || nominationData.nominationData[i].contractName == ''){
									$(dlist).find('.unEditcontactName').html(nominationData.nominationData[i].contractName);
									} else {
										var append  = nominationData.nominationData[i].contractName.length > 15 ? '....' : '' ;
									    $(dlist).find('.unEditcontactName').html(nominationData.nominationData[i].contractName.slice(0,15) + append + htmlConstants.contractNameInfo);
									}
									// $(dlist).find('.unEditcontactName').html(nominationData.nominationData[i].contractName + htmlConstants.contractNameInfo);
									$(dlist).find('.materialCode').html(nominationData.nominationData[i].materialCode + htmlConstants.materialDescInfo);
									$(dlist).find('.customerCode').html(nominationData.nominationData[i].customerCode + htmlConstants.customerDescInfo);
									$(dlist).find('.gasDate').html(nominationData.nominationData[i].gasDate);
									$(dlist).find('.unEditredelPoint').html(nominationData.nominationData[i].redelPoint);
								  	$(dlist).find('.unEditdelPoint').html(nominationData.nominationData[i].delPoint + '<span class="displayNone customerDesc"></span><span class="displayNone materialDesc"></span><span class="displayNone contractId"></span>');
									$(dlist).find('.customerDesc').html(nominationData.nominationData[i].customerDescription);
									$(dlist).find('.materialDesc').html(nominationData.nominationData[i].materialDescription);
									$(dlist).find('.sellerRedelPoint').html(nominationData.nominationData[i].seller_redel_point);
									if(contractAndNominationData != null)
										$(dlist).find('.contractValidity').html('Start Date:' + contractAndNominationData.nominationData[i].startDate + '<br/>' +'End Date:' +contractAndNominationData.nominationData[i].endDate);
									$(dlist).find('.contractId').html(nominationData.nominationData[i].contractId);
							}	
							$('.gtaDataDisplayTable').append('</tbody>');
						  }
						} else {
							$('.bothDataDisplayTable').append(htmlConstants.mulBothDataHeader)
							$('.displayBothNominationDetailTable').removeClass( "displayNone");
							$('.displayNominationDetail').addClass("displayNone");
							if(nominationData == null)
							{
								showAlertModal("No nomination data found.");
							} else {
							for(var i=0;i<nominationData.nominationData.length;i++) {
							$('.gtaUOM').text("UOM: "+nominationData.nominationData[0].unitOfMeasurements);	
									$('.bothDataDisplayTable').append(htmlConstants.mulBothDataRow);
									var dlist = $('.bothDataDisplayTable').find('.bothDataRowParent')[i];
									$(dlist).find('.contactRefrence').html(nominationData.nominationData[i].contractRef);
									// $(dlist).find('.unEditcontactName').html(nominationData.nominationData[i].contractName + htmlConstants.contractNameInfo);
									if(nominationData.nominationData[i].contractName == null || nominationData.nominationData[i].contractName == ''){
										$(dlist).find('.unEditcontactName').html(nominationData.nominationData[i].contractName);
									} else {
										var append  = nominationData.nominationData[i].contractName.length > 15 ? '....' : '' ;
									$(dlist).find('.unEditcontactName').html(nominationData.nominationData[i].contractName.slice(0,15) + append + htmlConstants.contractNameInfo);
									}
									$(dlist).find('.materialCode').html(nominationData.nominationData[i].materialCode + htmlConstants.materialDescInfo);
									$(dlist).find('.customerCode').html(nominationData.nominationData[i].customerCode + htmlConstants.customerDescInfo);
									$(dlist).find('.gasDate').html(nominationData.nominationData[i].gasDate);
									$(dlist).find('.unEditredelPoint').html(nominationData.nominationData[i].redelPoint);
									$(dlist).find('.unEditdelPoint').html(nominationData.nominationData[i].delPoint + '<span class="displayNone customerDesc"></span><span class="displayNone materialDesc"></span><span class="displayNone contractId"></span>');
									$(dlist).find('.customerDesc').html(nominationData.nominationData[i].customerDescription);
									$(dlist).find('.materialDesc').html(nominationData.nominationData[i].materialDescription);
									$(dlist).find('.sellerRedelPoint').html(nominationData.nominationData[i].seller_redel_point);
									if(contractAndNominationData != null)
										$(dlist).find('.contractValidity').html('Start Date:' + contractAndNominationData.nominationData[i].startDate + '<br/>' +'End Date:' +contractAndNominationData.nominationData[i].endDate);
									$(dlist).find('.contractId').html(nominationData.nominationData[i].contractId);
									$(dlist).find('.uom').html(nominationData.nominationData[i].unitOfMeasurements);
							}	
							$('.bothDataDisplayTable').append('</tbody>');
						  }
						}
					}
				else {
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


function goBackSelectDateInDisplay() {

	$('.displayGsaNominationDetailTable').addClass("displayNone");
	$('.displayNominationDetail').removeClass("displayNone");
	$('.displayGtaNominationDetailTable').addClass("displayNone");
	$('.displayBothNominationDetailTable').addClass("displayNone");

}	


function showMaterialDesc(object)
{
	var materialDesc;
	if(nominationType == "GSA" ) 
	{
		materialDesc=($($(object).closest('.gsaDataRowParent')).find('.materialDesc').text());
	}
	else if(nominationType == "GTA" )
	{
		materialDesc=($($(object).closest('.gtaDataRowParent')).find('.materialDesc').text());
	}
	else
		materialDesc=($($(object).closest('.bothDataRowParent')).find('.materialDesc').text());
	showMaterialDescModel(materialDesc);
	return;
}

function showContractValidity(object)
{
	var contractValidity;
	if(nominationType == "GSA" ) 
	{
		contractValidity=($($(object).closest('.gsaDataRowParent')).find('.contractValidity').html());
	}
	else if(nominationType == "GTA" )
	{

		contractValidity=($($(object).closest('.gtaDataRowParent')).find('.customerPayerId ').html());
	}
	else
		contractValidity=($($(object).closest('.bothDataRowParent')).find('.contractValidity').html());
	showContractRefModel(contractValidity);
	return;
}

function showContractName(object)
{
	var contractName;
	if(nominationType == "GSA" ) 
	{
		contractName=($($(object).closest('.gsaDataRowParent')).find('.unEditcontactName').html());
	}
	else if(nominationType == "GTA" )
	{
		contractName=($($(object).closest('.gtaDataRowParent')).find('.unEditcontactName').html());
	}
	else
		contractName=($($(object).closest('.bothDataRowParent')).find('.unEditcontactName').html());
	showContractRefModel(contractName);
	return;
}

function showContractName(object)
{
	var contractName;
	if(nominationType == "GSA" ) 
	{
		contractName=($($(object).closest('.gsaDataRowParent')).find('.unEditcontactName').html());
	}
	else if(nominationType == "GTA" )
	{
		contractName=($($(object).closest('.gtaDataRowParent')).find('.unEditcontactName').html());
	}
	else
		contractName=($($(object).closest('.bothDataRowParent')).find('.unEditcontactName').html());
	showContractRefModel(contractName);
	return;
}

function showCustomerDesc(object)
{
	var customerDesc;
	if(nominationType == "GSA" ) 
	{
		customerDesc=($($(object).closest('.gsaDataRowParent')).find('.customerDesc').text());
	}
	else if(nominationType == "GTA" )
	{
		customerDesc=($($(object).closest('.gtaDataRowParent')).find('.customerDesc').text());
	}
	else
		customerDesc=($($(object).closest('.bothDataRowParent')).find('.customerDesc').text());
	showCustomerDescModel(customerDesc);
		
		return;
		
}

//function showCustomerDesc(object)
//{
//	var customerDesc;
//	if(nominationType == "GSA" ) 
//	{
//		customerDesc=($($(object).closest('.gsaDataRowParent')).find('.customerDesc').text());
//	}
//	else if(nominationType == "GTA" )
//	{
//		customerDesc=($($(object).closest('.gtaDataRowParent')).find('.customerDesc').text());
//	}
//	else
//		customerDesc=($($(object).closest('.bothDataRowParent')).find('.customerDesc').text());
//	showCustomerDescModel(customerDesc);
//		
//		return;
//		
//}


/*Update Nomination*/
function saveOrUpdateNomination() {
	var nominationDetailTableTr;
    var nominationDetailTableTrLen;
    var nominationData = [];
	nominationSaveData = [];
	nominationUpdateData = [];

	var URL ;
	isSaveNomination= true;
	
	
	if(nominationType == "GSA" ) 
	{
		
		nominationDetailTableTr = $('.gsaDataTable tr');
		nominationDetailTableTrLen = nominationDetailTableTr.length;
		for(var i = 1; i < nominationDetailTableTrLen; i++)
		{
			if($($($(nominationDetailTableTr)[i]).find('.redelPoint')).val() != "")
			{
				if($($($(nominationDetailTableTr)[i]).find('.redelPoint')).val() < 0 ) {
				   
				   showAlertModal("Input value cannot be negative.");
				   return;
				}
			var formData = {};
			formData['contractId'] = $($($(nominationDetailTableTr)[i]).find('.contractId')).text();
			formData['delPoint'] = 0;
			formData['redelPoint'] = $($($(nominationDetailTableTr)[i]).find('.redelPoint')).val();
			formData['contractName'] = $($($(nominationDetailTableTr)[i]).find('.contactName')).val();
			if($($($(nominationDetailTableTr)[i]).find('.nominationId')).text()) {
			
				formData['nominationId'] = $($($(nominationDetailTableTr)[i]).find('.nominationId')).text();
				formData['revisionNo'] = $($($(nominationDetailTableTr)[i]).find('.revesionNo')).text();
				 nominationUpdateData.push(formData);
				//isSaveNomination=false;
				
			}
			else{
				nominationSaveData.push(formData);
				
			}
			
			
			
			} else {
				showAlertModal("RedelPoint should not be empty.");
				return;
			}
			
		}
			
	}
	else 
	{
		nominationDetailTableTr = $('.gtaDataTable tr');
		nominationDetailTableTrLen = nominationDetailTableTr.length;
		
		for(var i = 1; i < nominationDetailTableTrLen; i++) {
		
			if($($($(nominationDetailTableTr)[i]).find('.delPoint')).val() != "" && $($($(nominationDetailTableTr)[i]).find('.redelPoint')).val() != "")
			{
				if($($($(nominationDetailTableTr)[i]).find('.delPoint')).val() < 0 || $($($(nominationDetailTableTr)[i]).find('.redelPoint')).val() < 0 ) {
				   
				   showAlertModal("Input value should be grater than zero.");
				   return;
				}
			
			var formData = {};
			formData['contractId'] = $($($(nominationDetailTableTr)[i]).find('.contractId')).text();
			formData['delPoint'] = $($($(nominationDetailTableTr)[i]).find('.delPoint')).val();;
			formData['redelPoint'] = $($($(nominationDetailTableTr)[i]).find('.redelPoint')).val();
			formData['contractName'] = $($($(nominationDetailTableTr)[i]).find('.contactName')).val();
			formData['sellerRedelpoint'] = $($($(nominationDetailTableTr)[i]).find('.sellerRedelPoint')).text();
			
			formData['sellerUpdateTime'] = $($($(nominationDetailTableTr)[i]).find('.sellerUpdateDateClass')).text();
			
			
			if($($($(nominationDetailTableTr)[i]).find('.nominationId')).text()) {
				formData['nominationId'] = $($($(nominationDetailTableTr)[i]).find('.nominationId')).text();
				formData['revisionNo'] = $($($(nominationDetailTableTr)[i]).find('.revesionNo')).text();
				 
				 nominationUpdateData.push(formData)
			} else {
				nominationSaveData.push(formData);
				}				
			
			} else {
				showAlertModal("DelPoint and RedelPoint should not be empty.");
				return;
			}
			
		}
	}
			showProgress('saving data.. please wait..');
			//bulding final data obj

		if(nominationType == "GSA") {
			
//			var finalFormData = {};
//			finalFormData['userId'] = sessionStorage.getItem("userId");
//			finalFormData['gasDate'] = dateFromater($('.nominationDate').val().trim());
//			finalFormData['contractType'] = nominationType;
//	
//			
//			if(isSaveNomination) {
//				URL = 	constants.saveNominationsUrl; 
//				finalFormData['nominationData'] = nominationData;			
//			}else {
//				URL = 	constants.updateNominationUrl;
//				finalFormData['updateNominationData'] = nominationData;
//			}
//			var finalDataToRequest = JSON.stringify(finalFormData);
//			saveAndUpdateToNomination(URL,finalDataToRequest)
			
			
			if(nominationSaveData.length) {
				let finalFormDataSaveData={};
				let finalDataToRequest = {};
				finalFormDataSaveData['userId'] = sessionStorage.getItem("userId");
				finalFormDataSaveData['gasDate'] = dateFromater($('.nominationDate').val().trim());
				finalFormDataSaveData['contractType'] = nominationType;
				URL = 	constants.saveNominationsUrl; 
				finalFormDataSaveData['nominationData'] = nominationSaveData;
			    finalDataToRequest = JSON.stringify(finalFormDataSaveData);
				saveAndUpdateToNomination(URL,finalDataToRequest)
			}
			if(nominationUpdateData.length) {
				let finalFormDataUpdateData = {};
				let finalDataToRequest ={};
				finalFormDataUpdateData['userId'] = sessionStorage.getItem("userId");
				finalFormDataUpdateData['gasDate'] = dateFromater($('.nominationDate').val().trim());
				finalFormDataUpdateData['contractType'] = nominationType;
				URL = 	constants.updateNominationUrl;
				finalFormDataUpdateData['updateNominationData'] = nominationUpdateData;
			    finalDataToRequest = JSON.stringify(finalFormDataUpdateData);
				saveAndUpdateToNomination(URL,finalDataToRequest)
			}		
			
			
		} else {
			
			if(nominationSaveData.length) {
				let finalFormDataSaveData={};
				let finalDataToRequest = {};
				finalFormDataSaveData['userId'] = sessionStorage.getItem("userId");
				finalFormDataSaveData['gasDate'] = dateFromater($('.nominationDate').val().trim());
				finalFormDataSaveData['contractType'] = nominationType;
				URL = 	constants.saveNominationsUrl; 
				finalFormDataSaveData['nominationData'] = nominationSaveData;
			    finalDataToRequest = JSON.stringify(finalFormDataSaveData);
				saveAndUpdateToNomination(URL,finalDataToRequest)
			}
			if(nominationUpdateData.length) {
				let finalFormDataUpdateData = {};
				let finalDataToRequest ={};
				finalFormDataUpdateData['userId'] = sessionStorage.getItem("userId");
				finalFormDataUpdateData['gasDate'] = dateFromater($('.nominationDate').val().trim());
				finalFormDataUpdateData['contractType'] = nominationType;
				URL = 	constants.updateNominationUrl;
				finalFormDataUpdateData['updateNominationData'] = nominationUpdateData;
			    finalDataToRequest = JSON.stringify(finalFormDataUpdateData);
				saveAndUpdateToNomination(URL,finalDataToRequest)
			}

			
		}
		
		console.log(finalFormData);
		
		//creating header
	
}

function saveAndUpdateToNomination(URL,finalDataToRequest) {
	
	nominationDetailTableTr = JSON.parse(finalDataToRequest).contractType == "GSA" ?  $('.gsaDataTable tr') : $('.gtaDataTable tr');
	nominationDetailTableTrLen = nominationDetailTableTr.length;
	
	var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
	
	$.ajax({
		type: "POST",
		url: URL,       
		data: finalDataToRequest, 
		headers: headerParam,			
		dataType: "json",
		crossDomain: true,
		contentType: 'application/json',
		success: function(data){
			
			var responseStatus = data.responseStatus;
			var responseData = data.responseData;
			if(responseStatus.statusCode == constants.success){
				hideProgress();
				showNominationModal(messages.msgNominationSave);
				
				if(!isSaveNomination) {
					isSaveNomination = false;
				}
				
				for(var i = 0; i < responseData.length; i++) {
					
					if($($($(nominationDetailTableTr)[i+1]).find('.nominationId')).text()) {
						$($($(nominationDetailTableTr)[i+1]).find('.nominationId')).text(responseData[i].nominationId);
						 $($($(nominationDetailTableTr)[i+1]).find('.revesionNo')).text(responseData[i].revision_no);
					} else {
						 $($($(nominationDetailTableTr)[i+1]).find('.nominationId')).text(responseData[i].nominationId);
						 $($($(nominationDetailTableTr)[i+1]).find('.revesionNo')).text(responseData[i].revision_no);
					} 

				}
				nominationSaveData = [];
				nominationUpdateData = [];
				
			}
			else
			{
				hideProgress();
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


/*For add contract text pop up*/
function showContractShortText(object){
	var addContractInfo;
	if(nominationType == "GSA" || object.parentNode.parentElement.className == 'gsaDataRowParent') 
	{
		addContractInfo=($($(object).closest('.gsaDataRowParent')).find('.unEditcontactName').html());
		rowIndex = $('.gsaDataTable .gsaDataRowParent').index(($(object).closest('.gsaDataRowParent')));
		$('.contactName').val(contractAndNominationData.nominationData[rowIndex].contractName);
	}
	else if(nominationType == "GTA" || object.parentNode.parentElement.className == 'gtaDataRowParent')
	{
		addContractInfo=($($(object).closest('.gtaDataRowParent')).find('.unEditcontactName').html());
		rowIndex = $('.gtaDataTable .gtaDataRowParent').index(($(object).closest('.gtaDataRowParent')));
		$('.contactName').val(contractAndNominationData.nominationData[rowIndex].contractName);
	}
	editContractName(addContractInfo);
		
		return;
		
}

function showSellerRedelPointValuePoup(object) {
	
	var sellerRedelPointInfo;
	$('.editSaveButton').attr('disabled', false)
	sellerRedelPointInfo=($($(object).closest('.gtaDataRowParent.sellerPointTable')).find('.unEditredelPoint').html());
	sellerRedelPointInfo1=($($(object).closest('.gtaDataRowParent.sellerPointTable')).find('.unEditdelPoint').html());
	console.log( sellerRedelPointInfo )
	console.log( sellerRedelPointInfo1 )
	rowIndex = $('.gtaDataRowParent.sellerPointTable').index(($(object).closest('.gtaDataRowParent.sellerPointTable')));
//	$('.unEditredelPoint').val(sellerData.nominationData[rowIndex].seller_redel_point);
	let sellerVal = sellerData.nominationData[rowIndex].seller_redel_point ? sellerData.nominationData[rowIndex].seller_redel_point : sellerRedelPointInfo1;
	$('.sellerInput').val(sellerVal);
	editClientUserId = sellerData.nominationData[rowIndex].clientUserId;
	contactRefrenceGlobalVar = sellerData.nominationData[rowIndex].contractRef;
    contractId = sellerData.nominationData[rowIndex].contractId;
	nominationId = sellerData.nominationData[rowIndex].nominationId;
	editSellerPointInPopUp(sellerRedelPointInfo);
	
	return;
	
}

/*For saving contract text*/
function saveNominationContractText(){
	var nominationDataById;
	// for(var i=0;i<contractAndNominationData.nominationData.length;i++) {
	// 	if(i == rowIndex+1){
	// 		nominationDataById = nominationData.nominationData[i]
	// 	}
	// }
	//bulding final data obj
	var finalFormData = {};
	
		finalFormData['contractRef'] = contractAndNominationData.nominationData[rowIndex].contractRef;
		finalFormData['contractName'] = $('.contactName').val();
		finalFormData['payerId'] = contractAndNominationData.nominationData[rowIndex].customerCode;
//		debugger			
		var URL ;
		if(contractAndNominationData.nominationData[rowIndex].contractName == null || contractAndNominationData.nominationData[rowIndex].contractName == '')
		{
			URL = 	constants.saveContractText; 		
		}else
		{
			URL = 	constants.UpdateContractText;
		}	
		
		var finalDataToRequest = JSON.stringify(finalFormData);
		console.log(finalFormData);
		
		//creating header
		var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
		
		$.ajax({
			type: "POST",
			url: URL,       
			data: finalDataToRequest, 
			headers: headerParam,			
			dataType: "json",
			crossDomain: true,
			contentType: 'application/json',
			success: function(data){
				$('.contactName').val();
				var responseStatus = data.responseStatus;
				if(responseStatus.statusCode == constants.success)
				{
					
					hideProgress();
					if(contractAndNominationData.nominationData[rowIndex].contractName == null || contractAndNominationData.nominationData[rowIndex].contractName == '')
					{
						showNominationModal(messages.msgContractTextSave);		
					}else
					{
						showNominationModal(messages.msgContractTextUpdate);
					}
					
//					showNominationModal(messages.msgContractTextSave);
//					contractAndNominationData.nominationData[rowIndex].contractName = $('.contactName').val();
					var dlist = $('.gtaDataTable').find('.gtaDataRowParent')[rowIndex];
					$(dlist).find('.unEditcontactName').html( $('.contactName').val());

				}
				else
				{
					hideProgress();
					commonError(responseStatus.statusCode);
				} 
			
			},
			error: function(e)
			{
				$('.contactName').val();
				hideProgress();
				commonError(constants.internalServerError);
			}
		});
}

/*For Contract Information*/
function showContractTextName(object) {
	var contractName;
	var displaycontractName;
	if(nominationType == "GSA" || object.parentNode.parentElement.className == 'gsaDataRowParent') 
	{
		contractNamet=($($(object).closest('.gsaDataRowParent')).find('.unEditcontactName').html());
		rowIndex = $('.gsaDataTable .gsaDataRowParent').index(($(object).closest('.gsaDataRowParent')));
		displaycontractName = contractAndNominationData.nominationData[rowIndex].contractName;
	}
	else if(nominationType == "GTA" || object.parentNode.parentElement.className == 'gtaDataRowParent')
	{
		contractName=($($(object).closest('.gtaDataRowParent')).find('.unEditcontactName').html());
		rowIndex = $('.gtaDataTable .gtaDataRowParent').index(($(object).closest('.gtaDataRowParent')));
		displaycontractName = contractAndNominationData.nominationData[rowIndex].contractName;
	} /*else
		contractName=($($(object).closest('.bothDataRowParent')).find('.unEditcontactName').html());
		rowIndex = $('.bothDataDisplayTable .bothDataRowParent').index(($(object).closest('.bothDataRowParent')));
		displaycontractName = contractAndNominationData.nominationData[rowIndex].contractName;*/
	showContractRefModel(displaycontractName);
	return;
}

/*For Contract Display Information*/ 
function showContractDisplayTextName(object) {
	var contractName;
	var displaycontractName;
	if(nominationType == "GSA") 
	{
		contractNamet=($($(object).closest('.gtaDataRowParent')).find('.unEditredelPoint').html());
		rowIndex = $('.gsaDataDisplayTable .gtaDataRowParent').index(($(object).closest('.gtaDataRowParent')));
		displaycontractName = nominationData.nominationData[rowIndex].contractName;
	}
	else if(nominationType == "GTA")
	{
		contractName=($($(object).closest('.gtaDataRowParent')).find('.unEditcontactName').html());
		rowIndex = $('.gtaDataDisplayTable .gtaDataRowParent').index(($(object).closest('.gtaDataRowParent')));
		displaycontractName = nominationData.nominationData[rowIndex].contractName;
	} else if(nominationType == "Both"){
		contractName=($($(object).closest('.bothDataRowParent')).find('.unEditcontactName').html());
		rowIndex = $('.bothDataDisplayTable .bothDataRowParent').index(($(object).closest('.bothDataRowParent')));
		displaycontractName = nominationData.nominationData[rowIndex].contractName;
	}	
	showContractRefModel(displaycontractName);
	return;
}

//For updating seller data
function saveOrUpdateSeller() {
	var nominationDetailTableTr;
    var nominationDetailTableTrLen;
    var nominationData = [];
	var finalFormData = {};
	
	showProgress('saving data.. please wait..');
	
	//bulding final data obj
	finalFormData['userId'] = sessionStorage.getItem("userId");
	finalFormData['gasDate'] = dateFromater($('.sellerDate').val().trim());
	finalFormData['contractType'] = "GTA";
	finalFormData['contract_ref']= contactRefrenceGlobalVar;
	finalFormData['contract_id']= contractId;
	finalFormData['nomination_id'] = nominationId +"";
	finalFormData['seller_redel_point'] = $('.sellerInput').val();
	finalFormData['clientUserId'] = editClientUserId;

					  
	
		var URL  = 	constants.UpdateSellerRedelPoint;
//			finalFormData['updateNominationData'] = nominationData;
//		
		var finalDataToRequest = JSON.stringify(finalFormData);
		console.log(finalFormData);
		
		//creating header
		var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
		
		$.ajax({
			type: "POST",
			url: URL,       
			data: finalDataToRequest, 
			headers: headerParam,			
			dataType: "json",
			crossDomain: true,
			contentType: 'application/json',
			success: function(data){
				
				if(data.responseData) {
					var responseStatus = data.responseStatus;
					let responseData = data.responseData[0];
					if(responseStatus.statusCode == constants.success)
					{
						

						hideProgress();
						showNominationModal(messages.msgNominationSave);
					    isSaveSellerRedelPoint = isSaveSellerRedelPoint ? false : true;
					    var dlist = $('.gtasellerDataTable').find('.gtaDataRowParent')[rowIndex];
						if(responseData.sellerredelPoint == null || responseData.sellerredelPoint == '' && !responseData.sellerredelPoint == 0 ) {
							$(dlist).find('.unEditredelPoint').html(htmlConstants.addSellerRedelPointButton);
						} else {
							sellerData.nominationData[rowIndex].seller_redel_point = responseData.sellerredelPoint;
							sellerData.nominationData[rowIndex].nominationId = responseData.nominationId;
							$(dlist).find('.unEditredelPoint').html(responseData.sellerredelPoint + htmlConstants.editSellerRedelPoint);
							sellerRedelPointInfo = '';
							$('.sellerInput').val('')
							
						}
					    
					
					}
	
				}
				else
				{
					hideProgress();
					commonError(data.responseStatus.statusCode);
				}
 
			
			},
			error: function(e)
			{
				hideProgress();
				commonError(constants.internalServerError);
			}
		});		

		
		
		
		
		
		
		
}
