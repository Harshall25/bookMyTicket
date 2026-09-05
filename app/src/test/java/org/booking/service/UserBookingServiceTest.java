package org.booking.service;

import org.booking.entities.Ticket;
import org.booking.entities.User;
import org.booking.util.UserServiceUtil;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;

import static org.testng.Assert.*;

public class UserBookingServiceTest {

    private User testUser;
    private UserBookingService userBookingService;
    private File tempUserFile;

    @BeforeMethod
    public void setUp() throws IOException {
        tempUserFile = File.createTempFile("test_users", ".json");
        tempUserFile.deleteOnExit();
        Files.writeString(tempUserFile.toPath(), "[]");

        testUser = new User();
        testUser.setName("JohnDoe");
        testUser.setPassword("plainPassword123");
        testUser.setHashedPassword(UserServiceUtil.hashPassword("plainPassword123"));
        testUser.setUserId("user-001");
        testUser.setTicketsBooked(new ArrayList<>());

        userBookingService = new UserBookingService(testUser, tempUserFile.getAbsolutePath());
    }

    @Test(testName = "Cancel Booking - Success", description = "Verifies cancelBooking removes the matching ticket from user's booked tickets list and returns true")
    public void testCancelBookingSuccess() {
        Ticket ticket1 = new Ticket();
        ticket1.setTicketId("TCK-001");
        ticket1.setSource("Bangalore");
        ticket1.setDestination("Delhi");
        ticket1.setDateOfTravel(new Date());

        Ticket ticket2 = new Ticket();
        ticket2.setTicketId("TCK-002");
        ticket2.setSource("Delhi");
        ticket2.setDestination("Mumbai");
        ticket2.setDateOfTravel(new Date());

        testUser.getTicketsBooked().add(ticket1);
        testUser.getTicketsBooked().add(ticket2);

        assertEquals(testUser.getTicketsBooked().size(), 2);

        boolean isCancelled = userBookingService.cancelBooking("TCK-001");
        assertTrue(isCancelled, "Ticket should be cancelled successfully");
        assertEquals(testUser.getTicketsBooked().size(), 1, "Only 1 ticket should remain");
        assertEquals(testUser.getTicketsBooked().get(0).getTicketId(), "TCK-002");
    }

    @Test(testName = "Cancel Booking - Non Existent Ticket", description = "Verifies cancelBooking returns false when ticket ID does not match any booked ticket")
    public void testCancelBookingWithNonExistentTicketId() {
        Ticket ticket = new Ticket();
        ticket.setTicketId("TCK-100");
        testUser.getTicketsBooked().add(ticket);

        boolean isCancelled = userBookingService.cancelBooking("INVALID-ID");
        assertFalse(isCancelled, "Should return false for non-existent ticket ID");
        assertEquals(testUser.getTicketsBooked().size(), 1);
    }

    @Test(testName = "Cancel Booking - Null or Empty Ticket ID", description = "Verifies cancelBooking returns false when null or empty ticket ID is passed")
    public void testCancelBookingWithNullOrEmptyId() {
        boolean isCancelledNull = userBookingService.cancelBooking(null);
        assertFalse(isCancelledNull, "Should return false for null ticket ID");

        boolean isCancelledEmpty = userBookingService.cancelBooking("");
        assertFalse(isCancelledEmpty, "Should return false for empty ticket ID");
    }
}
