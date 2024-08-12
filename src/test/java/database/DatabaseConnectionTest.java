package database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.example.config.AppConfig;
import org.example.database.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class DatabaseConnectionTest {

  private DatabaseConnection databaseConnection;
  private final AppConfig config = new AppConfig();
  String url = config.getDatabaseUrl();
  String username = config.getDatabaseUsername();
  String password = config.getDatabasePassword();

  @BeforeEach
  void setUp() {
    databaseConnection = new DatabaseConnection();
  }

  @Test
  void testGetConnection() throws SQLException {
    try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
      Connection mockConnection = mock(Connection.class);
      mockedDriverManager.when(() -> DriverManager.getConnection(url, username, password))
          .thenReturn(mockConnection);

      Connection connection = databaseConnection.getConnection();

      assertNotNull(connection);
      assertEquals(mockConnection, connection);
      mockedDriverManager.verify(() -> DriverManager.getConnection(url, username, password),
          times(1));
    }
  }

  @Test
  void testGetItemData() throws SQLException {
    Connection mockConnection = mock(Connection.class);
    Statement mockStatement = mock(Statement.class);
    ResultSet mockResultSet = mock(ResultSet.class);

    try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
      mockedDriverManager.when(() -> DriverManager.getConnection(url, username, password))
          .thenReturn(mockConnection);

      when(mockConnection.createStatement()).thenReturn(mockStatement);
      when(mockStatement.executeQuery("SELECT * FROM itemdata")).thenReturn(mockResultSet);

      ResultSet resultSet = databaseConnection.getItemData();

      assertNotNull(resultSet);
      assertEquals(mockResultSet, resultSet);

      verify(mockConnection, times(1)).createStatement();
      verify(mockStatement, times(1)).executeQuery("SELECT * FROM itemdata");
    }
  }
}
