package com.gail.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gail.dao.RegionDao;
import com.gail.dao.UserAuthTokenDao;
import com.gail.model.Region;
import com.gail.responseData.RegionData;
import com.gail.responseData.RegionDataList;
import com.gail.service.RegionService;
import com.gail.utility.Constants;
import com.gail.utility.ErrorDetails;
import com.gail.utility.GailNominationServiceException;
import com.google.common.collect.Lists;

@Service("regionService")
public class RegionServiceImpl extends GenericServiceImpl<Region, Long> implements RegionService {

	@Autowired
	RegionDao regionDao;

	@Autowired
	UserAuthTokenDao userAuthTokenDao;

	private static final Logger logger = LoggerFactory.getLogger(RegionServiceImpl.class);

	@Override
	public RegionDataList getRegions() throws GailNominationServiceException {
		logger.info("fetching all regions");
		RegionDataList regionDataList = new RegionDataList();
		RegionData regionData = null;
		List<RegionData> list = Lists.newArrayList();
		List<Region> regionList = regionDao.getActiveRegions();
		if (!regionList.isEmpty()) {
			for (Region region : regionList) {
				regionData = new RegionData();
				regionData.setRegionId(region.getRegionId().toString());
				regionData.setRegionName(region.getRegionName());
				list.add(regionData);
			}
			regionDataList.setDataList(list);
		} else {
			logger.error("error while fetching all the regions");
			throw new GailNominationServiceException(
					new ErrorDetails(Constants.GET_REGION_ERROR, Constants.ERROR_TYPE_CODE_INTERNAL,
							Constants.ERROR_TYPE_ERROR, "Error while getting region."));
		}
		return regionDataList;
	}

}