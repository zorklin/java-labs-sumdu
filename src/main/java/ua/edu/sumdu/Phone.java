package ua.edu.sumdu;

import java.util.Objects;

public class Phone {

    private static int objectCount = 0;

    private String brand;
    private String model;
    private double price;
    private int storageCapacity;
    private PhoneType type;

    public Phone(String brand, String model, double price, int storageCapacity, PhoneType type) {
        setBrand(brand);
        setModel(model);
        setPrice(price);
        setStorageCapacity(storageCapacity);
        setType(type);
        objectCount++;
    }

    public Phone(Phone other) {
        setBrand(other.brand);
        setModel(other.model);
        setPrice(other.price);
        setStorageCapacity(other.storageCapacity);
        setType(other.type);
        objectCount++;
    }

    public static int getObjectCount() {
        return objectCount;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double getPrice() {
        return price;
    }

    public int getStorageCapacity() {
        return storageCapacity;
    }

    public PhoneType getType() {
        return type;
    }

    public void setBrand(String brand) {
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException("Brand must not be null or empty.");
        }
        this.brand = brand;
    }

    public void setModel(String model) {
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Model must not be null or empty.");
        }
        this.model = model;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price must not be negative. Got: " + price);
        }
        this.price = price;
    }

    public void setStorageCapacity(int storageCapacity) {
        if (storageCapacity <= 0) {
            throw new IllegalArgumentException(
                    "Storage capacity must be greater than 0. Got: " + storageCapacity);
        }
        this.storageCapacity = storageCapacity;
    }

    public void setType(PhoneType type) {
        if (type == null) {
            throw new IllegalArgumentException("Type must not be null.");
        }
        this.type = type;
    }

    @Override
    public String toString() {
        return "Phone{"
                + "brand='" + brand + '\''
                + ", model='" + model + '\''
                + ", price=" + price
                + ", storageCapacity=" + storageCapacity + " GB"
                + ", type=" + type
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Phone phone = (Phone) o;
        return Double.compare(phone.price, price) == 0
                && storageCapacity == phone.storageCapacity
                && Objects.equals(brand, phone.brand)
                && Objects.equals(model, phone.model)
                && type == phone.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, model, price, storageCapacity, type);
    }
}
