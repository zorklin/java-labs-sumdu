package ua.edu.sumdu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PhoneTest {

    @Test
    void constructor_shouldThrow_whenBrandIsEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                new Phone("", "Galaxy S24", 36499.00, 256, PhoneType.SMARTPHONE));
    }

    @Test
    void constructor_shouldThrow_whenModelIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Phone("Samsung", null, 36499.00, 256, PhoneType.SMARTPHONE));
    }

    @Test
    void constructor_shouldThrow_whenPriceIsNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                new Phone("Apple", "iPhone 15", -1.0, 256, PhoneType.SMARTPHONE));
    }

    @Test
    void constructor_shouldThrow_whenStorageIsZero() {
        assertThrows(IllegalArgumentException.class, () ->
                new Phone("Google", "Pixel 8", 28750.50, 0, PhoneType.SMARTPHONE));
    }

    @Test
    void constructor_shouldThrow_whenTypeIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Phone("Apple", "iPhone 15", 42999.99, 128, null));
    }

    @Test
    void constructor_shouldSucceed_withValidArguments() {
        assertDoesNotThrow(() -> new Phone("Apple", "iPhone 15", 42999.99, 128, PhoneType.SMARTPHONE));
    }

    @Test
    void setPrice_shouldThrow_whenNegative() {
        Phone phone = new Phone("Apple", "iPhone 15", 42999.99, 128, PhoneType.SMARTPHONE);
        assertThrows(IllegalArgumentException.class, () -> phone.setPrice(-100.0));
    }

    @Test
    void setBrand_shouldThrow_whenBlank() {
        Phone phone = new Phone("Apple", "iPhone 15", 42999.99, 128, PhoneType.SMARTPHONE);
        assertThrows(IllegalArgumentException.class, () -> phone.setBrand("   "));
    }

    @Test
    void setStorageCapacity_shouldThrow_whenNegative() {
        Phone phone = new Phone("Apple", "iPhone 15", 42999.99, 128, PhoneType.SMARTPHONE);
        assertThrows(IllegalArgumentException.class, () -> phone.setStorageCapacity(-64));
    }

    @Test
    void getPrice_shouldReturnCorrectValue() {
        Phone phone = new Phone("Samsung", "Galaxy S24", 36499.00, 256, PhoneType.SMARTPHONE);
        assertEquals(36499.00, phone.getPrice());
    }

    @Test
    void setStorageCapacity_shouldUpdate_whenValid() {
        Phone phone = new Phone("Google", "Pixel 8", 28750.50, 64, PhoneType.PUSH_BUTTON);
        phone.setStorageCapacity(128);
        assertEquals(128, phone.getStorageCapacity());
    }

    @Test
    void copyConstructor_shouldProduceEqualButDistinctObject() {
        Phone original = new Phone("Nokia", "3310", 999.00, 32, PhoneType.PUSH_BUTTON);
        Phone copy = new Phone(original);

        assertEquals(original, copy);
        assertNotSame(original, copy);
    }

    @Test
    void copyConstructor_shouldPreserveAllFields() {
        Phone original = new Phone("Motorola", "Razr", 15000.00, 128, PhoneType.FLIP);
        Phone copy = new Phone(original);

        assertEquals(original.getBrand(), copy.getBrand());
        assertEquals(original.getModel(), copy.getModel());
        assertEquals(original.getPrice(), copy.getPrice());
        assertEquals(original.getStorageCapacity(), copy.getStorageCapacity());
        assertEquals(original.getType(), copy.getType());
    }

    @Test
    void objectCount_shouldIncreaseAfterCreation() {
        int countBefore = Phone.getObjectCount();
        new Phone("Test", "Model", 1000.00, 64, PhoneType.SMARTPHONE);
        int countAfter = Phone.getObjectCount();

        assertTrue(countAfter > countBefore);
    }

    @Test
    void objectCount_shouldIncreaseAfterCopyConstruction() {
        Phone original = new Phone("Test", "Copy", 500.00, 32, PhoneType.FLIP);
        int countBefore = Phone.getObjectCount();
        new Phone(original);
        int countAfter = Phone.getObjectCount();

        assertTrue(countAfter > countBefore);
    }
}
