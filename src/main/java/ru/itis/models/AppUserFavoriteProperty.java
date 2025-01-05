package ru.itis.models;

import java.time.LocalDateTime;

public class AppUserFavoriteProperty {
    private int appUserId;
    private int propertyId;
    private LocalDateTime createdAt;

    public AppUserFavoriteProperty() {
    }

    public AppUserFavoriteProperty(int appUserId, int propertyId, LocalDateTime createdAt) {
        this.appUserId = appUserId;
        this.propertyId = propertyId;
        this.createdAt = createdAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
