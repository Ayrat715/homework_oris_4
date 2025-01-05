package ru.itis.repositories;

import ru.itis.models.AdminLog;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class AdminLogRepositoryImpl implements AdminLogRepository {

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
    public boolean save(AdminLog adminLog) {
        String sql = "INSERT INTO admin_log (admin_id, action, timestamp) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, adminLog.getAdminId());
            stmt.setString(2, adminLog.getAction());

            // если timestamp не задан, прописываем текущее время на данный момент
            LocalDateTime currentTime = adminLog.getTimestamp() != null
                    ? adminLog.getTimestamp()
                    : LocalDateTime.now();
            stmt.setTimestamp(3, Timestamp.valueOf(currentTime));

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public AdminLog findById(int id) {
        String sql = "SELECT id, admin_id, action, timestamp FROM admin_log WHERE id = ?";
        AdminLog adminLog = null;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    adminLog = mapRowToAdminLog(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminLog;
    }

    @Override
    public List<AdminLog> findAll() {
        String sql = "SELECT id, admin_id, action, timestamp FROM admin_log";
        List<AdminLog> logs = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next()) {
                AdminLog adminLog = mapRowToAdminLog(rs);
                logs.add(adminLog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    @Override
    public boolean update(AdminLog adminLog) {
        String sql = "UPDATE admin_log SET admin_id = ?, action = ? WHERE id = ?";
        // timestamp обычно не обновляют, но мне понадобится, поэтому пусть будет
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, adminLog.getAdminId());
            stmt.setString(2, adminLog.getAction());
            stmt.setInt(3, adminLog.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM admin_log WHERE id = ?";
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
    public List<AdminLog> findAllByAdminId(int adminId) {
        String sql = "SELECT id, admin_id, action, timestamp FROM admin_log WHERE admin_id = ?";
        List<AdminLog> logs = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, adminId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AdminLog adminLog = mapRowToAdminLog(rs);
                    logs.add(adminLog);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    // вспомогательный метод: мэп строки ResultSet в объект AdminLog
    private AdminLog mapRowToAdminLog(ResultSet rs) throws SQLException {
        AdminLog adminLog = new AdminLog();
        adminLog.setId(rs.getInt("id"));
        adminLog.setAdminId(rs.getInt("admin_id"));
        adminLog.setAction(rs.getString("action"));

        Timestamp timestampTs = rs.getTimestamp("timestamp");
        if (timestampTs != null) {
            adminLog.setTimestamp(timestampTs.toLocalDateTime());
        }
        return adminLog;
    }
}
