package com.mycompany.booklisting.controllers;

import com.mycompany.booklisting.dao.CourseDAO;
import com.mycompany.booklisting.models.Course;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "CourseController", urlPatterns = {"/courses"})
public class CourseController extends HttpServlet {

    private CourseDAO courseDAO;

    @Override
    public void init() { courseDAO = new CourseDAO(); }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "create":
                request.getRequestDispatcher("admin/course-create.jsp").forward(request, response);
                break;
            case "edit":
                int courseId = Integer.parseInt(request.getParameter("courseId"));
                Course course = courseDAO.getCourseById(courseId);
                request.setAttribute("course", course);
                request.getRequestDispatcher("admin/course-edit.jsp").forward(request, response);
                break;
            default:
                request.setAttribute("courses", courseDAO.getAllCourses());
                request.getRequestDispatcher("admin/courses-list.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        switch (action) {
            case "create":
                String code = request.getParameter("code");
                String deptIdStr = request.getParameter("departmentId");
                Integer deptId = (deptIdStr != null && !deptIdStr.isEmpty()) ? Integer.parseInt(deptIdStr) : null;
                courseDAO.addCourse(new Course.Builder().code(code).departmentId(deptId).build());
                response.sendRedirect("courses");
                break;
            case "edit":
                int courseId = Integer.parseInt(request.getParameter("courseId"));
                String updatedCode = request.getParameter("code");
                String updatedDeptStr = request.getParameter("departmentId");
                Integer updatedDeptId = (updatedDeptStr != null && !updatedDeptStr.isEmpty()) ? Integer.parseInt(updatedDeptStr) : null;
                courseDAO.updateCourse(new Course.Builder()
                        .courseId(courseId)
                        .code(updatedCode)
                        .departmentId(updatedDeptId)
                        .build());
                response.sendRedirect("courses");
                break;
            case "delete":
                courseDAO.deleteCourse(Integer.parseInt(request.getParameter("courseId")));
                response.sendRedirect("courses");
                break;
        }
    }
}
