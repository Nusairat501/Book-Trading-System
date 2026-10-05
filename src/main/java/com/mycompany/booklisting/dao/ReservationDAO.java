package com.mycompany.booklisting.dao;

import com.mycompany.booklisting.config.DbConnectionManager;
import com.mycompany.booklisting.constant.ListingStatus;
import com.mycompany.booklisting.constant.ListingType;
import com.mycompany.booklisting.constant.ReservationStatus;
import com.mycompany.booklisting.models.Reservation;
import com.mycompany.booklisting.models.Listing;
import com.mycompany.booklisting.models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    private static final String INSERT_SQL =
            "INSERT INTO reservations (listing_id, buyer_id, reserved_at, status) " +
            "VALUES (?, ?, ?, ?)";

    private static final String UPDATE_STATUS_SQL =
            "UPDATE reservations SET status = ? WHERE reservation_id = ?";

    private static final String SELECT_BY_LISTING_SQL =
            "SELECT r.reservation_id, r.listing_id, r.buyer_id, r.reserved_at, r.status, " +
            "l.title, l.user_id, u.name AS buyer_name " +
            "FROM reservations r " +
            "JOIN listings l ON r.listing_id = l.listing_id " +
            "JOIN users u ON r.buyer_id = u.user_id " +
            "WHERE r.listing_id = ?";

    private static final String SELECT_BY_USER_OR_BUYER_SQL =
            "SELECT r.reservation_id, r.reserved_at, r.status, " +
            "l.listing_id, l.title, l.price, l.status AS listing_status, l.listing_type, " +
            "u.user_id AS seller_id, u.name AS seller_name, " +
            "b.user_id AS buyer_id, b.name AS buyer_name " +
            "FROM reservations r " +
            "JOIN listings l ON r.listing_id = l.listing_id " +
            "JOIN users u ON l.user_id = u.user_id " +
            "JOIN users b ON r.buyer_id = b.user_id " +
            "WHERE l.user_id = ? OR r.buyer_id = ? " +
            "ORDER BY r.reserved_at DESC";


    public void create(Reservation reservation) {
        try (Connection con = DbConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {

            ps.setInt(1, reservation.getListing().getListingId());
            ps.setInt(2, reservation.getBuyer().getUserId());
            ps.setTimestamp(3, Timestamp.valueOf(reservation.getReservedAt()));
            ps.setString(4, reservation.getReservationStatus().name());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(int reservationId, ReservationStatus status) {
        try (Connection con = DbConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_STATUS_SQL)) {

            ps.setString(1, status.name());
            ps.setInt(2, reservationId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Reservation getByListingId(int listingId) {
        try (Connection con = DbConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_LISTING_SQL)) {

            ps.setInt(1, listingId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    Listing listing = new Listing.Builder()
                            .listingId(rs.getInt("listing_id"))
                            .title(rs.getString("title"))
                            .user(new User.Builder()
                                    .userId(rs.getInt("user_id"))
                                    .build())
                            .build();

                    User buyer = new User.Builder()
                            .userId(rs.getInt("buyer_id"))
                            .name(rs.getString("buyer_name"))
                            .build();

                    return new Reservation.Builder()
                            .reservationId(rs.getInt("reservation_id"))
                            .listing(listing)
                            .buyer(buyer)
                            .reservationStatus(ReservationStatus.valueOf(rs.getString("status")))
                            .reservedAt(rs.getTimestamp("reserved_at").toLocalDateTime())
                            .build();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Reservation> getByUserOrBuyerId(int userId) {
        List<Reservation> list = new ArrayList<>();

        try (Connection con = DbConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_USER_OR_BUYER_SQL)) {

            ps.setInt(1, userId);
            ps.setInt(2, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    User seller = new User.Builder()
                            .userId(rs.getInt("seller_id"))
                            .name(rs.getString("seller_name"))
                            .build();

                    User buyer = new User.Builder()
                            .userId(rs.getInt("buyer_id"))
                            .name(rs.getString("buyer_name"))
                            .build();

                    Listing listing = new Listing.Builder()
                            .listingId(rs.getInt("listing_id"))
                            .title(rs.getString("title"))
                            .price(rs.getDouble("price"))
                            .status(ListingStatus.valueOf(rs.getString("listing_status")))
                            .listingType(ListingType.valueOf(rs.getString("listing_type")))
                            .user(seller)
                            .build();

                    Reservation reservation = new Reservation.Builder()
                            .reservationId(rs.getInt("reservation_id"))
                            .listing(listing)
                            .buyer(buyer)
                            .reservationStatus(ReservationStatus.valueOf(rs.getString("status")))
                            .reservedAt(rs.getTimestamp("reserved_at").toLocalDateTime())
                            .build();

                    list.add(reservation);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
