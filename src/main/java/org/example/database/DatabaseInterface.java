package org.example.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface DatabaseInterface {
  public Connection getConnection() throws SQLException;

  public ResultSet getItemData() throws SQLException;

  public void closeResources(final Connection connection, final Statement statement,
                             final ResultSet resultSet) throws SQLException;
}
