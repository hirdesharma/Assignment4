package org.example.services;

import org.example.constants.TaxRateConstants;

public class ImportedItemTaxService implements TaxCalculationInterface {
  @Override
  public final double calculate(final double price) {
    if (price <= 0) {
      throw new IllegalArgumentException("Price should be a positive real number greater than "
          + "zero");
    }
    final double importDuty = price * TaxRateConstants.IMPORT_DUTY_RATE;
    final double surcharge = calculateSurcharge(price + importDuty);
    return importDuty + surcharge;
  }

  private static double calculateSurcharge(final double costAfterTax) {
    if (costAfterTax <= 100) {
      return TaxRateConstants.SURCHARGE_LOW;
    } else if (costAfterTax <= 200) {
      return TaxRateConstants.SURCHARGE_MEDIUM;
    } else {
      return costAfterTax * TaxRateConstants.SURCHARGE_HIGH_RATE;
    }
  }
}
