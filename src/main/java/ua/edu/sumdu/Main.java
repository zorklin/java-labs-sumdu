package ua.edu.sumdu;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Store store = new Store("My Phone Store");
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== Phone Manager ===");

        while (running) {
            printMenu();
            int choice = -1;

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("[Error] Please enter a number (1-5).");
            } finally {
                scanner.nextLine();
            }

            switch (choice) {
                case 1 -> createPhone(store, scanner);
                case 2 -> store.printInventory();
                case 3 -> copyLastPhone(store);
                case 4 -> System.out.println("Total Phone objects created: " + Phone.getObjectCount());
                case 5 -> {
                    running = false;
                    System.out.println("Goodbye!");
                }
                default -> System.out.println("[Error] Unknown option. Choose 1-5.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Add a new Phone");
        System.out.println("2. List all Phones");
        System.out.println("3. Copy last Phone");
        System.out.println("4. Show total Phone objects count");
        System.out.println("5. Exit");
        System.out.print("Your choice: ");
    }

    private static void createPhone(Store store, Scanner scanner) {
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
                System.out.println("[Error] Storage capacity must be a whole number.");
                return;
            }

            System.out.println("  Type: 1 - Smartphone, 2 - Push-button, 3 - Flip");
            System.out.print("  Your choice    : ");
            PhoneType type;
            try {
                int typeChoice = Integer.parseInt(scanner.nextLine().trim());
                switch (typeChoice) {
                    case 1 -> type = PhoneType.SMARTPHONE;
                    case 2 -> type = PhoneType.PUSH_BUTTON;
                    case 3 -> type = PhoneType.FLIP;
                    default -> {
                        System.out.println("[Error] Invalid type. Choose 1, 2, or 3.");
                        return;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("[Error] Type must be a number (1, 2, or 3).");
                return;
            }

            Phone phone = new Phone(brand, model, price, storage, type);
            store.addPhone(phone);
            System.out.println("[OK] Phone added: " + phone);

        } catch (IllegalArgumentException e) {
            System.out.println("[Error] Invalid data — " + e.getMessage());
        }
    }

    private static void copyLastPhone(Store store) {
        ArrayList<Phone> inventory = store.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("[Error] No phones in the store to copy.");
            return;
        }
        Phone last = inventory.get(inventory.size() - 1);
        Phone copy = new Phone(last);
        store.addPhone(copy);
        System.out.println("[OK] Copied: " + copy);
    }
}
