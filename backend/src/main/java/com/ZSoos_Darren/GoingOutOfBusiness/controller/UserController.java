package com.ZSoos_Darren.GoingOutOfBusiness.controller;

import com.ZSoos_Darren.GoingOutOfBusiness.dto.Login;
import com.ZSoos_Darren.GoingOutOfBusiness.dto.Registration;
import com.ZSoos_Darren.GoingOutOfBusiness.model.GoobUser;
import com.ZSoos_Darren.GoingOutOfBusiness.security.JwtUtil;
import com.ZSoos_Darren.GoingOutOfBusiness.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    UserService userService;

    @PostMapping(value = "login")
    public HttpEntity<GoobUser> performLogin(@RequestBody Login loginDto, HttpServletResponse response) {
        if (!userService.validateProfile(loginDto)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        GoobUser user = userService.findUserByEMail(loginDto.getEmail());
        String token = JwtUtil.generateTokenWithUser(user);
        userService.addJwtToCookies(token, response);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping(value = "logout")
    public HttpEntity<Void> performLogout(HttpServletResponse response) {
        userService.removeUserDetailFromCookies(response);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value = "logged-in-user-all-detail")
    public HttpEntity<GoobUser> getLoggedInUserDetail(HttpServletRequest request) {
        String eMail = userService.getEMailFromCookies(request);

        if (eMail == null) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
        }

        GoobUser user = userService.findUserByEMail(eMail);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping(value = "delete-profile")
    public HttpEntity<Void> deleteProfile(@RequestBody Login loginDto) {
        if (!userService.validateProfile(loginDto)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (userService.deleteUserByEMail(loginDto)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping(value = "registration")
    public HttpEntity<Void> performRegistration(@RequestBody Registration regDto) {
        if (!regDto.validateField()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        userService.saveNewUser(regDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value = "validate-email-for-register/{email}")
    public HttpEntity<Void> emailAlreadyExists(@PathVariable String email) {
        if (!userService.containsEmail(email)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
