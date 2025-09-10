Smart Healthcare Appointment System
The Smart Healthcare Appointment System is a Spring Boot 3 application designed to help a hospital manage patients, doctors, and appointments. It also handles prescriptions and medical records. The system integrates core Spring concepts like Bean Lifecycle, IoC, DI, AOP, and Security. It uses a dual-database approach, leveraging both relational databases (MySQL/Postgres) and a NoSQL database (MongoDB).


Features
Functional Requirements

Authentication & Authorization: Users log in using Basic Auth or JWT and are assigned one of three roles: Admin, Doctor, or Patient. Role-based access controls ensure that each user can only perform specific actions.

User Management: An Admin can manage doctors and patients. Specifically, an Admin can add, update, and remove doctors and register new patients. Patients can also update their own personal details.

Appointment Management: Patients can book or cancel appointments. The system prevents double-booking for the same doctor and time. Doctors can also mark appointments as completed.

Prescriptions & Medical Records: Doctors can add prescriptions, which are stored in MongoDB. Patients can view their prescription history and medical records, which may include notes and lab results.

Logging & Testing: Spring AOP is used to log events like appointment booking and prescription updates. The system is tested using JUnit and Mockito to ensure critical logic, such as the double-booking prevention, and CRUD operations for users are working correctly.

Non-Functional Requirements

Security: The system uses role-based authorization and secure endpoints.

Caching: Hibernate's first and second-level caching are used to optimize performance for frequently accessed doctor data.




Project Structure
com.example.demo
 ├─ controller/        
 ├─ entity/              
 ├─ repository/            
 ├─ models/                
 ├─ exception/        
 ├─ security/        
 ├─ server/              
 └─ testutil/               
