package ua.edu.sumdu;

import java.util.Objects;

public class SatellitePhone extends Phone {

    private String satelliteNetwork;

    public SatellitePhone(String brand, String model, double price, int storageCapacity, String satelliteNetwork) {
        super(brand, model, price, storageCapacity);
        this.type = "SatellitePhone";
        setSatelliteNetwork(satelliteNetwork);
    }

    public String getSatelliteNetwork() {
        return satelliteNetwork;
    }

    public void setSatelliteNetwork(String satelliteNetwork) {
        if (satelliteNetwork == null || satelliteNetwork.isBlank()) {
            throw new IllegalArgumentException("Satellite network must not be null or empty.");
        }
        this.satelliteNetwork = satelliteNetwork;
    }

    @Override
    public String toString() {
        return "SatellitePhone{"
                + "brand='" + getBrand() + '\''
                + ", model='" + getModel() + '\''
                + ", price=" + getPrice()
                + ", storageCapacity=" + getStorageCapacity() + " GB"
                + ", satelliteNetwork='" + satelliteNetwork + '\''
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
        SatellitePhone that = (SatellitePhone) o;
        return Objects.equals(satelliteNetwork, that.satelliteNetwork);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), satelliteNetwork);
    }
}
