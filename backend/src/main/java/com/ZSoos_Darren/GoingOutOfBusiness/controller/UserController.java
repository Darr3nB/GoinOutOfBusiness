package com.ZSoos_Darren.GoingOutOfBusiness.controller;

import com.ZSoos_Darren.GoingOutOfBusiness.dto.Login;
import com.ZSoos_Darren.GoingOutOfBusiness.model.GoobUser;
import com.ZSoos_Darren.GoingOutOfBusiness.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    UserService userService;

    @PostMapping(value = "login")
    public HttpEntity<Void> performLogin(@RequestBody Login loginDto, HttpServletResponse response) {
        if (!userService.validateLogin(loginDto)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        GoobUser user = userService.findUserByEMail(loginDto.getEMail());
        userService.loginUser(user, response);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
