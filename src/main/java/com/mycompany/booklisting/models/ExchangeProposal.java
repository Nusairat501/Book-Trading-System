package com.mycompany.booklisting.models;
import com.mycompany.booklisting.constant.ExchangeProposalStatus;
import java.time.LocalDateTime;

public class ExchangeProposal {

    private int proposalId;
    private User requester;
    private Listing targetListing;
    private Listing offeredListing;
    private ExchangeProposalStatus status;
    private LocalDateTime createdAt;

    private ExchangeProposal(Builder builder) {
        this.proposalId = builder.proposalId;
        this.requester = builder.requester;
        this.targetListing = builder.targetListing;
        this.offeredListing = builder.offeredListing;
        this.status = builder.status;
        this.createdAt = builder.createdAt;
    }

    public int getProposalId() {
        return proposalId;
    }

    public User getRequester() {
        return requester;
    }

    public Listing getTargetListing() {
        return targetListing;
    }

    public Listing getOfferedListing() {
        return offeredListing;
    }

    public ExchangeProposalStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static class Builder {
        private int proposalId;
        private User requester;
        private Listing targetListing;
        private Listing offeredListing;
        private ExchangeProposalStatus status = ExchangeProposalStatus.PENDING;
        private LocalDateTime createdAt;

        public Builder proposalId(int proposalId) {
            this.proposalId = proposalId;
            return this;
        }

        public Builder requester(User requester) {
            this.requester = requester;
            return this;
        }

        public Builder targetListing(Listing targetListing) {
            this.targetListing = targetListing;
            return this;
        }

        public Builder offeredListing(Listing offeredListing) {
            this.offeredListing = offeredListing;
            return this;
        }

        public Builder status(ExchangeProposalStatus status) {
            this.status = status;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ExchangeProposal build() {
            return new ExchangeProposal(this);
        }
    }
}
