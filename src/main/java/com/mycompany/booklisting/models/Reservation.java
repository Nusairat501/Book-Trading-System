
package com.mycompany.booklisting.models;
import com.mycompany.booklisting.constant.ReservationStatus;
import java.time.LocalDateTime;

public class Reservation {

    private int reservationId;
    private Listing listing;
    private User buyer;
    private LocalDateTime reservedAt;
    private ReservationStatus reservationStatus;

    private Reservation(Builder builder) {
        this.reservationId = builder.reservationId;
        this.listing = builder.listing;
        this.buyer = builder.buyer;
        this.reservedAt = builder.reservedAt;
        this.reservationStatus = builder.reservationStatus;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public int getReservationId() {
        return reservationId;
    }

    public Listing getListing() {
        return listing;
    }

    public User getBuyer() {
        return buyer;
    }

    public LocalDateTime getReservedAt() {
        return reservedAt;
    }

    public static class Builder {
        private int reservationId;
        private Listing listing;
        private User buyer;
        private LocalDateTime reservedAt;
        private ReservationStatus reservationStatus;

        public Builder reservationId(int reservationId) {
            this.reservationId = reservationId;
            return this;
        }

        public Builder listing(Listing listing) {
            this.listing = listing;
            return this;
        }

        public Builder buyer(User buyer) {
            this.buyer = buyer;
            return this;
        }

        public Builder reservedAt(LocalDateTime reservedAt) {
            this.reservedAt = reservedAt;
            return this;
        }
        public Builder reservationStatus(ReservationStatus reservationStatus) {
            this.reservationStatus = reservationStatus;
            return this;
        }
        public Reservation build() {
            return new Reservation(this);
        }
    }
}
