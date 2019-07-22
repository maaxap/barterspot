package com.aparovich.barterspot.model.bean;

import com.aparovich.barterspot.model.Model;

/**
 * Created by Maxim on 21.03.2017
 */
public class Settings extends Model {
    private String locale;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Settings)) return false;
        if (!super.equals(o)) return false;

        Settings settings = (Settings) o;

        return locale != null ? locale.equals(settings.locale) : settings.locale == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (locale != null ? locale.hashCode() : 0);
        return result;
    }
}
