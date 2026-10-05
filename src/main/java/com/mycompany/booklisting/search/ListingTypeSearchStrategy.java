package com.mycompany.booklisting.search;
import com.mycompany.booklisting.constant.ListingType;
import com.mycompany.booklisting.dao.BookListingDAO;
import com.mycompany.booklisting.models.Listing;
import java.util.List;

public class ListingTypeSearchStrategy implements ListingSearchStrategy {

    private final BookListingDAO listingDAO;

    public ListingTypeSearchStrategy(BookListingDAO listingDAO) {
        this.listingDAO = listingDAO;
    }

    @Override
    public List<Listing> search(String keyword) {
        return listingDAO.findByListingType(ListingType.valueOf(keyword));
    }
}

