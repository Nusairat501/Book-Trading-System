package com.mycompany.booklisting.models;

import com.mycompany.booklisting.constant.Condition;
import com.mycompany.booklisting.constant.ListingStatus;
import com.mycompany.booklisting.constant.ListingType;
import java.time.LocalDateTime;

public class Listing {

    private int listingId;
    private User user;
    private String title;
    private String author;
    private String edition;
    private int categoryId;
    private int courseId;
    private Double price;
    private String imagePath;
    private ListingStatus status;
    private LocalDateTime expiryDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Category category;
    private Course course;
    public Listing(){}
    private Listing(Builder builder) {
        this.listingId = builder.listingId;
        this.user = builder.user;
        this.listingType = builder.listingType;
        this.title = builder.title;
        this.author = builder.author;
        this.edition = builder.edition;
        this.categoryId = builder.categoryId;
        this.courseId = builder.courseId;
        this.condition = builder.condition;
        this.price = builder.price;
        this.imagePath = builder.imagePath;
        this.status = builder.status;
        this.expiryDate = builder.expiryDate;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.category = builder.category;
        this.course = builder.course;
    }

    public int getListingId() {
        return listingId;
    }

    public User getUser() {
        return user;
    }

    public ListingType getListingType() {
        return listingType;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getEdition() {
        return edition;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getCourseId() {
        return courseId;
    }

    public Condition getCondition() {
        return condition;
    }

    public Double getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public ListingStatus getStatus() {
        return status;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
     public Category getCategory() {
        return category;
    }

    public Course getCourse() {
        return course;
    }
    
    private ListingType listingType;
    private Condition condition;

    public static class Builder {

        private int listingId;
        private User user;
        private ListingType listingType;
        private String title;
        private String author;
        private String edition;
        private int categoryId;
        private int courseId;
        private Condition condition;
        private Double price;
        private String imagePath;
        private ListingStatus status;
        private LocalDateTime expiryDate;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Category category;
        private Course course;

        public Builder listingId(int listingId) {
            this.listingId = listingId;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder listingType(ListingType listingType) {
            this.listingType = listingType;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder edition(String edition) {
            this.edition = edition;
            return this;
        }

        public Builder categoryId(int categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder courseId(int courseId) {
            this.courseId = courseId;
            return this;
        }

        public Builder condition(Condition condition) {
            this.condition = condition;
            return this;
        }

        public Builder price(Double price) {
            this.price = price;
            return this;
        }

        public Builder imagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public Builder status(ListingStatus status) {
            this.status = status;
            return this;
        }

        public Builder expiryDate(LocalDateTime expiryDate) {
            this.expiryDate = expiryDate;
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

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Builder course(Course course) {
            this.course = course;
            return this;
        }

        public Listing build() {
            return new Listing(this);
        }
    }
}
