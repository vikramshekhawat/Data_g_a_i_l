function insertHeader()
{
	$('body').prepend(htmlConstants.headerCommon);
}

function insertFooter()
{
	$('body').append(htmlConstants.footerHtml);
}

function addActiveClass(object)
{
  var clickedText = $(object).html();
  sessionStorage.setItem("activeMenu",clickedText);
}

function removeActiveClass()
{
	sessionStorage.setItem("activeMenu",'');
}

function toggleDropdown(e){
	e.preventDefault();
	if($('.AshishSethDropBoxheader').hasClass('DisplayNone')){
		$('.AshishSethDropBoxheader').removeClass('DisplayNone');	
	}
	else
	{
		$('.AshishSethDropBoxheader').addClass('DisplayNone');	
	}
	$('.newMenuCont .active').removeClass('active');
}

function goToTrackingPage()
{
	location.replace("track-device.html");
}

