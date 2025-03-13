package org.example.config;

import java.sql.Connection;

public interface ISQLConfig {

    Connection getConnection();

    void closeConnection();

}