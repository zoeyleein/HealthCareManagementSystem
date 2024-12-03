-- Create the database
CREATE DATABASE hms;
USE hms;

-- Patient table
CREATE TABLE Patient (
                         PatientID INT AUTO_INCREMENT PRIMARY KEY,
                         FirstName VARCHAR(50) NOT NULL,
                         LastName VARCHAR(50) NOT NULL,
                         DateOfBirth DATETIME NOT NULL,
                         Gender VARCHAR(10),
                         PhoneNumber VARCHAR(15),
                         Email VARCHAR(100),
                         Address VARCHAR(200),
                         MedicalHistory TEXT
);

-- Appointment table
CREATE TABLE Appointment (
                             AppointmentID INT AUTO_INCREMENT PRIMARY KEY,
                             PatientID INT NOT NULL,
                             DoctorName VARCHAR(100) NOT NULL,
                             AppointmentDate DATETIME NOT NULL,
                             StartTime DATETIME NOT NULL,
                             EndTime DATETIME NOT NULL,
                             Status VARCHAR(20) DEFAULT 'Scheduled',
                             FOREIGN KEY (PatientID) REFERENCES Patient(PatientID)
);

-- Invoice table
CREATE TABLE Invoice (
                         InvoiceID INT AUTO_INCREMENT PRIMARY KEY,
                         PatientID INT NOT NULL,
                         TotalAmount DECIMAL(10, 2) NOT NULL,
                         InvoiceDate DATETIME DEFAULT CURRENT_TIMESTAMP,
                         PaymentStatus VARCHAR(20) DEFAULT 'Unpaid',
                         FOREIGN KEY (PatientID) REFERENCES Patient(PatientID)
);

-- MedicalRecord table
CREATE TABLE MedicalRecord (
                               RecordID INT AUTO_INCREMENT PRIMARY KEY,
                               PatientID INT NOT NULL,
                               Diagnosis VARCHAR(500),
                               Treatment VARCHAR(500),
                               TestResults TEXT,
                               RecordDate DATETIME DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (PatientID) REFERENCES Patient(PatientID)
);

-- UserAccount table
CREATE TABLE UserAccount (
                             UserID INT AUTO_INCREMENT PRIMARY KEY,
                             Username VARCHAR(50) NOT NULL UNIQUE,
                             PasswordHash VARCHAR(200) NOT NULL,
                             Role VARCHAR(20) NOT NULL,
                             PatientID INT,
                             FOREIGN KEY (PatientID) REFERENCES Patient(PatientID)
);

-- InsuranceClaim table
CREATE TABLE InsuranceClaim (
                                ClaimID INT AUTO_INCREMENT PRIMARY KEY,
                                PatientID INT NOT NULL,
                                InsuranceProvider VARCHAR(100) NOT NULL,
                                ClaimAmount DECIMAL(10, 2) NOT NULL,
                                ClaimDate DATETIME DEFAULT CURRENT_TIMESTAMP,
                                ClaimStatus VARCHAR(20) DEFAULT 'Pending',
                                FOREIGN KEY (PatientID) REFERENCES Patient(PatientID)
);

-- PharmacyInventory table
CREATE TABLE PharmacyInventory (
                                   ItemID INT AUTO_INCREMENT PRIMARY KEY,
                                   ItemName VARCHAR(100) NOT NULL,
                                   Quantity INT NOT NULL,
                                   ExpirationDate DATETIME NOT NULL,
                                   UnitPrice DECIMAL(10, 2) NOT NULL
);

-- Sample Data
INSERT INTO Patient (FirstName, LastName, DateOfBirth, Gender, PhoneNumber, Email, Address, MedicalHistory)
VALUES ('John', 'Doe', '1990-05-15', 'Male', '1234567890', 'johndoe@example.com', '123 Main St, Toronto, ON', 'No prior history.');

INSERT INTO Patient (FirstName, LastName, DateOfBirth, Gender, PhoneNumber, Email, Address, MedicalHistory)
VALUES ('Zoey', 'Lee', '1990-05-16', 'Female', '1234567890', 'zoey@example.com', '123 Main St, Toronto, ON', 'No prior history.');

INSERT INTO Patient (FirstName, LastName, DateOfBirth, Gender, PhoneNumber, Email, Address, MedicalHistory)
VALUES ('Cheng', 'Bert', '1990-05-13', 'Male', '1234567890', 'cheng@bert.com', '123 Main St, Toronto, ON', 'No prior history.');

INSERT INTO Appointment (PatientID, DoctorName, AppointmentDate, StartTime, EndTime, Status)
VALUES (1, 'Dr. Jane Smith', '2024-12-01', '2024-12-01 10:00:00', '2024-12-01 11:00:00', 'Scheduled');

INSERT INTO Invoice (PatientID, TotalAmount, InvoiceDate, PaymentStatus)
VALUES (1, 200.50, CURRENT_TIMESTAMP, 'Paid');

INSERT INTO MedicalRecord (PatientID, Diagnosis, Treatment, TestResults, RecordDate)
VALUES (1, 'Flu', 'Rest and hydration', 'Blood test normal', CURRENT_TIMESTAMP);

INSERT INTO UserAccount (Username, PasswordHash, Role, PatientID)
VALUES ('johndoe', 'hashed_password_example', 'Admin', 1);

INSERT INTO UserAccount (Username, PasswordHash, Role, PatientID)
VALUES ('ZoeyLeein', 'hashed_password_example', 'Patient', 2);

INSERT INTO UserAccount (Username, PasswordHash, Role, PatientID)
VALUES ('Cheng', 'hashed_password_example', 'Doctor', 3);

INSERT INTO InsuranceClaim (PatientID, InsuranceProvider, ClaimAmount, ClaimDate, ClaimStatus)
VALUES (1, 'Blue Cross', 150.75, CURRENT_TIMESTAMP, 'Approved');

INSERT INTO PharmacyInventory (ItemName, Quantity, ExpirationDate, UnitPrice)
VALUES ('Paracetamol', 500, '2025-12-31', 0.50);

COMMIT;