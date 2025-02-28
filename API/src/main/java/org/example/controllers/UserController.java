package org.example.controllers;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.model.UserPlant;
import org.example.services.UserService;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserController {
    private static final String API_VERSION = "/api/v1";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void registerRoutes(Javalin app) {
        // authorization routes
        app.post(API_VERSION + "/auth/login", this::login);

        app.before(API_VERSION + "/user/*", new JWTMiddleware());

        // user routes
        app.post(API_VERSION + "/user", this::addUser);
        app.delete(API_VERSION + "/user", this::deleteUser);

        // user plant library routes
        app.post(API_VERSION + "/user/plants", this::addPlantToUserLibrary);
        app.delete(API_VERSION + "/user/plants/{id}", this::deletePlantFromUserLibrary);
        app.get(API_VERSION + "/user/plants", this::getUserPlants);
        app.patch(API_VERSION + "/user/plants/water/{id}", this::waterPlant);
    }

    private void login(Context context) {
        userService.login(context);
    }

    private void addUser(Context context) {
        userService.addUser(context);
    }

    private void deleteUser(Context context) {
        userService.deleteUser(context);
    }

    private void addPlantToUserLibrary(Context context) {
        userService.addPlantToUserLibrary(context);
    }

    private void deletePlantFromUserLibrary(Context context) {
        userService.deletePlantFromUserLibrary(context);
    }

    private void getUserPlants(Context context) {
        userService.getUserPlants(context);
    }

    private void waterPlant(Context context) {
        userService.waterPlant(context);
    }

}
