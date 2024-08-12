package org.example.factories;

import org.example.services.ImportedItemTaxService;
import org.example.services.TaxCalculationInterface;

public class ImportedItemTaxFactory implements TaxServiceFactory {
  @Override
  public TaxCalculationInterface createTaxService() {
    return new ImportedItemTaxService();
  }
}