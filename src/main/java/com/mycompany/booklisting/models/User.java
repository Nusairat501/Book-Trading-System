package com.mycompany.booklisting.models;
import com.mycompany.booklisting.constant.Role;
import java.time.LocalDateTime;

public class User {

    private int userId;
    private String name;
    private String email;
    private String password;
    private Integer academicYear;
    private Role role;
    private boolean isBlocked;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User(Builder builder) {
        this.userId = builder.userId;
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
        this.academicYear = builder.academicYear;
        this.role = builder.role;
        this.isBlocked = builder.isBlocked;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Integer getAcademicYear() { return academicYear; }
    public Role getRole() { return role; }
    public boolean isBlocked() { return isBlocked; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setUserId(int userId) { this.userId = userId; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setAcademicYear(Integer academicYear) { this.academicYear = academicYear; }
    public void setRole(Role role) { this.role = role; }
    public void setBlocked(boolean blocked) { isBlocked = blocked; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static class Builder {
        private int userId;
        private String name;
        private String email;
        private String password;
        private Integer academicYear;
        private Role role;
        private boolean isBlocked = false;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder userId(int userId) { this.userId = userId; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder password(String password) { this.password = password; return this; }
        public Builder academicYear(Integer academicYear) { this.academicYear = academicYear; return this; }
        public Builder role(Role role) { this.role = role; return this; }
        public Builder isBlocked(boolean isBlocked) { this.isBlocked = isBlocked; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public User build() { return new User(this); }
    }
}
