package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jfr.Percentage;

public class User {
    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("colorTheme")
    private int colorTheme;

    public User(String username, String email, String password, int colorTheme) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.colorTheme = colorTheme;
    }

    public User() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public void setUsername(String userID) {
        this.username = userID;
    }

    public int getColorTheme() {
        return colorTheme;
    }

    public void setColorTheme(int colorTheme) {
        this.colorTheme = colorTheme;
    }
}
