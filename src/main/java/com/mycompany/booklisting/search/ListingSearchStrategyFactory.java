package com.mycompany.booklisting.search;


import com.mycompany.booklisting.dao.BookListingDAO;

public class ListingSearchStrategyFactory {

    public static ListingSearchStrategy getStrategy(
            String filter,
            BookListingDAO dao
    ) {
        return switch (filter.toLowerCase()) {
            case "title" -> new TitleSearchStrategy(dao);
            case "course" -> new CourseCodeSearchStrategy(dao);
            case "department" -> new DepartmentSearchStrategy(dao);
            case "condition" -> new ConditionSearchStrategy(dao);
            case "type" -> new ListingTypeSearchStrategy(dao);
            default -> throw new IllegalArgumentException("Invalid search filter");
        };
    }
}
