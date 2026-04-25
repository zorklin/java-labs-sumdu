package ua.edu.sumdu;

import java.util.Objects;

public class KeypadPhone extends Phone {

    private boolean hasFlashlight;

    public KeypadPhone(String brand, String model, double price, int storageCapacity, boolean hasFlashlight) {
        super(brand, model, price, storageCapacity);
        this.hasFlashlight = hasFlashlight;
    }

    public boolean isHasFlashlight() {
        return hasFlashlight;
    }

    public void setHasFlashlight(boolean hasFlashlight) {
        this.hasFlashlight = hasFlashlight;
    }

    @Override
    public String toString() {
        return "KeypadPhone{"
                + "brand='" + getBrand() + '\''
                + ", model='" + getModel() + '\''
                + ", price=" + getPrice()
                + ", storageCapacity=" + getStorageCapacity() + " GB"
                + ", hasFlashlight=" + hasFlashlight
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
        KeypadPhone that = (KeypadPhone) o;
        return hasFlashlight == that.hasFlashlight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), hasFlashlight);
    }
}
