package org.example.config;

import io.javalin.Javalin;
import io.javalin.plugin.bundled.CorsPluginConfig;
import org.example.controllers.PlantController;
import org.example.controllers.UserController;

public class AppConfig {
    public static Javalin configureApp(PlantController plantController, UserController userController) {
        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(CorsPluginConfig.CorsRule::anyHost);
            });
        });

        // Register routes within controllers
        plantController.registerRoutes(app);
        userController.registerRoutes(app);

        return app;
    }
}