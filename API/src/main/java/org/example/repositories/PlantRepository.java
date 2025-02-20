package org.example.repositories;

import org.example.model.Plant;

import java.util.ArrayList;
import java.util.List;

public class PlantRepository {

    // ToDo: Add object to connect to database

    public List<Plant> getPlantsByEmail(String email) {
        return null;
    }

    public boolean addPlant(Plant plant) {
        return false;
    }

    public boolean deletePlant(String email, String id) {
        return false;
    }

    public List<Plant> getAllPlants() {
        // make list of plants

        ArrayList<Plant> plants = new ArrayList<Plant>();

        // add plants to list
        plants.add(new Plant("1", "Aloe Vera", "Aloe Vera", "Asphodelaceae", "aloe-vera.jpg", 14, "Aloe", "2021-09-01"));
        plants.add(new Plant("2", "Basil", "Basil", "Lamiaceae", "basil.jpg", 7, "Ocimum", "2021-09-01"));
        plants.add(new Plant("3", "Cactus", "Cactus", "Cactaceae", "cactus.jpg", 21, "Cactaceae", "2021-09-01"));

        // ToDo: make actual request to database


        return  plants;
    }
}
