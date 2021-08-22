package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogIn extends AppCompatActivity {

    private EditText mUserNameEditText, mPasswordEditText;
    private Button mSigninButton;
    private Button switchToSignUp;
    private TextView statusLabel;
    private DatabaseReference dbUser;
    static public boolean connection;
    private TextView forgetPwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        //access database, prepare for checking connection later
        connection();

        mUserNameEditText = findViewById(R.id.userName);
        mPasswordEditText = findViewById(R.id.password);
        mSigninButton = findViewById(R.id.loginBtn);
        dbUser = FirebaseDatabase.getInstance().getReference("User");
        switchToSignUp = findViewById(R.id.toSignUp);
        statusLabel=findViewById(R.id.status);
        forgetPwd=findViewById(R.id.forgetPwd);
        forgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changePwdActivity=new Intent(LogIn.this,changePwdActivity.class);
                startActivity(changePwdActivity);
            }
        });


        switchToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                User u = new User(mUserNameEditText.getText().toString().trim(),mPasswordEditText.getText().toString());
//                dbUser.child(java.util.UUID.randomUUID().toString()).setValue(u);
                Intent switchSignUp = new Intent(LogIn.this, SignUpPage.class);
                startActivity(switchSignUp);
            }
        });

        mSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_name = mUserNameEditText.getText().toString().trim();
                String user_password = mPasswordEditText.getText().toString().trim();
                if (user_name.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter your username", Toast.LENGTH_SHORT).show();
                statusLabel.setText("Enter your username");}
                else if (user_password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter your password", Toast.LENGTH_SHORT).show();
                    statusLabel.setText("Enter your password");
                }else {
                    checkAccount(user_name, user_password);
                }
            }
        });


    }

    /**
     *
     * @return if the application is connected to firebase successfully
     */
    public static boolean connection() {
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");  //

        connectedRef.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        LogIn.connection = false;
                        if (snapshot.getValue(Boolean.class) != null) {

                            LogIn.connection = (boolean) snapshot.getValue(Boolean.class);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
        return LogIn.connection;
    }

    /**
     *
     * @param username username to check
     * @param password password to check
     * if no such account then display error message for user
     * if there exists an account in database then complete loggedin process
     */
    public void checkAccount(String username, String password) {

        if (!connection()) {
            Toast.makeText(getApplicationContext(), "Failed To Connect To Server \nPlease Check Your Settings", Toast.LENGTH_SHORT).show();
        }
        else {
            dbUser.orderByChild("userName").equalTo(username).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        for (DataSnapshot adSnapshot : dataSnapshot.getChildren()) {
                            User u = adSnapshot.getValue(User.class);

                            if (u.password.equals(password)) {

                                loggedin(u.userName);

                            } else {
                                statusLabel.setText("Incorrect username or password");
                                Toast.makeText(LogIn.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();

                            }
                        }
                    } else {
                        statusLabel.setText("Incorrect username or password");
                        Toast.makeText(LogIn.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(LogIn.this, "DatabaseError, Please try again later", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    /**
     *
     * @param UserName userName of the logged user
     * move to MainActivity with User Information
     */
    public void loggedin(String UserName) {
        mPasswordEditText.setText("");
        mUserNameEditText.setText("");

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("UserName",UserName);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_SHORT).show();


    }
}
