package ua.edu.sumdu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PhoneTest {

    @Test
    void phone_shouldThrow_whenBrandIsEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                new Phone("", "Model X", 10000.0, 64));
    }

    @Test
    void phone_shouldThrow_whenModelIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Phone("Brand", null, 10000.0, 64));
    }

    @Test
    void phone_shouldThrow_whenPriceIsNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                new Phone("Brand", "Model", -1.0, 64));
    }

    @Test
    void phone_shouldThrow_whenStorageIsZero() {
        assertThrows(IllegalArgumentException.class, () ->
                new Phone("Brand", "Model", 1000.0, 0));
    }

    @Test
    void smartPhone_shouldThrow_whenOperatingSystemIsBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                new SmartPhone("Apple", "iPhone 15", 42999.0, 128, "   "));
    }

    @Test
    void smartPhone_shouldThrow_whenOperatingSystemIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new SmartPhone("Apple", "iPhone 15", 42999.0, 128, null));
    }

    @Test
    void smartPhone_setOperatingSystem_shouldThrow_whenBlank() {
        SmartPhone phone = new SmartPhone("Apple", "iPhone 15", 42999.0, 128, "iOS");
        assertThrows(IllegalArgumentException.class, () -> phone.setOperatingSystem(""));
    }

    @Test
    void smartPhone_shouldSucceed_withValidArguments() {
        assertDoesNotThrow(() -> new SmartPhone("Samsung", "Galaxy S24", 36499.0, 256, "Android"));
    }

    @Test
    void smartPhone_getOperatingSystem_shouldReturnCorrectValue() {
        SmartPhone phone = new SmartPhone("Google", "Pixel 8", 28000.0, 128, "Android");
        assertEquals("Android", phone.getOperatingSystem());
    }

    @Test
    void smartPhone_equals_shouldReturnTrue_forSameFields() {
        SmartPhone a = new SmartPhone("Apple", "iPhone 15", 42999.0, 128, "iOS");
        SmartPhone b = new SmartPhone("Apple", "iPhone 15", 42999.0, 128, "iOS");
        assertEquals(a, b);
    }

    @Test
    void smartPhone_equals_shouldReturnFalse_whenOsDiffers() {
        SmartPhone a = new SmartPhone("Apple", "iPhone 15", 42999.0, 128, "iOS");
        SmartPhone b = new SmartPhone("Apple", "iPhone 15", 42999.0, 128, "Android");
        assertNotEquals(a, b);
    }

    @Test
    void keypadPhone_shouldSucceed_withFlashlightTrue() {
        assertDoesNotThrow(() -> new KeypadPhone("Nokia", "3310", 999.0, 32, true));
    }

    @Test
    void keypadPhone_shouldSucceed_withFlashlightFalse() {
        assertDoesNotThrow(() -> new KeypadPhone("Nokia", "3310", 999.0, 32, false));
    }

    @Test
    void keypadPhone_isHasFlashlight_shouldReturnCorrectValue() {
        KeypadPhone phone = new KeypadPhone("Nokia", "3310", 999.0, 32, true);
        assertTrue(phone.isHasFlashlight());
    }

    @Test
    void keypadPhone_isHasFlashlight_shouldReturnFalse_whenSetFalse() {
        KeypadPhone phone = new KeypadPhone("Nokia", "3310", 999.0, 32, false);
        assertFalse(phone.isHasFlashlight());
    }

    @Test
    void keypadPhone_equals_shouldReturnTrue_forSameFields() {
        KeypadPhone a = new KeypadPhone("Nokia", "3310", 999.0, 32, true);
        KeypadPhone b = new KeypadPhone("Nokia", "3310", 999.0, 32, true);
        assertEquals(a, b);
    }

    @Test
    void keypadPhone_equals_shouldReturnFalse_whenFlashlightDiffers() {
        KeypadPhone a = new KeypadPhone("Nokia", "3310", 999.0, 32, true);
        KeypadPhone b = new KeypadPhone("Nokia", "3310", 999.0, 32, false);
        assertNotEquals(a, b);
    }

    @Test
    void keypadPhone_shouldThrow_whenBrandIsEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                new KeypadPhone("", "3310", 999.0, 32, true));
    }

    @Test
    void keypadPhone_setPrice_shouldThrow_whenNegative() {
        KeypadPhone phone = new KeypadPhone("Nokia", "3310", 999.0, 32, true);
        assertThrows(IllegalArgumentException.class, () -> phone.setPrice(-1.0));
    }

    @Test
    void polymorphism_toString_shouldReturnSubclassRepresentation() {
        Phone phone = new SmartPhone("Apple", "iPhone 15", 42999.0, 128, "iOS");
        assertTrue(phone.toString().contains("SmartPhone"));
    }
}
