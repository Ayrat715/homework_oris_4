package ru.itis.repositories;

import ru.itis.models.Booking;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingRepositoryImpl implements BookingRepository {

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
    public boolean save(Booking booking) {
        String sql = "INSERT INTO booking (app_user_id, property_id, start_date, end_date, created_at) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, booking.getAppUserId());
            stmt.setInt(2, booking.getPropertyId());
            stmt.setDate(3, Date.valueOf(booking.getStartDate()));
            stmt.setDate(4, Date.valueOf(booking.getEndDate()));
            // если createdAt не задан, используем текущее время на данный момент
            LocalDateTime createdAt = booking.getCreatedAt() != null
                    ? booking.getCreatedAt()
                    : LocalDateTime.now();
            stmt.setTimestamp(5, Timestamp.valueOf(createdAt));

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Booking findById(int id) {
        String sql = "SELECT id, app_user_id, property_id, start_date, end_date, created_at " +
                "FROM booking WHERE id = ?";
        Booking booking = null;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    booking = mapRowToBooking(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }

    @Override
    public List<Booking> findAll() {
        String sql = "SELECT id, app_user_id, property_id, start_date, end_date, created_at FROM booking";
        List<Booking> bookings = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next()) {
                Booking booking = mapRowToBooking(rs);
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public boolean update(Booking booking) {
        String sql = "UPDATE booking " +
                "SET app_user_id = ?, property_id = ?, start_date = ?, end_date = ? " +
                "WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, booking.getAppUserId());
            stmt.setInt(2, booking.getPropertyId());
            stmt.setDate(3, Date.valueOf(booking.getStartDate()));
            stmt.setDate(4, Date.valueOf(booking.getEndDate()));
            stmt.setInt(5, booking.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM booking WHERE id = ?";
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
    public List<Booking> findAllByUserId(int appUserId) {
        String sql = "SELECT id, app_user_id, property_id, start_date, end_date, created_at " +
                "FROM booking WHERE app_user_id = ?";
        List<Booking> bookings = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, appUserId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = mapRowToBooking(rs);
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public List<Booking> findAllByPropertyId(int propertyId) {
        String sql = "SELECT id, app_user_id, property_id, start_date, end_date, created_at " +
                "FROM booking WHERE property_id = ?";
        List<Booking> bookings = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, propertyId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = mapRowToBooking(rs);
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    // вспомогательный метод: мэп строки ResultSet в объект Booking
    private Booking mapRowToBooking(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setId(rs.getInt("id"));
        booking.setAppUserId(rs.getInt("app_user_id"));
        booking.setPropertyId(rs.getInt("property_id"));

        // преобразуем SQL Date к LocalDate
        Date startDate = rs.getDate("start_date");
        if (startDate != null) {
            booking.setStartDate(startDate.toLocalDate());
        }
        Date endDate = rs.getDate("end_date");
        if (endDate != null) {
            booking.setEndDate(endDate.toLocalDate());
        }

        // преобразуем SQL Timestamp к LocalDateTime
        Timestamp createdAtTs = rs.getTimestamp("created_at");
        if (createdAtTs != null) {
            booking.setCreatedAt(createdAtTs.toLocalDateTime());
        }
        return booking;
    }
}
