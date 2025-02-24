package org.example.repositories;

import io.javalin.http.Context;
import org.example.model.OwnedPlant;
import org.example.model.User;
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

    public List<OwnedPlant> getUserPlants(Context context ) {
        List<OwnedPlant> ownedPlants = new ArrayList<>();
        String userId = context.cookie("user_id");

        String query = "SELECT op.id, op.nickname, op.last_watered, op.plant_id, op.image_url, " +
                "s.scientific_name, s.genus, s.family, s.common_name, s.image_url AS species_image_url, " +
                "s.light, s.url_wikipedia_en, s.water_frequency, " +
                "u.email " +
                "FROM plant op " +
                "JOIN species s ON op.plant_id = s.id " +
                "JOIN \"User\" u ON op.user_id = u.id " +
                "WHERE op.user_id = ?";

        try (Connection connection = queryExecutor.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ownedPlants.add(plantOwnerResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving user plants", e);
        }

        return ownedPlants;

    }

    private OwnedPlant plantOwnerResultSet(ResultSet resultSet) throws SQLException {
        String plantID = resultSet.getString("plant_id");
        String commonName = resultSet.getString("common_name");
        String scientificName = resultSet.getString("scientific_name");
        String familyName = resultSet.getString("family");
        String imagePath = resultSet.getString("species_image_url");
        int waterFrequency = Integer.parseInt(resultSet.getString("water_frequency"));
        String nickname = resultSet.getString("nickname");
        String lastWatered = resultSet.getString("last_watered");
        String ownersEmail = resultSet.getString("email");

        return new OwnedPlant(plantID, commonName, scientificName, familyName, imagePath, waterFrequency, nickname,
                lastWatered, ownersEmail);
    }

    public boolean checkLogin(String email, String password) {
        String query = "SELECT password FROM \"User\" WHERE email = ?";

        try (Connection connection = queryExecutor.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String hashedPassword = resultSet.getString(1);
                    return BCrypt.checkpw(password, hashedPassword);
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error during login check", e);
        }
        return false;

    }

    public boolean addUser(Context context) {
        try {
            User user = context.bodyAsClass(User.class);
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            String query = "INSERT INTO users (email, password_hash) VALUES (?, ?)";
            queryExecutor.executeUpdate(query, user.getEmail(), hashedPassword);

            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error adding user", e);
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

        try (Connection connection = queryExecutor.getConnection()) {
            queryExecutor.beginTransaction();

            try (PreparedStatement selectStatement = connection.prepareStatement(querySelect);
                 PreparedStatement deletePlantsStatement = connection.prepareStatement(queryDeletePlants);
                 PreparedStatement deleteUserStatement = connection.prepareStatement(queryDeleteUser)) {

                selectStatement.setString(1, email);

                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (!resultSet.next()) {
                        throw new SQLException("User not found");
                    }
                    int id = resultSet.getInt("id");


                    deletePlantsStatement.setInt(1, id);
                    deletePlantsStatement.executeUpdate();


                    deleteUserStatement.setInt(1, id);
                    deleteUserStatement.executeUpdate();

                    queryExecutor.endTransaction();
                    return true;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting account", e);
            queryExecutor.rollbackTransaction();

        }
        return false;
    }

    public User getUserDetails(Context context) {
        User user = null;
        String userID = context.pathParam("user_id");
        String query = "SELECT id, email, notification_activated, fun_facts_activated FROM \"User\" WHERE id = ?";

        try (ResultSet resultSet = queryExecutor.executeQuery(query, userID)) {
            if (resultSet.next()) {
                int uniqueID = resultSet.getInt("id");
                String email = resultSet.getString("email");
                boolean notificationActivated = resultSet.getBoolean("notification_activated");
                boolean funFactsActivated = resultSet.getBoolean("fun_facts_activated");

                System.out.println("User details: " + uniqueID + " " + email + " " + notificationActivated + " " + funFactsActivated);
                user = new User(String.valueOf(uniqueID), email, null);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return user;
    }


    public boolean changeNotifications(User user, boolean notifications) {
        LOGGER.log(Level.INFO, "Changing notification settings for user: " + user.getEmail());

        String query = "UPDATE \"User\" SET notification_activated = ? WHERE email = ?";
        int notificationsActivated = notifications ? 1 : 0;

        try (Connection connection = queryExecutor.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, notificationsActivated);
            statement.setString(2, user.getEmail());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Check if any rows were updated


        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error changing notification settings", e);
            return false;
        }
    }

    public boolean changeFunFacts(User user, Boolean funFactsActivated) {
        LOGGER.log(Level.INFO, "Changing fun facts settings for user: " + user.getEmail());

        String query = "UPDATE \"User\" SET fun_facts_activated = ? WHERE email = ?";
        int funFactsBitValue = funFactsActivated ? 1 : 0;


        try (Connection connection = queryExecutor.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, funFactsBitValue);
            statement.setString(2, user.getEmail());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Return true if update successful

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error changing fun facts settings", e);
            return false; // Indicate failure
        }
    }


}
