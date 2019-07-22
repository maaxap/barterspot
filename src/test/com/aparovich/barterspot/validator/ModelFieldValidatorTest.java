package com.aparovich.barterspot.validator;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * @author Maxim
 */
public class ModelFieldValidatorTest {
    @Test
    public void isBirthDate() throws Exception {
        LocalDate value = LocalDate.of(1996, 8, 19);

        Assert.assertTrue(ModelFieldValidator.isBirthDate(value));
    }

    @Test
    public void isNotBirthDate() throws Exception {
        LocalDate value = LocalDate.of(9999, 1, 1);

        Assert.assertFalse(ModelFieldValidator.isBirthDate(value));
    }

    @Test
    public void isDefaultPrice() throws Exception {
        BigDecimal value = new BigDecimal("102.12312332");

        Assert.assertTrue(ModelFieldValidator.isDefaultPrice(value));
    }

    @Test
    public void isNotDefaultPrice() throws Exception {
        BigDecimal value = new BigDecimal("-102.12312332");

        Assert.assertFalse(ModelFieldValidator.isDefaultPrice(value));
    }

    @Test
    public void isFinishing() throws Exception {
        LocalDateTime value = LocalDateTime.now().plusDays(1);

        Assert.assertTrue(ModelFieldValidator.isFinishing(value));
    }

    @Test
    public void isNotFinishing() throws Exception {
        LocalDateTime value = LocalDateTime.now().minusDays(1);

        Assert.assertFalse(ModelFieldValidator.isFinishing(value));
    }

}