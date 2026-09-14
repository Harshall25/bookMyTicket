# BookMyTicket

A terminal-based train ticket booking system written in Java, built with clean architecture and comprehensive test coverage.

[![CodeFactor](https://www.codefactor.io/repository/github/harshall25/bookmyticket/badge)](https://www.codefactor.io/repository/github/harshall25/bookmyticket)

---

## Features

| Feature | Description |
|---|---|
| **Authentication** | Sign up & login with BCrypt-hashed passwords |
| **Train Search** | Find available trains by origin and destination |
| **Booking Management** | View booking history and cancel active tickets |
| **Persistence** | Local JSON file storage — no database required |

---

## Project Structure

```
bookMyTicket/
├── app/
│   └── src/
│       ├── main/java/
│       │   ├── entities/      # User, Train, Ticket models
│       │   ├── services/      # UserBookingService, TrainService
│       │   ├── utils/         # Helpers (hash validation, etc.)
│       │   └── App.java       # CLI entry point
│       └── test/java/         # Unit tests
├── gradle/
├── gradlew
└── settings.gradle
```

### Layer Overview

- **Entities** — Plain Java classes defining the core data schema (`User`, `Train`, `Ticket`)
- **Services** — Business logic layer handling authentication, search, and booking operations
- **Utils** — Reusable helpers such as password hashing and validation
- **App** — CLI presentation layer; the main entry point for user interaction

---

## Getting Started

### Prerequisites

- Java 11+
- Gradle (wrapper included — no separate install needed)

### 1. Clone the Repository

```bash
git clone https://github.com/Harshall25/bookMyTicket.git
cd bookMyTicket
```

### 2. Build the Project

```bash
./gradlew build
```

### 3. Run the Application

```bash
./gradlew run
```

The CLI menu will launch in your terminal. Follow the prompts to sign up, search for trains, and manage bookings.

---

## Testing & Code Coverage

Unit tests cover all core functionalities — authentication, route searching, and the full booking lifecycle. Tests use isolated in-memory storage to guarantee safe, reproducible executions.

**Run the full test suite:**

```bash
./gradlew test
```

**Run tests and generate a JaCoCo coverage report:**

```bash
./gradlew test jacocoTestReport
```

The HTML coverage report will be generated at:

```
app/build/reports/jacoco/test/html/index.html
```

---

## Tech Stack

- **Language:** Java
- **Build Tool:** Gradle
- **Password Hashing:** BCrypt
- **Storage:** JSON files
- **Testing:** JUnit + JaCoCo

---

## Contributing

1. Fork the repo
2. Create a feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes
4. Push and open a Pull Request

---

## License

This project is open source. Feel free to use, modify, and distribute.
