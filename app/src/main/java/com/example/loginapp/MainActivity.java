package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Credentials;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText eName;
    private EditText ePassword;
    private Button elogin;
    private TextView eAttempetsInfo;
    private TextView eRegister;
    private CheckBox eRememberMe;

    private boolean isValid = false;
    private int counter = 5;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eName = findViewById(R.id.etName);
        ePassword = findViewById(R.id.etPassword);
        elogin = findViewById(R.id.btnLogin);
        eAttempetsInfo = findViewById(R.id.tvAttempetsInfo);
        eRegister = findViewById(R.id.tvRegister);
        eRememberMe = findViewById(R.id.cbRememberMe);

        sharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDB", MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        if (sharedPreferences != null) {
            String savedUsername = sharedPreferences.getString("Username", "");
            String savedPassword = sharedPreferences.getString("Password", "");

            RegistationActivity.cradentials = new Cradentials(savedUsername, savedPassword);

            if (sharedPreferences.getBoolean("RememberMeCheckbox", false)) {
                eName.setText(savedUsername);
                ePassword.setText(savedPassword);
                eRememberMe.setChecked(true);

            }
        }
        eRememberMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferencesEditor.putBoolean("RememberMeCheckbox", eRememberMe.isChecked());
                sharedPreferencesEditor.apply();
            }
        });

            eRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, RegistationActivity.class));
                }
            });

            //to program the login button//
            elogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String inputName = eName.getText().toString();
                    String inputPassword = ePassword.getText().toString();

                    // to check the username and the password fields is not empty//
                    if (inputName.isEmpty() || inputPassword.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please enter valid UserName and Password :) ",
                                Toast.LENGTH_LONG).show();
                        eName.requestFocus();

                        //if the username and password fields is used , now it's time to chick the validation//
                    } else {
                        isValid = validate(inputName, inputPassword);

                        //when the username and the password isd wrong//
                        if (!isValid) {
                            Toast.makeText(MainActivity.this, "Username Or Password is incorrect :( ",
                                    Toast.LENGTH_LONG).show();
                            eName.requestFocus();
                            counter--;
                            eAttempetsInfo.setText("No. of attempts remaining: " + counter);

                            //when the user run out of attempts//
                            if (counter == 0) {
                                elogin.setEnabled(false);
                            }

                            //when the username and the password is correct//
                        } else {
                            Toast.makeText(MainActivity.this, "Login successful :) ",
                                    Toast.LENGTH_LONG).show();


                            // add the code to go to new activity //
                            Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            });
        }

        //method to validate the username and password//
        private boolean validate (String name, String password){
            if (RegistationActivity.cradentials != null) {

                if (name.equals(RegistationActivity.cradentials.getUsername()) &&
                        password.equals(RegistationActivity.cradentials.getPassword())) {
                    return true;
                }
            }
            return false;
        }

    }