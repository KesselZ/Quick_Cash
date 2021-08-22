package com.example.project1;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class SignUpPageUnitTest {
    static MainActivity mainActivity;
    static SignUpPage signUp;
    static User user;


    @BeforeClass
    public static void setup(){
        mainActivity = new MainActivity();
        signUp = new SignUpPage();
        user = new User();
    }

    @Test
    public void isValidUserName() {
        // user name should be between 2 to 16 digits
        String username = "and123";
        assertTrue(signUp.isValidUserName(username));

        // check long username
        String longName = "aaaaaaaaaaaaaaaaaaaa";
        assertFalse(signUp.isValidUserName(longName));
    }

    @Test
    public void isValidPassword() {
        // password should between 8 to 16 digits
        // check short password
        String shortPassword = "1111";
        assertFalse(signUp.isValidPassword(shortPassword));

        // check long password
        String longPassword = "11111111111111111111";
        assertFalse(signUp.isValidPassword(longPassword));

        // valid password
        String validPassword = "11111111asd";
        assertTrue(signUp.isValidPassword(validPassword));
    }
}