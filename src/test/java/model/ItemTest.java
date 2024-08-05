package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.example.model.Item;
import org.junit.jupiter.api.Test;

public class ItemTest {

  @Test
  public final void testGetAndSetId() {
    final Item item = new Item();
    final int id = 1;
    item.setId(id);
    assertEquals(id, item.getId());
  }

  @Test
  public final void testGetAndSetName() {
    final Item item = new Item();
    final String name = "Test Item";
    item.setName(name);
    assertEquals(name, item.getName());
  }

  @Test
  public final void testGetAndSetCost() {
    final Item item = new Item();
    final double cost = 100.0;
    item.setCost(cost);
    assertEquals(cost, item.getCost());
  }

  @Test
  public final void testGetAndSetType() {
    final Item item = new Item();
    final String type = "RAW";
    item.setType(type);
    assertEquals(type, item.getType());
  }

  @Test
  public final void testGetAndSetTax() {
    final Item item = new Item();
    final double tax = 12.5;
    item.setTax(tax);
    assertEquals(tax, item.getTax());
  }

  @Test
  public final void testToString() {
    final Item item = new Item();
    item.setId(1);
    item.setName("Test Item");
    item.setCost(100.0);
    item.setType("RAW");
    item.setTax(12.5);

    final String expectedString = "Item{id=1, name='Test Item', cost=100.0, type='RAW', tax=12.5}";
    assertEquals(expectedString, item.toString());
  }

  @Test
  public final void testDefaultConstructor() {
    final Item item = new Item();
    assertNotNull(item);
    assertEquals(0, item.getId());
    assertNull(item.getName());
    assertEquals(0.0, item.getCost());
    assertNull(item.getType());
    assertEquals(0.0, item.getTax());
  }
}
