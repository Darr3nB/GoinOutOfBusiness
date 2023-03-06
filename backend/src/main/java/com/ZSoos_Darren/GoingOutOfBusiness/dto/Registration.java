package com.ZSoos_Darren.GoingOutOfBusiness.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Registration {
    private String email;
    private String userName;
    private String password;
    private String passwordAgain;
    @JsonFormat(pattern = "YY.MM.dd")
    private Date dateOfBrith;
    private String profilePicture;

    public Boolean validateField() {
        if (!this.password.equals(this.passwordAgain)) {
            return false;
        }

        if (!validateEmail()) {
            return false;
        }

        return this.userName.length() > 3 && this.password.length() > 3;
    }

    private Boolean validateEmail() {
        if (this.email == null || this.email.isEmpty()) {
            return false;
        }

        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        return this.email.matches(regex);
    }
}
