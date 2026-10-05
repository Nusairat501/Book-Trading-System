package com.mycompany.booklisting.dao;

import com.mycompany.booklisting.config.DbConnectionManager;
import com.mycompany.booklisting.constant.ExchangeProposalStatus;
import com.mycompany.booklisting.constant.ListingStatus;
import com.mycompany.booklisting.models.ExchangeProposal;
import com.mycompany.booklisting.models.Listing;
import com.mycompany.booklisting.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExchangeProposalDAO {

    private static final String INSERT_SQL =
            "INSERT INTO exchange_proposals (requester_id, target_listing_id, offered_listing_id, status, created_at) " +
            "VALUES (?, ?, ?, ?, ?)";

    private static final String SELECT_BY_TARGET_SQL =
            "SELECT ep.proposal_id, ep.requester_id, ep.target_listing_id, ep.offered_listing_id, ep.status, ep.created_at, " +
            "r.name AS requester_name " +
            "FROM exchange_proposals ep " +
            "JOIN users r ON ep.requester_id = r.user_id " +
            "WHERE ep.target_listing_id = ?";

    private static final String SELECT_BY_USER_SQL =
            "SELECT ep.proposal_id, ep.requester_id, r.name AS requester_name, " +
                    "ep.target_listing_id, lt.title AS target_title, lt.status AS target_status, lu.user_id AS target_owner_id, lu.name AS target_username, " +
                    "ep.offered_listing_id, lo.title AS offered_title, lo.status AS offered_status, lo.user_id AS offered_owner_id, " +
                    "ep.status AS proposal_status, ep.created_at " +
                    "FROM exchange_proposals ep " +
                    "JOIN users r ON ep.requester_id = r.user_id " +
                    "JOIN listings lt ON ep.target_listing_id = lt.listing_id " +
                    "JOIN listings lo ON ep.offered_listing_id = lo.listing_id " +
                    "JOIN users lu ON lt.user_id = lu.user_id " +
                    "WHERE ep.requester_id = ? OR lt.user_id = ? " +
                    "ORDER BY ep.created_at DESC";

    public List<ExchangeProposal> getAllByUser(int userId) {
        List<ExchangeProposal> proposals = new ArrayList<>();

        try (Connection con = DbConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_USER_SQL)) {

            ps.setInt(1, userId);
            ps.setInt(2, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User requester = new User.Builder()
                            .userId(rs.getInt("requester_id"))
                            .name(rs.getString("requester_name"))
                            .build();

                    Listing targetListing = new Listing.Builder()
                            .listingId(rs.getInt("target_listing_id"))
                            .title(rs.getString("target_title"))
                            .status(ListingStatus.valueOf(rs.getString("target_status")))
                            .user(new User.Builder()
                                    .userId(rs.getInt("target_owner_id"))
                                    .name(rs.getString("target_username"))
                                    .build())
                            .build();

                    Listing offeredListing = new Listing.Builder()
                            .listingId(rs.getInt("offered_listing_id"))
                            .title(rs.getString("offered_title"))
                            .status(ListingStatus.valueOf(rs.getString("offered_status")))
                            .user(new User.Builder()
                                    .userId(rs.getInt("offered_owner_id"))
                                    .build())
                            .build();

                    ExchangeProposal proposal = new ExchangeProposal.Builder()
                            .proposalId(rs.getInt("proposal_id"))
                            .requester(requester)
                            .targetListing(targetListing)
                            .offeredListing(offeredListing)
                            .status(ExchangeProposalStatus.valueOf(rs.getString("proposal_status")))
                            .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                            .build();

                    proposals.add(proposal);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return proposals;
    }

    
    public void create(ExchangeProposal proposal) {
        try (Connection con = DbConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {

            ps.setInt(1, proposal.getRequester().getUserId());
            ps.setInt(2, proposal.getTargetListing().getListingId());
            ps.setInt(3, proposal.getOfferedListing().getListingId());
            ps.setString(4, proposal.getStatus().name());
            ps.setTimestamp(5, Timestamp.valueOf(proposal.getCreatedAt()));
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ExchangeProposal> getByTargetListing(int listingId) {
        List<ExchangeProposal> proposals = new ArrayList<>();

        try (Connection con = DbConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_TARGET_SQL)) {

            ps.setInt(1, listingId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User requester = new User.Builder()
                            .userId(rs.getInt("requester_id"))
                            .name(rs.getString("requester_name"))
                            .build();

                    Listing targetListing = new Listing.Builder()
                            .listingId(rs.getInt("target_listing_id"))
                            .build();

                    Listing offeredListing = new Listing.Builder()
                            .listingId(rs.getInt("offered_listing_id"))
                            .build();

                    ExchangeProposal proposal = new ExchangeProposal.Builder()
                            .proposalId(rs.getInt("proposal_id"))
                            .requester(requester)
                            .targetListing(targetListing)
                            .offeredListing(offeredListing)
                            .status(ExchangeProposalStatus.valueOf(rs.getString("status")))
                            .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                            .build();

                    proposals.add(proposal);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proposals;
    }

    public ExchangeProposal getById(int proposalId) {
        String sql = "SELECT ep.*, r.name AS requester_name, "
                + "tl.user_id AS target_user_id, ol.user_id AS offered_user_id "
                + "FROM exchange_proposals ep "
                + "JOIN users r ON ep.requester_id = r.user_id "
                + "JOIN listings tl ON ep.target_listing_id = tl.listing_id "
                + "JOIN listings ol ON ep.offered_listing_id = ol.listing_id "
                + "WHERE ep.proposal_id = ?";

        try (Connection con = DbConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, proposalId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User requester = new User.Builder()
                            .userId(rs.getInt("requester_id"))
                            .name(rs.getString("requester_name"))
                            .build();

                    Listing targetListing = new Listing.Builder()
                            .listingId(rs.getInt("target_listing_id"))
                            .user(new User.Builder().userId(rs.getInt("target_user_id")).build())
                            .build();

                    Listing offeredListing = new Listing.Builder()
                            .listingId(rs.getInt("offered_listing_id"))
                            .user(new User.Builder().userId(rs.getInt("offered_user_id")).build())
                            .build();

                    return new ExchangeProposal.Builder()
                            .proposalId(rs.getInt("proposal_id"))
                            .requester(requester)
                            .targetListing(targetListing)
                            .offeredListing(offeredListing)
                            .status(ExchangeProposalStatus.valueOf(rs.getString("status")))
                            .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                            .build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateStatus(int proposalId, ExchangeProposalStatus status) {
        String sql = "UPDATE exchange_proposals SET status = ? WHERE proposal_id = ?";
        try (Connection con = DbConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status.name());
            ps.setInt(2, proposalId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
