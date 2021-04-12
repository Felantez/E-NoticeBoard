package com.example.anew;

public class signUpData {

    String name;
    String email;
    String username;
    String password;
    String phoneNumber;

    public signUpData(){

    }


    public signUpData(String name, String email, String username, String password, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;


    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}

