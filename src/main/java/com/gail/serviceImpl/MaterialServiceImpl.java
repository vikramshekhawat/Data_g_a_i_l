package com.gail.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gail.dao.MaterialDao;
import com.gail.dao.UserAuthTokenDao;
import com.gail.model.Material;
import com.gail.responseData.MaterialData;
import com.gail.responseData.MaterialDataList;
import com.gail.service.MaterialService;
import com.gail.utility.Constants;
import com.gail.utility.ErrorDetails;
import com.gail.utility.GailNominationServiceException;
import com.google.common.collect.Lists;

@Service("materialService")
public class MaterialServiceImpl extends GenericServiceImpl<Material, Long> implements MaterialService {

	@Autowired
	MaterialDao materialDao;

	@Autowired
	UserAuthTokenDao userAuthTokenDao;

	private static final Logger logger = LoggerFactory.getLogger(MaterialServiceImpl.class);

	@Override
	public MaterialDataList getMaterials() throws GailNominationServiceException {
		logger.info("fetching all materials");
		MaterialDataList materialDataList = new MaterialDataList();
		MaterialData materialData = null;
		List<MaterialData> list = Lists.newArrayList();
		List<Material> materialList = materialDao.getActiveMaterials();
		if (!materialList.isEmpty()) {
			for (Material material : materialList) {
				materialData = new MaterialData();
				materialData.setMaterialId(material.getMaterialId());
				materialData.setMaterialCode(material.getMaterialCode());
				list.add(materialData);
			}
			materialDataList.setMaterialDataList(list);
		} else {
			logger.error("error while fetching all the materials");
			throw new GailNominationServiceException(
					new ErrorDetails(Constants.GET_MATERIAL_ERROR, Constants.ERROR_TYPE_CODE_INTERNAL,
							Constants.ERROR_TYPE_ERROR, "Error while getting material list"));
		}
		return materialDataList;
	}

}