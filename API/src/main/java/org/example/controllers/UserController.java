package org.example.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.model.UserPlant;
import org.example.services.UserService;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Controller class for API endpoints related to users
 * Contains methods for handling user-related requests
 *
 * @author Kasper Schröder
 */
public class UserController {
    private static final String API_VERSION = "/api/v1";

    private final UserService userService;

    /**
     * Constructor for UserController
     * @param userService the user service to be used for handling user-related operations
     *
     * @author Peter Norten
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registers routes for user-related endpoints
     * @param app the Javalin application to register routes with
     *
     * @author Pehr Norten
     * @author Kasper Schröder
     */
    public void registerRoutes(Javalin app) {

        // authorization routes
        app.post(API_VERSION + "/auth/login", this::login);

        //app.patch("/api/v1/user", this::changeUserName);
        //app.patch("/api/v1/user/theme/{theme_id}", this::changeColorTheme);
        //app.patch("/api/v1/user/plants/note/{id}", this::updateNote);

        app.before(API_VERSION + "/user/*",
                context -> {
                    if (!context.method().toString().equals("OPTIONS")) {
                        new JWTMiddleware().handle(context);
                    }
                });


        // user routes
        app.post(API_VERSION + "/user", this::addUser);
        app.delete(API_VERSION + "/user", this::deleteUser);

        // user plant library routes
        app.post(API_VERSION + "/user/plants", this::addPlantToUserLibrary);
        app.delete(API_VERSION + "/user/plants/{id}", this::deletePlantFromUserLibrary);
        app.get(API_VERSION + "/user/plants", this::getUserPlants);
        app.patch(API_VERSION + "/user/plants/water/{id}", this::waterPlant);
        app.patch(API_VERSION + "/user/plants/{id}", this::changeNickName);
    }

    private void login(Context context) {
        userService.login(context);
    }

    private void addUser(Context context) throws JsonProcessingException {
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

    private void changeNickName(Context context) {
        userService.changeNickname(context);
    }

}
