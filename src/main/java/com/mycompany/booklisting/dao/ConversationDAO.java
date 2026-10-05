package com.mycompany.booklisting.dao;

import com.mycompany.booklisting.config.DbConnectionManager;
import com.mycompany.booklisting.models.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConversationDAO {


    private static final String INSERT_CONVERSATION_SQL =
            "INSERT INTO conversations (listing_id, proposer_id, created_at) VALUES (?, ?, ?)";

    private static final String SELECT_EXISTING_CONVERSATION_SQL =
            "SELECT conversation_id FROM conversations WHERE listing_id = ? AND proposer_id = ?";

    private static final String SELECT_CONVERSATIONS_BY_USER_SQL =
            "SELECT c.conversation_id, c.created_at, " +
                    "l.listing_id, l.title AS listing_title, " +
                    "owner.user_id AS owner_id, owner.name AS owner_name, " +
                    "prop.user_id AS proposer_id, prop.name AS proposer_name " +
                    "FROM conversations c " +
                    "JOIN listings l ON c.listing_id = l.listing_id " +
                    "JOIN users owner ON l.user_id = owner.user_id " +
                    "JOIN users prop ON c.proposer_id = prop.user_id " +
                    "WHERE c.proposer_id = ? OR l.user_id = ? " +
                    "ORDER BY c.created_at DESC";

    private static final String INSERT_MESSAGE_SQL =
            "INSERT INTO messages (conversation_id, sender_id, receiver_id, content, sent_at, is_read) " +
                    "VALUES (?, ?, ?, ?, ?, false)";

    private static final String SELECT_MESSAGES_BY_CONVERSATION_SQL =
            "SELECT m.message_id, m.content, m.sent_at, m.is_read, " +
                    "s.user_id AS sender_id, s.name AS sender_name, " +
                    "r.user_id AS receiver_id, r.name AS receiver_name " +
                    "FROM messages m " +
                    "JOIN users s ON m.sender_id = s.user_id " +
                    "JOIN users r ON m.receiver_id = r.user_id " +
                    "WHERE m.conversation_id = ? " +
                    "ORDER BY m.sent_at";

    public int createConversationIfNotExists(int listingId, int proposerId) {

        try (Connection con = DbConnectionManager.getConnection()) {

            // Check existing conversation
            PreparedStatement check = con.prepareStatement(SELECT_EXISTING_CONVERSATION_SQL);
            check.setInt(1, listingId);
            check.setInt(2, proposerId);

            ResultSet rs = check.executeQuery();
            if (rs.next()) {
                return rs.getInt("conversation_id");
            }

            // Create new conversation
            PreparedStatement ps = con.prepareStatement(
                    INSERT_CONVERSATION_SQL,
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1, listingId);
            ps.setInt(2, proposerId);
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }


    public List<Conversation> getAllByUserId(int userId) {
        List<Conversation> conversations = new ArrayList<>();

        try (Connection con = DbConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_CONVERSATIONS_BY_USER_SQL)) {

            ps.setInt(1, userId);
            ps.setInt(2, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                User owner = new User.Builder()
                        .userId(rs.getInt("owner_id"))
                        .name(rs.getString("owner_name"))
                        .build();

                User proposer = new User.Builder()
                        .userId(rs.getInt("proposer_id"))
                        .name(rs.getString("proposer_name"))
                        .build();

                Listing listing = new Listing.Builder()
                        .listingId(rs.getInt("listing_id"))
                        .title(rs.getString("listing_title"))
                        .user(owner)
                        .build();

                Conversation conversation = new Conversation.Builder()
                        .conversationId(rs.getInt("conversation_id"))
                        .listing(listing)
                        .proposer(proposer)
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .build();

                conversations.add(conversation);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conversations;
    }


    public void insertMessage(int conversationId, int senderId, int receiverId, String content) {
        try (Connection con = DbConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_MESSAGE_SQL)) {

            ps.setInt(1, conversationId);
            ps.setInt(2, senderId);
            ps.setInt(3, receiverId);
            ps.setString(4, content);
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Message> getMessagesByConversationId(int conversationId) {
        List<Message> messages = new ArrayList<>();

        try (Connection con = DbConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_MESSAGES_BY_CONVERSATION_SQL)) {

            ps.setInt(1, conversationId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                User sender = new User.Builder()
                        .userId(rs.getInt("sender_id"))
                        .name(rs.getString("sender_name"))
                        .build();

                User receiver = new User.Builder()
                        .userId(rs.getInt("receiver_id"))
                        .name(rs.getString("receiver_name"))
                        .build();

                Message message = new Message.Builder()
                        .messageId(rs.getInt("message_id"))
                        .sender(sender)
                        .receiver(receiver)
                        .content(rs.getString("content"))
                        .sentAt(rs.getTimestamp("sent_at").toLocalDateTime())
                        .isRead(rs.getBoolean("is_read"))
                        .build();

                messages.add(message);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }
}
