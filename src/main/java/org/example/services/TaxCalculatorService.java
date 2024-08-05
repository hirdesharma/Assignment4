package org.example.services;

import java.util.HashMap;
import java.util.Map;
import org.example.enums.ItemType;

public class TaxCalculatorService implements TaxCalculatorServiceInterface {

  private final Map<ItemType, TaxCalculationInterface> taxStrategyMap;

  public TaxCalculatorService() {
    taxStrategyMap = new HashMap<>();
    taxStrategyMap.put(ItemType.RAW, new RawItemTaxService());
    taxStrategyMap.put(ItemType.MANUFACTURED, new ManufacturedItemTaxService());
    taxStrategyMap.put(ItemType.IMPORTED, new ImportedItemTaxService());
  }

  @Override
  public final double calculateTax(final ItemType type, final Double price) {
    if (price == null || price < 0) {
      throw new IllegalArgumentException("Price must be a non-null, non-negative value.");
    }

    final TaxCalculationInterface strategy = taxStrategyMap.get(type);
    if (strategy == null) {
      throw new IllegalArgumentException("Unsupported item type: " + type);
    }

    return strategy.calculate(price);
  }
}
