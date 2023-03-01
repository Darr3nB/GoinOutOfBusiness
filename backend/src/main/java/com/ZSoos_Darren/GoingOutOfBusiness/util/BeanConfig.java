package com.ZSoos_Darren.GoingOutOfBusiness.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BeanConfig {
    @Bean
    public BCryptPasswordEncoder bCryptFactory(){
        return new BCryptPasswordEncoder();
    }
}
