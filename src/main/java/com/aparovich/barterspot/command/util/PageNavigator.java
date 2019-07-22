package com.aparovich.barterspot.command.util;

/**
 * Created by Maxim on 05.04.2017
 */
public class PageNavigator {
    private String page;
    private ResponseType type;

    public PageNavigator(String page, ResponseType type) {
        this.page = page;
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public ResponseType getType() {
        return type;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }
}
