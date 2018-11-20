package com.gail.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gail.dao.RegionDao;
import com.gail.model.Material;
import com.gail.model.Region;
import com.gail.utility.Constants;

@Repository("regionDao")
public class RegionDaoImpl extends GenericDaoImpl<Region, Long> implements RegionDao {

	public static final Logger logger = LoggerFactory.getLogger(Material.class);

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Region> getActiveRegions() {
		Criteria cr = currentSession().createCriteria(Region.class);
		cr.add(Restrictions.eq("status", Constants.ACTIVE_STATUS_FOR_REGION));
		List<Region> regionlist = cr.list();

		if (regionlist.isEmpty()) {
			logger.info("No  region found  {}");
		}
		return regionlist;
	}

	@Transactional
	public Region getRegionDetail(String regionCode) {
		Region region = null;
		Criteria cr = currentSession().createCriteria(Region.class);
		cr.add(Restrictions.eq("regionCode", regionCode));
		region = (Region) cr.uniqueResult();
		if (region != null) {
			logger.info("Data found for regionCode  {} ", regionCode);
			return region;
		}
		logger.info("No Data found for regionCode {}  ", regionCode);
		return null;
	}

}
