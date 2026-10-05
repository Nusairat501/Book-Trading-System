package com.mycompany.booklisting.controllers;

import com.mycompany.booklisting.dao.ConversationDAO;
import com.mycompany.booklisting.models.Conversation;
import com.mycompany.booklisting.models.Message;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ConversationController", urlPatterns = {"/conversations"})
public class ConversationController extends HttpServlet {

    private ConversationDAO conversationDAO;

    @Override
    public void init() {
        conversationDAO = new ConversationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int userId = (int) req.getSession().getAttribute("userId");

        List<Conversation> conversations = conversationDAO.getAllByUserId(userId);
        req.setAttribute("conversations", conversations);

        String conversationIdParam = req.getParameter("conversationId");
        if (conversationIdParam != null) {
            int conversationId = Integer.parseInt(conversationIdParam);

            List<Message> messages =
                    conversationDAO.getMessagesByConversationId(conversationId);

            req.setAttribute("messages", messages);
            req.setAttribute("activeConversationId", conversationId);
        }

        req.getRequestDispatcher("/student/conversations.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int userId = (int) req.getSession().getAttribute("userId");
        String action = req.getParameter("action");

        if ("start".equals(action)) {
            int listingId = Integer.parseInt(req.getParameter("listingId"));
            int proposerId = Integer.parseInt(req.getParameter("proposerId"));

            int conversationId =
                    conversationDAO.createConversationIfNotExists(listingId, proposerId);

            resp.sendRedirect("conversations?conversationId=" + conversationId);
            return;
        }

        if ("send".equals(action)) {
            int conversationId = Integer.parseInt(req.getParameter("conversationId"));
            int receiverId = Integer.parseInt(req.getParameter("receiverId"));
            String content = req.getParameter("content");

            conversationDAO.insertMessage(
                    conversationId,
                    userId,
                    receiverId,
                    content
            );

            resp.sendRedirect("conversations?conversationId=" + conversationId);
        }
    }
}
