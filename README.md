# PawStock Warehouse

## Project Overview

PawStock Warehouse is a Spring Boot inventory management application designed for a pet supply warehouse. The application allows users to browse and manage pet products while keeping product information connected to brands, categories, suppliers, and inventory quantities.

The project includes product CRUD operations, searching, filtering, sorting, pagination, user registration and login, role-based authorization, an admin management area, and separate database configurations for development and MySQL-based production/QA testing.

**Group Name:** The Super Trio  
**Group Number:** Group 7

## Team Members

- `benjaminkle`
- `beyzay`
- `samuuca1`

## Main Features

- Product inventory management
- Create, view, edit, and delete products
- Product quantity tracking
- Search products by keyword
- Filter products by brand, category, and pet type
- Product sorting and pagination
- Brand, category, and supplier relationships
- Server-side form validation
- User registration and custom login/logout
- BCrypt password encryption
- Role-based authorization using `CUSTOMER`, `STAFF`, and `ADMIN` roles
- Public product browsing
- Admin dashboard for user management
- Admin management of brands, categories, and suppliers
- Custom access-denied page
- About, warehouse, and project roadmap pages
- Development profile using H2
- Production/QA-style profile using MySQL and Docker

## Technologies Used

- Java 21
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Data REST
- Spring Security
- Thymeleaf
- Jakarta Validation
- H2 Database
- MySQL 8.4
- Docker
- Bootstrap 5
- Maven / Maven Wrapper

## Requirements

Before running the application, make sure you have:

- Java 21 installed
- Git installed if cloning the repository
- Docker Desktop installed and running when using the MySQL profile
- Port `8080` available for the Spring Boot application
- Port `3306` available when running the MySQL Docker container

The project includes the Maven Wrapper, so a separate Maven installation is not required.

## Clone the Repository

```bash
git clone https://github.com/Samuuca1/PawStock-Warehouse-Group7.git
cd PawStock-Warehouse-Group7
```

---

# Running the Application

PawStock uses Spring profiles so the database configuration can be changed depending on the environment.

The project currently contains:

- `dev` profile — H2 in-memory database
- `prod` profile — MySQL database

The `dev` profile is also configured as the default profile when no profile is specified.

## Development Profile — H2

Use the development profile for local development and testing.

### 1. Run the application

From PowerShell in the project folder, run:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

### 2. Open the application

Open:

```text
http://localhost:8080
```

### 3. H2 Database Information

The development profile uses an in-memory H2 database.

- JDBC URL: `jdbc:h2:mem:pawstockdb`
- Username: `sa`
- Password: leave blank
- H2 Console: `http://localhost:8080/h2-console`

The database tables are recreated when the application starts, and the sample data from `data.sql` is loaded automatically.

Because H2 is an in-memory database, the data is reset when the application is stopped and restarted.

---

## Production / QA Testing — MySQL with Docker

The project uses the `prod` Spring profile for the MySQL environment. This profile can also be used when testing the application against MySQL for QA purposes.

### 1. Start Docker Desktop

Docker Desktop must be running before starting the MySQL container.

### 2. Create the MySQL container

This command only needs to be run once.

Choose your own MySQL root password and replace `<your-password>` below:

```powershell
docker run --name pawstock-mysql `
  -e MYSQL_ROOT_PASSWORD=<your-password> `
  -e MYSQL_DATABASE=pawstockdb `
  -p 3306:3306 `
  -v pawstock-mysql-data:/var/lib/mysql `
  -d mysql:8.4
```

This creates:

- A Docker container named `pawstock-mysql`
- A MySQL database named `pawstockdb`
- A persistent Docker volume named `pawstock-mysql-data`

### 3. Set the required environment variables

In the same PowerShell window, set the database connection variables:

```powershell
$env:DB_HOST="localhost"
$env:DB_PORT="3306"
$env:DB_NAME="pawstockdb"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="<your-password>"
```

The password must match the password used when the Docker container was created.

### 4. Run PawStock with the MySQL profile

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=prod"
```

### 5. Open the application

Open:

```text
http://localhost:8080
```

### Future MySQL Runs

After the MySQL container has already been created, it does not need to be created again.

Start Docker Desktop, then run:

```powershell
docker start pawstock-mysql
```

Set the environment variables again if they are not already available in the current PowerShell session, and then start PawStock with the `prod` profile.

---

## MySQL Schema and Data Behaviour

When the MySQL container is created, the environment variable below creates the database:

```text
MYSQL_DATABASE=pawstockdb
```

