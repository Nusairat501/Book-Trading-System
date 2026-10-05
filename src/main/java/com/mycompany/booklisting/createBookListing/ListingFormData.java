package com.mycompany.booklisting.createBookListing;

import com.mycompany.booklisting.constant.Condition;
import com.mycompany.booklisting.constant.ListingType;
import com.mycompany.booklisting.models.User;

public class ListingFormData {

    public User user;
    public ListingType listingType;
    public String title;
    public String author;
    public String edition;
    public int categoryId;
    public int courseId;
    public Condition condition;
    public Double price;
    public String imagePath;
}
