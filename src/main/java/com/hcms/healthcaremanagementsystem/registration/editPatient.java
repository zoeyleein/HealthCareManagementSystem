package com.hcms.healthcaremanagementsystem.registration;
import com.hcms.healthcaremanagementsystem.dao.PatientDAO;
import com.hcms.healthcaremanagementsystem.dto.PatientDTO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "editPatient", value = "/edit-patient")
public class editPatient extends HttpServlet{
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contextPath = request.getContextPath();
        int patientID = Integer.parseInt(request.getParameter("patientID"));
        PatientDAO patientDAO = new PatientDAO();
        PatientDTO patientDTO = new PatientDTO();
        patientDTO = patientDAO.getPatient(patientID);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head>");
        out.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css'>");
        out.println("</head><body>");
        out.println("<div class='container mt-5'>");
        out.println("<h1 class='mb-4'>Edit Patient</h1>");
        out.println("<form action='edit-patient' method='post'>");
        out.println("<div class='form-group'>");
        out.println("<label for='firstName'>First Name</label>");
        out.println("<input type='text' name='firstName' class='form-control' placeholder='Enter First Name' value='" + patientDTO.getFirstName() + "' required>");
        out.println("<label for='lastName'>Last Name</label>");
        out.println("<input type='text' name='lastName' class='form-control' placeholder='Enter Last Name' value='" + patientDTO.getLastName() + "' required>");
        out.println("<label for='dateOfBirth'>Date of Birth</label>");
        out.println("<input type='date' name='dateOfBirth' class='form-control' placeholder='Enter Date of Birth' value='" + patientDTO.getDateOfBirth() + "' required>");
        out.println("<label for='gender'>Gender</label>");
        out.println("<input type='text' name='gender' class='form-control' placeholder='Enter Gender' value='" + patientDTO.getGender() + "' required>");
        out.println("<label for='Phone Number'>Phone Number</label>");
        out.println("<input type='text' name='phoneNumber' class='form-control' placeholder='Enter Phone Number' value='" + patientDTO.getPhoneNumber() + "' required>");
        out.println("<label for='email'>Email</label>");
        out.println("<input type='email' name='email' class='form-control' placeholder='Enter Email' value='" + patientDTO.getEmail() + "' required>");
        out.println("<label for='address'>Address</label>");
        out.println("<input type='text' name='address' class='form-control' placeholder='Enter Address' value='" + patientDTO.getAddress() + "' required>");
        out.println("<label for='MedicalHistory'>Medical History</label>");
        out.println("<input type='text' name='medicalHistory' class='form-control' placeholder='Enter Medical History' value='" + patientDTO.getMedicalHistory() + "' required>");
        out.println("<input type='hidden' name='patientID' value='" + patientID + "'>");
        out.println("</div>");
        out.println("<button type='submit' class='btn btn-primary'>Update</button>");
        out.println("</form>");
        out.println("</div>");
        out.println("</body></html>");

    }
}
