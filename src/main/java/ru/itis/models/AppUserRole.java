package ru.itis.models;

import java.time.LocalDateTime;

public class AppUserRole {

    private int appUserId;
    private int roleId;
    private LocalDateTime createdAt;

    public AppUserRole() {
    }

    public AppUserRole(int appUserId, int roleId, LocalDateTime createdAt) {
        this.appUserId = appUserId;
        this.roleId = roleId;
        this.createdAt = createdAt;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
