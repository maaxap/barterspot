package com.aparovich.barterspot.manager;

import java.util.ResourceBundle;

import static com.aparovich.barterspot.manager.util.ManagerConstant.PAGES;

/**
 *
 *
 * @author Maxim
 */
public class PagesManager {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(PAGES);

    private PagesManager() {}

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }

    public static String getProperty(String key, String parameter) {
        return resourceBundle.getString(key) + parameter;
    }
}
