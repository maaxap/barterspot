package com.aparovich.barterspot.validator;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Maxim
 */
public class ParametersValidatorTest {
    @Test
    public void checkEmailFormat() throws Exception {
        String value = "test@test.test";

        Assert.assertTrue(ParametersValidator.checkEmailFormat(value));
    }

    @Test
    public void checkNotEmailFormat() throws Exception {
        String value = "test-test.test";

        Assert.assertFalse(ParametersValidator.checkEmailFormat(value));
    }

    @Test
    public void checkPasswordFormat() throws Exception {
        String value = "any_password";

        Assert.assertTrue(ParametersValidator.checkPasswordFormat(value));
    }

    @Test
    public void checkNotPasswordFormat() throws Exception {
        String value = "";

        Assert.assertFalse(ParametersValidator.checkEmailFormat(value));
    }

    @Test
    public void checkNameFormat() throws Exception {
        String value = "Name & second-name";

        Assert.assertTrue(ParametersValidator.checkNameFormat(value));
    }

    @Test
    public void checkNotNameFormat() throws Exception {
        String value = "not_name+not_second_name";

        Assert.assertFalse(ParametersValidator.checkNameFormat(value));
    }

    @Test
    public void checkSurnameFormat() throws Exception {
        String value = "Surname & something-else";

        Assert.assertTrue(ParametersValidator.checkNameFormat(value));
    }

    @Test
    public void checkNotSurnameFormat() throws Exception {
        String value = "not_surname+not_something_else";

        Assert.assertFalse(ParametersValidator.checkNameFormat(value));
    }

    @Test
    public void checkPhoneNumberFormat() throws Exception {
        String value = "+375296898584";

        Assert.assertTrue(ParametersValidator.checkPhoneNumberFormat(value));
    }

    @Test
    public void checkNotPhoneNumberFormat() throws Exception {
        String value = "+375-29&689$85!84";

        Assert.assertFalse(ParametersValidator.checkPhoneNumberFormat(value));
    }

    @Test
    public void checkAddressFormat() throws Exception {
        String value = "City, str. & home number - 8";

        Assert.assertTrue(ParametersValidator.checkAddressFormat(value));
    }

    @Test
    public void checkNotAddressFormat() throws Exception {
        String value = "$$$";

        Assert.assertFalse(ParametersValidator.checkAddressFormat(value));
    }

    @Test
    public void checkLocaleFormat() throws Exception {
        String value = "en_EN";

        Assert.assertTrue(ParametersValidator.checkLocaleFormat(value));
    }

    @Test
    public void checkNotLocaleFormat() throws Exception {
        String value = "not_Locale";

        Assert.assertFalse(ParametersValidator.checkLocaleFormat(value));
    }

    @Test
    public void checkPostCodeFormat() throws Exception {
        String value = "220017ABCD";

        Assert.assertTrue(ParametersValidator.checkPostCodeFormat(value));
    }

    @Test
    public void checkNotPostCodeFormat() throws Exception {
        String value = "?NOT_POST_CODE?";

        Assert.assertFalse(ParametersValidator.checkPostCodeFormat(value));
    }

    @Test
    public void checkDayFormat() throws Exception {
        String value = "13";

        Assert.assertTrue(ParametersValidator.checkDayFormat(value));
    }

    @Test
    public void checkNotDayFormat() throws Exception {
        String value = "NOT_A_DAY";

        Assert.assertFalse(ParametersValidator.checkDayFormat(value));
    }

    @Test
    public void checkMonthFormat() throws Exception {
        String value = "1";

        Assert.assertTrue(ParametersValidator.checkMonthFormat(value));
    }

    @Test
    public void checkNotMonthFormat() throws Exception {
        String value = "NOT_A_MONTH";

        Assert.assertFalse(ParametersValidator.checkMonthFormat(value));
    }

    @Test
    public void checkYearFormat() throws Exception {
        String value = "2017";

        Assert.assertTrue(ParametersValidator.checkYearFormat(value));
    }

    @Test
    public void checkNotYearFormat() throws Exception {
        String value = "NOT_A_YEAR";

        Assert.assertFalse(ParametersValidator.checkYearFormat(value));
    }

    @Test
    public void checkIntegerFormat() throws Exception {
        String value = "123";

        Assert.assertTrue(ParametersValidator.checkIntegerFormat(value));
    }

    @Test
    public void checkNotIntegerFormat() throws Exception {
        String value = "NOT_AN_INTEGER";

        Assert.assertFalse(ParametersValidator.checkIntegerFormat(value));
    }

    @Test
    public void checkBigDecimalFormat() throws Exception {
        String value = "123.123";

        Assert.assertTrue(ParametersValidator.checkBigDecimalFormat(value));
    }

    @Test
    public void checkNotBigDecimalFormat() throws Exception {
        String value = "NOT_A_BIG_DECIMAL";

        Assert.assertFalse(ParametersValidator.checkBigDecimalFormat(value));
    }

}