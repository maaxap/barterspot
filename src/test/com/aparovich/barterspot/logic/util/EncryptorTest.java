package com.aparovich.barterspot.logic.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Maxim
 */
public class EncryptorTest {
    @Test
    public void md5() throws Exception {
        String encryptorHashedValue = Encryptor.md5("barterspot");
        String hashedValue = "c22f692220bf76a5adc6a4ab068f7639";

        Assert.assertTrue(hashedValue.equals(encryptorHashedValue));
    }

}