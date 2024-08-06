package org.example.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import org.example.database.DatabaseInterface;
import org.example.model.Item;

public class ItemProducer implements Runnable {
  private final BlockingQueue<Item> itemQueue;
  private final DatabaseInterface dbConnection;

  public ItemProducer(final BlockingQueue<Item> itemQueue, final DatabaseInterface dbConnection) {
    this.itemQueue = itemQueue;
    this.dbConnection = dbConnection;
  }

  @Override
  public void run() {
    try {
      final ResultSet resultSet = dbConnection.getItemData();
      while (resultSet.next()) {
        final int id = resultSet.getInt("id");
        final String name = resultSet.getString("name");
        final double cost = resultSet.getDouble("cost");
        final String type = resultSet.getString("type");

        final Item item = new Item();
        item.setType(type);
        item.setCost(cost);
        item.setId(id);
        item.setName(name);

        itemQueue.put(item);
        System.out.println("\nProduced: " + item);
      }
    } catch (SQLException | InterruptedException e) {
      throw new RuntimeException("Thread was interrupted, failed to process item", e);
    }
  }
}
