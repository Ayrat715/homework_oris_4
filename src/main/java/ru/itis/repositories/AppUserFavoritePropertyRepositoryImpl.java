package ru.itis.repositories;

import ru.itis.models.AppUserFavoriteProperty;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppUserFavoritePropertyRepositoryImpl implements AppUserFavoritePropertyRepository {

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
    public boolean save(AppUserFavoriteProperty favorite) {
        String sql = "INSERT INTO app_user_favorite_property (app_user_id, property_id, created_at) " +
                "VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, favorite.getAppUserId());
            stmt.setInt(2, favorite.getPropertyId());
            // если favorite.getCreatedAt() == null, прописываем дату на текущтй момент
            if (favorite.getCreatedAt() != null) {
                stmt.setTimestamp(3, Timestamp.valueOf(favorite.getCreatedAt()));
            } else {
                stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            }

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int appUserId, int propertyId) {
        String sql = "DELETE FROM app_user_favorite_property " +
                "WHERE app_user_id = ? AND property_id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, appUserId);
            stmt.setInt(2, propertyId);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<AppUserFavoriteProperty> findAllByUserId(int appUserId) {
        String sql = "SELECT app_user_id, property_id, created_at " +
                "FROM app_user_favorite_property " +
                "WHERE app_user_id = ?";
        List<AppUserFavoriteProperty> result = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, appUserId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AppUserFavoriteProperty favorite = mapRowToFavorite(rs);
                    result.add(favorite);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<AppUserFavoriteProperty> findAllByPropertyId(int propertyId) {
        String sql = "SELECT app_user_id, property_id, created_at " +
                "FROM app_user_favorite_property " +
                "WHERE property_id = ?";
        List<AppUserFavoriteProperty> result = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, propertyId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AppUserFavoriteProperty favorite = mapRowToFavorite(rs);
                    result.add(favorite);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<AppUserFavoriteProperty> findAll() {
        String sql = "SELECT app_user_id, property_id, created_at FROM app_user_favorite_property";
        List<AppUserFavoriteProperty> result = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next()) {
                AppUserFavoriteProperty favorite = mapRowToFavorite(rs);
                result.add(favorite);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    // вспомогательный метод: мэп строки ResultSet в объект AppUserFavoriteProperty
    private AppUserFavoriteProperty mapRowToFavorite(ResultSet rs) throws SQLException {
        AppUserFavoriteProperty favorite = new AppUserFavoriteProperty();
        favorite.setAppUserId(rs.getInt("app_user_id"));
        favorite.setPropertyId(rs.getInt("property_id"));

        Timestamp createdAtTs = rs.getTimestamp("created_at");
        if (createdAtTs != null) {
            favorite.setCreatedAt(createdAtTs.toLocalDateTime());
        }
        return favorite;
    }
}
