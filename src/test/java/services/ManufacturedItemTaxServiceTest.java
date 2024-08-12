package services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.example.constants.TaxRateConstants;
import org.example.services.ManufacturedItemTaxService;
import org.junit.jupiter.api.Test;

public class ManufacturedItemTaxServiceTest {

  private final ManufacturedItemTaxService service = new ManufacturedItemTaxService();

  @Test
  public final void testCalculateTaxForNormalPrice() {
    final double price = 100.0;
    final double expectedTax = price * TaxRateConstants.BASE_TAX_RATE
        + (price * (1 + TaxRateConstants.BASE_TAX_RATE))
        * TaxRateConstants.MANUFACTURED_SURCHARGE_RATE;
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
    final double expectedTax = price * TaxRateConstants.BASE_TAX_RATE
        + (price * (1 + TaxRateConstants.BASE_TAX_RATE))
        * TaxRateConstants.MANUFACTURED_SURCHARGE_RATE;
    final double actualTax = service.calculate(price);
    assertEquals(expectedTax, actualTax);
  }
}
