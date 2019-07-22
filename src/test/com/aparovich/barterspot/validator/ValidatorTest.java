package com.aparovich.barterspot.validator;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Maxim
 */
public class ValidatorTest {

    @Test
    public void checkStringFormat() throws Exception {
        String pattern = "^[A-Za-zА-Яа-я]+$";
        String value = "test";

        Assert.assertTrue(Validator.checkStringFormat(value, pattern));
    }

    @Test
    public void checkEqualityTrue() {
        String first = "barterspot" ;
        String second = "barterspot";

        Assert.assertTrue(Validator.checkEquality(first, second));
    }

    @Test
    public void  checkEqualityFalse() {
        String first = "barterspot";
        String second = "any_string";

        Assert.assertFalse(Validator.checkEquality(first, second));
    }

    @Test
    public void isEnumContains() throws Exception {
        String value = "barterspot";

        Assert.assertTrue(Validator.isEnumContains(value, ValidatorTestEnum.values()));
    }

    @Test
    public void isNotEnumContains() {
        String value = "magnit";

        Assert.assertFalse(Validator.isEnumContains(value, ValidatorTestEnum.values()));
    }
}
