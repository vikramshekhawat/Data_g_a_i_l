package com.gail.utility;

import java.util.List;

import com.gail.responseData.ContactData;

public class ContactDataList {
	
	private List<ContactData> contactDataList ;

	public List<ContactData> getContactDataList() {
		return contactDataList;
	}

	public void setContactDataList(List<ContactData> contactDataList) {
		this.contactDataList = contactDataList;
	}

	@Override
	public String toString() {
		return "ContactDataList [contactDataList=" + contactDataList + "]";
	}
	


}
