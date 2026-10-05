package com.mycompany.booklisting.controllers;

import com.mycompany.booklisting.dao.UserDAO;
import com.mycompany.booklisting.constant.Role;
import com.mycompany.booklisting.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "AuthController", urlPatterns = {"/auth"})
public class AuthController extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("logout".equalsIgnoreCase(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect("login.jsp");
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("login".equalsIgnoreCase(action)) {
            processLogin(request, response);
        } else if ("register".equalsIgnoreCase(action)) {
            processRegister(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    private void processLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userDAO.login(email, password);

        if (user != null) {
            if (user.isBlocked()) {
                request.setAttribute(
                        "error",
                        "Your account has been blocked by the administrator. Please contact support."
                );
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("name", user.getName());
            session.setAttribute("role", user.getRole().name());

            switch (user.getRole()) {
                case ADMIN:
                    response.sendRedirect("users?action=users-list");
                    break;
                case STUDENT:
                    response.sendRedirect("listings?action=books-list");
                    break;
            }

        } else {
            response.sendRedirect("login.jsp?error=invalid");
        }
    }

    private void processRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String academicYearStr = request.getParameter("academicYear");

        Integer academicYear = null;
        if (academicYearStr != null && !academicYearStr.isEmpty()) {
            academicYear = Integer.parseInt(academicYearStr);
        }

        User newUser = new User.Builder()
                .name(name)
                .email(email)
                .password(password)
                .academicYear(academicYear)
                .role(Role.STUDENT)
                .build();

        userDAO.addUser(newUser);
        HttpSession session = request.getSession();
        session.setAttribute("userId", newUser.getUserId());
        session.setAttribute("name", newUser.getName());
        session.setAttribute("role", newUser.getRole().name());

        response.sendRedirect("login.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Authentication Controller for login, registration, and logout";
    }
}
