package com.example.dj.Activities;

public class User {
    private String email; //the name is the email
    private String fullName;
    private String userType;
    private String genre;
    private String id;



    public User(String email, String fullName, String userType, String genre, String id) {
        this.email = email;
        this.fullName = fullName;
        this.userType = userType;
        this.genre =genre;
        this.id=id;


    }
    public User(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
