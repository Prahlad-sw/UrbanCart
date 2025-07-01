🛒 Spring Boot Store Application with JWT Authentication
This is a full-stack-ready backend store application built using Spring Boot, implementing JWT (JSON Web Token) authentication and role-based access control for two primary user roles: Consumer and Supplier.

🔐 Features
JWT Authentication (Login, Token Generation, Token Validation)
Role-based Access Control

Consumer – Browse and purchase products, add product to cart

Supplier – Manage and supply products , add product

Secure Login API

RESTful Endpoints for:

User Registration

User Login

Product Management (CRUD)

Role-specific operations

🚀 Tech Stack
Spring Boot
Spring Security
JWT
MySQL
Postman
Maven
JPA / Hibernate

📁 Project Structure
pgsql
Copy
Edit
src/
├── config/         → Security configuration and JWT filters
├── controller/     → REST API controllers (Login, Consumer, Supplier,Public)
├── model/          → Entity and DTO classes
├── repository/     → JPA Repositories
├── service/        → Business logic and authentication services
└── util/           → JWT utilities and helpers

🔧 Setup & Run
Clone the repository:

bash
Copy
Edit
git clone https://github.com/your-username/springboot-store-jwt.git
Configure database in application.properties

Run the app using your IDE or:

arduino
Copy
Edit
mvn spring-boot:run
📫 Endpoints Preview
Endpoint	Method	Description	Access Role
/api/auth/login	POST	Login with JWT issuance	Public
/api/consumer/...	GET/POST	Consumer-specific actions	Consumer
/api/supplier/...	GET/POST	Supplier-specific actions	Supplier
