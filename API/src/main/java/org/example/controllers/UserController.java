package org.example.controllers;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.model.OwnedPlant;
import org.example.services.UserService;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void registerRoutes(Javalin app) {

        // authoritzation routes
        app.post("/api/v1/auth/login", this::login);

        //  user routes
        app.post("/api/v1/user", this::addUser);
        app.delete("/api/v1/user", this::deleteUser);
        //app.patch("/api/v1/user", this::changeUserName);
        //app.patch("/api/v1/user/theme/{theme_id}", this::changeColorTheme);

        //  user plant library routes

        //Collections
        app.post("/api/v1/user/plants", this::addPlantToUserLibrary);
        app.delete("/api/v1/user/plants/{id}", this::deletePlantFromUserLibrary);
        app.get("/api/v1/user/plants", this::getUserPlants);
        app.patch("/api/v1/user/plants/water/{id}", this::waterPlant);
        //app.patch("/api/v1/user/plants/{id}", this::changeNickName);
        //app.patch("/api/v1/user/plants/note/{id}", this::updateNote);
    }

    public void addUser(@NotNull Context ctx) {
        userService.addUser(ctx);
    }

    public void login(@NotNull Context ctx) {
        userService.login(ctx);
    }

    public void getUserByEmail(@NotNull Context ctx) {
        userService.getUserByEmail(ctx);
    }

    public void deleteUser(@NotNull Context ctx) {
        userService.deleteUser(ctx);
    }

    //  get users plant library
    public void getUserPlants(@NotNull Context ctx) {
        List<OwnedPlant> userPlants = userService.getUserPlants(ctx);
        ctx.status(200).json(userPlants);
    }

    //  add plant to users library
    public void addPlantToUserLibrary(@NotNull Context ctx) {
        userService.addPlantToUserLibrary(ctx);
    }

    //  delete plant from users library
    public void deletePlantFromUserLibrary(@NotNull Context ctx) {
        userService.deletePlantFromUserLibrary(ctx);

    }

    //  update plant in users library
    public void updatePlantInUserLibrary(@NotNull Context ctx) {
        userService.updatePlantInUserLibrary(ctx);
    }

    // water plant
    public void waterPlant(@NotNull Context ctx) {
        userService.waterPlant(ctx);
    }
}
