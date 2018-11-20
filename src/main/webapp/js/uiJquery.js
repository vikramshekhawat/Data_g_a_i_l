// JavaScript Document

$('.SelectContractType').hide();
$(document).on('click','#SelectContractType',function(){
	if($('.SelectContractType').css('display')=='none'){
	$('.SelectContractType').show();
		$('.SelectContractType ul li a').click(function(){
			$('.SelectContractType').hide();
			
			})
	}
	else
	{
		$('.SelectContractType').hide();
	}
	});


$('td').click(function (event) {
      $('td').removeClass('focus'); //Remove focus from other TDs
     $(this).addClass('focus');
	 
	 
	 
});


	 
$(document).on('click','#cancel',function(){
		$('.darkBg').fadeOut(300);
		 $('.popUpBox').fadeOut(300);
});

$('.topDropDown').hide();
$(document).on('click','.dropDownMenu',function(){
	if($('.topDropDown').css('display')=='none'){
		$('.topDropDown').show();	
		
		$(document).on('click','.topDropDown ul li a ',function(){
			$('.topDropDown').hide()
		})
		
		$('.topDropDown').mouseleave(function() {
				$(this).hide();
			})
	}
	else
	{
		$('.topDropDown').hide();
	}

});

/*******************tab ***********/


function nominate(){
	$('#display').show();
	$('#nominate').hide();
	$('#TickerData').hide();
	$('#seller').hide();
	$('#PushNotificationData').hide();
	$('#NominateTab').removeClass('active');
	$('#DisplayTab').addClass('active');
	$('#TickerTab').removeClass('active');
	$('#SellerTab').removeClass('active');
	$('#PushNotificationTab').removeClass('active');
}
function seller(){
	$('#seller').show();
	$('#display').hide();
	$('#nominate').hide();
	$('#TickerData').hide();
	$('#PushNotificationData').hide();
	$('#NominateTab').removeClass('active');
	$('#SellerTab').addClass('active');
	$('#DisplayTab').removeClass('active');
	$('#PushNotificationTab').removeClass('active');
}
function display(){
	$('#nominate').show();
	$('#display').hide();
	$('#TickerData').hide();
	$('#seller').hide();
	$('#PushNotificationData').hide();
	$('#DisplayTab').removeClass('active');
	$('#NominateTab').addClass('active');
	$('#UpdateDataTab').removeClass('active');
	$('#TickerTab').removeClass('active');
	$('#SellerTab').removeClass('active');
	$('#PushNotificationTab').removeClass('active');
	$('#UpdateData').hide();
}
function updata(){
	$('#UpdateData').show();
	$('#nominate').hide();
	$('#TickerData').hide();
	$('#PushNotificationData').hide();
	$('#NominateTab').removeClass('active');
	$('#UpdateDataTab').addClass('active');
	$('#TickerTab').removeClass('active');
	$('#PushNotificationTab').removeClass('active');
}
function ticker(){
	$('#TickerData').show();
	$('#UpdateData').hide();
	$('#nominate').hide();
	$('#PushNotificationData').hide();
	$('#NominateTab').removeClass('active');
	$('#UpdateDataTab').removeClass('active');
	$('#TickerTab').addClass('active');
	$('#PushNotificationTab').removeClass('active');
}

function pushNotification(){
	$('#TickerData').hide();
	$('#UpdateData').hide();
	$('#nominate').hide();
	$('#PushNotificationData').show();
	$('#NominateTab').removeClass('active');
	$('#UpdateDataTab').removeClass('active');
	$('#TickerTab').removeClass('active');
	$('#PushNotificationTab').addClass('active');
}

$(document).on('click','#NominateTab',function(){
	display();
	})
$(document).on('click','#DisplayTab',function(){
	nominate();
	})	
$(document).on('click','#UpdateDataTab',function(){
	updata();
	})		
$(document).on('click','#TickerTab',function(){
	ticker();
	})	
$(document).on('click','#PushNotificationTab',function(){
	pushNotification();
	})
$(document).on('click','#SellerTab',function(){
	seller();
	})	
	
	
/*******************tab end***********/

/*******************edit profile toggle***********/
function PassChang(){
	$('#togleChangePawsBox').toggleClass('displayNone displayBlock');
	$('#psChOpenTab').toggleClass('OpenTab closeTab');
	$('#EmailToggleBox').addClass('displayNone ');
	$('#EmailToggleBox').removeClass('displayBlock ');
	$('#EmailToggleHed').removeClass('lastTab2');
	$('#EmailToggleHed').addClass('lastTab');
	$('#EmailChOpenTab').addClass('OpenTab');
	$('#EmailChOpenTab').removeClass('closeTab');
	
}
function EmailHed(){
	$('#EmailToggleBox').toggleClass('displayNone displayBlock');
	$('#EmailChOpenTab').toggleClass('OpenTab closeTab');
	$('#EmailToggleHed').toggleClass('lastTab lastTab2');
	$('#togleChangePawsBox').addClass('displayNone ');
	$('#togleChangePawsBox').removeClass('displayBlock ');
	$('#psChOpenTab').addClass('OpenTab');
	$('#psChOpenTab').removeClass('closeTab');

}
$('#togleChangePawsHed').click(function(){
		PassChang();
	});
$('#EmailToggleHed').click(function(){
		EmailHed();
		
	});	
$(document).on('click','.cancelPass',function(){
	PassChang();
	
	});	
$(document).on('click','.cancelEmail',function(){
	EmailHed();
	
	});		
	
/*******************edit profile toggle end***********/	
