package services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.example.enums.ItemType;
import org.example.services.TaxCalculatorService;
import org.junit.jupiter.api.Test;

public class TaxCalculatorServiceTest {

  private final TaxCalculatorService taxCalculatorService = new TaxCalculatorService();

  @Test
  public final void testCalculateTaxRawItem() {
    final double price = 100.0;
    final double expectedTax = 12.5; // 12.5% of 100.0
    final double actualTax = taxCalculatorService.calculateTax(ItemType.RAW, price);
    assertEquals(expectedTax, actualTax);
  }

  @Test
  public final void testCalculateTaxManufacturedItem() {
    final double price = 100.0;
    final double expectedTax = 14.75; // 12.5% of 100.0 + 2% of (100.0 + 12.5)
    final double actualTax = taxCalculatorService.calculateTax(ItemType.MANUFACTURED, price);
    assertEquals(expectedTax, actualTax);
  }

  @Test
  public final void testCalculateTaxImportedItem() {
    final double price = 100.0;
    final double expectedTax = 10.0 + 10.0; // 10% of 100.0 + surcharge Rs. 10 (final cost <= 200)
    final double actualTax = taxCalculatorService.calculateTax(ItemType.IMPORTED, price);
    assertEquals(expectedTax, actualTax);
  }

  @Test
  public final void testCalculateTaxImportedMediumSurcharge() {
    final double price = 150.0;
    final double expectedTax = 15.0 + 10.0; // 10% of 150.0 + surcharge Rs. 10 (100 < final cost <= 200)
    final double actualTax = taxCalculatorService.calculateTax(ItemType.IMPORTED, price);
    assertEquals(expectedTax, actualTax);
  }

  @Test
  public final void testCalculateTaxImportedHighSurcharge() {
    final double price = 250.0;
    final double finalCost = price + (price * 0.10); // Price + 10% import duty
    final double expectedTax = (price * 0.10) + (finalCost * 0.05); // 10% import duty + 5% surcharge
    final double actualTax = taxCalculatorService.calculateTax(ItemType.IMPORTED, price);
    assertEquals(expectedTax, actualTax);
  }

  @Test
  public final void testCalculateTaxWithNullPrice() {
    assertThrows(IllegalArgumentException.class,
        () -> taxCalculatorService.calculateTax(ItemType.RAW, null));
  }

  @Test
  public final void testCalculateTaxWithNegativePrice() {
    assertThrows(IllegalArgumentException.class,
        () -> taxCalculatorService.calculateTax(ItemType.RAW, -10.0));
  }

  @Test
  public final void testCalculateTaxWithUnsupportedItemType() {
    assertThrows(IllegalArgumentException.class,
        () -> taxCalculatorService.calculateTax(null, 100.0));
  }

  @Test
  public final void testCalculateTaxWithInvalidItemType() {
    assertThrows(IllegalArgumentException.class,
        () -> taxCalculatorService.calculateTax(ItemType.valueOf("INVALID_TYPE"), 100.0));
  }
}
