package com.zybooks.weightlogv2.controller.LoginControllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.zybooks.weightlogv2.Menu.fragment_settings;
import com.zybooks.weightlogv2.R;
import com.zybooks.weightlogv2.model.LoginModels.LoginInfoDataSource;
import com.zybooks.weightlogv2.controller.MainControllers.MainActivity;

public class UserLogin extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button submitButton;
    private Button createButton;
    private Toolbar toolbar; // Declare a Toolbar variable

    private static LoginInfoDataSource instance;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        usernameEditText = findViewById(R.id.username_login);
        passwordEditText = findViewById(R.id.password_login);
        submitButton = findViewById(R.id.submit_button);
        createButton = findViewById(R.id.create_button);

        instance = LoginInfoDataSource.getInstance(this); // Initialize the instance


        setSupportActionBar(findViewById(R.id.tool_bar));
        getSupportActionBar().setTitle("Weight Journal");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitButtonClick();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateButtonClick();
            }
        });
    }

    public void onSubmitButtonClick() {
        // Handle login button click
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(instance.evaluateUserCredentials(username,password)){
            Intent intent = new Intent(UserLogin.this, MainActivity.class);
            startActivity(intent);
        }else{
            //No Matching values in LoginInfo table
        }
    }

    //Calls the fragment to the fragment container in the user login layout
    public void onCreateButtonClick() {
        CreateNewAccount createNewAccountFragment = new CreateNewAccount();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, createNewAccountFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Fragment settingsFragment = new fragment_settings();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, settingsFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}