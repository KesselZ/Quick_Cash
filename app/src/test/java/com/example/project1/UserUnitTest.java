package com.example.project1;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserUnitTest {
    static User user;

    @BeforeClass
    public static void setup(){

        user = new User();
    }

    @Test
    public void getUserName() {
        User temp1 = new User("12345","123456678");
        assertEquals(temp1.getUserName(), "12345");
    }

    @Test
    public void getPassword() {
        User temp1 = new User("12345","123456678@");
        assertEquals(temp1.getPassword(), "123456678@");
    }

    @Test
    public void changePassword() {
        User temp1 = new User("122345", "122345asdfg");
        temp1.changePassword("123456789");
        assertEquals(temp1.getPassword(), "123456789");
    }

    @Test
    public void changeName() {
        User temp1 = new User("12345", "123456789");
        temp1.changeName("abcd");
        assertEquals(temp1.getUserName(), "abcd");
    }
}