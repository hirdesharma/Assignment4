package org.example.factories;

import org.example.services.TaxCalculationInterface;

public interface TaxServiceFactory {
  TaxCalculationInterface createTaxService();
}
