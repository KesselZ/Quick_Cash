package com.example.project1;

/**
 * This class stored all the user information
 */
public class User {
    public String userName;
    public String password;

    public User(){

    }
    // Initialization of user
    public User(String name, String password) {
        this.userName = name;
        this.password = password;
    }


    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }


    // User can change their password as they want
    public void changePassword(String password) {
        this.password = password;
    }

    // User can change their userName as they want
    public void changeName(String name) {
        this.userName = name;
    }



}
