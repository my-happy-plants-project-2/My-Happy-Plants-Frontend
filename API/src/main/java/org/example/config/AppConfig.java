package org.example.config;

import io.javalin.Javalin;
import io.javalin.plugin.bundled.CorsPluginConfig;
import org.example.controllers.PlantController;
import org.example.controllers.UserController;

public class AppConfig {
    public static Javalin configureApp() {
        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(CorsPluginConfig.CorsRule::anyHost);
            });
        });

        // Initialize controllers
        PlantController plantController = new PlantController();
        UserController userController = new UserController();


        // Register routes
        app.post("/api/v1/plants", plantController::addPlant);
        app.delete("/api/v1/plants/{email}/{id}", plantController::deletePlant);
        app.get("/api/v1/plants/{email}", plantController::getPlantsByEmail);
        app.get("/api/v1/plants", plantController::getAllPlants);
        app.post("/api/v1/user", userController::addUser);
        app.post("/api/v1/user/login", userController::login);
        app.get("/api/v1/user/{email}", userController::getUserByEmail);
        app.delete("/api/v1/user/{email}", userController::deleteUser);

        return app;
    }
}