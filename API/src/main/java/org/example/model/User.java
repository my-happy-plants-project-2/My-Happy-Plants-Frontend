package org.example.model;

public class User {
    private String userName;
    private String email;
    private String password;
    private int colorTheme;

    public User(String userName, String email, String password, int colorTheme) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.colorTheme = colorTheme;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userID) {
        this.userName = userName;
    }

    public int getColorTheme() {
        return colorTheme;
    }

    public void setColorTheme(int colorTheme) {
        this.colorTheme = colorTheme;
    }
}
