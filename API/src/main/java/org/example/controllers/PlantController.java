package org.example.controllers;

import io.javalin.http.Context;
import org.example.model.Plant;
import org.example.services.PlantService;
import org.jetbrains.annotations.NotNull;

public class PlantController {
    private final PlantService plantService;

    public PlantController() {
        this.plantService = new PlantService();
    }

    public void addPlant(@NotNull Context ctx) {
        Plant plant = ctx.bodyAsClass(Plant.class);
        if (plantService.addPlant(plant)) {
            ctx.status(201);
        } else {
            ctx.status(400);
        }
    }

    public void deletePlant(@NotNull Context ctx) {
        String email = ctx.pathParam("email");
        String id = ctx.pathParam("id");
        if(plantService.deletePlant(email, id)) {
            ctx.status(204);
        } else {
            ctx.status(404);
        }
    }

    public void getPlantsByEmail(@NotNull Context ctx) {
        String email = ctx.pathParam("email");
        ctx.json(plantService.getPlantsByEmail(email));
    }

    public void getAllPlants(Context ctx) {
        ctx.json(plantService.getAllPlants());
    }
}
