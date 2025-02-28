package org.example.repositories;

import org.example.model.Species;
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

    public List<Species> getAllSpecies() {
        List<Species> species = new ArrayList<>();
        try (ResultSet resultSet = queryExecutor.executeQuery("SELECT * FROM species")){
            while (resultSet.next()) {
                String scientificName = resultSet.getString("scientific_name");
                String family = resultSet.getString("family");
                String commonName = resultSet.getString("common_name");
                String category = resultSet.getString("category");
                String imageUrl = resultSet.getString("image_url");
                int lightReqs = resultSet.getInt("light_reqs");
                int waterFrequency = resultSet.getInt("water_frequency");

                species.add(new Species(scientificName, commonName, family, category, imageUrl, lightReqs, waterFrequency));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving plants from database", e);

        }
        return species;

    }
}
