package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.project1.ui.history.HistoryPage;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    private String UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //access loggedin user information
        Intent intent=getIntent();
        UserName=intent.getStringExtra("UserName");



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_post, R.id.nav_my_post,R.id.nav_my_task,R.id.nav_login,R.id.nav_not_logged,R.id.nav_edit,R.id.nav_history,R.id.nav_chat)
                .setDrawerLayout(drawer)
                .build();

         NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //when click "login" in the navigation bar, go to login activity
        Intent switchLogIn = new Intent(this, LogIn.class);
        Intent switchHistory = new Intent(this, HistoryPage.class);
        switchLogIn.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_login:{
                        startActivity(switchLogIn);
                        break;}
                    case R.id.nav_post:{
                        if (UserName == null) {
                            navController.navigate(R.id.nav_not_logged);
                        }
                        else{
                        navController.navigate(R.id.nav_post);}
                        break;
                    }

                    case R.id.nav_home:{
                        navController.navigate(R.id.nav_home);
                        break;
                    }
                    case R.id.nav_mypost:{
                        if (UserName == null) {
                            navController.navigate(R.id.nav_not_logged);
                        }
                        else{
                        navController.navigate(R.id.nav_my_post);}
                        break;
                    }
                    case R.id.nav_mytask:{
                        if (UserName == null) {
                            navController.navigate(R.id.nav_not_logged);
                        }
                        else{
                        navController.navigate(R.id.nav_my_task);}
                        break;
                    }
                    case R.id.nav_chat:{
                        if (UserName == null) {
                            navController.navigate(R.id.nav_not_logged);
                        }
                        else{
                            navController.navigate(R.id.nav_chat);}
                        break;
                    }
                    case  R.id.nav_history:{
                        if (UserName == null) {
                            navController.navigate(R.id.nav_not_logged);
                        }
                        else{
                            navController.navigate(R.id.nav_history);
                        break;
                        }
                    }

                }
                return true;
            }
        });

        //after login and get user information
        if (UserName!=null){
        //update the user information in the navigation bar
            View mHeaderView = navigationView.getHeaderView(0);
            TextView mDrawerHeaderTitle = (TextView) mHeaderView.findViewById(R.id.nav_username);
           mDrawerHeaderTitle.setText(UserName);
           //set up logout button
           Menu menuInfo=navigationView.getMenu();
            MenuItem itemInfo = menuInfo.getItem(6);
            itemInfo.setTitle(R.string.logoutBtn);
            }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View view) {


    }
    public  String getUserName(){
        return this.UserName;
    }




}