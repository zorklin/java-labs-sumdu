package ua.edu.sumdu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PhoneTest {

    private Store buildStore() {
        Store store = new Store();
        store.addNewPhone(new SmartPhone("Apple", "iPhone 15", 42999.0, 128, "iOS"), 1);
        store.addNewPhone(new SmartPhone("Samsung", "Galaxy S24", 36499.0, 256, "Android"), 1);
        store.addNewPhone(new KeypadPhone("Nokia", "3310", 999.0, 32, true), 1);
        store.addNewPhone(new SatellitePhone("Iridium", "9575A", 55000.0, 16, "Iridium"), 1);
        store.addNewPhone(new LandlinePhone("Panasonic", "KX-TG", 2500.0, 8, false), 1);
        return store;
    }

    @Test
    void phone_shouldThrow_whenBrandIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Phone("", "Model X", 10000.0, 64));
    }

    @Test
    void phone_shouldThrow_whenPriceIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Phone("Brand", "Model", -1.0, 64));
    }

    @Test
    void phone_shouldThrow_whenStorageIsZero() {
        assertThrows(IllegalArgumentException.class, () -> new Phone("Brand", "Model", 1000.0, 0));
    }

    @Test
    void smartPhone_shouldThrow_whenOperatingSystemIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new SmartPhone("Apple", "iPhone 15", 42999.0, 128, "   "));
    }

    @Test
    void smartPhone_shouldThrow_whenOperatingSystemIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new SmartPhone("Apple", "iPhone 15", 42999.0, 128, null));
    }

    @Test
    void smartPhone_shouldSucceed_withValidArguments() {
        assertDoesNotThrow(() -> new SmartPhone("Samsung", "Galaxy S24", 36499.0, 256, "Android"));
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
    void smartPhone_type_shouldBeSmartPhone() {
        SmartPhone phone = new SmartPhone("Apple", "iPhone 15", 42999.0, 128, "iOS");
        assertEquals("SmartPhone", phone.getType());
    }

    @Test
    void keypadPhone_shouldSucceed_withFlashlightTrue() {
        assertDoesNotThrow(() -> new KeypadPhone("Nokia", "3310", 999.0, 32, true));
    }

    @Test
    void keypadPhone_isHasFlashlight_shouldReturnCorrectValue() {
        KeypadPhone phone = new KeypadPhone("Nokia", "3310", 999.0, 32, true);
        assertTrue(phone.isHasFlashlight());
    }

    @Test
    void keypadPhone_equals_shouldReturnFalse_whenFlashlightDiffers() {
        KeypadPhone a = new KeypadPhone("Nokia", "3310", 999.0, 32, true);
        KeypadPhone b = new KeypadPhone("Nokia", "3310", 999.0, 32, false);
        assertNotEquals(a, b);
    }

    @Test
    void keypadPhone_shouldThrow_whenBrandIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new KeypadPhone("", "3310", 999.0, 32, true));
    }

    @Test
    void satellitePhone_shouldThrow_whenNetworkIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new SatellitePhone("Iridium", "9575A", 55000.0, 16, null));
    }

    @Test
    void satellitePhone_shouldThrow_whenNetworkIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new SatellitePhone("Iridium", "9575A", 55000.0, 16, "   "));
    }

    @Test
    void satellitePhone_shouldSucceed_withValidArguments() {
        assertDoesNotThrow(() -> new SatellitePhone("Iridium", "9575A", 55000.0, 16, "Iridium"));
    }

    @Test
    void satellitePhone_getSatelliteNetwork_shouldReturnCorrectValue() {
        SatellitePhone phone = new SatellitePhone("Thuraya", "X5-Touch", 48000.0, 32, "Thuraya");
        assertEquals("Thuraya", phone.getSatelliteNetwork());
    }

    @Test
    void satellitePhone_equals_shouldReturnTrue_forSameFields() {
        SatellitePhone a = new SatellitePhone("Iridium", "9575A", 55000.0, 16, "Iridium");
        SatellitePhone b = new SatellitePhone("Iridium", "9575A", 55000.0, 16, "Iridium");
        assertEquals(a, b);
    }

    @Test
    void satellitePhone_equals_shouldReturnFalse_whenNetworkDiffers() {
        SatellitePhone a = new SatellitePhone("Iridium", "9575A", 55000.0, 16, "Iridium");
        SatellitePhone b = new SatellitePhone("Iridium", "9575A", 55000.0, 16, "Globalstar");
        assertNotEquals(a, b);
    }

    @Test
    void satellitePhone_setSatelliteNetwork_shouldThrow_whenBlank() {
        SatellitePhone phone = new SatellitePhone("Iridium", "9575A", 55000.0, 16, "Iridium");
        assertThrows(IllegalArgumentException.class, () -> phone.setSatelliteNetwork(""));
    }

    @Test
    void landlinePhone_shouldSucceed_withCordlessTrue() {
        assertDoesNotThrow(() -> new LandlinePhone("Panasonic", "KX-TG", 2500.0, 8, true));
    }

    @Test
    void landlinePhone_isCordless_shouldReturnTrue() {
        LandlinePhone phone = new LandlinePhone("Panasonic", "KX-TG", 2500.0, 8, true);
        assertTrue(phone.isCordless());
    }

    @Test
    void landlinePhone_isCordless_shouldReturnFalse_whenSetFalse() {
        LandlinePhone phone = new LandlinePhone("Panasonic", "KX-TG", 2500.0, 8, false);
        assertFalse(phone.isCordless());
    }

    @Test
    void landlinePhone_equals_shouldReturnTrue_forSameFields() {
        LandlinePhone a = new LandlinePhone("Panasonic", "KX-TG", 2500.0, 8, true);
        LandlinePhone b = new LandlinePhone("Panasonic", "KX-TG", 2500.0, 8, true);
        assertEquals(a, b);
    }

    @Test
    void landlinePhone_equals_shouldReturnFalse_whenCordlessDiffers() {
        LandlinePhone a = new LandlinePhone("Panasonic", "KX-TG", 2500.0, 8, true);
        LandlinePhone b = new LandlinePhone("Panasonic", "KX-TG", 2500.0, 8, false);
        assertNotEquals(a, b);
    }

    @Test
    void landlinePhone_shouldThrow_whenPriceIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new LandlinePhone("Panasonic", "KX-TG", -100.0, 8, true));
    }

    @Test
    void polymorphism_toString_smartPhone_shouldContainClassName() {
        Phone phone = new SmartPhone("Apple", "iPhone 15", 42999.0, 128, "iOS");
        assertTrue(phone.toString().contains("SmartPhone"));
    }

    @Test
    void polymorphism_toString_satellitePhone_shouldContainNetwork() {
        Phone phone = new SatellitePhone("Iridium", "9575A", 55000.0, 16, "Iridium");
        assertTrue(phone.toString().contains("Iridium"));
    }

    @Test
    void polymorphism_toString_landlinePhone_shouldContainClassName() {
        Phone phone = new LandlinePhone("Panasonic", "KX-TG", 2500.0, 8, true);
        assertTrue(phone.toString().contains("LandlinePhone"));
    }

    @Test
    void store_addNewPhone_shouldMergeQuantity_whenPhoneAlreadyExists() {
        Store store = new Store();
        SmartPhone phone = new SmartPhone("Apple", "iPhone 15", 42999.0, 128, "iOS");
        store.addNewPhone(phone, 2);
        store.addNewPhone(new SmartPhone("Apple", "iPhone 15", 42999.0, 128, "iOS"), 3);

        assertEquals(1, store.getItems().size());
        assertEquals(5, store.getItems().get(0).getQuantity());
    }

    @Test
    void store_addNewPhone_shouldAddNewEntry_whenPhoneIsDistinct() {
        Store store = new Store();
        store.addNewPhone(new SmartPhone("Apple", "iPhone 15", 42999.0, 128, "iOS"), 1);
        store.addNewPhone(new SmartPhone("Samsung", "Galaxy S24", 36499.0, 256, "Android"), 1);

        assertEquals(2, store.getItems().size());
    }

    @Test
    void store_addNewPhone_shouldPreserveQuantityOfOtherEntries_whenMerging() {
        Store store = new Store();
        SmartPhone apple = new SmartPhone("Apple", "iPhone 15", 42999.0, 128, "iOS");
        SmartPhone samsung = new SmartPhone("Samsung", "Galaxy S24", 36499.0, 256, "Android");
        store.addNewPhone(apple, 1);
        store.addNewPhone(samsung, 4);
        store.addNewPhone(new SmartPhone("Apple", "iPhone 15", 42999.0, 128, "iOS"), 2);

        assertEquals(2, store.getItems().size());
        assertEquals(3, store.getItems().get(0).getQuantity());
        assertEquals(4, store.getItems().get(1).getQuantity());
    }

    @Test
    void store_findByBrand_shouldReturnMatchingDevices() {
        Store store = buildStore();
        ArrayList<Phone> result = store.findByBrand("apple");
        assertEquals(1, result.size());
        assertEquals("Apple", result.get(0).getBrand());
    }

    @Test
    void store_findByBrand_shouldBeCaseInsensitive() {
        Store store = buildStore();
        ArrayList<Phone> lower = store.findByBrand("samsung");
        ArrayList<Phone> upper = store.findByBrand("SAMSUNG");
        assertEquals(lower.size(), upper.size());
        assertEquals(1, lower.size());
    }

    @Test
    void store_findByBrand_shouldSupportPartialMatch() {
        Store store = buildStore();
        ArrayList<Phone> result = store.findByBrand("sung");
        assertEquals(1, result.size());
    }

    @Test
    void store_findByBrand_shouldReturnEmpty_whenNoMatch() {
        Store store = buildStore();
        ArrayList<Phone> result = store.findByBrand("Motorola");
        assertTrue(result.isEmpty());
    }

    @Test
    void store_findByPriceRange_shouldReturnDevicesInRange() {
        Store store = buildStore();
        ArrayList<Phone> result = store.findByPriceRange(1000.0, 40000.0);
        assertEquals(2, result.size());
    }

    @Test
    void store_findByPriceRange_shouldIncludeBoundaryValues() {
        Store store = buildStore();
        ArrayList<Phone> result = store.findByPriceRange(999.0, 999.0);
        assertEquals(1, result.size());
        assertEquals("Nokia", result.get(0).getBrand());
    }

    @Test
    void store_findByPriceRange_shouldReturnEmpty_whenNoMatch() {
        Store store = buildStore();
        ArrayList<Phone> result = store.findByPriceRange(100000.0, 200000.0);
        assertTrue(result.isEmpty());
    }

    @Test
    void store_findByPriceRange_shouldReturnAll_whenRangeCoversAll() {
        Store store = buildStore();
        ArrayList<Phone> result = store.findByPriceRange(0.0, 1_000_000.0);
        assertEquals(store.getItems().size(), result.size());
    }

    @Test
    void store_findByStorage_shouldReturnDevicesWithSufficientStorage() {
        Store store = buildStore();
        ArrayList<Phone> result = store.findByStorage(128);
        assertEquals(2, result.size());
    }

    @Test
    void store_findByStorage_shouldIncludeBoundaryValue() {
        Store store = buildStore();
        ArrayList<Phone> result = store.findByStorage(256);
        assertEquals(1, result.size());
        assertEquals("Samsung", result.get(0).getBrand());
    }

    @Test
    void store_findByStorage_shouldReturnEmpty_whenNoMatch() {
        Store store = buildStore();
        ArrayList<Phone> result = store.findByStorage(512);
        assertTrue(result.isEmpty());
    }

    @Test
    void store_findByStorage_shouldReturnAll_whenMinIsOne() {
        Store store = buildStore();
        ArrayList<Phone> result = store.findByStorage(1);
        assertEquals(store.getItems().size(), result.size());
    }

    @Test
    void json_saveAndLoad_storeEntry_shouldPreserveQuantityAndType(@TempDir Path tempDir) {
        String filePath = tempDir.resolve("store.json").toString();

        Store original = new Store();
        original.addNewPhone(new SmartPhone("Apple", "iPhone 15", 42999.0, 128, "iOS"), 3);
        original.addNewPhone(new KeypadPhone("Nokia", "3310", 999.0, 32, true), 5);

        saveStoreToPath(original, filePath);
        Store loaded = loadStoreFromPath(filePath);

        assertEquals(2, loaded.getItems().size());
        assertEquals(3, loaded.getItems().get(0).getQuantity());
        assertEquals(5, loaded.getItems().get(1).getQuantity());
        assertInstanceOf(SmartPhone.class, loaded.getItems().get(0).getPhone());
        assertInstanceOf(KeypadPhone.class, loaded.getItems().get(1).getPhone());
    }

    @Test
    void json_saveAndLoad_shouldMergeOnLoad_whenSamePhoneAddedTwice(@TempDir Path tempDir) {
        String filePath = tempDir.resolve("store.json").toString();

        Store original = new Store();
        SmartPhone phone = new SmartPhone("Apple", "iPhone 15", 42999.0, 128, "iOS");
        original.addNewPhone(phone, 2);
        original.addNewPhone(new SmartPhone("Apple", "iPhone 15", 42999.0, 128, "iOS"), 3);

        saveStoreToPath(original, filePath);
        Store loaded = loadStoreFromPath(filePath);

        assertEquals(1, loaded.getItems().size());
        assertEquals(5, loaded.getItems().get(0).getQuantity());
    }

    private void saveStoreToPath(Store store, String path) {
        com.google.gson.Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
        com.google.gson.JsonArray array = new com.google.gson.JsonArray();
        ArrayList<StoreEntry> items = store.getItems();
        for (int i = 0; i < items.size(); i++) {
            StoreEntry entry = items.get(i);
            com.google.gson.JsonObject entryObj = new com.google.gson.JsonObject();
            entryObj.add("phone", gson.toJsonTree(entry.getPhone()));
            entryObj.addProperty("quantity", entry.getQuantity());
            array.add(entryObj);
        }
        try (java.io.FileWriter writer = new java.io.FileWriter(path)) {
            gson.toJson(array, writer);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Store loadStoreFromPath(String path) {
        com.google.gson.Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
        Store store = new Store();
        try (java.io.FileReader reader = new java.io.FileReader(path)) {
            com.google.gson.JsonArray array = com.google.gson.JsonParser.parseReader(reader).getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                com.google.gson.JsonObject entryObj = array.get(i).getAsJsonObject();
                com.google.gson.JsonObject phoneObj = entryObj.getAsJsonObject("phone");
                String type = phoneObj.get("type").getAsString();
                Phone phone = null;
                switch (type) {
                    case "SmartPhone" -> phone = gson.fromJson(phoneObj, SmartPhone.class);
                    case "KeypadPhone" -> phone = gson.fromJson(phoneObj, KeypadPhone.class);
                    case "SatellitePhone" -> phone = gson.fromJson(phoneObj, SatellitePhone.class);
                    case "LandlinePhone" -> phone = gson.fromJson(phoneObj, LandlinePhone.class);
                    default -> phone = gson.fromJson(phoneObj, Phone.class);
                }
                int quantity = entryObj.has("quantity") ? entryObj.get("quantity").getAsInt() : 1;
                if (phone != null) {
                    store.addNewPhone(phone, quantity);
                }
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        return store;
    }
}
