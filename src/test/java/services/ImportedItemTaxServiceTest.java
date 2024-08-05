package services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.example.constants.TaxRateConstants;
import org.example.services.ImportedItemTaxService;
import org.junit.jupiter.api.Test;

public class ImportedItemTaxServiceTest {

  private final ImportedItemTaxService service = new ImportedItemTaxService();

  @Test
  public final void testCalculateTaxForLowCost() {
    final double price = 80.0;
    final double importDuty = price * TaxRateConstants.IMPORT_DUTY_RATE;
    final double expectedTax = importDuty + TaxRateConstants.SURCHARGE_LOW;
    final double actualTax = service.calculate(price);
    assertEquals(expectedTax, actualTax);
  }

  @Test
  public final void testCalculateTaxForMediumCost() {
    final double price = 150.0;
    final double importDuty = price * TaxRateConstants.IMPORT_DUTY_RATE;
    final double expectedTax = importDuty + TaxRateConstants.SURCHARGE_MEDIUM;
    final double actualTax = service.calculate(price);
    assertEquals(expectedTax, actualTax);
  }

  @Test
  public final void testCalculateTaxForHighCost() {
    final double price = 250.0;
    final double importDuty = price * TaxRateConstants.IMPORT_DUTY_RATE;
    final double surcharge = (price + importDuty) * TaxRateConstants.SURCHARGE_HIGH_RATE;
    final double expectedTax = importDuty + surcharge;
    final double actualTax = service.calculate(price);
    assertEquals(expectedTax, actualTax);
  }

  @Test
  public final void testCalculateTaxForZeroPrice() {
    final double price = 0.0;
    assertThrows(IllegalArgumentException.class, () -> service.calculate(price));
  }

  @Test
  public final void testCalculateTaxForNegativePrice() {
    final double price = -50.0;
    assertThrows(IllegalArgumentException.class, () -> service.calculate(price));
  }

  @Test
  public final void testCalculateTaxForMaxDoubleValue() {
    final double price = Double.MAX_VALUE;
    final double importDuty = price * TaxRateConstants.IMPORT_DUTY_RATE;
    final double surcharge = (price + importDuty) * TaxRateConstants.SURCHARGE_HIGH_RATE;
    final double expectedTax = importDuty + surcharge;
    final double actualTax = service.calculate(price);
    assertEquals(expectedTax, actualTax);
  }
}
