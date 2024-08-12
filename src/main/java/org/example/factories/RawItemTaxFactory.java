package org.example.factories;

import org.example.services.RawItemTaxService;
import org.example.services.TaxCalculationInterface;

public class RawItemTaxFactory implements TaxServiceFactory {
  @Override
  public TaxCalculationInterface createTaxService() {
    return new RawItemTaxService();
  }
}