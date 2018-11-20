package com.gail.service;

import com.gail.model.Material;
import com.gail.responseData.MaterialDataList;
import com.gail.utility.GailNominationServiceException;

public interface MaterialService extends GenericService<Material, Long> {

	public MaterialDataList getMaterials() throws GailNominationServiceException;
}
