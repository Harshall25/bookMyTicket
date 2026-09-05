package org.booking.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.booking.entities.User;
import org.booking.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class responsible for handling user lifecycle, authentication,
 * and ticket reservation/cancellation management.
 */
public class UserBookingService {

    private User user;
    private List<User> userList;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private String usersPath = "app/src/main/java/org/booking/localdb/users.json";

    public UserBookingService() throws IOException {
        loadUsers();
    }

    public UserBookingService(User user) throws IOException {
        this.user = user;
        loadUsers();
    }

    public UserBookingService(User user, String customPath) throws IOException {
        this.user = user;
        this.usersPath = customPath;
        loadUsers();
    }

    private void loadUsers() throws IOException {
        File users = new File(usersPath);
        if (!users.exists() && usersPath.equals("app/src/main/java/org/booking/localdb/users.json")) {
            users = new File("src/main/java/org/booking/localdb/users.json");
        }
        if (users.exists() && users.length() > 0) {
            userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});
        } else {
            userList = new ArrayList<>();
        }
    }

    /**
     * Authenticates the user by verifying credentials against stored records.
     * Uses BCrypt password hash verification.
     *
     * @return true if credentials are valid and user session is established, false otherwise.
     */
    public Boolean loginUser() {
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equalsIgnoreCase(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();

        if (foundUser.isPresent()) {
            this.user = foundUser.get();
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * Registers a new user and persists the updated user database to JSON storage.
     *
     * @param user1 The user instance to register.
     * @return true if registration and file persistence succeeded, false otherwise.
     */
    public Boolean signUp(User user1) {
        try {
            userList.add(user1);
            saveUserListToFile();
            this.user = user1;
            return Boolean.TRUE;
        } catch (IOException e) {
            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile() throws IOException {
        File usersFile = new File(usersPath);
        if (!usersFile.exists() && usersPath.equals("app/src/main/java/org/booking/localdb/users.json")) {
            usersFile = new File("src/main/java/org/booking/localdb/users.json");
        }
        objectMapper.writeValue(usersFile, userList);
    }

    public void fetchBooking() {
        if (user != null) {
            user.printTickets();
        } else {
            System.out.println("Please login first to view bookings.");
        }
    }

    public void fetchBooking(User user) {
        user.printTickets();
    }

    /**
     * Cancels a booking identified by ticket ID for the currently authenticated user
     * and synchronizes the change to local JSON storage.
     *
     * @param ticketId Unique identifier of the ticket to cancel.
     * @return true if the ticket was found and removed, false otherwise.
     */
    public boolean cancelBooking(String ticketId) {
        if (user == null) {
            System.out.println("Please login first to cancel tickets.");
            return false;
        }

        if (ticketId == null || ticketId.isEmpty()) {
            System.out.println("Ticket ID cannot be null or empty.");
            return false;
        }

        if (user.getTicketsBooked() == null || user.getTicketsBooked().isEmpty()) {
            System.out.println("No tickets found for this user.");
            return false;
        }

        boolean isRemoved = user.getTicketsBooked().removeIf(ticket -> ticketId.equals(ticket.getTicketId()));

        if (isRemoved) {
            try {
                saveUserListToFile();
                return true;
            } catch (IOException e) {
                System.out.println("Failed to save changes: " + e.getMessage());
                return false;
            }
        } else {
            System.out.println("No ticket found with ID: " + ticketId);
            return false;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
