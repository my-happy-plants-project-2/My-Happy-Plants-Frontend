package org.example.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface IQueryExecutor {

    boolean executeUpdate(String query, Object... parameters);

    List<Map<String, Object>> executeQuery(String query, Object... parameters);

    void beginTransaction();

    void endTransaction();

    void rollbackTransaction();

    Connection getConnection();
}
