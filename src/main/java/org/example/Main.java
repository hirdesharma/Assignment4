package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.example.database.DatabaseConnection;
import org.example.database.DatabaseInterface;
import org.example.model.Item;
import org.example.services.ItemConsumer;
import org.example.services.ItemProducer;
import org.example.services.TaxCalculatorService;

public class Main {
  public static void main(String[] args) {
    BlockingQueue<Item> itemQueue = new LinkedBlockingQueue<>();
    BlockingQueue<Item> processedItemQueue = new LinkedBlockingQueue<>();
    TaxCalculatorService taxCalculatorService = new TaxCalculatorService();
    DatabaseInterface dbConnection = new DatabaseConnection();

    // user Item data is produced
    ItemProducer producer = new ItemProducer(itemQueue, dbConnection);
    // user Item data is consumed after calculating the tax depending on the Item type
    ItemConsumer consumer = new ItemConsumer(itemQueue, processedItemQueue, taxCalculatorService);

    Thread producerThread = new Thread(producer);
    Thread consumerThread = new Thread(consumer);

    producerThread.start();
    consumerThread.start();

    try {
      producerThread.join();
      consumerThread.join();
    } catch (InterruptedException e) {
      System.out.println("The operation was interrupted. Please try again.");
      Thread.currentThread().interrupt(); // Restore the interrupted status
    }
  }
}
