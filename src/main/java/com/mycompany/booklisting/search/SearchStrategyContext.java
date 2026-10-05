package com.mycompany.booklisting.search;

import com.mycompany.booklisting.models.Listing;
import java.util.List;


public class SearchStrategyContext {
    
    public static List<Listing> start(ListingSearchStrategy listingSearchStrategy, String keyword){
       return listingSearchStrategy.search(keyword);
    }
}
