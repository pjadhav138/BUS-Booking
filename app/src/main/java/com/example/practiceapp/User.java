package com.example.practiceapp;

public class User {
    String name;
    String email;
    String country;
    String Password;
    boolean Termsandcondition;

    public User(String name, String email, String country, String password, boolean termsandcondition) {
        this.name = name;
        this.email = email;
        this.country = country;
        Password = password;
        Termsandcondition = termsandcondition;


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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public boolean isTermsandcondition() {
        return Termsandcondition;
    }

    public void setTermsandcondition(boolean termsandcondition) {
        Termsandcondition = termsandcondition;
    }
}
