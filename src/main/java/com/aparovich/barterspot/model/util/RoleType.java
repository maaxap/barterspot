package com.aparovich.barterspot.model.util;

/**
 * Created by Maxim on 20.03.2017
 */
public enum RoleType {
    CLIENT("client"),
    ADMIN("admin"),
    GUEST("guest");

    private String role;

    RoleType(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
