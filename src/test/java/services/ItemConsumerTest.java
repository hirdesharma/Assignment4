package services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.example.enums.ItemType;
import org.example.model.Item;
import org.example.services.ItemConsumer;
import org.example.services.TaxCalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ItemConsumerTest {

  private BlockingQueue<Item> itemQueue;
  private BlockingQueue<Item> processedItemQueue;
  private TaxCalculatorService taxCalculatorService;
  private ItemConsumer itemConsumer;

  @BeforeEach
  public void setUp() {
    itemQueue = new ArrayBlockingQueue<>(10);
    processedItemQueue = new ArrayBlockingQueue<>(10);
    taxCalculatorService = mock(TaxCalculatorService.class);
    itemConsumer = new ItemConsumer(itemQueue, processedItemQueue, taxCalculatorService);
  }

  @Test
  public final void testConsumesAndProcessesItem() throws InterruptedException {
    // Arrange
    final Item item = new Item();
    item.setType(ItemType.RAW.name());
    item.setCost(100.0);
    itemQueue.put(item);

    when(taxCalculatorService.calculateTax(ItemType.RAW, 100.0)).thenReturn(10.0);

    final Thread consumerThread = new Thread(itemConsumer);
    consumerThread.start();

    Thread.sleep(100);  //time for the consumer to process
    consumerThread.interrupt(); // Stop the consumer thread

    // Assert
    assertEquals(0, itemQueue.size());
    assertEquals(1, processedItemQueue.size());
    Item processedItem = processedItemQueue.take();
    assertEquals(10.0, processedItem.getTax());
  }

  @Test
  public final void testHandlesInterruptedException() throws InterruptedException {
    final Thread consumerThread = new Thread(itemConsumer);
    consumerThread.start();

    consumerThread.interrupt(); // Interrupt the thread

    Thread.sleep(100); // Give time to handle the interrupt
    assertEquals(0, itemQueue.size());
    assertEquals(0, processedItemQueue.size());
  }

  @Test
  public final void testProcessesMultipleItems() throws InterruptedException {
    // Arrange
    final Item item1 = new Item();
    item1.setType(ItemType.RAW.name());
    item1.setCost(100.0);
    final Item item2 = new Item();
    item2.setType(ItemType.MANUFACTURED.name());
    item2.setCost(200.0);
    itemQueue.put(item1);
    itemQueue.put(item2);

    when(taxCalculatorService.calculateTax(ItemType.RAW, 100.0)).thenReturn(10.0);
    when(taxCalculatorService.calculateTax(ItemType.MANUFACTURED, 200.0)).thenReturn(30.0);

    final Thread consumerThread = new Thread(itemConsumer);
    consumerThread.start();

    Thread.sleep(100); // time for the consumer to process
    consumerThread.interrupt(); // Stop the consumer thread

    assertEquals(0, itemQueue.size());
    assertEquals(2, processedItemQueue.size());
    Item processedItem1 = processedItemQueue.take();
    Item processedItem2 = processedItemQueue.take();
    assertEquals(10.0, processedItem1.getTax());
    assertEquals(30.0, processedItem2.getTax());
  }
}
