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
            printMenu();
            int choice = -1;

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("[Error] Please enter a number (1-4).");
            } finally {
                scanner.nextLine();
            }

            switch (choice) {
                case 1 -> addSmartPhone(inventory, scanner);
                case 2 -> addKeypadPhone(inventory, scanner);
                case 3 -> listDevices(inventory);
                case 4 -> {
                    running = false;
                    System.out.println("Goodbye!");
                }
                default -> System.out.println("[Error] Unknown option. Choose 1-4.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Add SmartPhone");
        System.out.println("2. Add KeypadPhone");
        System.out.println("3. List all devices");
        System.out.println("4. Exit");
        System.out.print("Your choice: ");
    }

    private static void addSmartPhone(ArrayList<Phone> inventory, Scanner scanner) {
        try {
            System.out.print("  Brand            : ");
            String brand = scanner.nextLine().trim();

            System.out.print("  Model            : ");
            String model = scanner.nextLine().trim();

            System.out.print("  Price (UAH)      : ");
            double price;
            try {
                price = Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[Error] Price must be a valid number.");
                return;
            }

            System.out.print("  Storage (GB)     : ");
            int storage;
            try {
                storage = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[Error] Storage must be a whole number.");
                return;
            }

            System.out.print("  Operating System : ");
            String os = scanner.nextLine().trim();

            SmartPhone phone = new SmartPhone(brand, model, price, storage, os);
            inventory.add(phone);
            System.out.println("[OK] Added: " + phone);

        } catch (IllegalArgumentException e) {
            System.out.println("[Error] Invalid data — " + e.getMessage());
        }
    }

    private static void addKeypadPhone(ArrayList<Phone> inventory, Scanner scanner) {
        try {
            System.out.print("  Brand          : ");
            String brand = scanner.nextLine().trim();

            System.out.print("  Model          : ");
            String model = scanner.nextLine().trim();

            System.out.print("  Price (UAH)    : ");
            double price;
            try {
                price = Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[Error] Price must be a valid number.");
                return;
            }

            System.out.print("  Storage (GB)   : ");
            int storage;
            try {
                storage = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[Error] Storage must be a whole number.");
                return;
            }

            System.out.print("  Has flashlight? (true/false) : ");
            boolean flashlight = Boolean.parseBoolean(scanner.nextLine().trim());

            KeypadPhone phone = new KeypadPhone(brand, model, price, storage, flashlight);
            inventory.add(phone);
            System.out.println("[OK] Added: " + phone);

        } catch (IllegalArgumentException e) {
            System.out.println("[Error] Invalid data — " + e.getMessage());
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
