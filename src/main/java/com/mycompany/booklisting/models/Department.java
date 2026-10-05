package com.mycompany.booklisting.models;


public class Department {

    private int departmentId;
    private String name;

    public Department(int departmentId, String name) {
        this.departmentId = departmentId;
        this.name = name;
    }

    private Department(Builder builder) {
        this.departmentId = builder.departmentId;
        this.name = builder.name;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public String getName() {
        return name;
    }

    public static class Builder {

        private int departmentId;
        private String name;

        public Builder departmentId(int departmentId) {
            this.departmentId = departmentId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Department build() {
            return new Department(this);
        }
    }
}

