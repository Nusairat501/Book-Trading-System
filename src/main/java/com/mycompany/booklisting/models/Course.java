package com.mycompany.booklisting.models;

import java.time.LocalDateTime;

public class Course {

    private int courseId;
    private String code;
    private Integer departmentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Department department;

    private Course(Builder builder) {
        this.courseId = builder.courseId;
        this.code = builder.code;
        this.departmentId = builder.departmentId;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.department = builder.department;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCode() {
        return code;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Department getDepartment() {
        return department;
    }

    public static class Builder {

        private int courseId;
        private String code;
        private Integer departmentId;
        private Department department;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder courseId(int courseId) {
            this.courseId = courseId;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder departmentId(Integer departmentId) {
            this.departmentId = departmentId;
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

        public Builder department(Department department) {
            this.department = department;
            return this;
        }

        public Course build() {
            return new Course(this);
        }
    }
}
