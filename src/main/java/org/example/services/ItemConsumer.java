package org.example.services;

import java.util.concurrent.BlockingQueue;
import org.example.enums.ItemType;
import org.example.model.Item;

public class ItemConsumer implements Runnable {
  private final BlockingQueue<Item> itemQueue;
  private final BlockingQueue<Item> processedItemQueue;
  private final TaxCalculatorService taxCalculatorService;

  public ItemConsumer(final BlockingQueue<Item> itemQueue,
                      final BlockingQueue<Item> processedItemQueue,
                      final TaxCalculatorService taxCalculatorService) {
    this.itemQueue = itemQueue;
    this.processedItemQueue = processedItemQueue;
    this.taxCalculatorService = taxCalculatorService;
  }

  @Override
  public void run() {
    try {
      while (true) {
        final Item item = itemQueue.take();
        final double taxOnItem =
            taxCalculatorService.calculateTax(ItemType.fromString(item.getType()),
                item.getCost());
        item.setTax(taxOnItem);
        processedItemQueue.put(item);
        System.out.println("Consumed: " + item);
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt(); // Preserving the interrupt status
      throw new RuntimeException("Thread was interrupted, failed to process item", e);
    }
  }
}
