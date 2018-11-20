package com.gail.dao;

import java.math.BigInteger;
import java.util.List;

import com.gail.model.User;
import com.gail.responseData.DownloadUserDTO;

public interface UserDao extends GenericDao<User, Long> {

	public User fetchUserByUserName(String userName);

	public String getQuery();

	public List<DownloadUserDTO> getUserReport(String query);

	public User getPayerByUserId(BigInteger userId);

	public User getUserByUserName(String userName);

}
