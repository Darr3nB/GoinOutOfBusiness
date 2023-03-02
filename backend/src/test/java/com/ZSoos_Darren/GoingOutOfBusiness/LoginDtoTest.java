package com.ZSoos_Darren.GoingOutOfBusiness;

import com.ZSoos_Darren.GoingOutOfBusiness.dto.Login;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoginDtoTest {
    Login loginDto;
    String initEmail = "fake@mail.com";
    String initPassword = "password";

    @BeforeEach
    public void initLoginDto(){
        loginDto = new Login(initEmail, initPassword);
    }

    @Test
    public void getInitEmail(){
        Assertions.assertEquals(loginDto.getEmail(), initEmail);
    }

    @Test
    public void setEmail(){
        loginDto.setEmail("mail@mail.com");
        Assertions.assertEquals(loginDto.getEmail(), "mail@mail.com");
    }

    @Test
    public void getPassword(){
        Assertions.assertEquals(loginDto.getPassword(), initPassword);
    }

    @Test
    public void setPassword(){
        loginDto.setPassword("TestPassword");
        Assertions.assertEquals(loginDto.getPassword(), "TestPassword");
    }

    @Test
    public void successfulValidation(){
        Assertions.assertTrue(loginDto.validateField());
    }

    @Test
    public void incorrectValidationWrongPassword(){
        loginDto.setPassword("A");
        Assertions.assertFalse(loginDto.validateField());
    }

    @Test
    public void incorrectMailValidationNameMissing(){
        loginDto.setEmail("@email.com");
        Assertions.assertFalse(loginDto.validateField());
    }

    @Test
    public void incorrectMailValidationAtMissing(){
        loginDto.setEmail("fakemail.com");
        Assertions.assertFalse(loginDto.validateField());
    }

    @Test
    public void incorrectMailValidationOverall(){
        loginDto.setEmail("fakemailcom");
        Assertions.assertFalse(loginDto.validateField());
    }
}
