package com.mycompany.booklisting.createBookListing;
import com.mycompany.booklisting.models.Listing;

public abstract class ListingCreator {
    public abstract Listing create(ListingFormData data);
}

