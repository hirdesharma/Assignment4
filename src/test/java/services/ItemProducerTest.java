package services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import org.example.database.DatabaseInterface;
import org.example.model.Item;
import org.example.services.ItemProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ItemProducerTest {

  private BlockingQueue<Item> itemQueue;
  private DatabaseInterface dbConnection;
  private ItemProducer itemProducer;

  @BeforeEach
  void setUp() {
    itemQueue = mock(BlockingQueue.class);
    dbConnection = mock(DatabaseInterface.class);
    itemProducer = new ItemProducer(itemQueue, dbConnection);
  }

  @Test
  void testRun() throws SQLException, InterruptedException {
    // Mocking ResultSet
    final ResultSet resultSet = mock(ResultSet.class);

    // Mocking database behavior
    when(dbConnection.getItemData()).thenReturn(resultSet);

    // Simulating two items being returned by the ResultSet
    when(resultSet.next()).thenReturn(true, true, false); // Two items and then end of ResultSet
    when(resultSet.getInt("id")).thenReturn(1, 2);
    when(resultSet.getString("name")).thenReturn("Item1", "Item2");
    when(resultSet.getDouble("cost")).thenReturn(100.0, 200.0);
    when(resultSet.getString("type")).thenReturn("Type1", "Type2");

    // Run the ItemProducer
    itemProducer.run();

    // Verifying the interactions
    verify(itemQueue, times(2)).put(any(Item.class)); // Two items should be added to the queue
    verify(dbConnection, times(1)).getItemData(); // getItemData should be called once
  }

  @Test
  void testRun_withSQLException() throws SQLException {
    // Mocking database behavior to throw SQLException
    when(dbConnection.getItemData()).thenThrow(new SQLException("Database error"));

    // Testing the exception is properly handled
    RuntimeException exception = assertThrows(RuntimeException.class, itemProducer::run);
    assertEquals("Thread was interrupted, failed to process item", exception.getMessage());
  }

  @Test
  void testRun_withInterruptedException() throws SQLException, InterruptedException {
    // Mocking ResultSet
    final ResultSet resultSet = mock(ResultSet.class);
    when(dbConnection.getItemData()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true, false);
    when(resultSet.getInt("id")).thenReturn(1);
    when(resultSet.getString("name")).thenReturn("Item1");
    when(resultSet.getDouble("cost")).thenReturn(100.0);
    when(resultSet.getString("type")).thenReturn("Type1");

    // Mocking itemQueue to throw InterruptedException
    doThrow(new InterruptedException()).when(itemQueue).put(any(Item.class));

    // Testing the exception is properly handled
    RuntimeException exception = assertThrows(RuntimeException.class, itemProducer::run);
    assertEquals("Thread was interrupted, failed to process item", exception.getMessage());
  }
}
