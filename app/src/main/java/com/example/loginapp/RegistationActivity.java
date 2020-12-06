package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistationActivity extends AppCompatActivity {

    private EditText eRegName;
    private EditText eRegPassword;
    private Button eRegister;

    public static Cradentials cradentials;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);

        eRegName = findViewById(R.id.etRegName);
        eRegPassword = findViewById(R.id.etRegPassword);
        eRegister = findViewById(R.id.btnRegister);

        sharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDB", MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();


        eRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regUsername = eRegName.getText().toString();
                String regPassword = eRegPassword.getText().toString();

                if (validate(regUsername, regPassword)) {
                    cradentials = new Cradentials(regUsername, regPassword);
                    // store the credentials at this point//
                    sharedPreferencesEditor.putString("Username", regUsername);
                    sharedPreferencesEditor.putString("Password", regPassword);
                    sharedPreferencesEditor.apply();

                    startActivity(new Intent(RegistationActivity.this, MainActivity.class));
                    Toast.makeText(RegistationActivity.this, "Registration Successful :) ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validate(String username, String password) {
        if (username.isEmpty() || password.length() < 8) {
            Toast.makeText(this, "Please enter all date ,Password should be at least 8 Character:(", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}