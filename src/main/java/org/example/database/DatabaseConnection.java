package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.example.constants.DatabaseConstants;

public class DatabaseConnection implements DatabaseInterface {
  @Override
  public final Connection getConnection() throws SQLException {
    return DriverManager.getConnection(DatabaseConstants.URL, DatabaseConstants.USER,
        DatabaseConstants.PASSWORD);
  }

  @Override
  public final ResultSet getItemData() throws SQLException {
    try {
      final Connection connection = getConnection();
      final Statement statement = connection.createStatement();
      return statement.executeQuery("SELECT * FROM items");
    } catch (SQLException e) {
      throw new SQLException("Error executing query: " + e.getMessage(), e);
    }
  }

  @Override
  public final void closeResources(final Connection connection, final Statement statement,
                                   final ResultSet resultSet) throws SQLException {
    try {
      if (resultSet != null) {
        resultSet.close();
      }
      if (statement != null) {
        statement.close();
      }
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException e) {
      throw new SQLException(e);
    }
  }
}
