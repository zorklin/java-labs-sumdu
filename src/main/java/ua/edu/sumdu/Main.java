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
        ArrayList<Phone> inventory = loadFromFile();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== Phone Manager ===");
        System.out.println("Loaded " + inventory.size() + " device(s) from " + FILE_PATH);

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
                case 1 -> handleCreateMenu(inventory, scanner);
                case 2 -> listDevices(inventory);
                case 3 -> handleSearchMenu(inventory, scanner);
                case 4 -> {
                    running = false;
                    saveToFile(inventory);
                    System.out.println("Data saved to " + FILE_PATH + ". Goodbye!");
                }
                default -> System.out.println("[Error] Unknown option. Choose 1-4.");
            }
        }

        scanner.close();
    }

    static ArrayList<Phone> loadFromFile() {
        ArrayList<Phone> result = new ArrayList<>();
        if (!Files.exists(Paths.get(FILE_PATH))) {
            return result;
        }
        try (FileReader reader = new FileReader(FILE_PATH)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                JsonObject obj = array.get(i).getAsJsonObject();
                JsonElement typeElement = obj.get("type");
                if (typeElement == null) {
                    continue;
                }
                String type = typeElement.getAsString();
                Phone phone = null;
                switch (type) {
                    case "SmartPhone" -> phone = GSON.fromJson(obj, SmartPhone.class);
                    case "KeypadPhone" -> phone = GSON.fromJson(obj, KeypadPhone.class);
                    case "SatellitePhone" -> phone = GSON.fromJson(obj, SatellitePhone.class);
                    case "LandlinePhone" -> phone = GSON.fromJson(obj, LandlinePhone.class);
                    default -> phone = GSON.fromJson(obj, Phone.class);
                }
                if (phone != null) {
                    result.add(phone);
                }
            }
        } catch (IOException e) {
            System.out.println("[Warning] Could not read " + FILE_PATH + ": " + e.getMessage());
        }
        return result;
    }

    static void saveToFile(ArrayList<Phone> inventory) {
        JsonArray array = new JsonArray();
        for (int i = 0; i < inventory.size(); i++) {
            Phone phone = inventory.get(i);
            JsonElement element = GSON.toJsonTree(phone);
            array.add(element);
        }
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            GSON.toJson(array, writer);
        } catch (IOException e) {
            System.out.println("[Error] Could not save to " + FILE_PATH + ": " + e.getMessage());
        }
    }

    private static void printMainMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Create new device");
        System.out.println("2. List all devices");
        System.out.println("3. Search devices");
        System.out.println("4. Exit");
        System.out.print("Your choice: ");
    }

    private static void handleSearchMenu(ArrayList<Phone> inventory, Scanner scanner) {
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
                    printSearchResults(findByBrand(inventory, brand));
                }
                case 2 -> {
                    try {
                        System.out.print("  Min price (UAH) : ");
                        double min = Double.parseDouble(scanner.nextLine().trim());
                        System.out.print("  Max price (UAH) : ");
                        double max = Double.parseDouble(scanner.nextLine().trim());
                        printSearchResults(findByPriceRange(inventory, min, max));
                    } catch (NumberFormatException e) {
                        System.out.println("[Error] Invalid numeric value.");
                    }
                }
                case 3 -> {
                    try {
                        System.out.print("  Min storage (GB) : ");
                        int minStorage = Integer.parseInt(scanner.nextLine().trim());
                        printSearchResults(findByStorage(inventory, minStorage));
                    } catch (NumberFormatException e) {
                        System.out.println("[Error] Invalid numeric value.");
                    }
                }
                case 0 -> inSearch = false;
                default -> System.out.println("[Error] Unknown option.");
            }
        }
    }

    static ArrayList<Phone> findByBrand(ArrayList<Phone> inventory, String brand) {
        ArrayList<Phone> result = new ArrayList<>();
        String lowerBrand = brand.toLowerCase();
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getBrand().toLowerCase().contains(lowerBrand)) {
                result.add(inventory.get(i));
            }
        }
        return result;
    }

    static ArrayList<Phone> findByPriceRange(ArrayList<Phone> inventory, double min, double max) {
        ArrayList<Phone> result = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++) {
            double price = inventory.get(i).getPrice();
            if (price >= min && price <= max) {
                result.add(inventory.get(i));
            }
        }
        return result;
    }

    static ArrayList<Phone> findByStorage(ArrayList<Phone> inventory, int minStorage) {
        ArrayList<Phone> result = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getStorageCapacity() >= minStorage) {
                result.add(inventory.get(i));
            }
        }
        return result;
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

    private static void handleCreateMenu(ArrayList<Phone> inventory, Scanner scanner) {
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

        switch (typeChoice) {
            case 1 -> addSmartPhone(inventory, scanner);
            case 2 -> addKeypadPhone(inventory, scanner);
            case 3 -> addSatellitePhone(inventory, scanner);
            case 4 -> addLandlinePhone(inventory, scanner);
            case 0 -> System.out.println("Returning to main menu...");
            default -> System.out.println("[Error] Unknown type. Returning to main menu.");
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

    private static void addSmartPhone(ArrayList<Phone> inventory, Scanner scanner) {
        try {
            String brand = readString(scanner, "  Brand            : ");
            String model = readString(scanner, "  Model            : ");
            double price = readDouble(scanner, "  Price (UAH)      : ");
            int storage = readInt(scanner, "  Storage (GB)     : ");
            String os = readString(scanner, "  Operating System : ");

            SmartPhone phone = new SmartPhone(brand, model, price, storage, os);
            inventory.add(phone);
            System.out.println("[OK] Added: " + phone);

        } catch (NumberFormatException e) {
            System.out.println("[Error] Invalid numeric value.");
        } catch (IllegalArgumentException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }

    private static void addKeypadPhone(ArrayList<Phone> inventory, Scanner scanner) {
        try {
            String brand = readString(scanner, "  Brand          : ");
            String model = readString(scanner, "  Model          : ");
            double price = readDouble(scanner, "  Price (UAH)    : ");
            int storage = readInt(scanner, "  Storage (GB)   : ");
            System.out.print("  Has flashlight? (true/false) : ");
            boolean flashlight = Boolean.parseBoolean(scanner.nextLine().trim());

            KeypadPhone phone = new KeypadPhone(brand, model, price, storage, flashlight);
            inventory.add(phone);
            System.out.println("[OK] Added: " + phone);

        } catch (NumberFormatException e) {
            System.out.println("[Error] Invalid numeric value.");
        } catch (IllegalArgumentException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }

    private static void addSatellitePhone(ArrayList<Phone> inventory, Scanner scanner) {
        try {
            String brand = readString(scanner, "  Brand              : ");
            String model = readString(scanner, "  Model              : ");
            double price = readDouble(scanner, "  Price (UAH)        : ");
            int storage = readInt(scanner, "  Storage (GB)       : ");
            String network = readString(scanner, "  Satellite Network  : ");

            SatellitePhone phone = new SatellitePhone(brand, model, price, storage, network);
            inventory.add(phone);
            System.out.println("[OK] Added: " + phone);

        } catch (NumberFormatException e) {
            System.out.println("[Error] Invalid numeric value.");
        } catch (IllegalArgumentException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }

    private static void addLandlinePhone(ArrayList<Phone> inventory, Scanner scanner) {
        try {
            String brand = readString(scanner, "  Brand          : ");
            String model = readString(scanner, "  Model          : ");
            double price = readDouble(scanner, "  Price (UAH)    : ");
            int storage = readInt(scanner, "  Storage (GB)   : ");
            System.out.print("  Is cordless? (true/false) : ");
            boolean cordless = Boolean.parseBoolean(scanner.nextLine().trim());

            LandlinePhone phone = new LandlinePhone(brand, model, price, storage, cordless);
            inventory.add(phone);
            System.out.println("[OK] Added: " + phone);

        } catch (NumberFormatException e) {
            System.out.println("[Error] Invalid numeric value.");
        } catch (IllegalArgumentException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }

    private static void listDevices(ArrayList<Phone> inventory) {
        if (inventory.isEmpty()) {
            System.out.println("  No devices in the inventory yet.");
            return;
        }
        System.out.println("\n--- Inventory (" + inventory.size() + " item(s)) ---");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println((i + 1) + ". " + inventory.get(i).toString());
        }
    }
}
