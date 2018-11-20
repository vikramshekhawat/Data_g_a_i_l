package com.gail.daoImpl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gail.dao.UserAuthTokenDao;
import com.gail.model.UserTokenMapping;
import com.gail.utility.QueryConstants;

@Repository("userAuthTokenDao")
public class UserAuthTokenDaoImpl extends GenericDaoImpl<UserTokenMapping, Long> implements UserAuthTokenDao{

	@Transactional
	public int fetchTokenId(String token) {
		Query query = currentSession().createQuery(QueryConstants.fetchTokenId).setParameter("token", token);
		if (query.list().size() > 0) {
			int tokenId = Integer.parseInt(query.list().get(0).toString());
			System.out.println(tokenId);
			return tokenId;
		} else
			return 0;
	}
	
	@Transactional
	public void removeById(Long tokenId){
		currentSession().createQuery(QueryConstants.removeByTokenId).setParameter("id", tokenId).executeUpdate();
	};
}
