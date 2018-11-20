package com.gail.utility;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RandomNumberGenerator {
	private static SecureRandom random = new SecureRandom();

	public static String getNewRandomNumber() {

		return new BigInteger(32, random).toString(32);
	}
}
