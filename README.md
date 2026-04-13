# 🚗 Scalable Car Rental Backend System

A production-grade REST API backend for a car rental platform built with **Spring Boot**, **MySQL**, and **JWT authentication**. Supports role-based access control, vehicle management, and booking workflows with conflict detection.

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 3.2 |
| Security | Spring Security + JWT |
| ORM | Spring Data JPA / Hibernate |
| Database | MySQL 8 |
| Build Tool | Maven |
| Testing | Postman |

---

## 📁 Project Structure

```
src/main/java/com/suyash/carrental/
├── controller/         # REST API endpoints
│   ├── AuthController.java
│   ├── VehicleController.java
│   └── BookingController.java
├── service/            # Service interfaces
├── serviceimpl/        # Business logic implementations
├── repository/         # JPA repositories
├── model/              # JPA entities
├── dto/                # Request/Response DTOs
├── security/           # JWT filter, UserDetailsService
├── config/             # Security configuration
└── exception/          # Custom exceptions + global handler
```

---

## ⚙️ Setup & Run

### Prerequisites
- Java 17+
- MySQL 8+
- Maven 3.6+

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/Suyash1608/car-rental-backend.git
   cd car-rental-backend
   ```

2. **Configure MySQL**

   Create a database (or let the app auto-create it):
   ```sql
   CREATE DATABASE car_rental_db;
   ```

3. **Update `application.properties`**
   ```properties
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   Server starts at: `http://localhost:8080`

---

## 🔐 Authentication

This API uses **JWT (JSON Web Token)** authentication.

1. Register or login to get a token
2. Add the token to all protected requests:
   ```
   Authorization: Bearer <your_token>
   ```

### Roles
| Role | Access |
|------|--------|
| `USER` | Browse vehicles, create/cancel own bookings |
| `ADMIN` | Full access including vehicle management and all bookings |

---

## 📡 API Endpoints

### Auth
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | `/api/auth/register` | Public | Register new user |
| POST | `/api/auth/login` | Public | Login and get JWT token |

### Vehicles
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/vehicles/available` | Public | List all available vehicles |
| GET | `/api/vehicles/available/category/{category}` | Public | Filter by category (SEDAN, SUV, etc.) |
| GET | `/api/vehicles/{id}` | Public | Get vehicle details |
| POST | `/api/vehicles` | ADMIN | Add new vehicle |
| PUT | `/api/vehicles/{id}` | ADMIN | Update vehicle |
| DELETE | `/api/vehicles/{id}` | ADMIN | Delete vehicle |
| GET | `/api/vehicles` | ADMIN | List all vehicles |

### Bookings
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | `/api/bookings` | USER | Create a booking |
| PUT | `/api/bookings/{id}/cancel` | USER | Cancel own booking |
| GET | `/api/bookings/my` | USER | View own bookings |
| GET | `/api/bookings` | ADMIN | View all bookings |
| PUT | `/api/bookings/{id}/complete` | ADMIN | Mark booking as completed |

---

## 📋 Sample Requests

### Register
```json
POST /api/auth/register
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "phone": "9876543210"
}
```

### Create Booking
```json
POST /api/bookings
Authorization: Bearer <token>
{
  "vehicleId": 1,
  "startDate": "2025-05-01",
  "endDate": "2025-05-05"
}
```

---

## ✅ Key Features

- **JWT Authentication** with role-based access control (USER / ADMIN)
- **Conflict detection** — prevents double booking of the same vehicle
- **Automatic total calculation** based on days × price per day
- **Centralized exception handling** with meaningful error responses
- **Bean Validation** on all request DTOs
- **Transactional** booking operations to ensure data consistency

---

## 📬 Postman Collection

Import `Car-Rental-API.postman_collection.json` from the root of this repository into Postman to test all endpoints.

---

## 👤 Author

**Suyash Gupta** — Java Backend Developer  
[LinkedIn](https://linkedin.com/in/suyash-16d08m/) | [GitHub](https://github.com/Suyash1608)
