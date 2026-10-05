package com.mycompany.booklisting.dao;

import com.mycompany.booklisting.config.DbConnectionManager;
import com.mycompany.booklisting.constant.ListingStatus;
import com.mycompany.booklisting.models.*;
import com.mycompany.booklisting.constant.Condition;
import com.mycompany.booklisting.constant.ListingStatus;
import com.mycompany.booklisting.constant.ListingType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookListingDAO {

    private static final String INSERT_SQL
            = "INSERT INTO listings (user_id, listing_type, title, author, edition, category_id, course_id, `condition`, price, image_path, status, expiry_date) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_SQL
            = "UPDATE listings SET title=?, author=?, edition=?, category_id=?, course_id=?, `condition`=?, price=?, image_path=?, updated_at=NOW() "
            + "WHERE listing_id=?";

    private static final String DELETE_SQL
            = "DELETE FROM listings WHERE listing_id=?";

    private static final String UPDATE_STATUS_SQL
            = "UPDATE listings SET status=? WHERE listing_id=?";

    private static final String SELECT_BY_ID_SQL
            = "SELECT l.*, c.name AS category_name, co.code AS course_code "
            + "FROM listings l "
            + "JOIN categories c ON l.category_id = c.category_id "
            + "JOIN courses co ON l.course_id = co.course_id "
            + "WHERE l.listing_id = ?";

    private static final String SELECT_ALL_SQL
            = "SELECT l.*, c.name AS category_name, co.code AS course_code "
            + "FROM listings l "
            + "JOIN categories c ON l.category_id = c.category_id "
            + "JOIN courses co ON l.course_id = co.course_id "
            + "ORDER BY l.created_at DESC";



    private static final String SELECT_BY_USER_SQL
            = "SELECT l.*, c.name AS category_name, co.code AS course_code "
            + "FROM listings l "
            + "JOIN categories c ON l.category_id = c.category_id "
            + "JOIN courses co ON l.course_id = co.course_id "
            + "WHERE l.user_id=? ORDER BY l.created_at DESC";

    private static final String BASE_SEARCH_SQL
            = "SELECT l.*, c.name AS category_name, co.code AS course_code, d.name AS department_name "
            + "FROM listings l "
            + "JOIN categories c ON l.category_id = c.category_id "
            + "JOIN courses co ON l.course_id = co.course_id "
            + "JOIN departments d ON co.department_id = d.department_id "
            + "WHERE l.status = ? AND l.expiry_date > NOW() ";



    public void create(Listing listing) {
        try (Connection con = DbConnectionManager.getConnection(); PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {

            ps.setInt(1, listing.getUser().getUserId());
            ps.setString(2, listing.getListingType().name());
            ps.setString(3, listing.getTitle());
            ps.setString(4, listing.getAuthor());
            ps.setString(5, listing.getEdition());
            ps.setInt(6, listing.getCategoryId());
            ps.setInt(7, listing.getCourseId());
            ps.setString(8, listing.getCondition().name());
            if (listing.getPrice() != null) {
                ps.setDouble(9, listing.getPrice());
            } else {
                ps.setNull(9, java.sql.Types.DOUBLE);
            }
            ps.setString(10, listing.getImagePath());
            ps.setString(11, listing.getStatus().name());
            ps.setTimestamp(12, Timestamp.valueOf(listing.getExpiryDate()));

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Listing listing) {
        try (Connection con = DbConnectionManager.getConnection(); PreparedStatement ps = con.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, listing.getTitle());
            ps.setString(2, listing.getAuthor());
            ps.setString(3, listing.getEdition());
            ps.setInt(4, listing.getCategoryId());
            ps.setInt(5, listing.getCourseId());
            ps.setString(6, listing.getCondition().name());
            if (listing.getPrice() != null) {
                ps.setDouble(7, listing.getPrice());
            } else {
                ps.setNull(7, java.sql.Types.DOUBLE);
            }
            ps.setString(8, listing.getImagePath());
            ps.setInt(9, listing.getListingId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int listingId) {
        try (Connection con = DbConnectionManager.getConnection(); PreparedStatement ps = con.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, listingId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(int listingId, ListingStatus status) {
        try (Connection con = DbConnectionManager.getConnection(); PreparedStatement ps = con.prepareStatement(UPDATE_STATUS_SQL)) {

            ps.setString(1, status.name());
            ps.setInt(2, listingId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Listing getById(int listingId) {
        try (Connection con = DbConnectionManager.getConnection(); PreparedStatement ps = con.prepareStatement(SELECT_BY_ID_SQL)) {

            ps.setInt(1, listingId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Listing> getAll() {
        List<Listing> list = new ArrayList<>();

        try (Connection con = DbConnectionManager.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(SELECT_ALL_SQL)) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Listing> getAvailable() {
        List<Listing> list = new ArrayList<>();

        try (Connection con = DbConnectionManager.getConnection(); PreparedStatement ps = con.prepareStatement(BASE_SEARCH_SQL)) {

            ps.setString(1, ListingStatus.AVAILABLE.name());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowWithDepartment(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Listing> getByUser(int userId) {
        List<Listing> list = new ArrayList<>();

        try (Connection con = DbConnectionManager.getConnection(); PreparedStatement ps = con.prepareStatement(SELECT_BY_USER_SQL)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Listing mapRow(ResultSet rs) throws SQLException {

        User user = new User.Builder()
                .userId(rs.getInt("user_id"))
                .build();
        return new Listing.Builder()
                .listingId(rs.getInt("listing_id"))
                .user(user)
                .listingType(ListingType.valueOf(rs.getString("listing_type")))
                .condition(Condition.valueOf(rs.getString("condition")))
                .title(rs.getString("title"))
                .author(rs.getString("author"))
                .edition(rs.getString("edition"))
                .categoryId(rs.getInt("category_id"))
                .courseId(rs.getInt("course_id"))
                .price(rs.getDouble("price"))
                .imagePath(rs.getString("image_path"))
                .status(ListingStatus.valueOf(rs.getString("status")))
                .expiryDate(rs.getTimestamp("expiry_date").toLocalDateTime())
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .category(new Category.Builder().name(rs.getString("category_name")).build())
                .course(new Course.Builder().code(rs.getString("course_code")).build())
                .updatedAt(
                        rs.getTimestamp("updated_at") != null
                        ? rs.getTimestamp("updated_at").toLocalDateTime()
                        : null
                )
                .build();
    }

    private Listing mapRowWithDepartment(ResultSet rs) throws SQLException {

        User user = new User.Builder()
                .userId(rs.getInt("user_id"))
                .build();

        Department department = new Department.Builder()
                .name(rs.getString("department_name"))
                .build();

        Course course = new Course.Builder()
                .courseId(rs.getInt("course_id"))
                .code(rs.getString("course_code"))
                .department(department)
                .build();

        Category category = new Category.Builder()
                .name(rs.getString("category_name"))
                .build();

        return new Listing.Builder()
                .listingId(rs.getInt("listing_id"))
                .listingType(ListingType.valueOf(rs.getString("listing_type")))
                .condition(Condition.valueOf(rs.getString("condition")))
                .title(rs.getString("title"))
                .author(rs.getString("author"))
                .edition(rs.getString("edition"))
                .categoryId(rs.getInt("category_id"))
                .courseId(rs.getInt("course_id"))
                .price(rs.getDouble("price"))
                .imagePath(rs.getString("image_path"))
                .status(ListingStatus.valueOf(rs.getString("status")))
                .expiryDate(rs.getTimestamp("expiry_date").toLocalDateTime())
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .user(user)
                .updatedAt(
                        rs.getTimestamp("updated_at") != null
                                ? rs.getTimestamp("updated_at").toLocalDateTime()
                                : null
                )
                .category(category)
                .course(course)
                .build();
    }

    public List<Listing> findByTitle(String keyword) {
        List<Listing> list = new ArrayList<>();
        String sql = BASE_SEARCH_SQL + "AND l.title LIKE ?";

        try (Connection con = DbConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ListingStatus.AVAILABLE.name());
            ps.setString(2, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowWithDepartment(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Listing> findByCourseCode(String keyword) {
        List<Listing> list = new ArrayList<>();
        String sql = BASE_SEARCH_SQL + "AND co.code LIKE ?";

        try (Connection con = DbConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ListingStatus.AVAILABLE.name());
            ps.setString(2, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowWithDepartment(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Listing> findByCondition(Condition condition) {
        List<Listing> list = new ArrayList<>();
        String sql = BASE_SEARCH_SQL + "AND l.`condition` = ?";

        try (Connection con = DbConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ListingStatus.AVAILABLE.name());
            ps.setString(2, condition.name());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowWithDepartment(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Listing> findByListingType(ListingType type) {
        List<Listing> list = new ArrayList<>();
        String sql = BASE_SEARCH_SQL + "AND l.listing_type = ?";

        try (Connection con = DbConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ListingStatus.AVAILABLE.name());
            ps.setString(2, type.name());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowWithDepartment(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<Listing> findByDepartment(String keyword) {
        List<Listing> list = new ArrayList<>();
        String sql = BASE_SEARCH_SQL + "AND d.name LIKE ?";

        try (Connection con = DbConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ListingStatus.AVAILABLE.name());
            ps.setString(2, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowWithDepartment(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


}
