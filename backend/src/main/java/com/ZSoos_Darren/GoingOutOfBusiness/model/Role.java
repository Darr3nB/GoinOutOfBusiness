package com.ZSoos_Darren.GoingOutOfBusiness.model;

public enum Role {
    ADMIN,
    USER;

    public static Role fromString(String roleString) {
        if (roleString == null) return null;
        return roleString.toUpperCase().equals(ADMIN.toString()) ? ADMIN : USER;
    }
}
