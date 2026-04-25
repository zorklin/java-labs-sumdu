package ua.edu.sumdu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final String FILE_PATH = "input.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        Store store = loadFromFile();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== Phone Manager ===");
        System.out.println("Loaded " + store.getItems().size() + " entry/entries from " + FILE_PATH);

        while (running) {
            printMainMenu();
            int choice = -1;

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("[Error] Please enter a number.");
            } finally {
                scanner.nextLine();
            }

            switch (choice) {
                case 1 -> handleCreateMenu(store, scanner);
                case 2 -> store.printAll();
                case 3 -> handleSearchMenu(store, scanner);
                case 4 -> printSortedPhones(store);
                case 5 -> {
                    running = false;
                    saveToFile(store);
                    System.out.println("Data saved to " + FILE_PATH + ". Goodbye!");
                }
                default -> System.out.println("[Error] Unknown option. Choose 1-5.");
            }
        }

        scanner.close();
    }

    static Store loadFromFile() {
        Store store = new Store();
        if (!Files.exists(Paths.get(FILE_PATH))) {
            return store;
        }
        try (FileReader reader = new FileReader(FILE_PATH)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                JsonObject entryObj = array.get(i).getAsJsonObject();
                JsonObject phoneObj = entryObj.getAsJsonObject("phone");
                if (phoneObj == null) {
                    continue;
                }
                JsonElement typeElement = phoneObj.get("type");
                if (typeElement == null) {
                    continue;
                }
                String type = typeElement.getAsString();
                Phone phone = null;
                switch (type) {
                    case "SmartPhone" -> phone = GSON.fromJson(phoneObj, SmartPhone.class);
                    case "KeypadPhone" -> phone = GSON.fromJson(phoneObj, KeypadPhone.class);
                    case "SatellitePhone" -> phone = GSON.fromJson(phoneObj, SatellitePhone.class);
                    case "LandlinePhone" -> phone = GSON.fromJson(phoneObj, LandlinePhone.class);
                    default -> phone = GSON.fromJson(phoneObj, Phone.class);
                }
                if (phone != null) {
                    int quantity = entryObj.has("quantity") ? entryObj.get("quantity").getAsInt() : 1;
                    store.addNewPhone(phone, quantity);
                }
            }
        } catch (IOException e) {
            System.out.println("[Warning] Could not read " + FILE_PATH + ": " + e.getMessage());
        }
        return store;
    }

    static void saveToFile(Store store) {
        JsonArray array = new JsonArray();
        ArrayList<StoreEntry> items = store.getItems();
        for (int i = 0; i < items.size(); i++) {
            StoreEntry entry = items.get(i);
            JsonObject entryObj = new JsonObject();
            entryObj.add("phone", GSON.toJsonTree(entry.getPhone()));
            entryObj.addProperty("quantity", entry.getQuantity());
            array.add(entryObj);
        }
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            GSON.toJson(array, writer);
        } catch (IOException e) {
            System.out.println("[Error] Could not save to " + FILE_PATH + ": " + e.getMessage());
        }
    }

    private static void printMainMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Add device to store");
        System.out.println("2. List all devices");
        System.out.println("3. Search devices");
        System.out.println("4. Вивести відсортовану інформацію про всі телефони");
        System.out.println("5. Exit");
        System.out.print("Your choice: ");
    }

    private static void handleCreateMenu(Store store, Scanner scanner) {
        System.out.println("\n--- SELECT TYPE ---");
        System.out.println("1. SmartPhone");
        System.out.println("2. KeypadPhone");
        System.out.println("3. SatellitePhone");
        System.out.println("4. LandlinePhone");
        System.out.println("0. Back");
        System.out.print("Your choice: ");

        int typeChoice = -1;
        try {
            typeChoice = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("[Error] Please enter a number.");
        } finally {
            scanner.nextLine();
        }

        Phone phone = null;
        switch (typeChoice) {
            case 1 -> phone = buildSmartPhone(scanner);
            case 2 -> phone = buildKeypadPhone(scanner);
            case 3 -> phone = buildSatellitePhone(scanner);
            case 4 -> phone = buildLandlinePhone(scanner);
            case 0 -> {
                System.out.println("Returning to main menu...");
                return;
            }
            default -> {
                System.out.println("[Error] Unknown type.");
                return;
            }
        }

        if (phone == null) {
            return;
        }

        int quantity = 1;
        try {
            quantity = readInt(scanner, "  Quantity         : ");
        } catch (NumberFormatException e) {
            System.out.println("[Warning] Invalid quantity, defaulting to 1.");
        }

        store.addNewPhone(phone, quantity);
        System.out.println("[OK] Added: " + phone + " x" + quantity);
    }

    private static void handleSearchMenu(Store store, Scanner scanner) {
        boolean inSearch = true;
        while (inSearch) {
            System.out.println("\n--- SEARCH ---");
            System.out.println("1. Search by brand");
            System.out.println("2. Search by price range");
            System.out.println("3. Search by minimum storage");
            System.out.println("0. Back");
            System.out.print("Your choice: ");

            int choice = -1;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("[Error] Please enter a number.");
            } finally {
                scanner.nextLine();
            }

            switch (choice) {
                case 1 -> {
                    System.out.print("  Brand (partial match) : ");
                    String brand = scanner.nextLine().trim();
                    printSearchResults(store.findByBrand(brand));
                }
                case 2 -> {
                    try {
                        System.out.print("  Min price (UAH) : ");
                        double min = Double.parseDouble(scanner.nextLine().trim());
                        System.out.print("  Max price (UAH) : ");
                        double max = Double.parseDouble(scanner.nextLine().trim());
                        printSearchResults(store.findByPriceRange(min, max));
                    } catch (NumberFormatException e) {
                        System.out.println("[Error] Invalid numeric value.");
                    }
                }
                case 3 -> {
                    try {
                        System.out.print("  Min storage (GB) : ");
                        int minStorage = Integer.parseInt(scanner.nextLine().trim());
                        printSearchResults(store.findByStorage(minStorage));
                    } catch (NumberFormatException e) {
                        System.out.println("[Error] Invalid numeric value.");
                    }
                }
                case 0 -> inSearch = false;
                default -> System.out.println("[Error] Unknown option.");
            }
        }
    }

    private static void printSearchResults(ArrayList<Phone> results) {
        if (results.isEmpty()) {
            System.out.println("  Збігів не знайдено.");
            return;
        }
        System.out.println("\n--- Search Results (" + results.size() + " found) ---");
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". " + results.get(i).toString());
        }
    }

    private static void printSortedPhones(Store store) {
        ArrayList<Phone> sorted = store.getSortedPhones();
        if (sorted.isEmpty()) {
            System.out.println("  No devices in the store yet.");
            return;
        }
        System.out.println("\n--- Sorted Phones (" + sorted.size() + " total) ---");
        for (int i = 0; i < sorted.size(); i++) {
            System.out.println((i + 1) + ". " + sorted.get(i).toString());
        }
    }

    private static String readString(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static double readDouble(Scanner scanner, String prompt) throws NumberFormatException {
        System.out.print(prompt);
        return Double.parseDouble(scanner.nextLine().trim());
    }

    private static int readInt(Scanner scanner, String prompt) throws NumberFormatException {
        System.out.print(prompt);
        return Integer.parseInt(scanner.nextLine().trim());
    }

    private static SmartPhone buildSmartPhone(Scanner scanner) {
        try {
            String brand = readString(scanner, "  Brand            : ");
            String model = readString(scanner, "  Model            : ");
            double price = readDouble(scanner, "  Price (UAH)      : ");
            int storage = readInt(scanner, "  Storage (GB)     : ");
            String os = readString(scanner, "  Operating System : ");
            return new SmartPhone(brand, model, price, storage, os);
        } catch (NumberFormatException e) {
            System.out.println("[Error] Invalid numeric value.");
        } catch (IllegalArgumentException e) {
            System.out.println("[Error] " + e.getMessage());
        }
        return null;
    }

    private static KeypadPhone buildKeypadPhone(Scanner scanner) {
        try {
            String brand = readString(scanner, "  Brand          : ");
            String model = readString(scanner, "  Model          : ");
            double price = readDouble(scanner, "  Price (UAH)    : ");
            int storage = readInt(scanner, "  Storage (GB)   : ");
            System.out.print("  Has flashlight? (true/false) : ");
            boolean flashlight = Boolean.parseBoolean(scanner.nextLine().trim());
            return new KeypadPhone(brand, model, price, storage, flashlight);
        } catch (NumberFormatException e) {
            System.out.println("[Error] Invalid numeric value.");
        } catch (IllegalArgumentException e) {
            System.out.println("[Error] " + e.getMessage());
        }
        return null;
    }

    private static SatellitePhone buildSatellitePhone(Scanner scanner) {
        try {
            String brand = readString(scanner, "  Brand              : ");
            String model = readString(scanner, "  Model              : ");
            double price = readDouble(scanner, "  Price (UAH)        : ");
            int storage = readInt(scanner, "  Storage (GB)       : ");
            String network = readString(scanner, "  Satellite Network  : ");
            return new SatellitePhone(brand, model, price, storage, network);
        } catch (NumberFormatException e) {
            System.out.println("[Error] Invalid numeric value.");
        } catch (IllegalArgumentException e) {
            System.out.println("[Error] " + e.getMessage());
        }
        return null;
    }

    private static LandlinePhone buildLandlinePhone(Scanner scanner) {
        try {
            String brand = readString(scanner, "  Brand          : ");
            String model = readString(scanner, "  Model          : ");
            double price = readDouble(scanner, "  Price (UAH)    : ");
            int storage = readInt(scanner, "  Storage (GB)   : ");
            System.out.print("  Is cordless? (true/false) : ");
            boolean cordless = Boolean.parseBoolean(scanner.nextLine().trim());
            return new LandlinePhone(brand, model, price, storage, cordless);
        } catch (NumberFormatException e) {
            System.out.println("[Error] Invalid numeric value.");
        } catch (IllegalArgumentException e) {
            System.out.println("[Error] " + e.getMessage());
        }
        return null;
    }
}
