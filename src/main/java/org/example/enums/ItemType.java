package org.example.enums;

public enum ItemType {
  RAW,
  MANUFACTURED,
  IMPORTED;

  public static ItemType fromString(final String type) {
    if (type == null) {
      throw new NullPointerException("Item type cannot be null");
    }
    try {
      return ItemType.valueOf(type.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid item type: " + type);
    }
  }
}
