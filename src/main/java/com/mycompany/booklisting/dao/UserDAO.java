package com.mycompany.booklisting.dao;

import com.mycompany.booklisting.config.DbConnectionManager;
import com.mycompany.booklisting.models.User;
import com.mycompany.booklisting.constant.Role;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static final String INSERT_USER_SQL
            = "INSERT INTO users (name, email, password, academic_year, role, is_blocked) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER_SQL
            = "UPDATE users SET name = ?, email = ?, password = ?, academic_year = ?, role = ?, is_blocked = ? WHERE user_id = ?";
    private static final String DELETE_USER_SQL = "DELETE FROM users WHERE user_id = ?";
    private static final String SELECT_USER_BY_ID_SQL = "SELECT * FROM users WHERE user_id = ?";
    private static final String SELECT_ALL_USERS_SQL = "SELECT * FROM users";
    private static final String SELECT_ALL_STUDENTS_SQL = "SELECT * FROM users where role = 'STUDENT' ";
    private static final String LOGIN_SQL = "SELECT * FROM users WHERE email = ? AND password = ?";

    public void addUser(User user) {
        try (Connection conn = DbConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());

            if (user.getAcademicYear() != null) {
                ps.setInt(4, user.getAcademicYear());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.setString(5, user.getRole().name());
            ps.setBoolean(6, user.isBlocked());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setUserId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {
        try (Connection conn = DbConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_USER_SQL)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());

            if (user.getAcademicYear() != null) {
                ps.setInt(4, user.getAcademicYear());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.setString(5, user.getRole().name());
            ps.setBoolean(6, user.isBlocked());
            ps.setInt(7, user.getUserId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int userId) {
        try (Connection conn = DbConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_USER_SQL)) {

            ps.setInt(1, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(int userId) {
        try (Connection conn = DbConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_USER_BY_ID_SQL)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = DbConnectionManager.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(SELECT_ALL_USERS_SQL)) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<User> getAllStudents() {
        List<User> users = new ArrayList<>();
        try (Connection conn = DbConnectionManager.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(SELECT_ALL_STUDENTS_SQL)) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User login(String email, String password) {
        try (Connection conn = DbConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(LOGIN_SQL)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUserBlockStatus(int userId, boolean blocked) {
        String sql = "UPDATE users SET is_blocked = ? WHERE user_id = ?";

        try (Connection conn = DbConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, blocked);
            ps.setInt(2, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DbConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User.Builder()
                .userId(rs.getInt("user_id"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .academicYear(rs.getObject("academic_year") != null ? rs.getInt("academic_year") : null)
                .role(Role.valueOf(rs.getString("role")))
                .isBlocked(rs.getBoolean("is_blocked"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                .build();
    }

}
