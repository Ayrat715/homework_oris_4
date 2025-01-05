package ru.itis.models;

import java.time.LocalDateTime;

public class Review {
    private int id;
    private int appUserId;
    private int propertyId;
    private int rating;
    private String text;
    private LocalDateTime createdAt;

    public Review() {
    }

    public Review(int id, int appUserId, int propertyId,
                  int rating, String text, LocalDateTime createdAt) {
        this.id = id;
        this.appUserId = appUserId;
        this.propertyId = propertyId;
        this.rating = rating;
        this.text = text;
        this.createdAt = createdAt;
    }

    // Геттеры и сеттеры

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getAppUserId() {
        return appUserId;
    }
    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public int getPropertyId() {
        return propertyId;
    }
    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
