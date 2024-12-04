package com.hcms.healthcaremanagementsystem.medicalhistory;

import com.hcms.healthcaremanagementsystem.dbconnection.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecordDAO {
    private Connection connection;

    public MedicalRecordDAO() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    public List<MedicalRecord> getAllRecords() throws SQLException {
        List<MedicalRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM MedicalRecord";

        if (connection == null) {
            throw new SQLException("Database connection is not initialized.");
        }
        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {

            while (resultSet.next()) {
                MedicalRecord record = new MedicalRecord(0, 0, "", "", "");
                record.setRecordID(resultSet.getInt("RecordID"));
                record.setPatientID(resultSet.getInt("PatientID"));
                record.setDiagnosis(resultSet.getString("Diagnosis"));
                record.setTreatment(resultSet.getString("Treatment"));
                record.setTestResults(resultSet.getString("TestResults"));
                record.setRecordDate(resultSet.getTimestamp("RecordDate"));
                records.add(record);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching records: " + e.getMessage());
            throw e;
        }
        return records;
    }

    public void addRecord(MedicalRecord record) throws SQLException {
        String sql = "INSERT INTO MedicalRecord (PatientID, Diagnosis, Treatment, TestResults) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, record.getPatientID());
            stmt.setString(2, record.getDiagnosis());
            stmt.setString(3, record.getTreatment());
            stmt.setString(4, record.getTestResults());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error adding MedicalRecord to MedicalRecordDAO: " + e.getMessage(), e);
        }
    }

    public void updateRecord(MedicalRecord record) throws SQLException {
        String sql = "UPDATE MedicalRecord SET Diagnosis = ?, Treatment = ?, TestResults = ? WHERE RecordID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, record.getDiagnosis());
            stmt.setString(2, record.getTreatment());
            stmt.setString(3, record.getTestResults());
            stmt.setInt(4, record.getRecordID());
            stmt.executeUpdate();
        }
    }

    public void deleteRecord(int recordID) throws SQLException {
        String sql = "DELETE FROM MedicalRecord WHERE RecordID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, recordID);
            stmt.executeUpdate();
        }
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
