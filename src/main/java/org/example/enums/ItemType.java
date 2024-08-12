package org.example.enums;

import java.util.Objects;

public enum ItemType {
  RAW,
  MANUFACTURED,
  IMPORTED;

  public static ItemType fromString(final String type) {
    if (Objects.isNull(type)) {
      throw new NullPointerException("Item type cannot be null");
    }
    try {
      return ItemType.valueOf(type.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid item type: " + type);
    }
  }
}
