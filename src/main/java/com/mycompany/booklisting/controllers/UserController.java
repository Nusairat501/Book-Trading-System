package com.mycompany.booklisting.controllers;

import com.mycompany.booklisting.dao.UserDAO;
import com.mycompany.booklisting.models.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UsersController", urlPatterns = {"/users"})
public class UserController extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("users-list".equalsIgnoreCase(action)) {
            listUsers(request, response);

        } else if ("get-profile".equalsIgnoreCase(action)) {
            getProfile(request, response);

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }


    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<User> users = userDAO.getAllStudents();
        request.setAttribute("users", users);
        request.getRequestDispatcher("admin/users-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("block-user".equalsIgnoreCase(action)) {
            toggleBlock(request, response, true);

        } else if ("unblock-user".equalsIgnoreCase(action)) {
            toggleBlock(request, response, false);

        } else if ("delete-user".equalsIgnoreCase(action)) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            userDAO.deleteUser(userId);
            response.sendRedirect("users?action=users-list");

        } else if ("edit-profile".equalsIgnoreCase(action)) {
            updateProfile(request, response);

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }


    private void toggleBlock(HttpServletRequest request,
            HttpServletResponse response,
            boolean block)
            throws IOException {

        int userId = Integer.parseInt(request.getParameter("userId"));
        userDAO.updateUserBlockStatus(userId, block);
        response.sendRedirect("users?action=users-list");
    }
    private void getProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer userId = (Integer) request.getSession().getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = userDAO.getUserById(userId);

        request.setAttribute("user", user);
        request.getRequestDispatcher("student/profile.jsp").forward(request, response);
    }
    private void updateProfile(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int userId = Integer.parseInt(request.getParameter("userId"));

        User existing = userDAO.getUserById(userId);

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String academicYearStr = request.getParameter("academicYear");

        Integer academicYear = null;
        if (academicYearStr != null && !academicYearStr.isEmpty()) {
            academicYear = Integer.parseInt(academicYearStr);
        }

        User updated = new User.Builder()
                .userId(userId)
                .name(name)
                .email(email)
                .password(password != null && !password.isEmpty()
                        ? password
                        : existing.getPassword())
                .academicYear(academicYear)
                .role(existing.getRole())
                .isBlocked(existing.isBlocked())
                .build();

        userDAO.updateUser(updated);

        response.sendRedirect("users?action=get-profile");
    }

}
