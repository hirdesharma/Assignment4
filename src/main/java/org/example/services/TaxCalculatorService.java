package org.example.services;

import java.util.HashMap;
import java.util.Map;
import org.example.enums.ItemType;
import org.example.factories.ImportedItemTaxFactory;
import org.example.factories.ManufacturedItemTaxFactory;
import org.example.factories.RawItemTaxFactory;
import org.example.factories.TaxServiceFactory;

public class TaxCalculatorService implements TaxCalculatorServiceInterface {

  private final Map<ItemType, TaxServiceFactory> taxFactoryMap;

  public TaxCalculatorService() {
    taxFactoryMap = new HashMap<>();
    taxFactoryMap.put(ItemType.RAW, new RawItemTaxFactory());
    taxFactoryMap.put(ItemType.MANUFACTURED, new ManufacturedItemTaxFactory());
    taxFactoryMap.put(ItemType.IMPORTED, new ImportedItemTaxFactory());
  }

  @Override
  public final double calculateTax(final ItemType type, final Double price) {
    if (price == null || price < 0) {
      throw new IllegalArgumentException("Price must be a non-null, non-negative value.");
    }

    final TaxServiceFactory factory = taxFactoryMap.get(type);
    if (factory == null) {
      throw new IllegalArgumentException("Unsupported item type: " + type);
    }

    final TaxCalculationInterface taxService = factory.createTaxService();
    return taxService.calculate(price);
  }
}
