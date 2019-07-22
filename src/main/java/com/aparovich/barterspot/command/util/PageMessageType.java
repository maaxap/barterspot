package com.aparovich.barterspot.command.util;

/**
 * Created by Maxim on 17.05.2017
 */
public enum PageMessageType {
    DANGER("danger"),
    SUCCESS("success");

    private String type;

    PageMessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
