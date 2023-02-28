package com.ZSoos_Darren.GoingOutOfBusiness.service;

import com.ZSoos_Darren.GoingOutOfBusiness.dao.GoobUserDao;
import com.ZSoos_Darren.GoingOutOfBusiness.dto.Login;
import com.ZSoos_Darren.GoingOutOfBusiness.security.PasswordAgent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    GoobUserDao userDao;
    PasswordAgent passwordAgent;
    public Boolean validateLogin(Login loginDto){
        
    }
}
