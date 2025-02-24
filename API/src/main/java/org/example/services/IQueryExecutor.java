package org.example.services;

import java.sql.Connection;
import java.sql.ResultSet;

public interface IQueryExecutor {

    void executeUpdate(String query, Object... parameters);

    ResultSet executeQuery(String query, Object... parameters);

    void beginTransaction();

    void endTransaction();

    void rollbackTransaction();

    Connection getConnection();
}
