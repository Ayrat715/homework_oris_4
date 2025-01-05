package ru.itis.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Booking {
    private int id;
    private int appUserId;
    private int propertyId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;

    public Booking() {
    }

    public Booking(int id, int appUserId, int propertyId,
                   LocalDate startDate, LocalDate endDate,
                   LocalDateTime createdAt) {
        this.id = id;
        this.appUserId = appUserId;
        this.propertyId = propertyId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
    }

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

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}