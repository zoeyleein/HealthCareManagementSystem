package com.hcms.healthcaremanagementsystem.registration;

import com.hcms.healthcaremanagementsystem.dao.PatientDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet (name = "DeletePatient", value = "/delete-patient")
public class DeletePatient extends HttpServlet{
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contextPath = request.getContextPath();
        int patientID = Integer.parseInt(request.getParameter("patientID"));
        PatientDAO patientDAO = new PatientDAO();
        boolean isDeleted = patientDAO.deletePatient(patientID);
        if (!isDeleted) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head>");
            out.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css'>");
            out.println("</head><body>");
            out.println("<div class='container mt-5'>");
            out.println("<h1 class='mb-4'>Patient Not Deleted</h1>");
            out.println("<div class='alert alert-danger'>Patient Not Deleted</div>");
            out.println("<a href='"+contextPath+"/patient-management' class='btn btn-success'>Go to Patient Management</a>");
            out.println("</div>");
        } else
        {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head>");
            out.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css'>");
            out.println("</head><body>");
            out.println("<div class='container mt-5'>");
            out.println("<h1 class='mb-4'>Patient Deleted</h1>");
            out.println("<div class='alert alert-success'>Patient Deleted</div>");
            out.println("<a href='"+contextPath+"/patient-management' class='btn btn-success'>Go to Patient Management</a>");
            out.println("</div>");
        }
    }
}
