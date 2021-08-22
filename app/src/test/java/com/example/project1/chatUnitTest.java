package com.example.project1;

import com.example.project1.ui.chat.ChatFragment;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class chatUnitTest {
    User user1 = new User("abc", "aaaabbb");
    User user2 = new User("abcde", "11112345");
    User user3 = new User("ertdf", "3445677");
    ArrayList<User> userList = new ArrayList<>();
    ChatFragment chat= new ChatFragment();


    @Test
    public void matchUser(){
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        String keyword = "abc";
        ArrayList<User> result = chat.findUser(userList, keyword);
        assertEquals(2, result.size());
    }

}
