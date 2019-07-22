package com.aparovich.barterspot.tags;

import com.aparovich.barterspot.model.util.LocaleType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

/**
 * Created by Maxim on 14.04.2017
 */
public class ELFunction {

    private static final HashMap<Integer, String> enMonth;
    private static final HashMap<Integer, String> ruMonth;

    static {
        enMonth = new HashMap<>();
        enMonth.put(1, "January");
        enMonth.put(2, "February");
        enMonth.put(3, "March");
        enMonth.put(4, "April");
        enMonth.put(5, "May");
        enMonth.put(6, "June");
        enMonth.put(7, "July");
        enMonth.put(8, "August");
        enMonth.put(9, "September");
        enMonth.put(10, "October");
        enMonth.put(11, "November");
        enMonth.put(12, "December");

        ruMonth = new HashMap<>();
        ruMonth.put(1, "Январь");
        ruMonth.put(2, "Февраль");
        ruMonth.put(3, "Март");
        ruMonth.put(4, "Апрель");
        ruMonth.put(5, "Май");
        ruMonth.put(6, "Июнь");
        ruMonth.put(7, "Июль");
        ruMonth.put(8, "Август");
        ruMonth.put(9, "Сентябрь");
        ruMonth.put(10, "Октябрь");
        ruMonth.put(11, "Ноябрь");
        ruMonth.put(12, "Декабрь");
    }

    private static final Logger LOGGER = LogManager.getLogger(ELFunction.class);

    public static String crop(String string, int symbols) {
        return string.length() > symbols ? string.substring(0, symbols) + "..." : string;
    }

    public static HashMap<Integer, String> getMonthNames(String locale) {
        if(locale.equals(LocaleType.RU.getLocale())) {
            return ruMonth;
        } else {
            return enMonth;
        }
    }

    public static String formatBid(String str) {
        BigDecimal bid = null;
        try {
            bid = new BigDecimal(Double.valueOf(str)).setScale(2, RoundingMode.HALF_UP);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.INFO, e.getMessage());
        }
        return bid != null ? bid.toString() : "";

    }
}
