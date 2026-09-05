# BookMyTicket CLI

A terminal-based train ticket booking system written in Java.

## Features
- **User Authentication:** Sign up (hashed passwords via BCrypt) and secure login.
- **Search:** Find available trains based on origin and sequential destination.
- **Bookings:** View historical bookings and safely cancel active tickets.
- **Persistence:** Local JSON file storage.

## Low-Level Design (LLD) Structure
- **Entities:** Clean Java classes (`User`, `Train`, `Ticket`) defining object schema.
- **Services:** Business logic for operations (`UserBookingService`, `TrainService`).
- **Utils:** Helpers containing reusable functions (e.g., hash validations).
- **App:** The main CLI presentation layer.

## Testing & Code Coverage
**Unit tests are heavily implemented for all core functionalities covering authentication, route searching, and booking lifecycles.** Tests use isolated storage arrays to guarantee safe executions.

You can run the full test suite and automatically generate a Code Coverage report with this command:
```bash
./gradlew test jacocoTestReport
```

## Setup & Execution

1. **Clone the Repository**
   ```bash
   git clone <repository_url>
   cd bookMyTicket
   ```

2. **Build the Project**
   ```bash
   ./gradlew build
   ```

3. **Run the Application**
   Interact with the CLI menu directly in your terminal.
   ```bash
   ./gradlew run
   ```
