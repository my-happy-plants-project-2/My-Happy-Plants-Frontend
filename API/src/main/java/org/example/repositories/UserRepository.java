package org.example.repositories;

import io.javalin.http.Context;
import org.example.model.Species;
import org.example.model.UserPlant;
import org.example.services.IQueryExecutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

        try (ResultSet resultSet = queryExecutor.executeQuery("SELECT up.*, s.* FROM user_plants up JOIN species " +
                "s ON up.species = s.scientific_name WHERE owner = ?", userEmail)) {
            while (resultSet.next()) {
                userPlants.add(plantOwnerResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving user plants", e);
        }
        return userPlants;
    }

    private UserPlant plantOwnerResultSet(ResultSet resultSet) throws SQLException {
        String plantID = resultSet.getString("plant_id");
        String nickname = resultSet.getString("nickname");
        String owner = resultSet.getString("owner");
        Date lastWatered = resultSet.getDate("last_watered");
        String note = resultSet.getString("note");

        String scientificName = resultSet.getString("scientific_name");
        String commonName = resultSet.getString("common_name");
        String family = resultSet.getString("family");
        String category = resultSet.getString("category");
        String imageUrl = resultSet.getString("image_url");
        int lightReqs = resultSet.getInt("light_reqs");
        int waterFrequency = resultSet.getInt("water_frequency");

        Species species = new Species(scientificName, commonName, family, category, imageUrl, lightReqs, waterFrequency);

        return new UserPlant(plantID, nickname, owner, species, lastWatered, note);
    }

    public boolean checkLogin(String email, String password) {
        String query = "SELECT password FROM \"User\" WHERE email = ?";

        try (ResultSet resultSet = queryExecutor.executeQuery(query, email)) {
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString(1);
                return BCrypt.checkpw(password, hashedPassword);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error during login check", e);
        }
        return false;
    }

    public boolean waterPlant(String plantID, String userId) {
        try {
            String query = "UPDATE owned_plants SET last_watered = CURRENT_DATE " +
                    "WHERE plant_id = ? AND user_id = ?";

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

    public boolean deleteAccount(String email, String password) {
        if (!checkLogin(email, password)) {
            LOGGER.log(Level.INFO, "Login failure");
            return false;
        }

        String querySelect = "SELECT id FROM \"User\" WHERE email = ?";
        String queryDeletePlants = "DELETE FROM \"plant\" WHERE user_id = ?";
        String queryDeleteUser = "DELETE FROM \"User\" WHERE id = ?";

        try {
            queryExecutor.beginTransaction();

            try (ResultSet resultSet = queryExecutor.executeQuery(querySelect, email)) {
                if (!resultSet.next()) {
                    throw new SQLException("User not found");
                }
                int id = resultSet.getInt("id");

                queryExecutor.executeUpdate(queryDeletePlants, id);
                queryExecutor.executeUpdate(queryDeleteUser, id);

                queryExecutor.endTransaction();
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting account", e);
            queryExecutor.rollbackTransaction();
        }
        return false;
    }

    public boolean addOwnerPlant(String plantID, String nickname, String owner, String note) {
        try {
            queryExecutor.beginTransaction();
            String insertQuery = "INSERT INTO user_plants (plant_id, nickname, owner, note) VALUES (?, ?, ?, ?)";
            queryExecutor.executeUpdate(insertQuery, plantID, nickname, owner, note);
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

    public boolean upDateOwnerPlant() {
        return false;
    }

}
