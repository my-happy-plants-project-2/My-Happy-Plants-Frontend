package org.example.repositories;

import org.example.config.SQLConfig;
import org.example.model.Plant;
import org.example.services.IQueryExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlantRepository {

    private static final Logger LOGGER = Logger.getLogger(PlantRepository.class.getName());
    private IQueryExecutor queryExecutor;

    public PlantRepository(IQueryExecutor queryExecutor) {
        this.queryExecutor = queryExecutor;
    }

    // ToDo: Add object to connect to database
    public List<Plant> getAllPlants() {
        List<Plant> plants = new ArrayList<>();
        try (ResultSet resultSet = queryExecutor.executeQuery("SELECT * FROM plants")){
            while (resultSet.next()) {
                String plantID = resultSet.getString("plant_id"); // Replace with actual column name
                String commonName = resultSet.getString("common_name"); // Replace with actual column name
                String scientificName = resultSet.getString("scientific_name"); // Replace with actual column name
                String familyName = resultSet.getString("family_name"); // Replace with actual column name
                String imagePath = resultSet.getString("image_path"); // Replace with actual column name
                int waterFrequency = resultSet.getInt("water_frequency"); // Replace with actual column name

                plants.add(new Plant(plantID, commonName, scientificName, familyName, imagePath, waterFrequency));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving plants from database", e);

        }
        return plants;

    }
}
