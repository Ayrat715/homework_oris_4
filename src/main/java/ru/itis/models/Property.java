package ru.itis.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Property {
    private int id;
    private String title;
    private String description;
    private BigDecimal pricePerNight;
    private int ownerId;
    private LocalDateTime createdAt;

    public Property() {
    }

    public Property(int id, String title, String description,
                    BigDecimal pricePerNight, int ownerId, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
    }

    // Геттеры и сеттеры

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }
    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public int getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
