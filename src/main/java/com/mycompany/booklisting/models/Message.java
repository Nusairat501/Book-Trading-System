package com.mycompany.booklisting.models;
import java.time.LocalDateTime;

public class Message {

    private int messageId;
    private Conversation conversation;
    private User sender;
    private User receiver;
    private String content;
    private LocalDateTime sentAt;
    private boolean isRead;

    private Message(Builder builder) {
        this.messageId = builder.messageId;
        this.conversation = builder.conversation;
        this.sender = builder.sender;
        this.receiver = builder.receiver;
        this.content = builder.content;
        this.sentAt = builder.sentAt;
        this.isRead = builder.isRead;
    }

    public int getMessageId() {
        return messageId;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public static class Builder {
        private int messageId;
        private Conversation conversation;
        private User sender;
        private User receiver;
        private String content;
        private LocalDateTime sentAt;
        private boolean isRead = false;

        public Builder messageId(int messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder conversation(Conversation conversation) {
            this.conversation = conversation;
            return this;
        }

        public Builder sender(User sender) {
            this.sender = sender;
            return this;
        }

        public Builder receiver(User receiver) {
            this.receiver = receiver;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder sentAt(LocalDateTime sentAt) {
            this.sentAt = sentAt;
            return this;
        }

        public Builder isRead(boolean isRead) {
            this.isRead = isRead;
            return this;
        }

        public Message build() {
            return new Message(this);
        }

    }
}
