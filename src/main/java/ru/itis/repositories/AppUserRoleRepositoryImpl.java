package ru.itis.repositories;

import ru.itis.models.AppUserRole;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class AppUserRoleRepositoryImpl implements AppUserRoleRepository {

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
    public boolean save(AppUserRole userRole) {
        String sql = "INSERT INTO app_user_role (app_user_id, role_id, created_at) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, userRole.getAppUserId());
            stmt.setInt(2, userRole.getRoleId());
            if (userRole.getCreatedAt() != null) {
                stmt.setTimestamp(3, Timestamp.valueOf(userRole.getCreatedAt()));
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
    public boolean delete(int appUserId, int roleId) {
        String sql = "DELETE FROM app_user_role WHERE app_user_id = ? AND role_id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, appUserId);
            stmt.setInt(2, roleId);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<AppUserRole> findAllByUserId(int appUserId) {
        String sql = "SELECT app_user_id, role_id, created_at FROM app_user_role WHERE app_user_id = ?";
        List<AppUserRole> result = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, appUserId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AppUserRole userRole = mapRowToAppUserRole(rs);
                    result.add(userRole);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<AppUserRole> findAllByRoleId(int roleId) {
        String sql = "SELECT app_user_id, role_id, created_at FROM app_user_role WHERE role_id = ?";
        List<AppUserRole> result = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, roleId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AppUserRole userRole = mapRowToAppUserRole(rs);
                    result.add(userRole);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<AppUserRole> findAll() {
        String sql = "SELECT app_user_id, role_id, created_at FROM app_user_role";
        List<AppUserRole> result = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                AppUserRole userRole = mapRowToAppUserRole(rs);
                result.add(userRole);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // преобразует строку ResultSet в объект AppUser
    private AppUserRole mapRowToAppUserRole(ResultSet rs) throws SQLException {
        AppUserRole userRole = new AppUserRole();
        userRole.setAppUserId(rs.getInt("app_user_id"));
        userRole.setRoleId(rs.getInt("role_id"));

        Timestamp createdAtTs = rs.getTimestamp("created_at");
        if (createdAtTs != null) {
            userRole.setCreatedAt(createdAtTs.toLocalDateTime());
        }
        return userRole;
    }
}
