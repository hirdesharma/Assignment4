package org.example.services;

import org.example.constants.TaxRateConstants;

public class ManufacturedItemTaxService implements TaxCalculationInterface {
  @Override
  public final double calculate(final double price) {
    if (price <= 0) {
      throw new IllegalArgumentException("Price should be a positive real number greater than "
          + "zero");
    }
    return price * TaxRateConstants.BASE_TAX_RATE
        + (price * (1 + TaxRateConstants.BASE_TAX_RATE))
        * TaxRateConstants.MANUFACTURED_SURCHARGE_RATE;
  }
}
