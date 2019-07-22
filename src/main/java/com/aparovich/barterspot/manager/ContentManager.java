package com.aparovich.barterspot.manager;

import java.util.ResourceBundle;

/**
 *
 * @author Maxim
 */
public class ContentManager {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("content");

    private ContentManager() {}

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }

}
