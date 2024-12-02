package com.hcms.healthcaremanagementsystem.registration;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import com.hcms.healthcaremanagementsystem.dbconnection.DatabaseConnection;
import com.hcms.healthcaremanagementsystem.dao.PatientDAO;
import com.hcms.healthcaremanagementsystem.dto.PatientDTO;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="newPatient", value="/new-patient")
public class newPatient extends HttpServlet {

    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head>");
        out.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css'>");
        out.println("</head><body>");
        out.println("<div class='container mt-5'>");
        out.println("<h1 class='mb-4'>New Patient</h1>");
        out.println("<form action='new-patient' method='post'>");
        out.println("<div class='form-group'>");
        out.println("<label for='firstName'>First Name</label>");
        out.println("<input type='text' name='firstName' class='form-control' placeholder='Enter First Name' required>");
        out.println("<label for='lastName'>Last Name</label>");
        out.println("<input type='text' name='lastName' class='form-control' placeholder='Enter Last Name' required>");
        out.println("<label for='dateOfBirth'>Date of Birth</label>");
        out.println("<input type='date' name='dateOfBirth' class='form-control' placeholder='Enter Date of Birth' required>");
        out.println("<label for='gender'>Gender</label>");
        out.println("<input type='text' name='gender' class='form-control' placeholder='Enter Gender' required>");
        out.println("<label for='Phone Number'>Phone Number</label>");
        out.println("<input type='text' name='phoneNumber' class='form-control' placeholder='Enter Phone Number' required>");
        out.println("<label for='email'>Email</label>");
        out.println("<input type='email' name='email' class='form-control' placeholder='Enter Email' required>");
        out.println("<label for='address'>Address</label>");
        out.println("<input type='text' name='address' class='form-control' placeholder='Enter Address' required>");
        out.println("<label for='MedicalHistory'>Medical History</label>");
        out.println("<input type='text' name='medicalHistory' class='form-control' placeholder='Enter Medical History' required>");
        out.println("<button type='submit' class='btn btn-primary'>Submit</button>");
        out.println("<div class='form-group'>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String gender = request.getParameter("gender");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String medicalHistory = request.getParameter("medicalHistory");

        PatientDAO patientDAO = new PatientDAO();
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setFirstName(firstName);
        patientDTO.setLastName(lastName);
        patientDTO.setDateOfBirth(dateOfBirth);
        patientDTO.setGender(gender);
        patientDTO.setPhoneNumber(phoneNumber);
        patientDTO.setEmail(email);
        patientDTO.setAddress(address);
        patientDTO.setMedicalHistory(medicalHistory);

        patientDAO.createPatient(patientDTO);

        String contextPath = request.getContextPath();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head>");
        out.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css'>");
        out.println("</head><body>");
        out.println("<div class='container mt-5'>");
        out.println("<h1 class='mb-4'>New Patient</h1>");
        out.println("<div class='alert alert-success'>Patient Added Successfully</div>");
        out.println("<a href='"+contextPath+"/patient-management' class='btn btn-success'>Go to Patient Management</a>");
        out.println("</div>");

    }
}
