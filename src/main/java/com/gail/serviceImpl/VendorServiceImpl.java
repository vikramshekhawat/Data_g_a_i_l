package com.gail.serviceImpl;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gail.dao.VendorDao;
import com.gail.model.Vendor;
import com.gail.service.VendorService;

@Service("vendorService")
public class VendorServiceImpl extends GenericServiceImpl<Vendor, Long> implements VendorService {

	@Autowired
	VendorDao vendorDao;

	private static final Logger logger = LoggerFactory.getLogger(VendorServiceImpl.class);

	@Override
	public BigInteger getVendorUserId(String materialCode) {
		BigInteger userId = null;
		try {
			Vendor vendordata = vendorDao.getVendorByMaterialCode(materialCode);
			userId = vendordata.getUserId();
		} catch (Throwable ex) {
			logger.info("Exception in getVendorData by materialCode");
		}
		return userId;
	}

}