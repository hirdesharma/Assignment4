package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.example.config.AppConfig;

public class DatabaseConnection implements DatabaseInterface {
  private final AppConfig config;

  public DatabaseConnection() {
    this.config = new AppConfig();
  }

  @Override
  public final Connection getConnection() throws SQLException {
    String url = config.getDatabaseUrl();
    String username = config.getDatabaseUsername();
    String password = config.getDatabasePassword();
    return DriverManager.getConnection(url, username, password);
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
