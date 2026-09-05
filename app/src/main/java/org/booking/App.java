package org.booking;

import org.booking.entities.Train;
import org.booking.entities.User;
import org.booking.service.TrainService;
import org.booking.service.UserBookingService;
import org.booking.util.UserServiceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class App {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("      Welcome to BookMyTicket App       ");
        System.out.println("========================================");

        Scanner scanner = new Scanner(System.in);
        int option = 0;
        UserBookingService userBookingService;
        TrainService trainService;

        try {
            userBookingService = new UserBookingService();
            trainService = new TrainService();
        } catch (IOException ex) {
            System.out.println("Failed to initialize services: " + ex.getMessage());
            return;
        }

        while (option != 7) {
            System.out.println("\n----------------------------------------");
            System.out.println("Please choose an option:");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. Fetch Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a Seat");
            System.out.println("6. Cancel my Booking");
            System.out.println("7. Exit the App");
            System.out.println("----------------------------------------");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number between 1 and 7.");
                scanner.next();
                continue;
            }

            option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (option) {
                case 1:
                    System.out.println("\n--- Sign Up ---");
                    System.out.print("Enter username: ");
                    String nameToSignUp = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String passwordToSignUp = scanner.nextLine();

                    User userToSignUp = new User(
                            nameToSignUp,
                            passwordToSignUp,
                            UserServiceUtil.hashPassword(passwordToSignUp),
                            new ArrayList<>(),
                            UUID.randomUUID().toString()
                    );

                    if (userBookingService.signUp(userToSignUp)) {
                        System.out.println("User registered and logged in successfully!");
                    } else {
                        System.out.println("Failed to register user. Please try again.");
                    }
                    break;

                case 2:
                    System.out.println("\n--- Login ---");
                    System.out.print("Enter username: ");
                    String nameToLogin = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String passwordToLogin = scanner.nextLine();

                    User userToLogin = new User(
                            nameToLogin,
                            passwordToLogin,
                            null,
                            new ArrayList<>(),
                            null
                    );

                    try {
                        userBookingService = new UserBookingService(userToLogin);
                        if (userBookingService.loginUser()) {
                            System.out.println("Login successful! Welcome " + nameToLogin + ".");
                        } else {
                            System.out.println("Invalid username or password.");
                        }
                    } catch (IOException e) {
                        System.out.println("Error during login: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("\n--- Fetching Bookings ---");
                    userBookingService.fetchBooking();
                    break;

                case 4:
                    System.out.println("\n--- Search Trains ---");
                    System.out.print("Enter Source Station: ");
                    String source = scanner.nextLine();
                    System.out.print("Enter Destination Station: ");
                    String destination = scanner.nextLine();

                    List<Train> trains = trainService.searchTrains(source, destination);
                    if (trains.isEmpty()) {
                        System.out.println("No trains found between " + source + " and " + destination + ".");
                    } else {
                        System.out.println("Found " + trains.size() + " train(s):");
                        for (int i = 0; i < trains.size(); i++) {
                            Train t = trains.get(i);
                            System.out.println((i + 1) + ". Train ID: " + t.getTrainId() + " | Train No: " + t.getTrainNumber());
                        }
                    }
                    break;

                case 5:
                    System.out.println("\n--- Book a Seat ---");
                    System.out.println("Search trains first (Option 4) to check availability.");
                    // Booking flow logic can be integrated here
                    break;

                case 6:
                    System.out.println("\n--- Cancel Booking ---");
                    System.out.print("Enter Ticket ID to cancel: ");
                    String ticketId = scanner.nextLine();
                    boolean canceled = userBookingService.cancelBooking(ticketId);
                    if (canceled) {
                        System.out.println("Ticket " + ticketId + " has been cancelled successfully.");
                    }
                    break;

                case 7:
                    System.out.println("\nThank you for using BookMyTicket. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice! Please choose a number from 1 to 7.");
                    break;
            }
        }

        scanner.close();
    }
}
