package com.ZSoos_Darren.GoingOutOfBusiness.service;

import com.ZSoos_Darren.GoingOutOfBusiness.dao.GoobUserDao;
import com.ZSoos_Darren.GoingOutOfBusiness.dto.Login;
import com.ZSoos_Darren.GoingOutOfBusiness.model.GoobUser;
import com.ZSoos_Darren.GoingOutOfBusiness.security.PasswordAgent;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    GoobUserDao userDao;
    PasswordAgent passwordAgent;

    public GoobUser findUserByEMail(String eMail) {
        return userDao.findGoobUserByEMail(eMail);
    }

    public Boolean validateLogin(Login loginDto) {
        GoobUser foundUserByEMail = findUserByEMail(loginDto.getEMail());

        return passwordAgent.passwordMatcher(foundUserByEMail.getPassword(), loginDto.getPassword()) && !foundUserByEMail.getUserName().equals("DELETED_USER");
    }

    public void addUserDetailToCookies(GoobUser user, HttpServletResponse response) {
        Cookie theCookie = new Cookie("goobEMailAddress", user.getUserName());
        theCookie.setMaxAge(60 * 60 * 24);
        theCookie.setPath("/");

        response.addCookie(theCookie);
    }

    public void removeUserDetailFromCookies(HttpServletResponse response) {
        Cookie theCookie = new Cookie("goobEMailAddress", null);
        theCookie.setMaxAge(0);
        theCookie.setPath("/");
        response.addCookie(theCookie);
    }

    public String getEMailFromCookies(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if ("goobEMailAddress".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
