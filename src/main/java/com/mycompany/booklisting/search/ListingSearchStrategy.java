package com.mycompany.booklisting.search;
import com.mycompany.booklisting.models.Listing;
import java.util.List;

public interface ListingSearchStrategy {
    List<Listing> search(String keyword);
}