package ua.edu.sumdu;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<Phone> phones = new ArrayList<Phone>();

        phones.add(new Phone("Apple", "iPhone 15", 42999.99));
        phones.add(new Phone("Samsung", "Galaxy S24", 36499.00));
        phones.add(new Phone("Google", "Pixel 8", 28750.50));

        Scanner scanner = new Scanner(System.in);

        for (int i = 1; i <= 2; i++) {
            System.out.println("\nEnter details for phone #" + (3 + i) + ":");

            System.out.print("  Brand : ");
            String brand = scanner.nextLine().trim();

            System.out.print("  Model : ");
            String model = scanner.nextLine().trim();

            System.out.print("  Price : ");
            double price = scanner.nextDouble();
            scanner.nextLine();

            phones.add(new Phone(brand, model, price));
        }

        scanner.close();

        System.out.println("\n--- Phone List ---");
        for (int i = 0; i < phones.size(); i++) {
            System.out.println((i + 1) + ". " + phones.get(i).toString());
        }
    }
}
