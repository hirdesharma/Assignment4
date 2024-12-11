package enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.example.enums.ItemType;
import org.junit.jupiter.api.Test;

public class ItemTypeTest {

  @Test
  public final void testFromStringRaw() {
    final ItemType itemType = ItemType.fromString("RAW");
    assertEquals(ItemType.RAW, itemType);
  }

  @Test
  public final void testFromStringManufactured() {
    final ItemType itemType = ItemType.fromString("MANUFACTURED");
    assertEquals(ItemType.MANUFACTURED, itemType);
  }

  @Test
  public final void testFromStringImported() {
    final ItemType itemType = ItemType.fromString("IMPORTED");
    assertEquals(ItemType.IMPORTED, itemType);
  }

  @Test
  public final void testFromStringCaseInsensitive() {
    final ItemType itemType = ItemType.fromString("raw");
    assertEquals(ItemType.RAW, itemType);
  }

  @Test
  public final void testFromStringNull() {
    assertThrows(NullPointerException.class, () -> ItemType.fromString(null));
  }

  @Test
  public final void testFromStringInvalidType() {
    assertThrows(IllegalArgumentException.class, () -> ItemType.fromString("INVALID_TYPE"));
  }
}
