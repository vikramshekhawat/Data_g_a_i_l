package com.gail.dao;

import java.util.List;

import com.gail.model.Region;

public interface RegionDao extends GenericDao<Region, Long> {

	public List<Region> getActiveRegions();
	
	public Region getRegionDetail(String regionCode);

}
