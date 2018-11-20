package com.gail.dao;

import java.math.BigInteger;

import com.gail.model.Payer;

public interface PayerDao extends GenericDao<Payer, Long> {

	public Payer fetchUserByPayerId(BigInteger payerId);

	public Payer getPayerByKey(String payerKey);

}
