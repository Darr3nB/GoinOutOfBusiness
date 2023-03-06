package com.ZSoos_Darren.GoingOutOfBusiness.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Login {
    private String email;
    private String password;

    public Boolean validateField() {

        if (!validateEmail()) {
            return false;
        }

        return this.password.length() > 3;
    }

    private Boolean validateEmail() {
        if (this.email == null || this.email.isEmpty()) {
            return false;
        }

        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        return this.email.matches(regex);
    }
}
