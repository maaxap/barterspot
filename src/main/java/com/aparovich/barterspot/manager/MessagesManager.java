package com.aparovich.barterspot.manager;

import com.aparovich.barterspot.model.util.LocaleType;

import java.util.Locale;
import java.util.ResourceBundle;

import static com.aparovich.barterspot.manager.util.ManagerConstant.MESSAGES;

/**
 *
 * @author Maxim
 */
public class MessagesManager {
    private static ResourceBundle resourceBundle;

    private MessagesManager() {}

    public static String getProperty(String key, LocaleType locale) {
        resourceBundle = ResourceBundle.getBundle(MESSAGES, Locale.forLanguageTag(locale.getLocale().replace('_', '-')));
        return resourceBundle.getString(key);
    }
}
