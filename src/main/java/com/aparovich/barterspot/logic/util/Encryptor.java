package com.aparovich.barterspot.logic.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Maxim on 25.03.2017
 */
public class Encryptor {
    private static final Logger LOGGER = LogManager.getLogger(Encryptor.class);

    /**
     * Encrypts passed string.
     *
     * @param str source string.
     * @return encrypted string.
     */
    public static String md5(String str) {
        if(str == null) {
            return null;
        }
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.ERROR, "NoSuchAlgorithmException: Selected algorithm is not exists.");
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while( md5Hex.length() < 32 ){
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }
}
