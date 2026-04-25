package ua.edu.sumdu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PhoneTest {

    @Test
    void constructor_shouldThrow_whenBrandIsEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                new Phone("", "Galaxy S24", 36499.00, 256));
    }

    @Test
    void constructor_shouldThrow_whenModelIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Phone("Samsung", null, 36499.00, 256));
    }

    @Test
    void constructor_shouldThrow_whenPriceIsNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                new Phone("Apple", "iPhone 15", -1.0, 256));
    }

    @Test
    void constructor_shouldThrow_whenStorageIsZero() {
        assertThrows(IllegalArgumentException.class, () ->
                new Phone("Google", "Pixel 8", 28750.50, 0));
    }

    @Test
    void constructor_shouldSucceed_withValidArguments() {
        assertDoesNotThrow(() -> new Phone("Apple", "iPhone 15", 42999.99, 128));
    }

    @Test
    void setPrice_shouldThrow_whenNegative() {
        Phone phone = new Phone("Apple", "iPhone 15", 42999.99, 128);
        assertThrows(IllegalArgumentException.class, () -> phone.setPrice(-100.0));
    }

    @Test
    void setBrand_shouldThrow_whenBlank() {
        Phone phone = new Phone("Apple", "iPhone 15", 42999.99, 128);
        assertThrows(IllegalArgumentException.class, () -> phone.setBrand("   "));
    }

    @Test
    void setStorageCapacity_shouldThrow_whenNegative() {
        Phone phone = new Phone("Apple", "iPhone 15", 42999.99, 128);
        assertThrows(IllegalArgumentException.class, () -> phone.setStorageCapacity(-64));
    }

    @Test
    void getPrice_shouldReturnCorrectValue() {
        Phone phone = new Phone("Samsung", "Galaxy S24", 36499.00, 256);
        assertEquals(36499.00, phone.getPrice());
    }

    @Test
    void setStorageCapacity_shouldUpdate_whenValid() {
        Phone phone = new Phone("Google", "Pixel 8", 28750.50, 64);
        phone.setStorageCapacity(128);
        assertEquals(128, phone.getStorageCapacity());
    }
}
