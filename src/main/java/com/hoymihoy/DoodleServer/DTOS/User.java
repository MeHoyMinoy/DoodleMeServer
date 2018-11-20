package com.hoymihoy.DoodleServer.DTOS;

import java.sql.Date;

public class User {
    private int userID;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private Date birthdate;
    private String email;

    public void setUserID(int userID) { this.userID = userID; }

    public int getUserID() { return userID; }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
