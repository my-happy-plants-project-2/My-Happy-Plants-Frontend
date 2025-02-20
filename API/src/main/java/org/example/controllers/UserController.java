package org.example.controllers;

import io.javalin.http.Context;
import org.example.services.UserService;
import org.jetbrains.annotations.NotNull;

public class UserController {
    private final UserService userService;

    public UserController() {
        userService = new UserService();
    }

    public void addUser(@NotNull Context context) {
        userService.addUser(context);
    }

    public void login(@NotNull Context context) {
        userService.login(context);
    }

    public void getUserByEmail(@NotNull Context context) {
        userService.getUserByEmail(context);
    }

    public void deleteUser(@NotNull Context context) {
        userService.deleteUser(context);
    }
}
