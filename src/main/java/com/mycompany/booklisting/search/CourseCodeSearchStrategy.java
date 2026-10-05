package com.mycompany.booklisting.search;

import com.mycompany.booklisting.dao.BookListingDAO;
import com.mycompany.booklisting.models.Listing;

import java.util.List;

public class CourseCodeSearchStrategy implements ListingSearchStrategy {

    private final BookListingDAO listingDAO;

    public CourseCodeSearchStrategy(BookListingDAO listingDAO) {
        this.listingDAO = listingDAO;
    }

    @Override
    public List<Listing> search(String keyword) {
        return listingDAO.findByCourseCode(keyword);
    }
}
