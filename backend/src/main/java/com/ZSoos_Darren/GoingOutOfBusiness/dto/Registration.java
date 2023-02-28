package com.ZSoos_Darren.GoingOutOfBusiness.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Registration {
    private String eMail;
    private String userName;
    private String password;
    private String passwordAgain;
    @JsonFormat(pattern = "YY.MM.dd")
    private Date dateOfBrith;
    private String profilePicture;

    public Boolean validateField(){
        // TODO needs logic to validate all fields
        return null;
    }
}
