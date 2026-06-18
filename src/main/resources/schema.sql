-- Pet Shop Management System Database Schema
-- This script creates the database structure for the pet shop management system

-- Create Database
CREATE DATABASE IF NOT EXISTS pet_shop_db;
USE pet_shop_db;

-- Admin Table
CREATE TABLE IF NOT EXISTS admins (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'ADMIN',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Product Category Table
CREATE TABLE IF NOT EXISTS product_categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Product Table
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(200) NOT NULL,
    product_code VARCHAR(50) NOT NULL UNIQUE,
    barcode VARCHAR(50) UNIQUE,
    brand VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    gst_percentage DOUBLE NOT NULL,
    description TEXT,
    quantity INT NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    category_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES product_categories(id) ON DELETE CASCADE
);

-- Stock Table
CREATE TABLE IF NOT EXISTS stocks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL UNIQUE,
    stock_in INT NOT NULL DEFAULT 0,
    stock_out INT NOT NULL DEFAULT 0,
    current_stock INT NOT NULL DEFAULT 0,
    damaged_stock INT NOT NULL DEFAULT 0,
    low_stock_limit INT NOT NULL DEFAULT 10,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Insert Default Admin (password: admin@123 - BCrypt encoded)
INSERT INTO admins (username, email, password, role) 
VALUES ('admin@petshop.in', 'admin@petshop.in', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'ADMIN')
ON DUPLICATE KEY UPDATE username=username;

-- Insert Default Categories
INSERT INTO product_categories (category_name, description, status) VALUES
('Pet Food', 'Food for pets including dogs, cats, birds, etc.', 'ACTIVE'),
('Accessories', 'Pet accessories like collars, leashes, toys, etc.', 'ACTIVE'),
('Grooming', 'Grooming products like shampoos, brushes, etc.', 'ACTIVE'),
('Medicines', 'Pet medicines and health supplements', 'ACTIVE'),
('Toys', 'Pet toys for entertainment', 'ACTIVE')
ON DUPLICATE KEY UPDATE category_name=category_name;
