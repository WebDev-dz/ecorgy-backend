# Ecorgy Application - Backend API Documentation

## Overview
This is a Spring Boot backend application for the Ecorgy system, implementing a comprehensive user management and task/product management system with Oracle database integration.

## Architecture
- **Framework**: Spring Boot 3.5.7
- **Database**: Oracle with JDBC
- **ORM**: JPA/Hibernate
- **Build Tool**: Maven
- **Java Version**: 21

## Entities

### User Hierarchy
- **User** (Abstract base class)
  - **Admin**: System administrators with permissions
  - **Seller**: Users who manage products
  - **Worker**: Users who perform tasks
  - **Client**: Users who create tasks and have locations

### Core Entities
- **Product**: Items managed by sellers
- **ProductItem**: Individual instances of products with prices
- **Task**: Work assignments linking clients, workers, and products
- **Location**: Geographic information for clients and tasks

## API Endpoints

### User Management
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/email/{email}` - Get user by email
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user
- `PUT /api/users/{id}/password` - Update user password

### Admin Management
- `GET /api/admins` - Get all admins
- `GET /api/admins/{id}` - Get admin by ID
- `POST /api/admins` - Create new admin
- `PUT /api/admins/{id}` - Update admin
- `DELETE /api/admins/{id}` - Delete admin
- `DELETE /api/admins/{adminId}/products/{productId}` - Delete product as admin

### Seller Management
- `GET /api/sellers` - Get all sellers
- `GET /api/sellers/{id}` - Get seller by ID
- `POST /api/sellers` - Create new seller
- `PUT /api/sellers/{id}` - Update seller
- `DELETE /api/sellers/{id}` - Delete seller

### Worker Management
- `GET /api/workers` - Get all workers
- `GET /api/workers/{id}` - Get worker by ID
- `POST /api/workers` - Create new worker
- `PUT /api/workers/{id}` - Update worker
- `DELETE /api/workers/{id}` - Delete worker

### Client Management
- `GET /api/clients` - Get all clients
- `GET /api/clients/{id}` - Get client by ID
- `POST /api/clients` - Create new client
- `PUT /api/clients/{id}` - Update client
- `DELETE /api/clients/{id}` - Delete client

### Product Management
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/serial/{serialNumber}` - Get product by serial number
- `GET /api/products/seller/{sellerId}` - Get products by seller
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product

### Product Item Management
- `GET /api/product-items` - Get all product items
- `GET /api/product-items/{id}` - Get product item by ID
- `GET /api/product-items/product/{productId}` - Get product items by product
- `POST /api/product-items` - Create new product item
- `PUT /api/product-items/{id}` - Update product item
- `DELETE /api/product-items/{id}` - Delete product item

### Task Management
- `GET /api/tasks` - Get all tasks
- `GET /api/tasks/{id}` - Get task by ID
- `GET /api/tasks/worker/{workerId}` - Get tasks by worker
- `GET /api/tasks/client/{clientId}` - Get tasks by client
- `GET /api/tasks/product/{productId}` - Get tasks by product
- `GET /api/tasks/status/{status}` - Get tasks by status
- `POST /api/tasks` - Create new task
- `PUT /api/tasks/{id}` - Update task
- `DELETE /api/tasks/{id}` - Delete task

### Location Management
- `GET /api/locations` - Get all locations
- `GET /api/locations/{id}` - Get location by ID
- `POST /api/locations` - Create new location
- `PUT /api/locations/{id}` - Update location
- `DELETE /api/locations/{id}` - Delete location

## Database Configuration

### Oracle Connection
```properties
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:XE
spring.datasource.username=ecorgy
spring.datasource.password=ecorgy123
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
```

### JPA Configuration
- Hibernate DDL auto: update
- Show SQL: true
- Oracle 12c dialect
- Default schema: ECORGY

## Setup Instructions

1. **Database Setup**
   - Install Oracle Database
   - Create user 'ecorgy' with password 'ecorgy123'
   - Grant necessary permissions

2. **Application Setup**
   - Clone the repository
   - Run `mvn clean install`
   - Run `mvn spring-boot:run`

3. **Testing**
   - Use Postman or similar tool to test API endpoints
   - All endpoints return JSON responses

## Error Handling
- Global exception handler for consistent error responses
- Custom API response format with success/error status
- Proper HTTP status codes for all operations

## Security Considerations
- Passwords are stored in plain text (should be encrypted in production)
- CORS enabled for frontend integration
- Input validation through Spring validation annotations

## Future Enhancements
- Add authentication and authorization (JWT)
- Implement password encryption
- Add more comprehensive validation
- Implement pagination for large datasets
- Add search and filtering capabilities