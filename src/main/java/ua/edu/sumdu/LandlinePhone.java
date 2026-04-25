package ua.edu.sumdu;

import java.util.Objects;

public class LandlinePhone extends Phone {

    private boolean isCordless;

    public LandlinePhone(String brand, String model, double price, int storageCapacity, boolean isCordless) {
        super(brand, model, price, storageCapacity);
        this.type = "LandlinePhone";
        this.isCordless = isCordless;
    }

    public boolean isCordless() {
        return isCordless;
    }

    public void setCordless(boolean isCordless) {
        this.isCordless = isCordless;
    }

    @Override
    public String toString() {
        return "LandlinePhone{"
                + "brand='" + getBrand() + '\''
                + ", model='" + getModel() + '\''
                + ", price=" + getPrice()
                + ", storageCapacity=" + getStorageCapacity() + " GB"
                + ", isCordless=" + isCordless
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
        LandlinePhone that = (LandlinePhone) o;
        return isCordless == that.isCordless;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isCordless);
    }
}
