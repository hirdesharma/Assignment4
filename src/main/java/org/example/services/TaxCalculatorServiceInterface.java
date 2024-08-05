package org.example.services;

import org.example.enums.ItemType;

public interface TaxCalculatorServiceInterface {
  double calculateTax(final ItemType type, final Double price);
}
