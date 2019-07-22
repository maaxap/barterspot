package com.aparovich.barterspot.validator;

import com.aparovich.barterspot.logic.UserLogic;
import com.aparovich.barterspot.model.util.LocaleType;

import java.time.LocalDate;

/**
 * Created by Maxim on 19.05.2017
 */
public class ParametersValidator {

    /**
     * Regexp patterns.
     */
    private static final String EMPTY_STRING = "";
    private static final String NAME_PATTERN = "^[A-Za-zА-Яа-я-&\\s]{0,250}$";
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\." +
                                                "[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    private static final String PHONE_NUMBER_PATTERN = "^[0-9+]{1,15}$";
    private static final String POSITIVE_INTEGER_PATTERN = "^[0-9]{1,7}$";
    private static final String POSITIVE_BIG_DECIMAL_PATTERN = "^[0-9]+\\.?[0-9]*$";
    private static final String POST_CODE_PATTERN = "^[0-9A-Za-z]{1,25}$";
    private static final String ADDRESS_PATTERN = "^[0-9\"A-Za-zА-Яа-я\\s\\.,&-\\\\+:()/#_]{1,250}$";

    public static boolean checkEmailFormat(String str) {
        return Validator.checkStringFormat(str, EMAIL_PATTERN);
    }

    public static boolean checkPasswordFormat(String password){
        return password != null && !password.isEmpty() && !password.equals(UserLogic.encrypt(EMPTY_STRING));
    }

    public static boolean checkNameFormat(String str) {
        return Validator.checkStringFormat(str, NAME_PATTERN);
    }

    public static boolean checkSurnameFormat(String str) {
        return Validator.checkStringFormat(str, NAME_PATTERN);
    }

    public static boolean checkPhoneNumberFormat(String phoneNumber) {
        return Validator.checkStringFormat(phoneNumber, PHONE_NUMBER_PATTERN);
    }

    public static boolean checkAddressFormat(String address) {
        return Validator.checkStringFormat(address, ADDRESS_PATTERN);
    }

    public static boolean checkLocaleFormat(String locale) {
        return locale != null && (locale.equals(LocaleType.EN.getLocale()) || locale.equals(LocaleType.RU.getLocale()));
    }

    public static boolean checkPostCodeFormat(String postCode) {
        return Validator.checkStringFormat(postCode, POST_CODE_PATTERN);
    }

    /**
     * Checks if {@param dayOfMonth} can be parsed to {@code int} value and if it
     * falls into range between {@code '1'} and {@code '31'}.
     *
     * @param   dayOfMonth string of numeric month day representation.
     * @return  {@code true} if {@param dayOfMonth} is valid;
     *          {@code false} otherwise.
     *
     * @see     java.lang.Integer#valueOf(String)
     */
    public static boolean checkDayFormat(String dayOfMonth) {
        return  Validator.checkStringFormat(dayOfMonth.trim(), POSITIVE_INTEGER_PATTERN) &&
                Integer.valueOf(dayOfMonth) > 0 &&
                Integer.valueOf(dayOfMonth) < 32;
    }

    public static boolean checkMonthFormat(String month) {
        return  Validator.checkStringFormat(month, POSITIVE_INTEGER_PATTERN) &&
                Integer.valueOf(month) > 0 &&
                Integer.valueOf(month) < 13;
    }

    /**
     * Checks if {@param dayOfMonth} can be parsed to {@code int} value and if it
     * falls into range between {@code '1900'} and current year.
     *
     * @param   year string of numeric year representation.
     * @return  {@code true} if {@param year} is valid;
     *          {@code false} otherwise.
     *
     * @see     java.lang.Integer#valueOf(String)
     * @see     LocalDate#now()
     * @see     LocalDate#getYear()
     */
    public static boolean checkYearFormat(String year) {
        return  Validator.checkStringFormat(year, POSITIVE_INTEGER_PATTERN) &&
                year.trim().length() < 5;
    }

    public static boolean checkIntegerFormat(String number) {
        return Validator.checkStringFormat(number, POSITIVE_INTEGER_PATTERN);
    }

    public static boolean checkBigDecimalFormat(String number) {
        return Validator.checkStringFormat(number, POSITIVE_BIG_DECIMAL_PATTERN);
    }

}
