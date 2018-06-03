package com.jh.platform.util;

import com.jh.platform.model.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;


/**
 * @author jianghong
 *
 * @date 2017-7-27 下午4:32:06
 *
 */
public class PasswordHelper {

	public static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

	public static  String algorithmName = "md5";
	public static  int hashIterations = 2;

//	public static void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
//		this.randomNumberGenerator = randomNumberGenerator;
//	}

//	public void setAlgorithmName(String algorithmName) {
//		this.algorithmName = algorithmName;
//	}
//
//	public void setHashIterations(int hashIterations) {
//		this.hashIterations = hashIterations;
//	}

	/**
	 * 设置后台用户密码
	 *
	 * @param user
	 */
	public static void encryptPassword(User user) {
		user.setSalt(randomNumberGenerator.nextBytes().toHex());
		String newPassword = new SimpleHash(
				algorithmName,
				user.getPassword(),
				ByteSource.Util.bytes(user.getCredentialsSalt()),
				hashIterations).toHex();
		user.setPassword(newPassword);
	}


	/**
	 * 加密密码（后台）
	 *
	 * @param user
	 * @return 密码
	 */
	public static String decryptPassword(User user) {
		return new SimpleHash(
				algorithmName,
				user.getPassword(),
				ByteSource.Util.bytes(user.getCredentialsSalt()),
				hashIterations).toHex();
	}


	/**
	 * 加密密码
	 * @param pwd
	 * @return
	 */
	public static String decryptPassword(String pwd, String credentialsSalt) {
		return new SimpleHash(
				algorithmName,
				pwd,
				ByteSource.Util.bytes(credentialsSalt),
				hashIterations).toHex();
	}



//	public static void main(String[] args) {
//		PasswordHelper pHelper = new PasswordHelper();
//		User user = new User();
//		user.setUsercode("16666");
//		user.setPassword("888888");
//		pHelper.encryptPassword(user);
//		System.err.println(user);
//	}
}
