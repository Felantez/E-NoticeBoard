package com.example.anew;

public class signUpData {

    private String name;
    private String email;
    private String username;
    private String password;
    private String phoneNumber;

    public signUpData(){

    }
    public signUpData(String email, String name, String password, String phoneNumber, String username) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }







}

