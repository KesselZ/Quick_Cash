package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class changePwdActivity extends AppCompatActivity {
    private EditText userNameEditBox;
    private EditText passwordEditBox;
    private EditText verificationEditBox;
    private Button changePwdBtn;
    private Button sendVerificationBtn;
    private DatabaseReference dbVerification;
    private DatabaseReference dbUser;
    private User userToEdit;

    private String verificationCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);


        userNameEditBox = findViewById(R.id.changePwd_userName);
        passwordEditBox = findViewById(R.id.changePwd_password);
        verificationEditBox = findViewById(R.id.verification);
        changePwdBtn = findViewById(R.id.changePwdBtn);
        sendVerificationBtn = findViewById(R.id.sendVerificationBtn);
        dbUser = FirebaseDatabase.getInstance().getReference("User");
        dbVerification = FirebaseDatabase.getInstance().getReference("Verification");

//save Verification Code in Firebase and get user to edit from Firebase
        sendVerificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePwdBtn.setVisibility(View.VISIBLE);
                String userName = "";
                userName = userNameEditBox.getText().toString().trim();

                dbUser.orderByChild("userName").equalTo(userName).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            for (DataSnapshot adSnapshot : dataSnapshot.getChildren()) {
                                userToEdit = adSnapshot.getValue(User.class);
                                Query q = dbVerification.child(userToEdit.getUserName());
                                q.addValueEventListener(verificationCodeListener);
                            }

                            dbVerification.child(userToEdit.userName).child("Verification Code").setValue(generateCode());
                            Toast.makeText(changePwdActivity.this, "Verification Code Sent", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(changePwdActivity.this, "Invalid UserName", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(changePwdActivity.this, "DatabaseError, Please try again later", Toast.LENGTH_SHORT).show();
                    }
                });


            }


        });

//update the edited user to Firebase
        changePwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String code, newPassword;

                code = verificationEditBox.getText().toString().trim();
                newPassword = passwordEditBox.getText().toString().trim();
                if (code.isEmpty() || newPassword.isEmpty()) {
                    Toast.makeText(changePwdActivity.this, "Please Fill All Blanks", Toast.LENGTH_SHORT).show();
                } else {
                    if (code.equals(verificationCode)) {
                        userToEdit.changePassword(newPassword);
                        dbUser.child(userToEdit.userName).setValue(userToEdit);
                        Toast.makeText(changePwdActivity.this, "Password Change Successfully", Toast.LENGTH_SHORT).show();

                        finish();

                    } else {
                        Toast.makeText(changePwdActivity.this, "Incorrect Verification Code", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

    }

    /**
     * @return a random 6 digits verification code
     */
    protected String generateCode() {
        Random random = new Random();
        int x = random.nextInt(899999);
        x = x + 100000;
        return String.valueOf(x);
    }

    //get Verification Code in Firebase according to User to be edited
    ValueEventListener verificationCodeListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {

                for (DataSnapshot adSnapshot : dataSnapshot.getChildren()) {

                    verificationCode = adSnapshot.getValue(String.class);


                }
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


}
