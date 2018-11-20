

$(document).ready(function(){
localStorage.setItem('login','false');
});

// Login form submit
function submitLoginForm(object, event)
{	
	// Submit on enter
	if(event.keyCode == 13)
	{
		loginUser();
	}
}



function loginUser()
{
 	
	// check remind me
	
	if(localStorage.getItem('login') == 'true')
	{
		$('.username').val(localStorage.getItem('userName')) ;
		$('.password').val(localStorage.getItem('password')) ;
	}	
	// Get values
	var username = $('.username').val().trim();
	var password = $('.password').val().trim();
	if($('.chkbox').is(':checked'))
	{
		localStorage.setItem('rememberMe','true');
		localStorage.setItem('login','true');
		localStorage.setItem('userName',username);
		localStorage.setItem('password',password);
	}
	else
	{
		localStorage.setItem('rememberMe','false');
	}
	// Validation function call commom.js
	var validationResult = loginValidation(username, password, $('.username'), $('.password'));
	if(validationResult)
	{	
		showProgress('logging.. please wait..');
		var formData = {};
		formData['userName'] = username;
		formData['password'] = password;
		var finalData = JSON.stringify(formData);
		
		$.ajax({
			type: "POST",
			url: constants.loginUrl,       
			data: finalData,               
			dataType: "json",
			crossDomain: true,
			contentType: 'application/json',
			success: function(data){
				hideProgress();
				var responseStatus = data.responseStatus;
				if(responseStatus.statusCode == constants.success)
				{
					
					var responseData = data.responseData;
					var orgId = responseData.organizationId;
					if(typeof orgId != "undefined" && orgId != ' ')
					{
						sessionStorage.setItem('orgId', orgId);
					}
					var userId = responseData.userId;
					sessionStorage.setItem('', JSON.stringify(responseData));
					localStorage.setItem('login','true');
					localStorage.setItem('username',username);
					localStorage.setItem('password',password);
					sessionStorage.setItem('userId', responseData.userId);
					sessionStorage.setItem('token', responseData.token);
					sessionStorage.setItem('payerId', responseData.payerId);
					sessionStorage.setItem('userRole', responseData.role);
					sessionStorage.setItem('payerName', responseData.payerName);
					sessionStorage.setItem('tickerText', responseData.tickerText);
					sessionStorage.setItem('contactData', JSON.stringify(responseData.contactList));
					if (responseData.passwordChanged == true) {
						if (sessionStorage.getItem("userRole") == "payer") {
							location.replace("management-centre.html");
						} else if (sessionStorage.getItem("userRole") == "admin") {
							location.replace("download-report.html");
						}
					} else {
						location.replace("change-password.html");
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



function changePassword()
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
					if(sessionStorage.getItem("userRole") == "payer" )
					{
						location.replace("management-centre.html");
					}else if(sessionStorage.getItem("userRole") == "admin" )
					{
						location.replace("download-report.html");
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
	

	
	function configurePasswordPage() 
	{
		if(sessionStorage.getItem('userId')== null && sessionStorage.getItem('userId') == undefined)
			{
				location.replace('sign-in.html');
			}
		$('.loginUserName').html(sessionStorage.getItem("payerName"));
	}
	
//forget Password 
	
	function sendPassword() 
	{
		$('.FPassBoxPad').css('padding','10% 26% 1% 26%');
		$('.forgetPassword').fadeIn(300);
		
	}

	function clearUserMsg() {
		 $('.enterUserName').html("");
		 $('.forgotPasswordUserId').val("");
		 $('.FPassBoxPad').css('padding','10% 26% 1% 26%');
	}
	
	
   function sendForgetPassword()
   {
	   var userName = $('.forgotPasswordUserId').val().trim();
	   if(userName == "" || userName == undefined ) {
		   $('.enterUserName').html(messages.msgEnterUsername);
		  $('.FPassBoxPad').css('padding','0% 26% 1% 26%');
		   return;
	   }
	   
	   showProgress('sending password.. please wait..');
	   $('.enterUserName').html("");
	   $('.forgotPasswordUserId').val("");
	   $('.forgetPassword').fadeOut(300);
	   
	   
	   
	  var formData = {};
		formData['userName'] = userName;
			
		var finalData = JSON.stringify(formData);
		
		//creating header
		var headerParam = {'clientdata':sessionStorage.getItem("userId")+"|"+sessionStorage.getItem("token")};
		
		$.ajax({
			type: "POST",
			url: constants.forgotPasswordUrl,       
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
					showSuccessModal(messages.msgPasswordSended);
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
	


     