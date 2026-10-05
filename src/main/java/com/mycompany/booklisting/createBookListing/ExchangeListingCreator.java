package com.mycompany.booklisting.createBookListing;

import com.mycompany.booklisting.constant.ListingStatus;
import com.mycompany.booklisting.models.ExchangeListing;
import com.mycompany.booklisting.models.Listing;

import java.time.LocalDateTime;

public class ExchangeListingCreator extends ListingCreator {

    @Override
    public Listing create(ListingFormData data) {

        return new ExchangeListing.Builder()
            .user(data.user)
            .listingType(data.listingType)
            .title(data.title)
            .author(data.author)
            .edition(data.edition)
            .categoryId(data.categoryId)
            .courseId(data.courseId)
            .condition(data.condition)
            .price(null)
            .imagePath(data.imagePath)
            .status(ListingStatus.AVAILABLE)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .expiryDate(LocalDateTime.now().plusDays(30))
            .build();
    }
}

