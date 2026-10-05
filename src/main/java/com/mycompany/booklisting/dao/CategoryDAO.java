package com.mycompany.booklisting.dao;
import com.mycompany.booklisting.config.DbConnectionManager;
import com.mycompany.booklisting.models.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    private static final String SELECT_ALL =
            "SELECT * FROM categories";

    private static final String SELECT_BY_ID =
            "SELECT * FROM categories WHERE category_id=?";

    private static final String INSERT =
            "INSERT INTO categories(name) VALUES(?)";

    private static final String UPDATE =
            "UPDATE categories SET name=? WHERE category_id=?";

    private static final String DELETE =
            "DELETE FROM categories WHERE category_id=?";

    public List<Category> findAll() {
        List<Category> list = new ArrayList<>();

        try (Connection con = DbConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Category.Builder()
                        .categoryId(rs.getInt("category_id"))
                        .name(rs.getString("name"))
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                        .build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Category findById(int id) {
        try (Connection con = DbConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Category.Builder()
                        .categoryId(id)
                        .name(rs.getString("name"))
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void create(String name) {
        try (Connection con = DbConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {

            ps.setString(1, name);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(int id, String name) {
        try (Connection con = DbConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {

            ps.setString(1, name);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (Connection con = DbConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
