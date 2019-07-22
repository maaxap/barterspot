package com.aparovich.barterspot.command.util;

import com.aparovich.barterspot.manager.MessagesManager;
import com.aparovich.barterspot.model.util.LocaleType;
import com.aparovich.barterspot.validator.Validator;

/**
 * Created by Maxim on 17.05.2017
 */
public class PageMessage {
    private LocaleType locale;
    private String message;
    private PageMessageType type;

    public PageMessage(String locale) {
        if(LocaleType.RU.getLocale().equals(locale)) {
            this.locale = LocaleType.RU;
        } else {
            this.locale = LocaleType.EN;
        }
    }

    public void setParameters(String message, PageMessageType type) {
        this.message = MessagesManager.getProperty(message, locale);
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type.getType();
    }
}
