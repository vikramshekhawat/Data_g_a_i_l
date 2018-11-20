package com.gail.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gail.dao.MaterialDao;
import com.gail.model.Material;
import com.gail.utility.Constants;

@Repository("materialDao")
public class MaterialDaoImpl extends GenericDaoImpl<Material, Long> implements MaterialDao {

	public static final Logger logger = LoggerFactory.getLogger(Material.class);

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Material> getActiveMaterials() {
		Criteria cr = currentSession().createCriteria(Material.class);
		cr.add(Restrictions.eq("status", Constants.ACTIVE_STATUS));
		List<Material> materialList = cr.list();
		if (materialList.isEmpty()) {
			logger.info("material data not found  {}");
		}
		return materialList;
	}

	@Transactional
	@Override
	public Material getMaterialByCode(String materialCode) {
		Material materialModel = null;
		Criteria cr = currentSession().createCriteria(Material.class);
		cr.add(Restrictions.eq("materialCode", materialCode));
		materialModel = (Material) cr.uniqueResult();
		if (materialModel != null) {
			logger.info("Material Found for materialCode {} ", materialCode);
			return materialModel;
		} else {
			logger.info("Material Not Found for materialCode {} ", materialCode);
			return null;
		}
	}

}
