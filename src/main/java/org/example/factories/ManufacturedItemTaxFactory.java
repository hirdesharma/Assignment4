package org.example.factories;

import org.example.services.ManufacturedItemTaxService;
import org.example.services.TaxCalculationInterface;

public class ManufacturedItemTaxFactory implements TaxServiceFactory {
  @Override
  public TaxCalculationInterface createTaxService() {
    return new ManufacturedItemTaxService();
  }
}