package com.gail.dao;

import com.gail.model.UserTokenMapping;


public interface UserAuthTokenDao extends GenericDao<UserTokenMapping, Long> {
	int fetchTokenId(String token);
	
	void removeById(Long tokenId);


}
