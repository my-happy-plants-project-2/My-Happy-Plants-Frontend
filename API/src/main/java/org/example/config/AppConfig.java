package org.example.config;

import io.javalin.Javalin;
import io.javalin.plugin.bundled.CorsPluginConfig;
import org.example.controllers.PlantController;
import org.example.controllers.UserController;

/**
 * Class for configuring the Javalin application
 * Contains a method for configuring the application
 * with the necessary controllers
 *
 * @author Pehr Norten
 * @author Kasper Schröder
 */
public class AppConfig {

    /**
     * Configures the Javalin application with the necessary controllers
     * @param plantController the plant controller to be registered
     * @param userController the user controller to be registered
     * @return the configured Javalin application
     *
     * @author Kasper Schröder
     * @author Pehr Norten
     */
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