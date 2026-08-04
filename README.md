# PawStock Warehouse

## Project Description

PawStock Warehouse is a Spring Boot inventory management system for a pet supply warehouse. The system helps manage pet products, brands, suppliers, inventory levels, distribution centres, customers, and orders.

Our group chose this project because it is a realistic e-commerce and warehouse management system. It gives us clear CRUD features to build, such as adding products, viewing inventory, managing suppliers, and tracking customer orders.

## Group Information

**Group Name:** Super Trio  
**Group Number:** Group 7

## Team Members

- benjaminkle
- beyzay
- samuuca1

## Technologies Used

- Java
- Spring Boot
- Spring Web
- Thymeleaf
- Spring Data JPA
- Spring Security
- Spring Data REST
- Jakarta Validation
- H2 Database
- Bootstrap
- Maven

## Main Features

- Product inventory management
- Product creation, viewing, editing, and deletion
- Product search, filtering, sorting, and pagination
- User registration and login
- Role-based authorization
- Admin user-management dashboard
- Warehouse information page
- Project roadmap page
- About page

## How to Run the Project

1. Clone the repository:

```bash
git clone https://github.com/Samuuca1/PawStock-Warehouse-Group7.git
```

2. Open the project folder:

```bash
cd PawStock-Warehouse-Group7
```

3. Run the project with the Maven wrapper on Windows:

```bash
.\mvnw.cmd spring-boot:run
```

Alternatively, when Maven is installed locally:

```bash
mvn spring-boot:run
```

4. Open the application in a browser:

```text
http://localhost:8080
```

## Deliverable 1 Features

- Product inventory management
- Create, Read, Update, and Delete operations for products
- Product search, filtering, sorting, and pagination
- Brand, Category, and Supplier relationships
- Server-side form validation
- Responsive Bootstrap interface
- H2 in-memory database with sample data

## Deliverable 2 Features

- User registration with Jakarta Validation
- Custom login and logout using Spring Security
- BCrypt password encryption
- Role-based authorization for CUSTOMER, STAFF, and ADMIN users
- Public product browsing
- STAFF and ADMIN permission to create and edit products
- ADMIN permission to delete products
- Admin dashboard for viewing users
- Admin ability to change user roles
- Admin ability to delete user accounts
- Role-aware navbar links
- Custom access-denied page