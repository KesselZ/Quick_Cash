package com.example.project1;

import com.example.project1.Task;
import com.example.project1.ui.chat.Msg;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DatabasePersistence implements TaskPersistence,MsgPersistence{


    public boolean save(Task t) {
        DatabaseReference dbTask = FirebaseDatabase.getInstance().getReference("Task");
        dbTask.child(t.getTaskId()).setValue(t);
        return true;
    }


    public boolean send(HashMap<String, Object>chatMessage ) {
        DatabaseReference dbMsg = FirebaseDatabase.getInstance().getReference();
        dbMsg.child("Message").push().setValue(chatMessage);
        return true;
    }
}
