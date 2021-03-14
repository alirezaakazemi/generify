package com.generify.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class EncryptionUtil {
    public static final Logger logger = LoggerFactory.getLogger(EncryptionUtil.class);


    public static String decrypt(String encryptedValue, String algorithm, SecretKeySpec key) throws Exception {
        Cipher c = Cipher.getInstance(algorithm);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = new BASE64Decoder().decodeBuffer(encryptedValue);
        byte[] decryptedValue = c.doFinal(decodedValue);
        return new String(decryptedValue);
    }

    public static String encrypt(String valueToEnc, String algorithm, SecretKeySpec key) throws Exception {
        Cipher c = Cipher.getInstance(algorithm);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedValue = c.doFinal(valueToEnc.getBytes(StandardCharsets.UTF_8));
        return new BASE64Encoder().encode(encryptedValue);
    }
}
