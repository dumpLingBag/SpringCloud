package com.rngay.common.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptUtil {

    public static String SP_KEY = "jiaozibao";

    private byte[] md5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return md5.digest(str.getBytes("GBK"));
    }

    /**
    * 进行 MD5 加密
    * @Author: pengcheng
    * @Date: 2018/12/13
    */
    public static String MD5encrypt(String source) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(source.getBytes());

            byte[] digest = md5.digest();
            int i;
            StringBuilder builder = new StringBuilder();
            for (byte aDigest : digest) {
                i = aDigest;
                if (i < 0)
                    i += 256;
                if (i < 16)
                    builder.append("0");
                builder.append(Integer.toHexString(i));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
    * 得到3-DES的密钥匙
    * 根据接口规范，密钥匙为24个字节，md5加密出来的是16个字节，因此后面补8个字节的0
    * @Author: pengcheng
    * @Date: 2018/12/13
    */
    private byte[] getEnKey(String key) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] bytes = md5(key);
        byte[] desKey = new byte[24];
        int i = 0;
        while (i < bytes.length && i < 24) {
            desKey[i] = bytes[i];
            i++;
        }
        if (i < 24) {
            desKey[i] = 0;
            i++;
        }
        return desKey;
    }

    /**
    * 3-DES加密 enKey 要进行3-DES加密的byte[]  src 3-DES加密密钥
    * @Author: pengcheng
    * @Date: 2018/12/13
    */
    private byte[] Encrypt(String src, byte[] enKey) throws Exception {
        DESedeKeySpec dks = new DESedeKeySpec(enKey);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(src.getBytes(StandardCharsets.UTF_16LE));
    }

    /**
    * 进行3-DES解密（密钥匙等同于加密的密钥匙）。
    * @Author: pengcheng
    * @Date: 2018/12/13
    */
    private String deCrypt(byte[] debase64, String spKey) throws Exception {
        Cipher  cipher = Cipher.getInstance("DESede");
        byte[] key = getEnKey(spKey);
        DESedeKeySpec dks = new DESedeKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey sKey = keyFactory.generateSecret(dks);
        cipher.init(Cipher.DECRYPT_MODE, sKey);
        byte cipherText[] = cipher.doFinal(debase64);
        return new String(cipherText, StandardCharsets.UTF_16LE);
    }

    /**
    * 3-DES解密 进行base64编码
    * @Author: pengcheng
    * @Date: 2018/12/13
    */
    public String get3DESDecrypt(String src, String spkey) throws Exception {
        String source = Base64.decode(src);
        return deCrypt(Base64.oldDecode(source), spkey);
    }

    /**
    * 3-DES加密 进行base64编码
    * @Author: pengcheng
    * @Date: 2018/12/13
    */
    public String get3DESEncrypt(String src, String spkey) throws Exception {
        // 得到3-DES的密钥匙
        byte[] enKey = getEnKey(spkey);
        byte[] encryptedData = Encrypt(src, enKey);

        String source = Base64.encodeBytes(encryptedData);

        return Base64.encode(source);
    }

}
