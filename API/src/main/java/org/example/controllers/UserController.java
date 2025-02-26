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
        // Implement login logic here
    }

    private void addUser(Context context) {
        userService.addUser(context); // As implemented previously
    }

    private void deleteUser(Context context) {
        // Implement user deletion logic here.  Likely needs a user identifier.
    }

    private void addPlantToUserLibrary(Context context) {
        // Implement logic to add a plant to the user's library
    }


    private void deletePlantFromUserLibrary(Context context) {
        // Implement logic to delete a plant from the user's library.  Use context.pathParam("id") to get the plant ID.
    }

    private void getUserPlants(Context context) {
        // Implement logic to retrieve the plants in a user's library
    }

    private void waterPlant(Context context) {
        // Implement logic to update a plant's watering status.  Use context.pathParam("id") to get the plant ID.
    }

}
