package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpPage extends AppCompatActivity {
    private EditText mUserNameEditText, mPasswordEditText;
    private DatabaseReference dbUser;
    private String uName, pswd;
    private TextView messageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        mUserNameEditText = findViewById(R.id.userName);
        mPasswordEditText = findViewById(R.id.password);
        dbUser = FirebaseDatabase.getInstance().getReference("User");
        messageView = findViewById(R.id.errorMessage);

        Button backToLogIn = findViewById(R.id.BacktoLogin);
        backToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent backToLogIn = new Intent(SignUpPage.this, LogIn.class);
                startActivity(backToLogIn);
            }
        });

        Button create = findViewById(R.id.submit);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!LogIn.connection()) {
                    Toast.makeText(getApplicationContext(), "Failed To Connect To Server \nPlease Check Your Settings", Toast.LENGTH_SHORT).show();
                }
                else{
                getEditString();
                User user = new User(uName, pswd);
                if (TextUtils.isEmpty(uName)) {
                    messageView.setText("Please enter your username.");
                    Toast.makeText(SignUpPage.this, "Please enter your username.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(pswd)) {
                    messageView.setText("Please enter your user password.");
                    Toast.makeText(SignUpPage.this, "Please enter your user password.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!isValidUserName(uName)) {
                    messageView.setText("Your username must be a combination of numbers, lower or upper case letters(4-16 characters)");
                    Toast.makeText(SignUpPage.this, "Your username must be a combination of numbers, lower or upper case letters(4-16 characters)", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!isValidPassword(pswd)) {
                    messageView.setText("Your password must be a combination of numbers, lower or upper case letters(8-16 characters)");
                    Toast.makeText(SignUpPage.this, "Your password must be a combination of numbers, lower or upper case letters(8-16 characters)", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    isExistUserName(uName,pswd);
                }
            }
            }
        });


    }


    private void getEditString() {
        uName = mUserNameEditText.getText().toString().trim();
        pswd = mPasswordEditText.getText().toString().trim();
    }


    /**
     * Check if enter a valid username, should be less than 16 digits
     * @param username username to validate
     * @return true if the username is valid
     */
    public boolean isValidUserName(String username) {

        boolean validation = false;
        char[] convert = username.toCharArray();

        if (convert.length <= 16 && convert.length >= 4) {
            int count = 0;
            for (int i = 0; i < convert.length; i++) {
                int ascii = convert[i];
                if ((ascii >= 48 && ascii <= 57) || (ascii >= 65 && ascii <= 90) || (ascii >= 97 && ascii <= 122)) {
                    count++;
                }
            }
            if (count == username.length()) validation = true;
        }

        return validation;
    }



    /**
     * Check if enter a valid password, should be no less than 8 digits, no more than 16 letters
     * @param password password to validate
     * @return true if password is valid
     */
    public boolean isValidPassword(String password) {

        boolean validation = false;
        char[] convert = password.toCharArray();

        if (convert.length <= 16 && convert.length >= 8) {
            int count = 0;
            for (int i = 0; i < convert.length; i++) {
                int ascii = convert[i];
                if ((ascii >= 48 && ascii <= 57) || (ascii >= 65 && ascii <= 90) || (ascii >= 97 && ascii <= 122)) {
                    count++;
                }
            }
            if (count == password.length()) validation = true;
        }

        return validation;
    }

    /**
     * check if username exists in database
     * @param username username to validate
     * @param password password to validate
     */
    public void isExistUserName(String username, String password) {
        dbUser.orderByChild("userName").equalTo(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Toast.makeText(SignUpPage.this, "User name exist", Toast.LENGTH_SHORT).show();

                } else {

                    //getEditString();
                    User user = new User(username, password);

                    dbUser.child(user.userName).setValue(user);
                    Toast.makeText(SignUpPage.this, "Register successfully.", Toast.LENGTH_SHORT).show();
                    Intent backToLogIn = new Intent(SignUpPage.this, LogIn.class);
                    startActivity(backToLogIn);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SignUpPage.this, "DatabaseError, please try again later", Toast.LENGTH_SHORT).show();
            }
        });

    }





}

