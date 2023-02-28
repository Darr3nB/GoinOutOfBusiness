package com.ZSoos_Darren.GoingOutOfBusiness.controller;

import com.ZSoos_Darren.GoingOutOfBusiness.dto.Login;
import com.ZSoos_Darren.GoingOutOfBusiness.model.GoobUser;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    GoobUser user;

    @PostMapping(value = "login")
    public HttpEntity<Void> performLogin(@RequestBody Login loginDto, HttpServletResponse response){
        
    }
}
