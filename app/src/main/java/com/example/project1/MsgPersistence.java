package com.example.project1;

import com.example.project1.ui.chat.Msg;

import java.util.HashMap;

public interface MsgPersistence {
    public boolean send(HashMap<String, Object> t);
}
