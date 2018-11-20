var htmlConstants = {


headerCommon:  '<div class="headerMC"> <a href="#" title="GAIL - Gas Nomination" class="gail-logo"> <img src="images/GAIL-Logo-inner.png" width="76" height="56" alt="Gail Logo">'+
				'<p class="gailTaglin1">Gail (India) Limited</p>'+
				'<p class="gailTaglin2">India\'s <span>Youngest</span> Maharatna</p></a>'+
				'<div class="dropRight"><div class="dropDownMenu"><img src="images/dropBtn.png" width="38" height="49" /></div>'+
				'<div class="topDropDown"><img src="images/arrowDropDown.png" width="30" height="20" class="arrowDrop" />'+
				'<ul><li><a href="edit-profile.html">Edit Profile</a></li><li><a href="#" onclick="logout()">Logout</a></li></ul></div>'+
				'<div class="userName"><p class="user loginUserName"></p><p class="title">(user)</p></div></div><div class="clear"></div></div>',
noDistanceData:  '<tr>'+
				'<td height="400" align="center" valign="middle" bgcolor="#eef0f0"  scope="col"><img src="images/nodatafound2.png" class="img-responsive">'+
				'</td>',
						
alertModel: '<div class="darkBg alertModel1"></div>'+
			'<div class="popUpBox alertModel2"><h2>Alert!</h2><p></p>'+
			'<div class="ButtonBox"><button type="submit" class="popUpBtn" id="cancel">Ok</button></div></div>',
			
logoutModel: '<div class="darkBg logoutModel1"></div>'+
			'<div class="popUpBox logoutModel2"><h2>Alert!</h2><p></p>'+
			'<div class="ButtonBox"><button type="submit" class="popUpBtn" id="cancel" onclick="logout();">Ok</button></div></div>',
			
successModel:'<div class="darkBg successModel1"></div>'+
			 '<div class="popUpBox successModel2"><h2>Success!</h2><p></p>'+
			 '<div class="ButtonBox"><button type="submit" class="popUpBtn" id="cancel">Ok</button></div></div>',

successNominationModel:'<div class="darkBg successNominationModel1"></div>'+
			 '<div class="popUpBox successNominationModel2"><h2>Success!</h2><p></p>'+
			 '<div class="ButtonBox"><button type="submit" class="popUpBtn" id="cancel" onclick="getContractsOrNominationsUrl();">Ok</button></div></div>',

successTickerModel:'<div class="darkBg successTickerModel1"></div>'+
 			 '<div class="popUpBox successTickerModel2"><h2>Success!</h2><p></p>'+
 			 '<div class="ButtonBox"><button type="submit" class="popUpBtn" id="cancel" >Ok</button></div></div>',

customerDescModel:'<div class="darkBg customerDescModel1"></div>'+
			 '<div class="popUpBox customerDescModel2"><h2>Customer Description!</h2><p></p>'+
			 '<div class="ButtonBox"><button type="submit" class="popUpBtn" id="cancel">Ok</button></div></div>',

materialDescModel:'<div class="darkBg materialDescModel1"></div>'+
			 '<div class="popUpBox materialDescModel2"><h2>Material Description!</h2><p></p>'+
			 '<div class="ButtonBox"><button type="submit" class="popUpBtn" id="cancel">Ok</button></div></div>',

contractValidity:'<div class="darkBg contractValidity1"></div>'+
			 '<div class="popUpBox contractValidity2"><h2>Contract Validity</h2><p></p>'+
			 '<div class="ButtonBox"><button type="submit" class="popUpBtn" id="cancel">Ok</button></div></div>',

errorModel: '<div class="darkBg errorModel1"></div>'+
			'<div class="popUpBox errorModel2"><h2>Error!</h2><p></p>'+
			'<div class="ButtonBox button1"><button type="submit" class="popUpBtn" id="cancel">Ok</button></div></div>',

forgetPasswordModel: '<div class="darkBg forgetPassword"></div><div class="popUpBox forgetPassword"'+ 
					'style="height:225px;"><h2>Forgot password</h2><p style="text-align:left; line-height:18px;">We will send a password on the email id registered with your username.</p><input type="text" placeholder="Enter username" class="forgotPasswordUserId"><p style="color:red;text-align:left;" class="enterUserName"><p/><div class="ButtonBox FPassBoxPad" style="padding: 10% 26% 1% 26%;"><button type="submit" class="popUpBtn"  onclick="sendForgetPassword()">Send</button><button type="submit" class="popUpBtn" id="cancel" onclick="clearUserMsg();">Cancel</button><div class="clearboth"></div></div></div>'	,
gsaDataRow:  '<tr class="gsaDataRowParent">'+
			 '<td align="left" valign="middle" class="bdrBotm contactRefrence"></td>'+
			 '<td align="left" valign="middle" class="bdrBotm materialCode" id="MaterialCode"></td>'+
			 '<td align="left" valign="middle" class="bdrBotm customerCode" id="MaterialCode"></td>'+
			 '<td align="left" valign="middle" class="bdrBotm unEditredelPoint" ><span class="displayNone customerDesc"></span><span class="displayNone materialDesc"></span><span class="displayNone contractValidity"></span><span class="displayNone contractId"></span><span class="displayNone nominationId"></span><span class="displayNone revesionNo"></span><input type="text" onkeyup="numericFilter(this)" placeholder="Enter REDEL Point" class="redelPoint"/></td>'+
			 '<td align="left" valign="middle" class="bdrBotm uom"></td>'+
		   '</tr>',
		   
gsaDataHeader: '<tr >'+
				' <th width="16%" align="left" bgcolor="#f2f3f3" scope="col">Contract Reference</th>'+
				 '<th width="24%" align="left" bgcolor="#f2f3f3" id="MaterialCode" scope="col">Material Code</th>'+
				 '<th width="20%" align="left" bgcolor="#f2f3f3" id="MaterialCode" scope="col">Customer Code</th>'+
				 '<th width="20%" align="left" bgcolor="#f2f3f3" scope="col">REDEL Point</th>'+
				 '<th width="20%" align="left" bgcolor="#f2f3f3" scope="col">UOM</th>'+
			   '</tr>',
                        
gtaDataRow: '<tr class="gtaDataRowParent">'+
			 '<td align="left" valign="middle" class="bdrBotm contactRefrence"></td>'+
			 '<td align="left" valign="middle" class="bdrBotm materialCode" id="MaterialCode"></td>'+
			 '<td align="left" valign="middle" class="bdrBotm customerCode" id="MaterialCode"></td>'+
			 '<td align="left" valign="middle" class="bdrBotm  unEditdelPoint"><span class="displayNone customerDesc"></span><span class="displayNone materialDesc"></span><span class="displayNone contractValidity"></span><span class="displayNone contractId"></span><span class="displayNone nominationId"></span><span class="displayNone revesionNo"></span><input type="text" onkeyup="numericFilter(this)" placeholder="Enter DEL Point" class="delPoint"/></td>'+
			 '<td align="left" valign="middle" class="bdrBotm unEditredelPoint"><input type="text" onkeyup="numericFilter(this)" placeholder="Enter REDEL Point"  class="redelPoint"/></td>'+
		   '</tr>',
gtaDataHeader: '<tr>'+
				' <th width="16%" align="left" bgcolor="#f2f3f3" scope="col">Contract Reference</th>'+
				 '<th width="24%" align="left" bgcolor="#f2f3f3" id="MaterialCode" scope="col">Material Code</th>'+
				 '<th width="20%" align="left" bgcolor="#f2f3f3" id="MaterialCode" scope="col">Customer Code</th>'+
				 '<th width="20%" align="left" bgcolor="#f2f3f3" scope="col">DEL Point</th>'+
				 '<th width="20%" align="left" bgcolor="#f2f3f3" scope="col">REDEL Point</th>'+
			   '</tr>',
materialDescInfo: '<i class="impIcon" onclick="showMaterialDesc(this)"></i>',			   
customerDescInfo: '<i class="impIcon" onclick="showCustomerDesc(this)"></i>',		
contractValidityInfo: '<i class="impIcon" onclick="showContractValidity(this)"></i>',	   

materialDescInfo2: '<i class="impIcon" onclick="showMaterialDesc(this)"></i>',			   
customerDescInfo2: '<i class="impIcon" onclick="showCustomerDesc(this)"></i>',
contractValidityInfo2: '<i class="impIcon" onclick="showContractValidity(this)"></i>',			   
			
};
