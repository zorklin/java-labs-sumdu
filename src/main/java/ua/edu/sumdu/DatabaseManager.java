package ua.edu.sumdu;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseManager {

    private String url;

    public void loadConfig(String path) throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(path)) {
            props.load(fis);
        }
        this.url = props.getProperty("db.url");
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public void initializeDatabase() {
        StringBuilder sql = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("schema.sql"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("[DB Error] Could not read schema.sql: " + e.getMessage());
            return;
        }

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql.toString());
        } catch (SQLException e) {
            System.out.println("[DB Error] Could not initialize database: " + e.getMessage());
        }
    }

    public void savePhone(Phone phone) {
        String sql = "INSERT INTO phones "
                + "(type, brand, model, price, storage, operating_system, satellite_network, is_cordless, has_flashlight) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phone.getType());
            stmt.setString(2, phone.getBrand());
            stmt.setString(3, phone.getModel());
            stmt.setDouble(4, phone.getPrice());
            stmt.setInt(5, phone.getStorageCapacity());

            if (phone instanceof SmartPhone) {
                stmt.setString(6, ((SmartPhone) phone).getOperatingSystem());
            } else {
                stmt.setNull(6, java.sql.Types.VARCHAR);
            }

            if (phone instanceof SatellitePhone) {
                stmt.setString(7, ((SatellitePhone) phone).getSatelliteNetwork());
            } else {
                stmt.setNull(7, java.sql.Types.VARCHAR);
            }

            if (phone instanceof LandlinePhone) {
                stmt.setInt(8, ((LandlinePhone) phone).isCordless() ? 1 : 0);
            } else {
                stmt.setNull(8, java.sql.Types.INTEGER);
            }

            if (phone instanceof KeypadPhone) {
                stmt.setInt(9, ((KeypadPhone) phone).isHasFlashlight() ? 1 : 0);
            } else {
                stmt.setNull(9, java.sql.Types.INTEGER);
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("[DB Error] Could not save phone: " + e.getMessage());
        }
    }
}
