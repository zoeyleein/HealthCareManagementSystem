package com.hcms.healthcaremanagementsystem.dao;

import com.hcms.healthcaremanagementsystem.dto.PatientDTO;
import com.hcms.healthcaremanagementsystem.dbconnection.DatabaseConnection;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    public List<PatientDTO> getAllPatients() {
        List<PatientDTO> patients = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Patient")) {

            while (rs.next()) {
                PatientDTO patient = new PatientDTO();
                patient.setPatientID(rs.getInt("PatientID"));
                patient.setFirstName(rs.getString("FirstName"));
                patient.setLastName(rs.getString("LastName"));
                patient.setDateOfBirth(rs.getString("DateOfBirth"));
                patient.setGender(rs.getString("Gender"));
                patient.setPhoneNumber(rs.getString("PhoneNumber"));
                patient.setEmail(rs.getString("Email"));
                patient.setAddress(rs.getString("Address"));
                patient.setMedicalHistory(rs.getString("MedicalHistory"));
                patients.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    public PatientDTO getPatient(int patientID) {
        String sql = "SELECT * FROM Patient WHERE PatientID = ?";
        PatientDTO patient = new PatientDTO();
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setInt(1, patientID);
                ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                    patient.setPatientID(rs.getInt("PatientID"));
                    patient.setFirstName(rs.getString("FirstName"));
                    patient.setLastName(rs.getString("LastName"));
                    patient.setDateOfBirth(rs.getString("DateOfBirth"));
                    patient.setGender(rs.getString("Gender"));
                    patient.setPhoneNumber(rs.getString("PhoneNumber"));
                    patient.setEmail(rs.getString("Email"));
                    patient.setAddress(rs.getString("Address"));
                    patient.setMedicalHistory(rs.getString("MedicalHistory"));
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;

    }

    public void createPatient(PatientDTO patient) {
        String sql = "INSERT INTO Patient (FirstName, LastName, DateOfBirth, Gender, PhoneNumber, Email, Address, MedicalHistory) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, patient.getFirstName());
            pstmt.setString(2, patient.getLastName());
            pstmt.setString(3, patient.getDateOfBirth());
            pstmt.setString(4, patient.getGender());
            pstmt.setString(5, patient.getPhoneNumber());
            pstmt.setString(6, patient.getEmail());
            pstmt.setString(7, patient.getAddress());
            pstmt.setString(8, patient.getMedicalHistory());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updatePatient(PatientDTO patient) {
        String sql = "UPDATE Patient SET FirstName = ?, LastName = ?, DateOfBirth = ?, Gender = ?, PhoneNumber = ?, Email = ?, Address = ?, MedicalHistory = ? WHERE PatientID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, patient.getFirstName());
            pstmt.setString(2, patient.getLastName());
            pstmt.setString(3, patient.getDateOfBirth());
            pstmt.setString(4, patient.getGender());
            pstmt.setString(5, patient.getPhoneNumber());
            pstmt.setString(6, patient.getEmail());
            pstmt.setString(7, patient.getAddress());
            pstmt.setString(8, patient.getMedicalHistory());
            pstmt.setInt(9, patient.getPatientID());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean deletePatient(int patientID) {
        String sql = "DELETE FROM Patient WHERE PatientID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, patientID);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}