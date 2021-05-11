package com.rngay.oauth.util;

import com.rngay.feign.oauth.dto.RsaDTO;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtil {

    private static final Logger logger = LoggerFactory.getLogger(RSAUtil.class);

    private static final String RSA = "RSA";
    private static final Integer INITIALIZE = 2048;
    private static final String ALGORITHM = "SHA1PRNG";
    private static final String SEED = "rsa_bit";

    public static RsaDTO genKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator pairGenerator = KeyPairGenerator.getInstance(RSA);
        // SecureRandom random = SecureRandom.getInstance(ALGORITHM);
        // random.setSeed(SEED.getBytes());
        SecureRandom random = new SecureRandom();
        pairGenerator.initialize(INITIALIZE, random);
        KeyPair keyPair = pairGenerator.genKeyPair();
        PublicKey rsaPublicKey = keyPair.getPublic();
        PrivateKey rsaPrivateKey = keyPair.getPrivate();
        String rsaPublicKeyStr = new String(Base64.encodeBase64(rsaPublicKey.getEncoded()));
        String rsaPrivateKeyStr = new String(Base64.encodeBase64(rsaPrivateKey.getEncoded()));
        logger.info("rsaPublicKeyStr:{}", rsaPublicKeyStr);
        logger.info("rsaPrivateKeyStr:{}", rsaPrivateKeyStr);
        RsaDTO rsaDTO = new RsaDTO();
        rsaDTO.setPriveKey(rsaPrivateKeyStr);
        rsaDTO.setPublicKey(rsaPublicKeyStr);
        return rsaDTO;
    }

    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] decoded = Base64.decodeBase64(key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(new X509EncodedKeySpec(decoded));
    }

    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] decoded = Base64.decodeBase64(key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decoded));
    }


    public static String pubEncrypt(String str, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.encodeBase64String(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
    }

    public static String pubDecrypt(String str, PrivateKey privateKey) throws Exception {
        byte[] inputByte = Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(inputByte));
    }

    public static String priEncrypt(String str, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return Base64.encodeBase64String(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
    }

    public static String priDecrypt(String str, PublicKey publicKey) throws Exception {
        byte[] inputByte = Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return new String(cipher.doFinal(inputByte));
    }

    public static void main(String[] args) throws Exception {
        RsaDTO genKeyPair = genKeyPair();
        String message = "123456";
        String encrypt = priEncrypt(message, getPrivateKey(genKeyPair.getPriveKey()));
        System.out.println("加密字符串："+ encrypt);
        System.out.println("解密后字符串:" + priDecrypt(encrypt, getPublicKey(genKeyPair.getPublicKey())));
    }

}
