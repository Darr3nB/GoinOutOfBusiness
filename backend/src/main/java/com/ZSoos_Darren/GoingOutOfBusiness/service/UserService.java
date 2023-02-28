package com.ZSoos_Darren.GoingOutOfBusiness.service;

import com.ZSoos_Darren.GoingOutOfBusiness.dao.GoobUserDao;
import com.ZSoos_Darren.GoingOutOfBusiness.dto.Login;
import com.ZSoos_Darren.GoingOutOfBusiness.model.GoobUser;
import com.ZSoos_Darren.GoingOutOfBusiness.security.PasswordAgent;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    GoobUserDao userDao;
    PasswordAgent passwordAgent;

    public GoobUser findUserByEMail(String eMail){
        return userDao.findGoobUserByEMail(eMail);
    }

    public Boolean validateLogin(Login loginDto) {
        GoobUser foundUserByEMail = findUserByEMail(loginDto.getEMail());

        return passwordAgent.passwordMatcher(foundUserByEMail.getPassword(), loginDto.getPassword()) && !foundUserByEMail.getUserName().equals("DELETED_USER");
    }

    public void loginUser(GoobUser user, HttpServletResponse response) {
//        Cookie theCookie = new Cookie("hfUsername", user.getUserName());
//        theCookie.setMaxAge(Utility.oneDayForCookies);
//        theCookie.setPath("/");
//
//        response.addCookie(theCookie);
    }
}
