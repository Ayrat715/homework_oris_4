package ru.itis.repositories;

import ru.itis.models.Review;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepositoryImpl implements ReviewRepository {

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
    public boolean save(Review review) {
        String sql = "INSERT INTO review (app_user_id, property_id, rating, text, created_at) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, review.getAppUserId());
            stmt.setInt(2, review.getPropertyId());
            stmt.setInt(3, review.getRating());
            stmt.setString(4, review.getText());

            // если createdAt не задан, используем текущее время на данный момент
            LocalDateTime createdAt = review.getCreatedAt() != null
                    ? review.getCreatedAt()
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
    public Review findById(int id) {
        String sql = "SELECT id, app_user_id, property_id, rating, text, created_at " +
                "FROM review WHERE id = ?";
        Review review = null;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    review = mapRowToReview(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return review;
    }

    @Override
    public List<Review> findAll() {
        String sql = "SELECT id, app_user_id, property_id, rating, text, created_at FROM review";
        List<Review> reviews = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next()) {
                Review review = mapRowToReview(rs);
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    @Override
    public boolean update(Review review) {
        String sql = "UPDATE review " +
                "SET app_user_id = ?, property_id = ?, rating = ?, text = ? " +
                "WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, review.getAppUserId());
            stmt.setInt(2, review.getPropertyId());
            stmt.setInt(3, review.getRating());
            stmt.setString(4, review.getText());
            stmt.setInt(5, review.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM review WHERE id = ?";
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
    public List<Review> findAllByUserId(int appUserId) {
        String sql = "SELECT id, app_user_id, property_id, rating, text, created_at " +
                "FROM review WHERE app_user_id = ?";
        List<Review> reviews = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, appUserId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Review review = mapRowToReview(rs);
                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    @Override
    public List<Review> findAllByPropertyId(int propertyId) {
        String sql = "SELECT id, app_user_id, property_id, rating, text, created_at " +
                "FROM review WHERE property_id = ?";
        List<Review> reviews = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, propertyId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Review review = mapRowToReview(rs);
                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    // вспомогательный метод: мэп строки ResultSet в объект Review
    private Review mapRowToReview(ResultSet rs) throws SQLException {
        Review review = new Review();
        review.setId(rs.getInt("id"));
        review.setAppUserId(rs.getInt("app_user_id"));
        review.setPropertyId(rs.getInt("property_id"));
        review.setRating(rs.getInt("rating"));
        review.setText(rs.getString("text"));

        Timestamp createdAtTs = rs.getTimestamp("created_at");
        if (createdAtTs != null) {
            review.setCreatedAt(createdAtTs.toLocalDateTime());
        }
        return review;
    }
}