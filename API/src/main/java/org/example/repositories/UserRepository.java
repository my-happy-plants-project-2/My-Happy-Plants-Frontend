package org.example.repositories;

import io.javalin.http.Context;
import org.example.model.Species;
import org.example.model.UserPlant;
import org.example.services.IQueryExecutor;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mindrot.jbcrypt.BCrypt;

public class UserRepository {
    private static final Logger LOGGER = Logger.getLogger(UserRepository.class.getName());
    private IQueryExecutor queryExecutor;

    public UserRepository(IQueryExecutor queryExecutor) {
        this.queryExecutor = queryExecutor;
    }

    public List<UserPlant> getUserPlants(String userEmail) {
        List<UserPlant> userPlants = new ArrayList<>();
        String query = "SELECT * FROM user_plants JOIN species ON user_plants.species = species.scientific_name WHERE owner = ?";
        List<Map<String, Object>> plantList = queryExecutor.executeQuery(query, userEmail);
        System.out.println("Plant list: " + plantList);
        for (Map map : plantList) {
            userPlants.add(plantOwnerResultSet(map));
        }
        if (plantList.isEmpty()) {
            LOGGER.log(Level.SEVERE, "Error retrieving user plants");
        }
        System.out.println("User plants: " + userPlants);
        return userPlants;
    }

    private UserPlant plantOwnerResultSet(Map<String, Object> resultSet) {
        if (!resultSet.isEmpty()) {
            String plantID = resultSet.get("plant_id").toString();
            String nickname = resultSet.get("nickname").toString();
            String owner = resultSet.get("owner").toString();
            Date lastWatered = (Date) resultSet.get("last_watered");
            String note = resultSet.get("note").toString();
            String scientificName = resultSet.get("scientific_name").toString();
            String commonName = resultSet.get("common_name").toString();
            String family = resultSet.get("family").toString();
            String category = resultSet.get("category").toString();
            String imageUrl = resultSet.get("image_url").toString();
            int lightReqs = Integer.parseInt(resultSet.get("light_reqs").toString());
            int waterFrequency = Integer.parseInt(resultSet.get("waterfrequency").toString());

            Species species = new Species(scientificName, commonName, family, category, imageUrl, lightReqs, waterFrequency);

            return new UserPlant(plantID, nickname, owner, species, lastWatered, note);
        }
        System.out.println("Empty result set in plantOwnerResultSet");
        LOGGER.log(Level.SEVERE, "Error processing result set");
        return null;
    }

    public boolean checkLogin(String email, String password) {
        String query = "SELECT password FROM \"users\" WHERE email = ?";
        List<Map<String, Object>> results = queryExecutor.executeQuery(query, email);
        if (!results.isEmpty()) {
            String hashedPassword = results.getFirst().get("password").toString();
            return BCrypt.checkpw(password, hashedPassword);
        }
        LOGGER.log(Level.INFO, "Invalid login credentials");

        return false;
    }

    public boolean waterPlant(String plantID, String userId) {
        try {
            String query = "UPDATE user_plants SET last_watered = CURRENT_DATE " +
                    "WHERE plant_id = ? AND owner = ?";

            queryExecutor.beginTransaction();
            queryExecutor.executeUpdate(query, plantID, userId);
            queryExecutor.endTransaction();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error watering plant", e);
            queryExecutor.rollbackTransaction();
            return false;
        }
    }

    public boolean addUser(String email, String username, String password, String colorTheme) {
        try {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            int colorThemeInt = Integer.parseInt(colorTheme);

            queryExecutor.beginTransaction();
            String insertQuery = "INSERT INTO users (email, username, password, color_theme) VALUES (?, ?, ?, ?)";
            queryExecutor.executeUpdate(insertQuery, email, username, hashedPassword, colorThemeInt);
            queryExecutor.endTransaction();

            return true;
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid colorTheme format", e);
            return false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error adding user to database", e);
            return false;
        }
    }

/*    public boolean deleteAccount(String email, String password) {
        if (!checkLogin(email, password)) {
            LOGGER.log(Level.INFO, "Login failure");
            return false;
        }*/
        public boolean deleteAccount(String email) {

        String queryDeletePlants = "DELETE * FROM \"user_plants\" WHERE owner = ?";
        String queryDeleteUser = "DELETE FROM \"users\" WHERE email = ?";

        try {
            queryExecutor.beginTransaction();
            queryExecutor.executeUpdate(queryDeletePlants, email);
            queryExecutor.executeUpdate(queryDeleteUser, email);
            queryExecutor.endTransaction();
            return true;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting account", e);
            return false;
        }
    }

    public boolean addOwnerPlant(String plantID, String nickname, String owner, String note, String species) {
        try {
            queryExecutor.beginTransaction();
            Date lastWatered = Date.valueOf(LocalDate.now());
            String insertQuery = "INSERT INTO user_plants (plant_id, nickname, owner, note, species, last_watered) VALUES (?, ?, ?, ?, ?, ?)";
            queryExecutor.executeUpdate(insertQuery, plantID, nickname, owner, note, species, lastWatered);
            queryExecutor.endTransaction();

            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error adding user plant to database", e);
            return false;
        }
    }

    public boolean deleteOwnerPlant(String plantID) {
        try {
            queryExecutor.beginTransaction();
            String deleteQuery = "DELETE FROM user_plants WHERE plant_id = ?";
            queryExecutor.executeUpdate(deleteQuery, plantID);
            queryExecutor.endTransaction();

            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting user plant from database", e);
            return false;
        }
    }
}
