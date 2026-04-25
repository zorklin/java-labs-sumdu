package ua.edu.sumdu;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<Phone> phones = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== Phone Manager ===");

        while (running) {
            printMenu();
            int choice = -1;

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("[Error] Please enter a number (1, 2, or 3).");
            } finally {
                scanner.nextLine();
            }

            switch (choice) {
                case 1 -> createPhone(phones, scanner);
                case 2 -> listPhones(phones);
                case 3 -> {
                    running = false;
                    System.out.println("Goodbye!");
                }
                default -> System.out.println("[Error] Unknown option. Choose 1, 2, or 3.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Add a new Phone");
        System.out.println("2. List all Phones");
        System.out.println("3. Exit");
        System.out.print("Your choice: ");
    }

    private static void createPhone(ArrayList<Phone> phones, Scanner scanner) {
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

            Phone phone = new Phone(brand, model, price, storage);
            phones.add(phone);
            System.out.println("[OK] Phone added: " + phone);

        } catch (IllegalArgumentException e) {
            System.out.println("[Error] Invalid data — " + e.getMessage());
        }
    }

    private static void listPhones(ArrayList<Phone> phones) {
        if (phones.isEmpty()) {
            System.out.println("  No phones in the list yet.");
            return;
        }
        System.out.println("\n--- Phone List (" + phones.size() + " item(s)) ---");
        for (int i = 0; i < phones.size(); i++) {
            System.out.println((i + 1) + ". " + phones.get(i));
        }
    }
}
