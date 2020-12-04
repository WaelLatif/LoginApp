package com.example.loginapp;

public class Cradentials {

   private String Username ;
   private String Password ;
Cradentials(String username, String password){

    this.Username = username;
    this.Password = password;

}

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
