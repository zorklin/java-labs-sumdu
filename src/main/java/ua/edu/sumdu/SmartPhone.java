package ua.edu.sumdu;

import java.util.Objects;

public class SmartPhone extends Phone {

    private String operatingSystem;

    public SmartPhone(String brand, String model, double price, int storageCapacity, String operatingSystem) {
        super(brand, model, price, storageCapacity);
        this.type = "SmartPhone";
        setOperatingSystem(operatingSystem);
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        if (operatingSystem == null || operatingSystem.isBlank()) {
            throw new IllegalArgumentException("Operating system must not be null or empty.");
        }
        this.operatingSystem = operatingSystem;
    }

    @Override
    public String toString() {
        return "SmartPhone{"
                + "brand='" + getBrand() + '\''
                + ", model='" + getModel() + '\''
                + ", price=" + getPrice()
                + ", storageCapacity=" + getStorageCapacity() + " GB"
                + ", operatingSystem='" + operatingSystem + '\''
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
        if (!super.equals(o)) {
            return false;
        }
        SmartPhone that = (SmartPhone) o;
        return Objects.equals(operatingSystem, that.operatingSystem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), operatingSystem);
    }
}
