package com.gail.dao;

import java.math.BigInteger;

import com.gail.model.Vendor;
import com.gail.utility.GailNominationServiceException;

public interface VendorDao extends GenericDao<Vendor, Long> {

	public Boolean isVendor(BigInteger userId);

	public Vendor getVendorByMaterialCode(String materialCode);

	public BigInteger getVendorUserId(String contractId) throws GailNominationServiceException;

}
