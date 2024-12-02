package com.hcms.healthcaremanagementsystem.registration;

import java.io.*;
import java.sql.*;
import java.util.List;

import com.hcms.healthcaremanagementsystem.dao.PatientDAO;
import com.hcms.healthcaremanagementsystem.dto.PatientDTO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import com.hcms.healthcaremanagementsystem.dbconnection.DatabaseConnection;

@WebServlet(name="PatientManagement", value="/patient-management")

public class PatientManagement extends HttpServlet {

    private String message;
    public void init() {
        message = "Welcome to Patient Management System";
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        PatientDAO patientDao = new PatientDAO();
        List<PatientDTO> patients = patientDao.getAllPatients();

        out.println("<html><head>");
        out.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css'>");
        out.println("</head><body>");

        out.println("<div class='container mt-5'>");
        out.println("<h1 class='mb-4'>" + message + "</h1>");

        out.println("<table class='table table-hover'>");
        out.println("<thead class='thead-dark'><tr><th>ID</th><th>First Name</th><th>Last Name</th><th>Actions</th></tr></thead>");
        out.println("<tbody>");

        for (PatientDTO patient : patients) {
            out.println("<tr>");
            out.println("<td>" + patient.getPatientID() + "</td>");
            out.println("<td>" + patient.getFirstName() + "</td>");
            out.println("<td>" + patient.getLastName() + "</td>");
            out.println("<td>");
            out.println("<a href='edit?patientID=" + patient.getPatientID() + "' class='btn btn-sm btn-primary'>Edit</a> ");
            out.println("<a href='delete?patientID=" + patient.getPatientID() + "' class='btn btn-sm btn-danger'>Delete</a>");
            out.println("</td>");
            out.println("</tr>");
        }

        out.println("</tbody>");
        out.println("</table>");
        out.println("<a href='new' class='btn btn-success'>Add New Patient</a>");
        out.println("</div>");

        out.println("<script src='https://code.jquery.com/jquery-3.5.1.slim.min.js'></script>");
        out.println("<script src='https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js'></script>");
        out.println("<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js'></script>");

        out.println("</body></html>");
    }

    public void destroy() {
    }
}
