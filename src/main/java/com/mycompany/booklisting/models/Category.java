package com.mycompany.booklisting.models;
import java.time.LocalDateTime;

public class Category {

    private int categoryId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Category(Builder builder) {
        this.categoryId = builder.categoryId;
        this.name = builder.name;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static class Builder {
        private int categoryId;
        private String name;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder categoryId(int categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }
}
