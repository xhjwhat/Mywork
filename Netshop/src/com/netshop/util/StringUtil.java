package com.netshop.util;

import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import android.net.MailTo;
import android.text.TextUtils;
import android.util.Base64;

public class StringUtil {

	public static boolean isMobileNum(String mobiles) {
		String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * @param nickname
	 *            输入的是字母或者中文
	 * @return
	 */
	public static boolean checkNickName(String nickname) {
		String regex = "^[a-zA-Z\u4e00-\u9fa5]+$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(nickname);
		return m.matches();
	}

	public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

	/**
	 * DES算法，加密
	 * 
	 * @param data
	 *            待加密字符串
	 * @param key
	 *            加密私钥，长度不能够小于8位
	 * @return 加密后的字节数组，一般结合Base64编码使用
	 * @throws CryptException
	 *             异常
	 */
	public static String encode(String key, String data) throws Exception {
		return encode(key, data.getBytes());
	}

	/**
	 * DES算法，加密
	 * 
	 * @param data
	 *            待加密字符串
	 * @param key
	 *            加密私钥，长度不能够小于8位
	 * @return 加密后的字节数组，一般结合Base64编码使用
	 * @throws CryptException
	 *             异常
	 */
	public static String encode(String key, byte[] data) throws Exception {
		try {
			DESKeySpec dks = new DESKeySpec(key.getBytes());

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// key的长度不能够小于8位字节
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);

			byte[] bytes = cipher.doFinal(data);

			return Base64.encodeToString(bytes,Base64.DEFAULT);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * DES算法，解密
	 * 
	 * @param data
	 *            待解密字符串
	 * @param key
	 *            解密私钥，长度不能够小于8位
	 * @return 解密后的字节数组
	 * @throws Exception
	 *             异常
	 */
	public static byte[] decode(String key, byte[] data) throws Exception {
		try {
			SecureRandom sr = new SecureRandom();
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// key的长度不能够小于8位字节
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
			return cipher.doFinal(data);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	public static void main(String[] args) {
		String str = "si=1&cd=0001&ap=7012125874,AE79B33B63BA28A0443B62D1EC1032FF&pn=13535355656";
		try {
			System.out.println(decode("12345678",str.getBytes()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// http://www.xxxxxx.com/android.do?d=f971b468bc4d524d18ab1cdae6c631cd13426fd6a4eb8859ecbbe13fa10594b570f89bbcf44b578a3f27022b767a17ab3167803450f47fa5c806d1f9f43ade20ef4b155d51dfd4c6ee6d252986b4780e&vi=101000000

	}

	/**
	 * 获取编码后的值
	 * 
	 * @param key
	 * @param data
	 * @return
	 * @throws Exception
	 */
	/*public static String decodeValue(String key, String data) {
		byte[] datas;
		String value = null;
		try {
			if (System.getProperty("os.name") != null
					&& (System.getProperty("os.name").equalsIgnoreCase("sunos") || System
							.getProperty("os.name").equalsIgnoreCase("linux"))) {
				datas = decode(key, Base64.encodeToString(data,Base64.DEFAULT));
			} else {
				datas = decode(key, Base64.encodeToString(data));
			}

			value = new String(datas);
		} catch (Exception e) {
			value = "";
		}
		return value;
	}*/
}
