package com.gail.daoImpl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gail.dao.AuthUserDao;
import com.gail.model.AuthUser;
import com.gail.utility.Util;

@Repository("authUserDao")
public class AuthUserDaoImpl extends GenericDaoImpl<AuthUser, Long> implements AuthUserDao {

	@Transactional
	public boolean isValidAdmin(String username, String password) {
		Query query = currentSession().createQuery("from AuthUser where username = :username")
				.setParameter("username", username);
		AuthUser user = null;
		if (query.list().size() > 0) {
			user = (AuthUser) query.list().get(0);
			Util.checkPassword(password, user.getPassword());
			return Util.checkPassword(password, user.getPassword());
		} else
			return false;

		/*
		 * Query query = currentSession()
		 * .createQuery("select username, password from AuthUser where username = :username"
		 * ); query.setParameter("username", username); List<AuthUser>
		 * loginDetails = null; if (query.list().size() > 0) { loginDetails =
		 * query.list(); } return Util.checkPassword(password,
		 * loginDetails.get(0).getPassword());
		 */
	}

}
