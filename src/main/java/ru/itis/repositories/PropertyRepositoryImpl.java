package ru.itis.repositories;

import ru.itis.models.Property;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PropertyRepositoryImpl implements PropertyRepository {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/test_db4";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "Gamburger";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean save(Property property) {
        String sql = "INSERT INTO property (title, description, price_per_night, owner_id, created_at) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setString(1, property.getTitle());
            stmt.setString(2, property.getDescription());
            stmt.setBigDecimal(3, property.getPricePerNight());
            stmt.setInt(4, property.getOwnerId());
            stmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Property findById(int id) {
        String sql = "SELECT id, title, description, price_per_night, owner_id, created_at " +
                "FROM property WHERE id = ?";
        Property property = null;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    property = mapRowToProperty(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return property;
    }

    @Override
    public List<Property> findAll() {
        String sql = "SELECT id, title, description, price_per_night, owner_id, created_at FROM property";
        List<Property> properties = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next()) {
                Property property = mapRowToProperty(rs);
                properties.add(property);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return properties;
    }

    @Override
    public boolean update(Property property) {
        String sql = "UPDATE property SET title = ?, description = ?, price_per_night = ?, owner_id = ? " +
                "WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setString(1, property.getTitle());
            stmt.setString(2, property.getDescription());
            stmt.setBigDecimal(3, property.getPricePerNight());
            stmt.setInt(4, property.getOwnerId());
            stmt.setInt(5, property.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM property WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Property> findByOwnerId(int ownerId) {
        String sql = "SELECT id, title, description, price_per_night, owner_id, created_at " +
                "FROM property WHERE owner_id = ?";
        List<Property> properties = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, ownerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Property property = mapRowToProperty(rs);
                    properties.add(property);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return properties;
    }


    // вспомогательный метод: мэп ResultSet -> Property (преобразует строку в объект)
    private Property mapRowToProperty(ResultSet rs) throws SQLException {
        Property property = new Property();
        property.setId(rs.getInt("id"));
        property.setTitle(rs.getString("title"));
        property.setDescription(rs.getString("description"));

        // для поля price_per_night - BigDecimal
        BigDecimal price = rs.getBigDecimal("price_per_night");
        property.setPricePerNight(price);

        property.setOwnerId(rs.getInt("owner_id"));

        Timestamp createdAtTs = rs.getTimestamp("created_at");
        if (createdAtTs != null) {
            property.setCreatedAt(createdAtTs.toLocalDateTime());
        }

        return property;
    }
}