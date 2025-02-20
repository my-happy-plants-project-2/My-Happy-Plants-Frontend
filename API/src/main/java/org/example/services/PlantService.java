package org.example.services;

import org.example.model.Plant;
import org.example.repositories.PlantRepository;

import java.util.List;

public class PlantService {
    private final PlantRepository plantRepository;

    public PlantService() {
        this.plantRepository = new PlantRepository();
    }


    public List<Plant> getAllPlants() {
        return plantRepository.getAllPlants();
    }

    public List<Plant> getPlantsByEmail(String email) {
        return plantRepository.getPlantsByEmail(email);
    }

    public boolean addPlant(Plant plant) {
        return plantRepository.addPlant(plant);
    }

    public boolean deletePlant(String email, String id) {
        return plantRepository.deletePlant(email, id);
    }
}
