
-- Drop tables in the correct order to avoid foreign key constraint issues
DROP TABLE IF EXISTS Prescription;
DROP TABLE IF EXISTS Answer;
DROP TABLE IF EXISTS Consultation;
DROP TABLE IF EXISTS Question;
DROP TABLE IF EXISTS Subscription;
DROP TABLE IF EXISTS Doctor;
DROP TABLE IF EXISTS Customer;



CREATE TABLE Customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE Consultation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES Customer(id)
);

CREATE TABLE Question (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    text VARCHAR(255) NOT NULL
);

CREATE TABLE Answer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    consultation_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    response VARCHAR(255) NOT NULL,
    FOREIGN KEY (consultation_id) REFERENCES Consultation(id),
    FOREIGN KEY (question_id) REFERENCES Question(id)
);

CREATE TABLE Subscription (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    card_details VARCHAR(255) NOT NULL,
    active BOOLEAN DEFAULT FALSE,
    start_date TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES Customer(id)
);

CREATE TABLE Doctor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE Prescription (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    consultation_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (consultation_id) REFERENCES Consultation(id),
    FOREIGN KEY (doctor_id) REFERENCES Doctor(id)
);
