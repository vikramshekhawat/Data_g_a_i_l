package com.gail.service;

import com.gail.model.Region;
import com.gail.responseData.RegionDataList;
import com.gail.utility.GailNominationServiceException;

public interface RegionService extends GenericService<Region, Long> {

	public RegionDataList getRegions() throws GailNominationServiceException;
}
