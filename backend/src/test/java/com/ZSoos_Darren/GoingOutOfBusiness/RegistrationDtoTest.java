package com.ZSoos_Darren.GoingOutOfBusiness;

import com.ZSoos_Darren.GoingOutOfBusiness.dto.Registration;
import com.ZSoos_Darren.GoingOutOfBusiness.helper.Utility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class RegistrationDtoTest {
    Registration regDto;
    String initeMail = "fake@init.com";
    String initPassword = "password";
    String initPasswordAgain = "password";
    Date initDateOfBirth;
    String initProfilePicture = Utility.questionMarkPicture;
    String dateOfBirth = "1990.01.01";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");


    @BeforeEach
    public void initRegDto() throws Exception {
        initDateOfBirth = dateFormat.parse(dateOfBirth);
        regDto = new Registration(initeMail, initPassword, initPasswordAgain,
                initDateOfBirth, initProfilePicture);
    }

    @Test
    public void getInitEmail() {
        Assertions.assertEquals(regDto.getEmail(), initeMail);
    }

    @Test
    public void setEmail() {
        regDto.setEmail("fake@fake.com");
        Assertions.assertEquals(regDto.getEmail(), "fake@fake.com");
    }

    @Test
    public void getPassword() {
        Assertions.assertEquals(regDto.getPassword(), initPassword);
    }

    @Test
    public void setPassword() {
        regDto.setPassword("TestPassword");
        Assertions.assertEquals(regDto.getPassword(), "TestPassword");
    }

    @Test
    public void getPasswordAgain() {
        Assertions.assertEquals(regDto.getPasswordAgain(), initPasswordAgain);
    }

    @Test
    public void setPasswordAgain() {
        regDto.setPasswordAgain("TestPasswordAgain");
        Assertions.assertEquals(regDto.getPasswordAgain(), "TestPasswordAgain");
    }

    @Test
    public void getDateOfBirth() throws Exception {
        Assertions.assertEquals(regDto.getDateOfBrith(), dateFormat.parse(dateOfBirth));
    }

    @Test
    public void setDateOfBirth() throws Exception {
        regDto.setDateOfBrith(dateFormat.parse("2000.01.01"));
        Assertions.assertEquals(regDto.getDateOfBrith(), dateFormat.parse("2000.01.01"));
    }

    @Test
    public void getProfilePicture(){
        Assertions.assertEquals(regDto.getProfilePicture(), Utility.questionMarkPicture);
    }

    @Test
    public void setProfilePicture(){
        regDto.setProfilePicture("TestPictureText");
        Assertions.assertEquals(regDto.getProfilePicture(), "TestPictureText");
    }

    @Test
    public void successfulFieldValidation(){
        Assertions.assertTrue(regDto.validateField());
    }

    @Test
    public void incorrectPasswordValidation(){
        regDto.setPassword("A");
        Assertions.assertFalse(regDto.validateField());
    }

    @Test
    public void incorrectPasswordAgainValidation(){
        regDto.setPasswordAgain("A");
        Assertions.assertFalse(regDto.validateField());
    }

    @Test
    public void incorrectMailValidationNameMissing(){
        regDto.setEmail("@email.com");
        Assertions.assertFalse(regDto.validateField());
    }

    @Test
    public void incorrectMailValidationAtMissing(){
        regDto.setEmail("fakemail.com");
        Assertions.assertFalse(regDto.validateField());
    }

    @Test
    public void incorrectMailValidationOverall(){
        regDto.setEmail("fakemailcom");
        Assertions.assertFalse(regDto.validateField());
    }
}
