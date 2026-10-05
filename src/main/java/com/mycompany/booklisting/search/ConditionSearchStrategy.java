package com.mycompany.booklisting.search;
import com.mycompany.booklisting.constant.Condition;
import com.mycompany.booklisting.dao.BookListingDAO;
import com.mycompany.booklisting.models.Listing;
import java.util.List;

public class ConditionSearchStrategy implements ListingSearchStrategy {

    private final BookListingDAO listingDAO;

    public ConditionSearchStrategy(BookListingDAO listingDAO) {
        this.listingDAO = listingDAO;
    }

    @Override
    public List<Listing> search(String keyword) {
        return listingDAO.findByCondition(Condition.valueOf(keyword));
    }
}
