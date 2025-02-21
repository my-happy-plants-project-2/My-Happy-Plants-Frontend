package org.example.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.model.Plant;
import org.example.services.PlantService;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class PlantController {
    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    public void registerRoutes(Javalin app) {
        app.get("/api/v1/plants", this::getAllPlants);
    }

    public void getAllPlants(@NotNull Context ctx) {
        List<Plant> plants = plantService.getAllPlants();

        if (plants.isEmpty()) {
            ctx.status(200).json("[]");
        } else {
            ctx.json(plants);
        }
    }
}
