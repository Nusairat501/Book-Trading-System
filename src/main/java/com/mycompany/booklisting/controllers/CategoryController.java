package com.mycompany.booklisting.controllers;

import com.mycompany.booklisting.dao.CategoryDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CategoryController", urlPatterns = {"/categories"})
public class CategoryController extends HttpServlet {

    private CategoryDAO categoryDao;

    @Override
    public void init() {
        categoryDao = new CategoryDAO();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("create".equals(action)) {
            req.getRequestDispatcher("admin/category-create.jsp").forward(req, resp);
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(req.getParameter("categoryId"));
            req.setAttribute("category", categoryDao.findById(id));
            req.getRequestDispatcher("admin/category-edit.jsp").forward(req, resp);
        } else {
            req.setAttribute("categories", categoryDao.findAll());
            req.getRequestDispatcher("admin/categories-list.jsp").forward(req, resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String action = req.getParameter("action");

        if ("create".equals(action)) {
            categoryDao.create(req.getParameter("name"));
        } else if ("edit".equals(action)) {
            categoryDao.update(
                    Integer.parseInt(req.getParameter("categoryId")),
                    req.getParameter("name")
            );
        } else if ("delete".equals(action)) {
            categoryDao.delete(Integer.parseInt(req.getParameter("categoryId")));
        }

        resp.sendRedirect("categories");
    }
}
