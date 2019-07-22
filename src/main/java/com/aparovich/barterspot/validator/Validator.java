package com.aparovich.barterspot.validator;

import com.aparovich.barterspot.model.util.ModelType;
import com.aparovich.barterspot.validator.exception.ValidatorException;

import java.util.EnumSet;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Maxim on 19.05.2017
 */
public class Validator {

    public static boolean checkStringFormat(String str, String formatPattern) {
        if(str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(formatPattern);
        Matcher matcher = pattern.matcher(str.trim());
        return matcher.find();
    }

    public static boolean checkEquality(String str1, String str2) {
        return str1 != null && str1.equals(str2);
    }

    public static boolean isEnumContains(String str, Enum[] container) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        str = str.trim().replace('-', '_');
        for (Enum element : container) {
            if(element.toString().equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }
}
