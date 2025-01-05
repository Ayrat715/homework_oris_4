package ru.itis.models;

import java.time.LocalDateTime;

public class AdminLog {
    private int id;
    private int adminId;
    private String action;
    private LocalDateTime timestamp;

    public AdminLog() {
    }

    public AdminLog(int id, int adminId, String action, LocalDateTime timestamp) {
        this.id = id;
        this.adminId = adminId;
        this.action = action;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getAdminId() {
        return adminId;
    }
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
