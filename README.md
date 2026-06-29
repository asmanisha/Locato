# Locato - Location-Based Venue Booking Platform

## 📌 Overview
Locato is a full-stack web application that allows users to discover and book hotels, cafeterias, and theatres in specific locations (Vadalur and Neyveli). The platform enables cashless digital payments and QR code-based check-in verification.

## 🚀 Features

### User Features
- **Location-based Discovery**: Browse venues based on your selected location
- **Category Filtering**: Filter venues by Hotels, Cafeterias, or Theatres
- **Menu Browsing**: View cafeteria menus categorized by Breakfast, Lunch, and Beverages
- **Digital Payments**: Secure payments through Razorpay integration (test mode)
- **QR Code Generation**: Unique QR codes for each booking
- **Order History**: View all your past bookings

### Staff Features
- **QR Code Verification**: Scan QR codes to verify bookings
- **Order Management**: View and manage orders
- **Bill Printing**: Print bills for verified orders

## 🛠️ Technology Stack

| Layer | Technology |
|-------|------------|
| **Backend** | Java 17, Spring Boot 3.x |
| **Frontend** | Thymeleaf, Bootstrap 5, JavaScript |
| **Database** | MySQL |
| **ORM** | Spring Data JPA |
| **Payment** | Razorpay (Test Mode) |
| **QR Code** | ZXing Library |
| **Build Tool** | Maven |
| **Version Control** | Git & GitHub |

## 📋 Prerequisites

- Java JDK 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher
- IntelliJ IDEA (or any IDE of your choice)

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/locato.git
cd locato
```

## 2. Configure Database
```bash 
CREATE DATABASE locato_db;
```
## 3. Update Application Properties
Update src/main/resources/application.properties:
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/locato_db
spring.datasource.username=root
spring.datasource.password=your_password
```

## 4. Build and Run
```bash
mvn clean install
mvn spring-boot:run
```
## 5. Access Application
Open your browser and go to: http://localhost:8080

## 🔄 Workflow
User Login: User enters name and selects location

Dashboard: View venues based on location

Filter Venues: Filter by category (Hotel/Cafeteria/Theatre)

Select Venue: Click on a venue to view details

For Cafeterias: Browse menu, add items to cart

Checkout: Review cart and proceed to payment

Payment: Razorpay popup for digital payment

Success: QR code generated for booking

Staff Verification: Scan QR code to verify and print bill

## 🤝 Contributing
Fork the repository

Create your feature branch (git checkout -b feature/AmazingFeature)

Commit your changes (git commit -m 'Add some AmazingFeature')

Push to the branch (git push origin feature/AmazingFeature)

Open a Pull Request

## 📝 License
This project is licensed under the MIT License.

## 👥 Developed By
Developer: Manisha S

Project: Locato - Location-Based Venue Booking Platform
