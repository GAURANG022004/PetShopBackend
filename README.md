# Pet Shop Management System - Backend API

## Overview
This is the backend REST API for the Pet Shop Management System, built with Spring Boot 3.x, Java 21, and MySQL.

## Tech Stack
- Java 21
- Spring Boot 3.x
- Maven
- MySQL
- Spring Data JPA
- Hibernate
- Spring Validation
- Lombok
- Spring Security (JWT Authentication)
- REST API

## Project Structure
```
com.petshop
├── config
│   ├── DataInitializer.java
│   ├── JpaConfig.java
│   └── SecurityConfig.java
├── controller
│   ├── AuthController.java
│   ├── CategoryController.java
│   ├── ProductController.java
│   └── StockController.java
├── dto
│   ├── request
│   │   ├── AdminLoginRequest.java
│   │   ├── CategoryRequest.java
│   │   ├── ProductRequest.java
│   │   ├── StockInRequest.java
│   │   ├── StockOutRequest.java
│   │   └── DamagedStockRequest.java
│   └── response
│       ├── AdminResponse.java
│       ├── LoginResponse.java
│       ├── CategoryResponse.java
│       ├── ProductResponse.java
│       └── StockResponse.java
├── entity
│   ├── Admin.java
│   ├── ProductCategory.java
│   ├── Product.java
│   └── Stock.java
├── exception
│   ├── ResourceNotFoundException.java
│   ├── DuplicateResourceException.java
│   ├── InsufficientStockException.java
│   ├── AuthenticationException.java
│   └── GlobalExceptionHandler.java
├── mapper
│   ├── AdminMapper.java
│   ├── CategoryMapper.java
│   ├── ProductMapper.java
│   └── StockMapper.java
├── repository
│   ├── AdminRepository.java
│   ├── ProductCategoryRepository.java
│   ├── ProductRepository.java
│   └── StockRepository.java
├── response
│   └── ApiResponse.java
├── security
│   ├── CustomUserDetailsService.java
│   └── JwtAuthenticationFilter.java
├── service
│   ├── AuthService.java
│   ├── CategoryService.java
│   ├── ProductService.java
│   └── StockService.java
├── service.impl
│   ├── AuthServiceImpl.java
│   ├── CategoryServiceImpl.java
│   ├── ProductServiceImpl.java
│   └── StockServiceImpl.java
├── util
│   └── JwtUtil.java
├── constant
│   └── AppConstants.java
└── PetShopManagementSystemApplication.java
```

## Database Configuration
- Database: MySQL
- Database Name: pet_shop_db
- Default Port: 3306
- Username: root
- Password: root (update in application.properties)

## Default Admin Credentials
- Username: admin@petshop.in
- Password: admin@123

## API Endpoints

### Authentication
- `POST /api/auth/login` - Admin login

### Categories
- `POST /api/categories` - Create category
- `GET /api/categories` - Get all categories
- `GET /api/categories/{id}` - Get category by ID
- `PUT /api/categories/{id}` - Update category
- `DELETE /api/categories/{id}` - Delete category

### Products
- `POST /api/products` - Create product
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product
- `GET /api/products/search?name=` - Search products by name
- `GET /api/products/category/{id}` - Get products by category
- `GET /api/products/barcode/{barcode}` - Get product by barcode

### Stock Management
- `POST /api/stocks/in` - Add stock in
- `POST /api/stocks/out` - Add stock out
- `POST /api/stocks/damaged` - Record damaged stock
- `GET /api/stocks` - Get all stocks
- `GET /api/stocks/low` - Get low stock products
- `GET /api/stocks/product/{id}` - Get stock by product ID

## Setup Instructions

1. **Prerequisites**
   - Java 21 installed
   - MySQL installed and running
   - Maven installed

2. **Database Setup**
   - Create MySQL database: `pet_shop_db`
   - Update database credentials in `application.properties` if needed

3. **Build the Project**
   ```bash
   mvn clean install
   ```

4. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the API**
   - Base URL: `http://localhost:8080`
   - Login to get JWT token
   - Use token in Authorization header: `Bearer {token}`

## Features Implemented
- JWT Authentication
- Admin Management
- Product Category CRUD
- Product CRUD with search
- Stock Management (In/Out/Damaged)
- Low Stock Alerts
- Global Exception Handling
- Input Validation
- DTO Pattern
- Layered Architecture

## Future Enhancements
- Supplier Management
- Purchase Management
- Sales Management
- Billing
- Payment Integration
- Advanced Barcode System
- Reports and Analytics
- Customer Dashboard
- Invoice module
- Customer Module 
- Purchase Module
- coupon module

