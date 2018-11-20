package com.gail.dao;

import java.util.List;

import com.gail.model.Material;

public interface MaterialDao extends GenericDao<Material, Long>  {
	
	public List<Material> getActiveMaterials();

	public Material getMaterialByCode(String materialCode);
	
}