The production profile uses Hibernate with:

```text
ddl-auto: update
```

This allows Hibernate/JPA to create or update the required application tables inside `pawstockdb`, so a separate manual schema script is not required for the current project setup.

Development sample data is intentionally disabled in the production profile. The `data.sql` sample records automatically load in H2 development mode but do not repeatedly load into the persistent MySQL database.

The H2 Console is also disabled when using the production profile.

## Environment Variables

The MySQL configuration uses the following environment variables:

| Variable | Example | Purpose |
|---|---|---|
| `DB_HOST` | `localhost` | MySQL server address |
| `DB_PORT` | `3306` | MySQL server port |
| `DB_NAME` | `pawstockdb` | Database name |
| `DB_USERNAME` | `root` | MySQL username |
| `DB_PASSWORD` | your password | MySQL password |

Do not commit real database passwords or other private credentials to GitHub.

---

## User Roles and Permissions

PawStock uses three application roles:

### CUSTOMER

- Browse the product list
- View product details
- Use product search, filters, sorting, and pagination

### STAFF

- Includes product browsing permissions
- Create new products
- Edit existing products

### ADMIN

- Includes STAFF permissions
- Delete products
- Access the admin dashboard
- View and manage application users
- Change user roles
- Delete user accounts
- Manage brands
- Manage categories
- Manage suppliers

## Demo Accounts

The application initializes demonstration accounts when they do not already exist:

| Role | Username | Password |
|---|---|---|
| ADMIN | `admin` | `Admin123!` |
| STAFF | `staff` | `Staff123!` |
| CUSTOMER | `customer` | `Customer123!` |

These accounts are intended for development/course demonstration purposes.

---

## Project Structure

```text
src/main/java/com/pawstock/pawstock_warehouse/
├── config/          # Security and application initialization
├── controllers/     # MVC controllers
├── exception/       # Custom exception handling
├── model/           # JPA entities and form models
├── repository/      # Spring Data repositories
└── service/         # Application business logic

src/main/resources/
├── application.yml
├── application-dev.yml
├── application-prod.yml
├── data.sql
├── static/          # CSS and media files
└── templates/       # Thymeleaf HTML templates
```

---

## Team Contributions

### `benjaminkle`

- Contributed to Deliverable 1 documentation and project setup.
- Implemented major Spring Security functionality for Deliverable 2, including authentication, BCrypt password encryption, registration support, user roles, seeded demo users, and role-based authorization.
- Contributed to admin security and interface improvements.
- Implemented major Deliverable 3 work for database environment profiles and MySQL support.
- Added the MySQL dependency and development/production configuration files.
- Expanded admin management functionality for brands, categories, and suppliers.

### `beyzay`

- Contributed to the application layout, navigation, styling, and page content.
- Updated the project roadmap and About page for the project milestones.
- Improved the login and registration page user interface and experience.
- Contributed to Spring Security integration by updating the application user model and `UserDetails` support.
- Contributed to admin navigation/interface work and general application UI improvements.
- Updated project content to reflect the H2/MySQL database profiles and Docker integration.

### `samuuca1`

- Created the initial PawStock Warehouse project/repository setup.
- Implemented product Update and Delete functionality to complete product CRUD operations.
- Updated the product controller, service, product forms, product details/list views, and related product workflow.
- Expanded `data.sql` with sample warehouse data, including 20 sample products.
- Contributed to Deliverable 2 admin interface and documentation improvements.
- Contributed to the admin dashboard and role-management functionality.
- Updated the README documentation for the current project configuration and run instructions.

---

## Milestones

### Milestone 1 — Completed

- Web front end
- Product CRUD operations
- Validation
- H2 database integration
- Product search, filtering, sorting, and pagination

### Milestone 2 — Completed

- Spring Security
- Registration and login
- BCrypt password encryption
- Role-based authorization
- Admin dashboard and user management

### Milestone 3 — Current

- Hierarchical YAML configuration
- Development and production profiles
- H2 development database
- MySQL persistent database
- Docker integration
- Environment-variable-based MySQL configuration

---

## Notes

- The default Spring profile is `dev`.
- H2 sample data is recreated on each development run.
- MySQL data is persistent because the Docker configuration uses the `pawstock-mysql-data` volume.
- The current project contains a `prod` MySQL profile and does not contain a separate `application-qa.yml` file.
- If a separate `qa` Spring profile is required by the course, an additional QA configuration file should be added instead of documenting a profile that does not currently exist.
