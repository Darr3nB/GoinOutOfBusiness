package com.ZSoos_Darren.GoingOutOfBusiness.service;

import com.ZSoos_Darren.GoingOutOfBusiness.dao.GoobUserDao;
import com.ZSoos_Darren.GoingOutOfBusiness.dto.Login;
import com.ZSoos_Darren.GoingOutOfBusiness.dto.Registration;
import com.ZSoos_Darren.GoingOutOfBusiness.helper.Utility;
import com.ZSoos_Darren.GoingOutOfBusiness.model.GoobUser;
import com.ZSoos_Darren.GoingOutOfBusiness.model.Role;
import com.ZSoos_Darren.GoingOutOfBusiness.security.PasswordAgent;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements CommandLineRunner {
    GoobUserDao userDao;
    PasswordAgent passwordAgent;

    public GoobUser findUserByEMail(String eMail) {
        return userDao.findUserByeMail(eMail);
    }

    public Boolean validateProfile(Login loginDto) {
        GoobUser foundUserByEMail = findUserByEMail(loginDto.getEMail());

        return passwordAgent.passwordMatcher(foundUserByEMail.getPassword(), loginDto.getPassword()) && !foundUserByEMail.getUserName().equals("DELETED_USER");
    }

    public void addUserDetailToCookies(GoobUser user, HttpServletResponse response) {
        Cookie theCookie = new Cookie("goobEMailAddress", user.getUserName());
        theCookie.setMaxAge(Utility.oneDayForCookies);
        theCookie.setPath("/");

        response.addCookie(theCookie);
    }

    public void removeUserDetailFromCookies(HttpServletResponse response) {
        Cookie theCookie = new Cookie("goobEMailAddress", null);
        theCookie.setMaxAge(0);
        theCookie.setPath("/");
        response.addCookie(theCookie);
    }

    public String getEMailFromCookies(HttpServletRequest request) {
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

    public Boolean deleteUserByEMail(Login loginDto) {
        GoobUser userByEMail = findUserByEMail(loginDto.getEMail());

        if (userByEMail.getUserName().equals("DELETED_USER")) {
            return false;
        }

        userByEMail.setUserName("DELETED_USER");
        userByEMail.setPassword("-----");
        userByEMail.setDateOfBirth(null);
        userByEMail.setProfilePicture(Utility.questionMarkPicture);
        userDao.save(userByEMail);

        return null;
    }

    public void saveNewUser(Registration regDto) {
        GoobUser newRegistration = new GoobUser();

        newRegistration.setEMail(regDto.getEMail());
        newRegistration.setUserName(regDto.getUserName());
        newRegistration.setPassword(passwordAgent.hashPassword(regDto.getPassword()));
        newRegistration.setDateOfBirth(regDto.getDateOfBrith());
        if (regDto.getProfilePicture() == null) {
            newRegistration.setProfilePicture(Utility.questionMarkPicture);
        } else {
            newRegistration.setProfilePicture(regDto.getProfilePicture());
        }
        newRegistration.setRole(Role.USER);

        userDao.save(newRegistration);
    }

    public void addDefaultAdmin(){
        if (userDao.count() == 0){
            GoobUser defaultAdmin = new GoobUser();


            defaultAdmin.setEMail("fake@mail.com");
            defaultAdmin.setUserName("admin");
            defaultAdmin.setPassword("$2a$10$aggKLhBPm7ke/CfXkiSnAOzpHXdIXqm9j5MxFobGjr.O38gnngBsK");
            defaultAdmin.setDateOfBirth(null);
            defaultAdmin.setProfilePicture(Utility.questionMarkPicture);
            defaultAdmin.setRole(Role.ADMIN);

            userDao.save(defaultAdmin);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        addDefaultAdmin();
    }
}
