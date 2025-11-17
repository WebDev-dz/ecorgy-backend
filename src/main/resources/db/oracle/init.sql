-- Oracle Database Initialization Script for Ecorgy Application

-- Create sequences for ID generation
CREATE SEQUENCE USER_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE PRODUCT_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE PRODUCT_ITEM_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE TASK_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE LOCATION_SEQ START WITH 1 INCREMENT BY 1;

-- Create tables (will be created by JPA, but this is for reference)
-- User table with inheritance strategy
-- Product, ProductItem, Task, Location tables
-- All relationships are defined in the Java entities

-- Sample data insertion (optional)
-- INSERT INTO LOCATION (id, latitude, longitude, street, city, zip_code, country) VALUES (1, 36.7538, 3.0588, 'Rue Didouche Mourad', 'Algiers', '16000', 'Algeria');
-- INSERT INTO USER (id, first_name, last_name, email, birth_date, password, phone_number, user_type) VALUES (1, 'Admin', 'User', 'admin@ecorgy.com', TO_DATE('1990-01-01', 'YYYY-MM-DD'), 'admin123', 1234567890, 'ADMIN');