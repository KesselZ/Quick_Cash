package com.example.project1.ui.chat;

import android.os.Bundle;
import android.text.style.IconMarginSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.DatabasePersistence;
import com.example.project1.MainActivity;
import com.example.project1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Chat extends AppCompatActivity {
    private List<com.example.project1.ui.chat.Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private com.example.project1.ui.chat.MsgAdapter adapter;
    private String receiver;
    private String sender;
    private DatabaseReference dbChat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_chat);
        // the receiver and sender of the message
        receiver = (String)getIntent().getSerializableExtra("receiver");
        sender = (String)getIntent().getSerializableExtra("sender");
        getMessage(sender,receiver);

        //initMsgs();
        inputText = findViewById(R.id.input_text);
        send = findViewById(R.id.send_button);
        msgRecyclerView = findViewById(R.id.msg_chatting_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);

        adapter = new com.example.project1.ui.chat.MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = inputText.getText().toString();
                if(!content.isEmpty()){
                    sendMessage(sender, receiver, content);
                    com.example.project1.ui.chat.Msg msg = new com.example.project1.ui.chat.Msg(content, com.example.project1.ui.chat.Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size() - 1);
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                } else {
                    Toast.makeText(Chat.this, "Can not send empty message", Toast.LENGTH_LONG).show();
                }
                inputText.setText("");
            }
        });
    }

    private void initMsgs(){
        com.example.project1.ui.chat.Msg msg1 = new com.example.project1.ui.chat.Msg("Hello guy.", com.example.project1.ui.chat.Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        com.example.project1.ui.chat.Msg msg2 = new com.example.project1.ui.chat.Msg("Who are you?", com.example.project1.ui.chat.Msg.TYPE_SENT);
        msgList.add(msg2);
        com.example.project1.ui.chat.Msg msg3 = new com.example.project1.ui.chat.Msg("I'm Tiger.", com.example.project1.ui.chat.Msg.TYPE_RECEIVED);
        msgList.add(msg3);
    }

    private void sendMessage(String sender, String receiver, String content) {

        HashMap<String, Object> chatMessage = new HashMap<>();
        chatMessage.put("sender", sender);
        chatMessage.put("receiver", receiver);
        chatMessage.put("message", content);
        DatabasePersistence databasePersistence=new DatabasePersistence();
        databasePersistence.send(chatMessage);
    }

    private void getMessage(String sender, String receiver) {
        dbChat = FirebaseDatabase.getInstance().getReference("Message");
        dbChat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                msgList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    String s = (String)dataSnapshot.child("sender").getValue();
                    String r = (String)dataSnapshot.child("receiver").getValue();
                    String message = (String)dataSnapshot.child("message").getValue();
                    if (s.equals(sender) && r.equals(receiver)) {
                        com.example.project1.ui.chat.Msg msg = new com.example.project1.ui.chat.Msg(message, Msg.TYPE_SENT);
                        msgList.add(msg);
                        adapter.notifyDataSetChanged();
                    } else if ((s.equals(receiver) && r.equals(sender))) {
                        com.example.project1.ui.chat.Msg msg = new com.example.project1.ui.chat.Msg(message, Msg.TYPE_RECEIVED);
                        msgList.add(msg);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
