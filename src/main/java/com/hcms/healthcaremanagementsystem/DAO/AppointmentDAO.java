package com.hcms.healthcaremanagementsystem.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.hcms.healthcaremanagementsystem.Model.Appointment;
import com.hcms.healthcaremanagementsystem.Database.DatabaseConnection;

public class AppointmentDAO {

    /**
     * Adds a new appointment to the database.
     */
    public void addAppointment(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointments (patient_id, doctor_name, appointment_date, appointment_time) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, appointment.getPatientId());
            statement.setString(2, appointment.getDoctorName());
            statement.setString(3, appointment.getAppointmentDate());
            statement.setString(4, appointment.getAppointmentTime());

            statement.executeUpdate();
            System.out.println("Appointment added successfully.");
        }
    }

    /**
     * Updates an existing appointment in the database.
     */
    public void updateAppointment(Appointment appointment) throws SQLException {
        String sql = "UPDATE appointments SET patient_id = ?, doctor_name = ?, appointment_date = ?, appointment_time = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, appointment.getPatientId());
            statement.setString(2, appointment.getDoctorName());
            statement.setString(3, appointment.getAppointmentDate());
            statement.setString(4, appointment.getAppointmentTime());
            statement.setInt(5, appointment.getId());

            statement.executeUpdate();
            System.out.println("Appointment updated successfully.");
        }
    }

    /**
     * Retrieves all appointments from the database.
     */
    public List<Appointment> getAllAppointments() throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(resultSet.getInt("id"));
                appointment.setPatientId(resultSet.getInt("patient_id"));
                appointment.setDoctorName(resultSet.getString("doctor_name"));
                appointment.setAppointmentDate(resultSet.getString("appointment_date"));
                appointment.setAppointmentTime(resultSet.getString("appointment_time"));

                appointments.add(appointment);
            }
        }
        return appointments;
    }

    /**
     * Deletes an appointment from the database.
     */
    public void deleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, appointmentId);
            statement.executeUpdate();
            System.out.println("Appointment deleted successfully.");
        }
    }
}

