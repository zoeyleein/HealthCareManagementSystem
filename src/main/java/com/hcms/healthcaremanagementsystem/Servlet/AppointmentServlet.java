package com.hcms.healthcaremanagementsystem.Servlet;

import com.hcms.healthcaremanagementsystem.DAO.AppointmentDAO;
import com.hcms.healthcaremanagementsystem.Database.DatabaseConnection;
import com.hcms.healthcaremanagementsystem.Model.Appointment;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.*;
import java.sql.SQLException;
import java.util.List;

public class AppointmentServlet extends HttpServlet {

    private AppointmentDAO appointmentDAO;

    @Override
    public void init() throws ServletException {
        // Initialize DAO (assumes that a connection is passed in)
        try {
            appointmentDAO = new AppointmentDAO(DatabaseConnection.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Handle the creation of appointments
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int patientId = Integer.parseInt(request.getParameter("patientId"));
        String doctorName = request.getParameter("doctorName");
        String appointmentTime = request.getParameter("appointmentTime");
        String endTime = request.getParameter("endTime");
        String status = request.getParameter("status");

        Appointment appointment = new Appointment();
        appointment.setPatientId(patientId);
        appointment.setDoctorName(doctorName);
        appointment.setAppointmentTime(appointmentTime);
        appointment.setEndTime(endTime);
        appointment.setStatus(status);

        boolean success = appointmentDAO.createAppointment(appointment);

        if (success) {
            response.sendRedirect("viewAppointments.jsp");
        } else {
            response.sendRedirect("createAppointment.jsp?error=true");
        }
    }

    // Handle displaying all appointments
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Appointment> appointments = appointmentDAO.getAllAppointments();
        request.setAttribute("appointments", appointments);
        RequestDispatcher dispatcher = request.getRequestDispatcher("viewAppointments.jsp");
        dispatcher.forward(request, response);
    }
}
