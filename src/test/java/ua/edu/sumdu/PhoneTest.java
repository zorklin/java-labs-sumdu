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

    @Test
    void phone_shouldThrow_whenBrandIsEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                new Phone("", "Model X", 10000.0, 64));
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
        assertThrows(IllegalArgumentException.class, () ->
                new KeypadPhone("", "3310", 999.0, 32, true));
    }

    @Test
    void satellitePhone_shouldThrow_whenNetworkIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new SatellitePhone("Iridium", "9575A", 55000.0, 16, null));
    }

    @Test
    void satellitePhone_shouldThrow_whenNetworkIsBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                new SatellitePhone("Iridium", "9575A", 55000.0, 16, "   "));
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
        assertThrows(IllegalArgumentException.class, () ->
                new LandlinePhone("Panasonic", "KX-TG", -100.0, 8, true));
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
    void json_saveAndLoad_smartPhone_shouldPreserveAllFields(@TempDir Path tempDir) {
        String filePath = tempDir.resolve("test.json").toString();

        SmartPhone original = new SmartPhone("Apple", "iPhone 15", 42999.0, 128, "iOS");
        ArrayList<Phone> toSave = new ArrayList<>();
        toSave.add(original);

        saveToFileWithPath(toSave, filePath);
        ArrayList<Phone> loaded = loadFromFileWithPath(filePath);

        assertEquals(1, loaded.size());
        assertInstanceOf(SmartPhone.class, loaded.get(0));
        SmartPhone restored = (SmartPhone) loaded.get(0);
        assertEquals(original, restored);
        assertEquals(original.getOperatingSystem(), restored.getOperatingSystem());
    }

    @Test
    void json_saveAndLoad_keypadPhone_shouldPreserveFlashlight(@TempDir Path tempDir) {
        String filePath = tempDir.resolve("test.json").toString();

        KeypadPhone original = new KeypadPhone("Nokia", "3310", 999.0, 32, true);
        ArrayList<Phone> toSave = new ArrayList<>();
        toSave.add(original);

        saveToFileWithPath(toSave, filePath);
        ArrayList<Phone> loaded = loadFromFileWithPath(filePath);

        assertEquals(1, loaded.size());
        assertInstanceOf(KeypadPhone.class, loaded.get(0));
        KeypadPhone restored = (KeypadPhone) loaded.get(0);
        assertEquals(original.isHasFlashlight(), restored.isHasFlashlight());
    }

    @Test
    void json_saveAndLoad_multipleTypes_shouldPreserveCount(@TempDir Path tempDir) {
        String filePath = tempDir.resolve("test.json").toString();

        ArrayList<Phone> toSave = new ArrayList<>();
        toSave.add(new SmartPhone("Apple", "iPhone 15", 42999.0, 128, "iOS"));
        toSave.add(new KeypadPhone("Nokia", "3310", 999.0, 32, true));
        toSave.add(new SatellitePhone("Iridium", "9575A", 55000.0, 16, "Iridium"));
        toSave.add(new LandlinePhone("Panasonic", "KX-TG", 2500.0, 8, false));

        saveToFileWithPath(toSave, filePath);
        ArrayList<Phone> loaded = loadFromFileWithPath(filePath);

        assertEquals(4, loaded.size());
        assertInstanceOf(SmartPhone.class, loaded.get(0));
        assertInstanceOf(KeypadPhone.class, loaded.get(1));
        assertInstanceOf(SatellitePhone.class, loaded.get(2));
        assertInstanceOf(LandlinePhone.class, loaded.get(3));
    }

    private void saveToFileWithPath(ArrayList<Phone> inventory, String path) {
        com.google.gson.Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
        com.google.gson.JsonArray array = new com.google.gson.JsonArray();
        for (int i = 0; i < inventory.size(); i++) {
            array.add(gson.toJsonTree(inventory.get(i)));
        }
        try (java.io.FileWriter writer = new java.io.FileWriter(path)) {
            gson.toJson(array, writer);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<Phone> loadFromFileWithPath(String path) {
        com.google.gson.Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
        ArrayList<Phone> result = new ArrayList<>();
        try (java.io.FileReader reader = new java.io.FileReader(path)) {
            com.google.gson.JsonArray array = com.google.gson.JsonParser.parseReader(reader).getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                com.google.gson.JsonObject obj = array.get(i).getAsJsonObject();
                String type = obj.get("type").getAsString();
                Phone phone = null;
                switch (type) {
                    case "SmartPhone" -> phone = gson.fromJson(obj, SmartPhone.class);
                    case "KeypadPhone" -> phone = gson.fromJson(obj, KeypadPhone.class);
                    case "SatellitePhone" -> phone = gson.fromJson(obj, SatellitePhone.class);
                    case "LandlinePhone" -> phone = gson.fromJson(obj, LandlinePhone.class);
                    default -> phone = gson.fromJson(obj, Phone.class);
                }
                if (phone != null) {
                    result.add(phone);
                }
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
