package org.example.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface DatabaseInterface {
  Connection getConnection() throws SQLException;

  ResultSet getItemData() throws SQLException;

  void closeResources(final Connection connection, final Statement statement,
                      final ResultSet resultSet) throws SQLException;
}
