package com.mycompany.booklisting.controllers;

import com.mycompany.booklisting.dao.ReservationDAO;
import com.mycompany.booklisting.dao.ExchangeProposalDAO;
import com.mycompany.booklisting.models.Reservation;
import com.mycompany.booklisting.models.ExchangeProposal;
import com.mycompany.booklisting.models.Listing;
import com.mycompany.booklisting.models.User;
import com.mycompany.booklisting.constant.ExchangeProposalStatus;
import com.mycompany.booklisting.constant.ListingStatus;
import com.mycompany.booklisting.constant.ListingType;
import com.mycompany.booklisting.constant.ReservationStatus;
import com.mycompany.booklisting.dao.BookListingDAO;
import com.mycompany.booklisting.dao.CategoryDAO;
import com.mycompany.booklisting.dao.CourseDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "TransactionController", urlPatterns = {"/transactions"})
public class TransactionController extends HttpServlet {

    private ReservationDAO reservationDAO;
    private ExchangeProposalDAO proposalDAO;
    private BookListingDAO listingDAO;

    @Override
    public void init() {
        listingDAO = new BookListingDAO();
        proposalDAO = new ExchangeProposalDAO();
        reservationDAO = new ReservationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("my-reservations".equals(action)) {
            showMyReservations(req, resp);
        } else if ("propose".equals(action)) {
            showExchangePage(req, resp);
        } else if ("my-exchanges".equals(action)) {
            showMyExchangeProposals(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("reserve".equals(action)) {
            reserveListing(req, resp);
        } else if ("exchange-proposal".equals(action)) {
            createExchangeProposal(req, resp);
        } else if ("cancel-reservation".equals(action)) {
            int listingId = Integer.parseInt(req.getParameter("listingId"));
            int reservationId = Integer.parseInt(req.getParameter("reservationId"));
            listingDAO.updateStatus(listingId, ListingStatus.AVAILABLE);
            reservationDAO.updateStatus(reservationId, ReservationStatus.CANCELLED);
            resp.sendRedirect("transactions?action=my-reservations");
        } else if ("mark-sold".equals(action)) {
            int listingId = Integer.parseInt(req.getParameter("listingId"));
            int reservationId = Integer.parseInt(req.getParameter("reservationId"));
            listingDAO.updateStatus(listingId, ListingStatus.SOLD);
            reservationDAO.updateStatus(reservationId, ReservationStatus.SOLD);
            resp.sendRedirect("transactions?action=my-reservations");
        } else if ("accept-exchange".equals(action)) {
            handleAcceptExchange(req, resp);
        } else if ("reject-exchange".equals(action)) {
            handleRejectExchange(req, resp);
        } else if ("complete-exchange".equals(action)) {
            completeExchange(req, resp);
        } else if ("cancel-exchange".equals(action)) {
            cancelExchnage(req, resp);
        }

    }

    private synchronized void reserveListing(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int listingId = Integer.parseInt(req.getParameter("listingId"));
        int buyerId = (int) req.getSession().getAttribute("userId");

        Reservation reservation = new Reservation.Builder()
                .listing(new Listing.Builder().listingId(listingId).build())
                .buyer(new User.Builder().userId(buyerId).build())
                .reservedAt(LocalDateTime.now())
                .reservationStatus(ReservationStatus.RESERVED)
                .build();

        reservationDAO.create(reservation);
        listingDAO.updateStatus(listingId, ListingStatus.RESERVED);
        resp.sendRedirect("transactions?action=my-reservations");
    }

    private synchronized void createExchangeProposal(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int targetListingId = Integer.parseInt(req.getParameter("targetListingId"));
        int offeredListingId = Integer.parseInt(req.getParameter("offeredListingId"));
        int requesterId = (int) req.getSession().getAttribute("userId");

        ExchangeProposal proposal = new ExchangeProposal.Builder()
                .requester(new User.Builder().userId(requesterId).build())
                .targetListing(new Listing.Builder().listingId(targetListingId).build())
                .offeredListing(new Listing.Builder().listingId(offeredListingId).build())
                .status(ExchangeProposalStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        proposalDAO.create(proposal);
        listingDAO.updateStatus(targetListingId, ListingStatus.AVAILABLE);
        resp.sendRedirect("listings?action=my-exchanges");
    }

    private void showMyReservations(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int buyerId = (int) req.getSession().getAttribute("userId");

        req.setAttribute("reservations",
                reservationDAO.getByUserOrBuyerId(buyerId));

        req.getRequestDispatcher("/student/my-reservations.jsp")
                .forward(req, resp);
    }

    private void showExchangePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int targetListingId = Integer.parseInt(req.getParameter("listingId"));
        int userId = (int) req.getSession().getAttribute("userId");

        Listing targetListing = listingDAO.getById(targetListingId);
        req.setAttribute("targetListing", targetListing);

        List<Listing> myExchangeListings = listingDAO.getByUser(userId).stream()
                .filter(l -> l.getStatus() == ListingStatus.AVAILABLE && l.getListingType() == ListingType.EXCHANGE)
                .toList();
        req.setAttribute("myExchangeListings", myExchangeListings);

        req.getRequestDispatcher("/student/exchange-propose.jsp").forward(req, resp);
    }

    private void showMyExchangeProposals(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int userId = (int) req.getSession().getAttribute("userId");
        List<ExchangeProposal> proposals = proposalDAO.getAllByUser(userId);

        req.setAttribute("exchangeProposals", proposals);
        req.setAttribute("currentUserId", userId);

        req.getRequestDispatcher("/student/my-exchange-proposals.jsp").forward(req, resp);
    }

    private void handleAcceptExchange(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int proposalId = Integer.parseInt(req.getParameter("proposalId"));

        ExchangeProposal proposal = proposalDAO.getById(proposalId);
        if (proposal == null) {
            resp.sendRedirect("transactions?action=my-exchanges");
            return;
        }

        int currentUserId = (int) req.getSession().getAttribute("userId");
        if (proposal.getTargetListing().getUser().getUserId() != currentUserId) {
            resp.sendRedirect("transactions?action=my-exchanges");
            return;
        }

        proposalDAO.updateStatus(proposalId, ExchangeProposalStatus.ACCEPTED);

        listingDAO.updateStatus(proposal.getTargetListing().getListingId(), ListingStatus.RESERVED);
        listingDAO.updateStatus(proposal.getOfferedListing().getListingId(), ListingStatus.RESERVED);

        resp.sendRedirect("transactions?action=my-exchanges");
    }

    private void handleRejectExchange(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int proposalId = Integer.parseInt(req.getParameter("proposalId"));

        ExchangeProposal proposal = proposalDAO.getById(proposalId);
        if (proposal == null) {
            resp.sendRedirect("transactions?action=my-exchanges");
            return;
        }

        int currentUserId = (int) req.getSession().getAttribute("userId");
        if (proposal.getTargetListing().getUser().getUserId() != currentUserId) {
            resp.sendRedirect("transactions?action=my-exchanges");
            return;
        }

        proposalDAO.updateStatus(proposalId, ExchangeProposalStatus.REJECTED);

        listingDAO.updateStatus(proposal.getTargetListing().getListingId(), ListingStatus.AVAILABLE);
        listingDAO.updateStatus(proposal.getOfferedListing().getListingId(), ListingStatus.AVAILABLE);

        resp.sendRedirect("transactions?action=my-exchanges");
    }

    private void completeExchange(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int proposalId = Integer.parseInt(req.getParameter("proposalId"));
        ExchangeProposal proposal = proposalDAO.getById(proposalId);
        int userId = (int) req.getSession().getAttribute("userId");

        Listing listingToUpdate = null;

        if (userId == proposal.getTargetListing().getUser().getUserId()) {
            listingToUpdate = proposal.getTargetListing();
        } else if (userId == proposal.getRequester().getUserId()) {
            listingToUpdate = proposal.getOfferedListing();
        }

        if (listingToUpdate != null) {
            listingDAO.updateStatus(listingToUpdate.getListingId(), ListingStatus.EXCHANGED);
        }

        Listing target = listingDAO.getById(proposal.getTargetListing().getListingId());
        Listing offered = listingDAO.getById(proposal.getOfferedListing().getListingId());

        if (target.getStatus() == ListingStatus.EXCHANGED && offered.getStatus() == ListingStatus.EXCHANGED) {
            proposalDAO.updateStatus(proposalId, ExchangeProposalStatus.EXCHANGED);
        }

        resp.sendRedirect("transactions?action=my-exchanges");
    }

    private void cancelExchnage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int proposalId = Integer.parseInt(req.getParameter("proposalId"));

        ExchangeProposal proposal = proposalDAO.getById(proposalId);
        if (proposal == null) {
            resp.sendRedirect("transactions?action=my-exchanges");
            return;
        }

        int currentUserId = (int) req.getSession().getAttribute("userId");
        if (proposal.getTargetListing().getUser().getUserId() != currentUserId) {
            resp.sendRedirect("transactions?action=my-exchanges");
            return;
        }

        proposalDAO.updateStatus(proposalId, ExchangeProposalStatus.CANCELLED);
        listingDAO.updateStatus(proposal.getTargetListing().getListingId(), ListingStatus.AVAILABLE);
        listingDAO.updateStatus(proposal.getOfferedListing().getListingId(), ListingStatus.AVAILABLE);
        resp.sendRedirect("transactions?action=my-exchanges");
    }

}
