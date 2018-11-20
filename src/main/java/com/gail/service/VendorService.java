package com.gail.service;

import java.math.BigInteger;

import com.gail.model.Vendor;

public interface VendorService extends GenericService<Vendor, Long> {

	public BigInteger getVendorUserId(String materialCode);

}
