package com.hcms.healthcaremanagementsystem.DAO;
import com.hcms.healthcaremanagementsystem.Model.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {
    private Connection connection;

    public AppointmentDAO(Connection connection) {
        this.connection = connection;
    }

    // Create appointment
    public boolean createAppointment(Appointment appointment) {
        try {
            String query = "INSERT INTO appointments (patientId, doctorName, appointmentTime, endTime, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, appointment.getPatientId());
            statement.setString(2, appointment.getDoctorName());
            statement.setString(3, appointment.getAppointmentTime());
            statement.setString(4, appointment.getEndTime());
            statement.setString(5, appointment.getStatus());
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all appointments
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        try {
            String query = "SELECT * FROM appointments";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(resultSet.getInt("appointmentId"));
                appointment.setPatientId(resultSet.getInt("patientId"));
                appointment.setDoctorName(resultSet.getString("doctorName"));
                appointment.setAppointmentTime(resultSet.getString("appointmentTime"));
                appointment.setEndTime(resultSet.getString("endTime"));
                appointment.setStatus(resultSet.getString("status"));
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }
}
