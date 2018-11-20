var roleuser = sessionStorage.getItem("userRole");
var logolink;
if (roleuser == 'admin') {
	logolink = "download-report.html";
} else if (roleuser == 'payer') {
	logolink = "management-centre.html";
} else {
	logolink = "#"
}

var htmlConstants = {

	headerCommon : '<div class="headerMC"> <a href='
			+ this.logolink
			+ ' title="GAIL - Gas Nomination" class="gail-logo"> <img src="images/GAIL-Logo-inner.png" width="76" height="56" alt="Gail Logo">'
			+ '<p class="gailTaglin1">Gail (India) Limited</p>'
			+ '<p class="gailTaglin2">India\'s <span>Youngest</span> Maharatna</p></a>'
			+ '<div class="dropRight"><div class="dropDownMenu"><img src="images/dropBtn.png" width="38" height="49" /></div>'
			+ '<div class="topDropDown"><img src="images/arrowDropDown.png" width="30" height="20" class="arrowDrop" />'
			+ '<ul><li><a href="edit-profile.html">Edit Profile</a></li><li><a href="#" onclick="logout()">Logout</a></li></ul></div>'
			+ '<div class="userName"><p class="user loginUserName"></p><p class="title">(user)</p></div></div><div class="clear"></div></div>',

	noDistanceData : '<tr>'
			+ '<td height="400" align="center" valign="middle" bgcolor="#eef0f0"  scope="col"><img src="images/nodatafound2.png" class="img-responsive">'
			+ '</td>',

	alertModel : '<div class="darkBg alertModel1"></div>'
			+ '<div class="popUpBox alertModel2"><h2>Alert!</h2><p></p>'
			+ '<div class="ButtonBox"><button type="submit" class="popUpBtn" id="cancel">Ok</button></div></div>',

	logoutModel : '<div class="darkBg logoutModel1"></div>'
			+ '<div class="popUpBox logoutModel2"><h2>Alert!</h2><p></p>'
			+ '<div class="ButtonBox"><button type="submit" class="popUpBtn" id="cancel" onclick="logout();">Ok</button></div></div>',

	successModel : '<div class="darkBg successModel1"></div>'
			+ '<div class="popUpBox successModel2"><h2>Success!</h2><p></p>'
			+ '<div class="ButtonBox"><button type="submit" class="popUpBtn" id="cancel">Ok</button></div></div>',

	successNominationModel : '<div class="darkBg successNominationModel1"></div>'
			+ '<div class="popUpBox successNominationModel2"><h2>Success!</h2><p></p>'
			+ '<div class="ButtonBox"><button type="submit" class="popUpBtn" id="cancel">Ok</button></div></div>',

	successTickerModel : '<div class="darkBg successTickerModel1"></div>'
			+ '<div class="popUpBox successTickerModel2"><h2>Success!</h2><p></p>'
			+ '<div class="ButtonBox"><button type="submit" class="popUpBtn" id="cancel" >Ok</button></div></div>',

	customerDescModel : '<div class="darkBg customerDescModel1"></div>'
			+ '<div class="popUpBox customerDescModel2"><h2>Customer Description!</h2><p></p>'
			+ '<div class="ButtonBox"><button type="submit" class="popUpBtn" id="cancel">Ok</button></div></div>',

	materialDescModel : '<div class="darkBg materialDescModel1"></div>'
			+ '<div class="popUpBox materialDescModel2"><h2>Material Description!</h2><p></p>'
			+ '<div class="ButtonBox"><button type="submit" class="popUpBtn" id="cancel">Ok</button></div></div>',

	addContractInfo : '<div class="darkBg addContractInfo1"></div>'
			+ '<div class="popUpBox addContractInfo2"><h2>Contract Text</h2><input type="text"  placeholder="Enter Contract Short Text" class="contactName" data-toggle="tooltip" title="You can add/edit text in this field only for your reference. The text entered shall be limited to you only and GAIL has nothing to do with this." />'
			+ '<div class="ButtonBox"><button type="submit" class="popUpBtn" id="cancel">Cancel</button><button type="submit" class="popUpBtn" id="cancel" onclick="saveNominationContractText()">Save</button></div></div>',
			
	sellerRedelPointInfo : '<div class="darkBg addSellerPoint1"></div>'
				+ '<div class="popUpBox addSellerPoint2"><h2>Seller Del Point</h2><input type="text"  placeholder="Enter Seller Del Point" class="sellerInput" data-toggle="tooltip" title="You can add/edit text in this field only for your reference. The text entered shall be limited to you only and GAIL has nothing to do with this." />'
				+ '<div class="ButtonBox"><button type="submit" class="popUpBtn" id="cancel">Cancel</button><button type="submit" class="popUpBtn editSaveButton" id="cancel" onclick="saveOrUpdateSeller()">Save</button></div></div>',


	addcontractNameInput : '<input type="text"  placeholder="Enter Contract Short Text" class="contactName" data-toggle="tooltip" title="You can add/edit text in this field only for your reference. The text entered shall be limited to you only and GAIL has nothing to do with this." />',

	contractValidity : '<div class="darkBg contractValidity1"></div>'
			+ '<div class="popUpBox contractValidity2"><h2>Contract Validity</h2><p></p>'
			+ '<div class="ButtonBox"><button type="submit" class="popUpBtn" id="cancel">Ok</button></div></div>',

	contractName : '<div class="darkBg contractName1"></div>'
			+ '<div class="popUpBox contractName2"><h2>Contract Short Text</h2><p></p>'
			+ '<div class="ButtonBox"><button type="submit" class="popUpBtn" id="cancel">Ok</button></div></div>',

	errorModel : '<div class="darkBg errorModel1"></div>'
			+ '<div class="popUpBox errorModel2"><h2>Error!</h2><p></p>'
			+ '<div class="ButtonBox button1"><button type="submit" class="popUpBtn" id="cancel">Ok</button></div></div>',

	forgetPasswordModel : '<div class="darkBg forgetPassword"></div><div class="popUpBox forgetPassword"'
			+ 'style="height:225px;"><h2>Forgot password</h2><p style="text-align:left; line-height:18px;">We will send a password on the email id registered with your username.</p><input type="text" placeholder="Enter username" class="forgotPasswordUserId"><p style="color:red;text-align:left;" class="enterUserName"><p/><div class="ButtonBox FPassBoxPad" style="padding: 10% 26% 1% 26%;"><button type="submit" class="popUpBtn"  onclick="sendForgetPassword()">Send</button><button type="submit" class="popUpBtn" id="cancel" onclick="clearUserMsg();">Cancel</button><div class="clearboth"></div></div></div>',
	gsaDataRow : '<tr class="gsaDataRowParent" >'
			+ '<td align="left" valign="middle" class="bdrBotm contactRefrence"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm unEditcontactName" data-toggle="tooltip" title="You can add/edit text in this field only for your reference. The text entered shall be limited to you only and GAIL has nothing to do with this."></td>'
			+ '<td align="left" valign="middle" class="bdrBotm materialCode" id="MaterialCode"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm customerCode" id="MaterialCode"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm unEditredelPoint"><span class="displayNone customerDesc"></span><span class="displayNone materialDesc"></span><span class="displayNone contractValidity"></span><span class="displayNone contractName"></span><span class="displayNone contractId"></span><span class="displayNone nominationId"></span><span class="displayNone clientUserId"></span><span class="displayNone revesionNo"></span> <input type="text" onkeyup="numericFilter(this)" placeholder="Enter REDEL Point" class="redelPoint" data-toggle="tooltip" title="You can add/edit text in this field only for your reference. The text entered shall be limited to you only and GAIL has nothing to do with this." /></td>'
			+ '<td align="left" valign="middle" class="bdrBotm uom"></td>'
			+ '</tr>',
	gsaDataHeader : '<thead> <tr>'
			+ ' <th width="10%" align="left" bgcolor="#f2f3f3" scope="col">Contract Reference<div>Contract Reference</div></th>'
			+ ' <th width="20%" align="left" bgcolor="#f2f3f3" scope="col">Contract Short Text<div>Contract Short Text</div></th>'
			+ '<th width="20%" align="left" bgcolor="#f2f3f3" id="MaterialCode" scope="col">Material Code<div>Material Code</div></th>'
			+ '<th width="20%" align="left" bgcolor="#f2f3f3" id="MaterialCode" scope="col">Customer Code<div>Customer Code</div></th>'
			+ '<th width="20%" align="left" bgcolor="#f2f3f3" scope="col">REDEL Point<div>REDEL Point</div></th>'
			+ '<th width="20%" align="left" bgcolor="#f2f3f3" scope="col">UOM<div>UOM</div></th>'
			+ '</tr> </thead> <tbody>',

	gtaDataRow : '<tr class="gtaDataRowParent">'
			+ '<td align="left" valign="middle" class="bdrBotm contactRefrence"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm unEditcontactName" data-toggle="tooltip" title="You can add/edit text in this field only for your reference. The text entered shall be limited to you only and GAIL has nothing to do with this."> </td>'
			+ '<td align="left" valign="middle" class="bdrBotm materialCode" id="MaterialCode"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm customerCode" id="MaterialCode"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm unEditdelPoint"><span class="displayNone customerDesc"></span><span class="displayNone materialDesc"></span><span class="displayNone contractValidity"></span><span class="displayNone sellerUpdateDateClass"></span><span class="displayNone contractName"></span><span class="displayNone contractId"></span><span class="displayNone nominationId"></span><span class="displayNone revesionNo"></span><input type="text" onkeyup="numericFilter(this)" placeholder="Enter DEL Point" class="delPoint"/></td>'
			+ '<td align="left" valign="middle" class="bdrBotm unEditredelPoint">  <input type="text" onkeyup="numericFilter(this)" placeholder="Enter REDEL Point"  class="redelPoint" data-toggle="tooltip" title="You can add/edit text in this field only for your reference. The text entered shall be limited to you only and GAIL has nothing to do with this." /></td>'
			+ '<td align="left" valign="middle" class="bdrBotm sellerRedelPoint" id="SellerRedelPoint"></td>'
			+ '</tr>',
	gtaDataHeader : '<thead> <tr>'
			+ ' <th width="15%" align="left" bgcolor="#f2f3f3" scope="col">Contract Reference<div>Contract Reference</div></th>'
			+ ' <th width="20%" align="left" bgcolor="#f2f3f3" scope="col">Contract Short Text<div>Contract Short Text</div></th>'
			+ '<th width="20%" align="left" bgcolor="#f2f3f3" id="MaterialCode" scope="col">Material Code<div>Material Code</div></th>'
			+ '<th width="15%" align="left" bgcolor="#f2f3f3" id="MaterialCode" scope="col">Customer Code<div>Customer Code</div></th>'
			+ '<th width="15%" align="left" bgcolor="#f2f3f3" scope="col">DEL Point<div>DEL Point</div></th>'
			+ '<th width="15%" align="left" bgcolor="#f2f3f3" scope="col">REDEL Point<div>REDEL Point</div></th>'
			+ '<th width="15%" align="left" bgcolor="#f2f3f3" scope="col"> DEL Point By Seller <div>DEL Point By Seller</div></th>'
			+ '</tr> </thead> <tbody>',

	// Seller Data table
	sellergtaDataRow : '<tr class="gtaDataRowParent sellerPointTable">'
			+ '<td align="left" valign="middle" class="bdrBotm contactRefrence"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm unEditcontactName"> </td>'
			+ '<td align="left" valign="middle" class="bdrBotm customerPayerId id="MaterialCode"> </td>'
			+ '<td align="left" valign="middle" class="bdrBotm materialCode" id="MaterialCode"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm unEditdelPoint"> </td>'
			+ '<td align="left" valign="middle" class="bdrBotm unEditredelPoint"><span class="displayNone customerDesc"></span><span class="displayNone materialDesc"></span><span class="displayNone contractValidity"></span><span class="displayNone contractName"></span><span class="displayNone contractId"></span><span class="displayNone nominationId"></span><span class="displayNone revesionNo"></span> <input type="text" onkeyup="numericFilter(this)" placeholder="Enter REDEL Point" class="redelPoint" data-toggle="tooltip" title="You can add/edit text in this field only for your reference. The text entered shall be limited to you only and GAIL has nothing to do with this." /></td>'
//			+ '<td align="left" valign="middle" class="bdrBotm unEditredelPoint data-toggle="tooltip" title="You can add/edit text in this field only for your reference. The text entered shall be limited to you only and GAIL has nothing to do with this.">  <input type="text" onkeyup="numericFilter(this)" placeholder="Enter Supplier DEL Point"  class="redelPoint" data-toggle="tooltip" title="You can add/edit text in this field only for your reference. The text entered shall be limited to you only and GAIL has nothing to do with this." /></td>'
			+ '</tr>',
	sellergtaDataHeader : '<thead> <tr>'
			+ ' <th width="16%" align="left" bgcolor="#f2f3f3" scope="col">Contract Reference<div>Contract Reference</div></th>'
			+ ' <th width="20%" align="left" bgcolor="#f2f3f3" scope="col">Client Name<div>Client Name</div></th>'
			+ '<th width="20%" align="left" bgcolor="#f2f3f3" scope="col"> Payer Id<div>Payer Id</div></th>'
			+ '<th width="24%" align="left" bgcolor="#f2f3f3" id="MaterialCode" scope="col">Material Code<div>Material Code</div></th>'
			+ '<th width="20%" align="left" bgcolor="#f2f3f3" scope="col">DEL Point By Customer<div>DEL Point By Customer</div></th>'
			+ '<th width="20%" align="left" bgcolor="#f2f3f3" scope="col"> DEL Point By Seller<div>DEL Point By Seller</div></th>'
			+ '</tr> </thead> <tbody>',

	bothDataRow : '<tr class="bothDataRowParent">'
			+ '<td align="left" valign="middle" class="bdrBotm contactRefrence"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm unEditcontactName" ></td>'
			+ '<td align="left" valign="middle" class="bdrBotm materialCode" id="MaterialCode"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm customerCode" id="MaterialCode"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm  unEditdelPoint"><span class="displayNone customerDesc"></span><span class="displayNone materialDesc"></span><span class="displayNone contractValidity"></span><span class="displayNone contractName"></span><span class="displayNone contractId"></span><span class="displayNone nominationId"></span><span class="displayNone revesionNo"></span><input type="text" onkeyup="numericFilter(this)" placeholder="Enter DEL Point" class="delPoint"/></td>'
			+ '<td align="left" valign="middle" class="bdrBotm unEditredelPoint"><input type="text" onkeyup="numericFilter(this)" placeholder="Enter REDEL Point"  class="redelPoint" data-toggle="tooltip" title="You can add/edit text in this field only for your reference. The text entered shall be limited to you only and GAIL has nothing to do with this." /></td>'
			+ '<td align="left" valign="middle" class="bdrBotm sellerRedelPoint" id="SellerRedelPoint"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm uom"></td>'
			+ '</tr>',
	bothDataHeader : '<thead> <tr>'
			+ ' <th width="20%" align="left" bgcolor="#f2f3f3" scope="col">Contract Reference<div>Contract Reference</div></th>'
			+ ' <th width="20%" align="left" bgcolor="#f2f3f3" scope="col">Contract Short Text<div>Contract Short Text</div></th>'
			+ '<th width="20%" align="left" bgcolor="#f2f3f3" id="MaterialCode" scope="col">Material Code<div>Material Code</div></th>'
			+ '<th width="15%" align="left" bgcolor="#f2f3f3" id="MaterialCode" scope="col">Customer Code<div>Customer Code</div></th>'
			+ '<th width="15%" align="left" bgcolor="#f2f3f3" scope="col">DEL Point<div>DEL Point</div></th>'
			+ '<th width="15%" align="left" bgcolor="#f2f3f3" scope="col">REDEL Point<div>REDEL Point</div></th>'
			+ '<th width="15%" align="left" bgcolor="#f2f3f3" scope="col"> DEL Point By Seller <div>DEL Point By Seller</div></th>'
			+ '<th width="15%" align="left" bgcolor="#f2f3f3" scope="col">UOM<div>UOM</div></th>'
			+ '</tr> </thead> <tbody>',
	mulGsaDataRow : '<tr class="gsaDataRowParent">'
			+ '<td align="left" valign="middle" class="bdrBotm contactRefrence"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm unEditcontactName" ></td>'
			+ '<td align="left" valign="middle" class="bdrBotm materialCode" id="MaterialCode"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm customerCode" id="MaterialCode"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm gasDate"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm unEditredelPoint" ><span class="displayNone customerDesc"></span><span class="displayNone materialDesc"></span><span class="displayNone contractValidity"></span><span class="displayNone contractName"></span><span class="displayNone contractId"></span><span class="displayNone nominationId"></span><span class="displayNone revesionNo"></span><input type="text" onkeyup="numericFilter(this)" placeholder="Enter REDEL Point" class="redelPoint" data-toggle="tooltip" title="You can add/edit text in this field only for your reference. The text entered shall be limited to you only and GAIL has nothing to do with this." /></td>'
			+ '<td align="left" valign="middle" class="bdrBotm uom"></td>'
			+ '</tr>',
	mulGsaDataHeader : '<thead> <tr>'
			+ '<th width="16%" align="left" bgcolor="#f2f3f3" scope="col">Contract Reference<div>Contract Reference</div></th>'
			+ '<th width="20%" align="left" bgcolor="#f2f3f3" scope="col">Contract Short Text<div>Contract Short Text</div></th>'
			+ '<th width="20%" align="left" bgcolor="#f2f3f3" id="MaterialCode" scope="col">Material Code<div>Material Code</div></th>'
			+ '<th width="20%" align="left" bgcolor="#f2f3f3" id="MaterialCode" scope="col">Customer Code<div>Customer Code</div></th>'
			+ '<th width="10%" align="left" bgcolor="#f2f3f3" scope="col">Gas Date<div>Gas Date</div></th>'
			+ '<th width="20%" align="left" bgcolor="#f2f3f3" scope="col">REDEL Point<div>REDEL Point</div></th>'
			+ '<th width="20%" align="left" bgcolor="#f2f3f3" scope="col">UOM<div>UOM</div></th>'
			+ '</tr> </thead> <tbody>',

	mulGtaDataRow : '<tr class="gtaDataRowParent">'
			+ '<td align="left" valign="middle" class="bdrBotm contactRefrence"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm unEditcontactName" ></td>'
			+ '<td align="left" valign="middle" class="bdrBotm materialCode" id="MaterialCode"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm customerCode" id="MaterialCode"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm gasDate"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm unEditdelPoint"><span class="displayNone customerDesc"></span><span class="displayNone materialDesc"></span><span class="displayNone contractValidity"></span><span class="displayNone contractName"></span><span class="displayNone contractId"></span><span class="displayNone nominationId"></span><span class="displayNone revesionNo"></span><input type="text" onkeyup="numericFilter(this)" placeholder="Enter DEL Point" class="delPoint"/></td>'
			+ '<td align="left" valign="middle" class="bdrBotm unEditredelPoint"><input type="text" onkeyup="numericFilter(this)" placeholder="Enter REDEL Point"  class="redelPoint" data-toggle="tooltip" title="You can add/edit text in this field only for your reference. The text entered shall be limited to you only and GAIL has nothing to do with this." /></td>'
			+ '<td align="left" valign="middle" class="bdrBotm sellerRedelPoint" id="SellerRedelPoint"></td>'
			+ '</tr>',
	mulGtaDataHeader : '<thead> <tr>'
			+ ' <th width="16%" align="left" bgcolor="#f2f3f3" scope="col">Contract Reference<div>Contract Reference</div></th>'
			+ '<th width="20%" align="left" bgcolor="#f2f3f3" scope="col">Contract Short Text<div>Contract Short Text</div></th>'
			+ '<th width="24%" align="left" bgcolor="#f2f3f3" id="MaterialCode" scope="col">Material Code<div>Material Code</div></th>'
			+ '<th width="20%" align="left" bgcolor="#f2f3f3" id="MaterialCode" scope="col">Customer Code<div>Customer Code</div></th>'
			+ '<th width="10%" align="left" bgcolor="#f2f3f3" scope="col">Gas Date<div>Gas Date</div></th>'
			+ '<th width="20%" align="left" bgcolor="#f2f3f3" scope="col">DEL Point<div>DEL Point</div></th>'
			+ '<th width="20%" align="left" bgcolor="#f2f3f3" scope="col">REDEL Point<div>REDEL Point</div></th>'
			+ '<th width="15%" align="left" bgcolor="#f2f3f3" scope="col"> DEL Point By Seller<div>DEL Point By Seller</div></th>'
			+ '</tr> </thead> <tbody>',

	mulBothDataRow : '<tr class="bothDataRowParent">'
			+ '<td align="left" valign="middle" class="bdrBotm contactRefrence"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm unEditcontactName" ></td>'
			+ '<td align="left" valign="middle" class="bdrBotm materialCode" id="MaterialCode"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm customerCode" id="MaterialCode"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm gasDate"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm unEditdelPoint"><span class="displayNone customerDesc"></span><span class="displayNone materialDesc"></span><span class="displayNone contractValidity"></span><span class="displayNone contractName"></span><span class="displayNone contractId"></span><span class="displayNone nominationId"></span><span class="displayNone revesionNo"></span><input type="text" onkeyup="numericFilter(this)" placeholder="Enter DEL Point" class="delPoint"/></td>'
			+ '<td align="left" valign="middle" class="bdrBotm unEditredelPoint"><input type="text" onkeyup="numericFilter(this)" placeholder="Enter REDEL Point"  class="redelPoint" data-toggle="tooltip" title="You can add/edit text in this field only for your reference. The text entered shall be limited to you only and GAIL has nothing to do with this." /></td>'
			+ '<td align="left" valign="middle" class="bdrBotm sellerRedelPoint" id="SellerRedelPoint"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm uom"></td>'
			
			+ '</tr>',
	mulBothDataHeader : '<thead> <tr>'
			+ ' <th width="20%" align="left" bgcolor="#f2f3f3" scope="col">Contract Reference<div>Contract Reference</div></th>'
			+ '<th width="20%" align="left" bgcolor="#f2f3f3" scope="col">Contract Short Text<div>Contract Short Text</div></th>'
			+ '<th width="20%" align="left" bgcolor="#f2f3f3" id="MaterialCode" scope="col">Material Code<div>Material Code</div></th>'
			+ '<th width="15%" align="left" bgcolor="#f2f3f3" id="MaterialCode" scope="col">Customer Code<div>Customer Code</div></th>'
			+ '<th width="10%" align="left" bgcolor="#f2f3f3" scope="col">Gas Date<div>Gas Date</div></th>'
			+ '<th width="15%" align="left" bgcolor="#f2f3f3" scope="col">DEL Point<div>DEL Point</div></th>'
			+ '<th width="15%" align="left" bgcolor="#f2f3f3" scope="col">REDEL Point<div>REDEL Point</div></th>'
			+ '<th width="15%" align="left" bgcolor="#f2f3f3" scope="col"> DEL Point By Seller <div>DEL Point By Seller</div></th>'
			+ '<th width="15%" align="left" bgcolor="#f2f3f3" scope="col">UOM<div>UOM</div></th>'
			+ '</tr> </thead> <tbody>',

	tickerDataHeader : '<thead> <tr>'
			+ ' <th width="45%" align="left" bgcolor="#f2f3f3" scope="col">Ticker Text<div>Ticker Text</div></th>'
			+ ' <th width="20%" align="left" bgcolor="#f2f3f3" scope="col">Start<div>Start</div></th>'
			+ ' <th width="20%" align="left" bgcolor="#f2f3f3" scope="col">End<div>End</div></th>'
			+ '<th width="15%" align="left" bgcolor="#f2f3f3" scope="col">Action<div>Action</div></th>'
			+ '</tr> </thead> <tbody>',
	tickerDataRow : '<tr class="tickerDataRowParent">'
			+ '<td align="left" valign="middle" class="bdrBotm tickerId"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm tickerText"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm start"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm end"></td>'
			+ '<td align="left" valign="middle"> <div class="bdrBotm btnEdit" /> <div class="bdrBotm btnDelete" /> </td>'
			+ '</tr>',

	notificationDataHeader : '<thead> <tr>'
			+ ' <th width="60%" align="left" bgcolor="#f2f3f3" scope="col">Ticker Text<div>Ticker Text</div></th>'
			+ ' <th width="20%" align="left" bgcolor="#f2f3f3" scope="col">Send To<div>Send To</div></th>'
			+ '</tr> </thead> <tbody>',
	notificationDataRow : '<tr class="notificationDataRowParent">'
			+ '<td align="left" valign="middle" class="bdrBotm pushNotificationText"></td>'
			+ '<td align="left" valign="middle" class="bdrBotm sendTo"></td>'
			+ '</tr>',

	materialDescInfo : '<i class="impIcon" onclick="showMaterialDesc(this)"></i>',
	customerDescInfo : '<i class="impIcon" onclick="showCustomerDesc(this)"></i>',
	contractValidityInfo : '<i class="impIcon" onclick="showContractValidity(this)"></i>',
	// contractNameInfo: '<i class="impIcon"
	// onclick="showContractName(this)"></i>',
	contractNameInfo : '<i class="impIcon" onclick="showContractDisplayTextName(this)"></i>',

	materialDescInfo2 : '<i class="impIcon" onclick="showMaterialDesc(this)"></i>',
	customerDescInfo2 : '<i class="impIcon" onclick="showCustomerDesc(this)"></i>',
	contractValidityInfo2 : '<i class="impIcon" onclick="showContractValidity(this)"></i>',
	// contractNameInfo2: '<i class="impIcon"
	// onclick="showContractName(this)"></i>',
	contractNameInfo2 : '<i class="impIcon" onclick="showContractTextName(this)"></i>',
	editcontractName : '<i class="editIcon" onclick="showContractShortText(this)"></i>',
	addcontractName : '<button class="ComnSaveBtn" onclick="showContractShortText(this)">Add Contract Text</button>',
	addSellerRedelPointButton : '<button class="ComnSaveBtn" onclick="showSellerRedelPointValuePoup(this)">Enter Nomination</button>',
	editSellerRedelPoint : '<i class="editIcon" onclick="showSellerRedelPointValuePoup(this)"></i>',
/*
 * addcontractName2: '<button class="ComnSaveBtn"
 * onclick="showContractShortText(this)">Add Contract Text</button>',
 * editcontractName2: '<i class="editIcon"
 * onclick="showContractShortText(this)"></i>'
 */

};
