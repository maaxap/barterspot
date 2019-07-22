package com.aparovich.barterspot.validator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by Maxim on 21.05.2017
 */
public class ModelFieldValidator {

    private static final int AGE_LIMIT = 18;

    /**
     * Checks if {@param birthDate} is bigger than {@code '01.01.1900'} and less
     * than {@link LocalDate#now()} minus {@code '18'} years.
     *
     * @param   birthDate {@link LocalDate} representation of birth date.
     * @return  {@code true} if {@param year} is valid;
     *          {@code false} otherwise.
     *
     * @see     LocalDate
     * @see     Comparable
     *
     */
    public static boolean isBirthDate(LocalDate birthDate) {
        return  birthDate != null &&
                LocalDate.of(1900, 1, 1).compareTo(birthDate) < 0 &&
                LocalDate.now().minusYears(birthDate.getYear()).getYear() >= AGE_LIMIT;
    }

    public static boolean isDefaultPrice(BigDecimal defaultPrice) {
        return BigDecimal.ZERO.compareTo(defaultPrice) <= 0;
    }

    public static boolean isFinishing(LocalDateTime finishing) {
        return LocalDateTime.now().compareTo(finishing) < 0;
    }
}
