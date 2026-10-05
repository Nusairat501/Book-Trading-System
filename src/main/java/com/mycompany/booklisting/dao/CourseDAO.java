
package com.mycompany.booklisting.dao;
import com.mycompany.booklisting.models.Course;
import com.mycompany.booklisting.config.DbConnectionManager;
import com.mycompany.booklisting.models.Department;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    private static final String INSERT_SQL = "INSERT INTO courses (code, department_id) VALUES (?, ?)";
    private static final String UPDATE_SQL = "UPDATE courses SET code=?, department_id=? WHERE course_id=?";
    private static final String DELETE_SQL = "DELETE FROM courses WHERE course_id=?";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM courses WHERE course_id=?";

    public void addCourse(Course course) {
        try (Connection conn = DbConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, course.getCode());
            if (course.getDepartmentId() != null) ps.setInt(2, course.getDepartmentId());
            else ps.setNull(2, Types.INTEGER);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) course.setCourseId(rs.getInt(1));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void updateCourse(Course course) {
        try (Connection conn = DbConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, course.getCode());
            if (course.getDepartmentId() != null) ps.setInt(2, course.getDepartmentId());
            else ps.setNull(2, Types.INTEGER);
            ps.setInt(3, course.getCourseId());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void deleteCourse(int courseId) {
        try (Connection conn = DbConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
            ps.setInt(1, courseId);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public Course getCourseById(int courseId) {
        try (Connection conn = DbConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {
            ps.setInt(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Course.Builder()
                            .courseId(rs.getInt("course_id"))
                            .code(rs.getString("code"))
                            .departmentId(rs.getInt("department_id"))
                            .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                            .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                            .build();
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

   public List<Course> getAllCourses() {
    List<Course> list = new ArrayList<>();
    String sql = "SELECT c.course_id, c.code, c.department_id, c.created_at, c.updated_at, d.name as department_name " +
                 "FROM courses c  JOIN departments d ON c.department_id = d.department_id";
    try (Connection conn = DbConnectionManager.getConnection();
         Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery(sql)) {
        while (rs.next()) {
            Department dept = null;
            Integer deptId = rs.getObject("department_id") != null ? rs.getInt("department_id") : null;
            if (deptId != null) {
                dept = new Department(deptId, rs.getString("department_name"));
            }
            list.add(new Course.Builder()
                    .courseId(rs.getInt("course_id"))
                    .code(rs.getString("code"))
                    .department(dept)
                    .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                    .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                    .build());
        }
    } catch (SQLException e) { e.printStackTrace(); }
    return list;
}

}

