package com.mycompany.booklisting.controllers;

import com.mycompany.booklisting.dao.BookListingDAO;
import com.mycompany.booklisting.dao.CategoryDAO;
import com.mycompany.booklisting.dao.CourseDAO;
import com.mycompany.booklisting.models.Listing;
import com.mycompany.booklisting.models.User;

import com.mycompany.booklisting.constant.Condition;
import com.mycompany.booklisting.constant.ListingStatus;
import com.mycompany.booklisting.constant.ListingType;
import com.mycompany.booklisting.createBookListing.ExchangeListingCreator;
import com.mycompany.booklisting.createBookListing.ListingCreator;
import com.mycompany.booklisting.createBookListing.ListingFormData;
import com.mycompany.booklisting.createBookListing.SellListingCreator;
import com.mycompany.booklisting.search.ListingSearchStrategy;
import com.mycompany.booklisting.search.ListingSearchStrategyFactory;
import com.mycompany.booklisting.search.SearchStrategyContext;

import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import javax.servlet.annotation.MultipartConfig;

import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "BookListingController", urlPatterns = {"/listings"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 100
)
public class BookListingController extends HttpServlet {

    private BookListingDAO listingDAO;
    private CourseDAO courseDAO;
    private CategoryDAO categoryDAO;

    @Override
    public void init() {
        listingDAO = new BookListingDAO();
        courseDAO = new CourseDAO();
        categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null || action.equals("books-list")) {
            String filter = req.getParameter("filter");
            String keyword = req.getParameter("keyword");
            List<Listing> listings;

            if (filter != null && keyword != null && !keyword.isEmpty()) {
                ListingSearchStrategy strategy = ListingSearchStrategyFactory.getStrategy(filter, listingDAO);
                listings = SearchStrategyContext.start(strategy, keyword);
            } else {
                listings = listingDAO.getAvailable();
            }

            req.setAttribute("listings", listings);
            req.getRequestDispatcher("/student/books-list.jsp").forward(req, resp);

        } else if (action.equals("my-books")) {
            int userId = (int) req.getSession().getAttribute("userId");
            req.setAttribute("listings", listingDAO.getByUser(userId));
            req.getRequestDispatcher("/student/my-books-list.jsp").forward(req, resp);

        } else if (action.equals("create")) {
            req.setAttribute("categories", categoryDAO.findAll());
            req.setAttribute("courses", courseDAO.getAllCourses());
            req.getRequestDispatcher("/student/book-create.jsp").forward(req, resp);

        } else if (action.equals("edit")) {
            int id = Integer.parseInt(req.getParameter("listingId"));
            req.setAttribute("listing", listingDAO.getById(id));
            req.setAttribute("categories", categoryDAO.findAll());
            req.setAttribute("courses", courseDAO.getAllCourses());

            req.getRequestDispatcher("/student/book-edit.jsp").forward(req, resp);

        } else if (action.equals("details")) {
            int id = Integer.parseInt(req.getParameter("listingId"));
            req.setAttribute("listing", listingDAO.getById(id));
            req.getRequestDispatcher("/student/book-details.jsp").forward(req, resp);
        } else if (action.equals("admin-books-list")) {
            req.setAttribute("listings", listingDAO.getAll());
            req.getRequestDispatcher("/admin/books-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action.equals("create")) {
            createListing(req, resp);
        } else if (action.equals("update")) {
            updateListing(req, resp);
        } else if (action.equals("delete")) {
            int id = Integer.parseInt(req.getParameter("listingId"));
            listingDAO.delete(id);
            String role = (String) req.getSession().getAttribute("role");
            if (role.equals("ADMIN")) {
                resp.sendRedirect("listings?action=admin-books-list");
            } else {
                resp.sendRedirect("listings?action=my-books");
            }
        }
//        else if (action.equals("change-status")) {
//            int id = Integer.parseInt(req.getParameter("listingId"));
//            ListingStatus status = ListingStatus.valueOf(req.getParameter("status"));
//            listingDAO.updateStatus(id, status);
//            resp.sendRedirect("listings?action=my-books");
//        }

    }

    private void createListing(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        int userId = (int) req.getSession().getAttribute("userId");

        String imagePath = handleImageUpload(req, null);

        Double price = null;
        String priceParam = req.getParameter("price");
        if (priceParam != null && !priceParam.isBlank()) {
            price = Double.valueOf(priceParam);
        }

        ListingType listingType
                = ListingType.valueOf(req.getParameter("listingType"));

        ListingFormData data = new ListingFormData();
        data.user = new User.Builder().userId(userId).build();
        data.listingType = listingType;
        data.title = req.getParameter("title");
        data.author = req.getParameter("author");
        data.edition = req.getParameter("edition");
        data.categoryId = Integer.parseInt(req.getParameter("categoryId"));
        data.courseId = Integer.parseInt(req.getParameter("courseId"));
        data.condition = Condition.valueOf(req.getParameter("condition"));
        data.price = price;
        data.imagePath = imagePath;

        ListingCreator creator;

        switch (listingType) {
            case SELL ->
                creator = new SellListingCreator();
            case EXCHANGE ->
                creator = new ExchangeListingCreator();
            default ->
                throw new IllegalStateException("Unsupported listing type");
        }
        Listing listing = creator.create(data);

        listingDAO.create(listing);
        resp.sendRedirect("listings?action=my-books");
    }

    private void updateListing(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int listingId = Integer.parseInt(req.getParameter("listingId"));
        String existingImagePath = req.getParameter("existingImagePath");
        String imagePath = handleImageUpload(req, existingImagePath);
        String priceParam = req.getParameter("price");
        Double price = null;
        if (priceParam != null && !priceParam.trim().isEmpty()) {
            price = Double.valueOf(priceParam);
        }
        Listing listing = new Listing.Builder()
                .listingId(listingId)
                .title(req.getParameter("title"))
                .author(req.getParameter("author"))
                .edition(req.getParameter("edition"))
                .categoryId(Integer.parseInt(req.getParameter("categoryId")))
                .courseId(Integer.parseInt(req.getParameter("courseId")))
                .condition(Condition.valueOf(req.getParameter("condition")))
                .price(price)
                .imagePath(imagePath)
                .build();

        listingDAO.update(listing);
        resp.sendRedirect("listings?action=my-books");
    }

    private String handleImageUpload(HttpServletRequest req, String existingImagePath) throws IOException, ServletException {
        Part filePart = req.getPart("image");
        String imagePath = existingImagePath;

        if (filePart != null && filePart.getSize() > 0) {
            String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String fileExtension = "";
            String originalImageName = "";
            int dotIndex = originalFileName.lastIndexOf('.');
            if (dotIndex > 0) {
                fileExtension = originalFileName.substring(dotIndex);
                originalImageName = originalFileName.substring(0, dotIndex);
            }

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String uniqueFileName = originalImageName + "_" + timestamp + "_" + UUID.randomUUID() + fileExtension;

            String uploadDir = getServletContext().getRealPath("/uploads");
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            filePart.write(uploadDir + File.separator + uniqueFileName);
            imagePath = "uploads/" + uniqueFileName;
        }

        return imagePath;
    }

}
