package ua.edu.sumdu;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<Phone> inventory = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== Phone Manager ===");

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
                case 3 -> {
                    running = false;
                    System.out.println("Goodbye!");
                }
                default -> System.out.println("[Error] Unknown option. Choose 1-3.");
            }
        }

        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Create new device");
        System.out.println("2. List all devices");
        System.out.println("3. Exit");
        System.out.print("Your choice: ");
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
