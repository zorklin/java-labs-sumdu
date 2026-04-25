package ua.edu.sumdu;

import java.util.ArrayList;

public class Store {

    private String name;
    private ArrayList<Phone> inventory;

    public Store(String name) {
        this.name = name;
        this.inventory = new ArrayList<>();
    }

    public void addPhone(Phone phone) {
        inventory.add(phone);
    }

    public ArrayList<Phone> getInventory() {
        return inventory;
    }

    public void printInventory() {
        if (inventory.isEmpty()) {
            System.out.println("  Store \"" + name + "\" has no phones yet.");
            return;
        }
        System.out.println("\n--- Store: " + name + " (" + inventory.size() + " item(s)) ---");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println((i + 1) + ". " + inventory.get(i));
        }
    }
}
