🏥 Smart Healthcare Appointment System

A Spring Boot 3 application for managing patients, doctors, appointments, prescriptions, and medical records — built with ❤️ for clean code and modern architecture.

This project demonstrates core Spring concepts (IoC, DI, AOP, Security, Bean Lifecycle, Testing) and integrates two database worlds:

Relational DB (MySQL/Postgres with JPA/Hibernate) for doctors, patients, appointments

NoSQL DB (MongoDB) for prescriptions & medical records

✨ Features
🔐 Authentication & Authorization

Login via Basic Auth or JWT

Roles: Admin, Doctor, Patient

Role-based access:

Admin → manage doctors & patients

Doctor → manage appointments & prescriptions

Patient → book/cancel appointments, view records

👩‍⚕️ User & Doctor Management

Admin adds/updates/removes doctors

Patients can search doctors by specialty

Admin registers new patients

Patients update their own details

📅 Appointment Management

Patients can book / cancel appointments

Prevents double booking

Doctors can mark appointments as completed

💊 Prescriptions & Medical Records

Doctors add prescriptions → stored in MongoDB

Patients view prescription history & lab records

📝 Logging & Testing

Spring AOP logs booking, cancellation, and prescription updates

JUnit + Mockito test:

double-booking prevention

CRUD APIs for patients & doctors

⚙️ Non-Functional Goodies

Security → Spring Security role-based authorization

Caching → Hibernate 1st & 2nd-level caching for doctor data

📚 Example Use Cases

Patient Books Appointment

Patient logs in → searches doctor → books available slot

System prevents double booking → logs via AOP

Doctor Adds Prescription

Doctor selects patient → adds medicines & notes

Stored in MongoDB → instantly viewable by patient

Admin Adds Doctor

Admin registers new doctor

Data saved in relational DB → cached list refreshed

🏗️ Project Structure
com.example.demo
 ├─ controller/       # REST Controllers
 ├─ entity/           # JPA Entities
 ├─ repository/       # JPA Repositories
 ├─ models/           # DTOs & API Models
 ├─ exception/        # Custom Exceptions
 ├─ security/         # Spring Security Config
 ├─ server/           # Server Config
 ├─ service/          # Business Logic Layer
 └─ testutil/         # Utilities for Testing

🚀 Getting Started
Prerequisites

Java 17+

Maven 3.9+

MySQL / Postgres

MongoDB

Run the App
./mvnw clean spring-boot:run -DskipTests

🧪 Tech Stack

Backend: Spring Boot 3

Databases: MySQL/Postgres + MongoDB

Security: Spring Security (JWT, Basic Auth)

Testing: JUnit 5, Mockito

Logging: Spring AOP