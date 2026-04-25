package ua.edu.sumdu;

import java.util.ArrayList;

public class Store {

    private ArrayList<StoreEntry> items;

    public Store() {
        this.items = new ArrayList<>();
    }

    public ArrayList<StoreEntry> getItems() {
        return items;
    }

    public void addNewPhone(Phone ph, int quantity) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getPhone().equals(ph)) {
                items.get(i).setQuantity(items.get(i).getQuantity() + quantity);
                return;
            }
        }
        items.add(new StoreEntry(ph, quantity));
    }

    public void printAll() {
        if (items.isEmpty()) {
            System.out.println("  No devices in the store yet.");
            return;
        }
        System.out.println("\n--- Store (" + items.size() + " entry/entries) ---");
        for (int i = 0; i < items.size(); i++) {
            StoreEntry entry = items.get(i);
            System.out.println((i + 1) + ". [qty: " + entry.getQuantity() + "] " + entry.getPhone().toString());
        }
    }

    public ArrayList<Phone> findByBrand(String brand) {
        ArrayList<Phone> result = new ArrayList<>();
        String lowerBrand = brand.toLowerCase();
        for (int i = 0; i < items.size(); i++) {
            Phone phone = items.get(i).getPhone();
            if (phone.getBrand().toLowerCase().contains(lowerBrand)) {
                result.add(phone);
            }
        }
        return result;
    }

    public ArrayList<Phone> findByPriceRange(double min, double max) {
        ArrayList<Phone> result = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            Phone phone = items.get(i).getPhone();
            double price = phone.getPrice();
            if (price >= min && price <= max) {
                result.add(phone);
            }
        }
        return result;
    }

    public ArrayList<Phone> findByStorage(int minStorage) {
        ArrayList<Phone> result = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            Phone phone = items.get(i).getPhone();
            if (phone.getStorageCapacity() >= minStorage) {
                result.add(phone);
            }
        }
        return result;
    }
}
