package com.ZSoos_Darren.GoingOutOfBusiness.security;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PasswordAgent {
    private BCryptPasswordEncoder passwordEncoder;

    public String hashPassword(String plainPassword){
        return passwordEncoder.encode(plainPassword);
    }
}
