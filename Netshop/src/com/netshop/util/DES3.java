/**
 * @(#)DES3.java 1.0 2014-3-4
 * @Copyright:  Copyright 2000 - 2014 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2014-3-4
 * Author:      dengxinjun 42715
 * Version:     MPRSP_CAPV1.D1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package com.netshop.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import android.annotation.SuppressLint;


public final class DES3
{
    
    /**
     * 字符编码格式
     */
    public static final String CHARSET = "UTF-8";
    
    /**
     * 密钥
     */
    private static final String SECRET_KEY = "0MLk7TY6hR/PFCefQUcDX6tdK";
    
    /**
     * 向量参数规格
     */
    private static final String IV_PARAMETER_SPEC = "init Vec";
    
    /**
     * 密钥算法
    */
    private static final String KEY_ALGORITHM = "DESede";
    
    /**
     * 工作模式、填充方式
     */
    private static final String DEFAULT_CIPHER_ALGORITHM =
        "DESede/CBC/PKCS5Padding";
    
    /**
     * 加密
     * @param content 待加密数据
     * @return 加密数据（base64编码）
     * @throws Exception
     */
    @SuppressLint("TrulyRandom")
	public static String encrypt(String content) throws Exception
    {
        DESedeKeySpec spec = new DESedeKeySpec(SECRET_KEY.getBytes());
        SecretKeyFactory keyfactory =
            SecretKeyFactory.getInstance(KEY_ALGORITHM);
        Key deskey = keyfactory.generateSecret(spec);
        IvParameterSpec ips = new IvParameterSpec(IV_PARAMETER_SPEC.getBytes());
        
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] encryptData = cipher.doFinal(content.getBytes(CHARSET));
        
        return Base64.encode(encryptData);
    }
    
    /**
     * 解密
     * @param content 待解密数据（base64编码）
     * @return 解密数据
     */
    public static String decrypt(String content)
    {
        try
        {
            DESedeKeySpec spec = new DESedeKeySpec(SECRET_KEY.getBytes());
            SecretKeyFactory keyfactory =
                SecretKeyFactory.getInstance(KEY_ALGORITHM);
            Key deskey = keyfactory.generateSecret(spec);
            IvParameterSpec ips =
                new IvParameterSpec(IV_PARAMETER_SPEC.getBytes());
            
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
            byte[] decryptData = cipher.doFinal(Base64.decode(content));
            
            return new String(decryptData, CHARSET);
        }
        catch (Exception e)
        {
            return null;
        }
    }
      
}
