package com.mycompany.booklisting.search;

import com.mycompany.booklisting.dao.BookListingDAO;
import com.mycompany.booklisting.models.Listing;
import java.util.List;

public class TitleSearchStrategy implements ListingSearchStrategy {

    private final BookListingDAO listingDAO;

    public TitleSearchStrategy(BookListingDAO listingDAO) {
        this.listingDAO = listingDAO;
    }

    @Override
    public List<Listing> search(String keyword) {
        return listingDAO.findByTitle(keyword);
    }
}
