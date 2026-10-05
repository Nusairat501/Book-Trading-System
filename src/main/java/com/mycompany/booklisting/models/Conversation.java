package com.mycompany.booklisting.models;

import java.time.LocalDateTime;

public class Conversation {

    private int conversationId;
    private Listing listing;
    private User proposer;
    private LocalDateTime createdAt;


    public Conversation() {
    }
    private Conversation(Builder builder) {
        this.conversationId = builder.conversationId;
        this.listing = builder.listing;
        this.proposer = builder.proposer;
        this.createdAt = builder.createdAt;
    }

    public int getConversationId() {
        return conversationId;
    }

    public Listing getListing() {
        return listing;
    }

    public User getProposer() {
        return proposer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getOtherUser(int currentUserId) {
        if (listing != null && listing.getUser().getUserId() == currentUserId) {
            return proposer;
        }
        return listing.getUser();
    }

    public boolean isOwner(int currentUserId) {
        return listing != null && listing.getUser().getUserId() == currentUserId;
    }

    public static class Builder {

        private int conversationId;
        private Listing listing;
        private User proposer;
        private LocalDateTime createdAt;

        public Builder conversationId(int conversationId) {
            this.conversationId = conversationId;
            return this;
        }

        public Builder listing(Listing listing) {
            this.listing = listing;
            return this;
        }

        public Builder proposer(User proposer) {
            this.proposer = proposer;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Conversation build() {
            return new Conversation(this);
        }
    }
}
