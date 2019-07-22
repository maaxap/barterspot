package com.aparovich.barterspot.model.util;

/**
 * Created by Maxim on 15.04.2017
 */
public enum LocaleType {
    EN("en_EN"),
    RU("ru_RU");

    private String locale;

    LocaleType(String locale) {
        this.locale = locale;
    }

    public String getLocale() {
        return this.locale;
    }
}
