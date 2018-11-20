package com.gail.dao;

import com.gail.model.AuthUser;

public interface AuthUserDao extends GenericDao<AuthUser, Long>{
	
	public boolean isValidAdmin(String username, String password);

}
