package com.hcms.healthcaremanagementsystem.invoicing;

import com.hcms.healthcaremanagementsystem.dbconnection.DatabaseConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServlet;

/**
 * Servlet that handles invoice generation for the healthcare management system.
 * This servlet processes HTTP POST requests to insert invoice details into the database.
 */
@WebServlet("/invoicing")
public class InvoicingServlet extends HttpServlet {
    
    /**
     * Handles POST requests for creating a new invoice.
     *
     * @param request  the HttpServletRequest object containing client request parameters
     * @param response the HttpServletResponse object to send response back to the client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve input parameters from the request
        String patientID = request.getParameter("patientID");
        String totalAmount = request.getParameter("totalAmount");
        boolean isPaid = request.getParameter("isPaid") != null;

        try (Connection conn = DatabaseConnection.getConnection()) {
            // SQL query to insert a new invoice record
            String sql = "INSERT INTO Invoice (PatientID, TotalAmount, PaymentStatus) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, Integer.parseInt(patientID));
                stmt.setDouble(2, Double.parseDouble(totalAmount));
                stmt.setString(3, isPaid ? "Paid" : "Unpaid");
                stmt.executeUpdate();
            }

            // Use RedirectWithCountdown to prepare and send an HTML response with a redirect
            response.setContentType("text/html");
            try (PrintWriter out = response.getWriter()) {
                RedirectWithCountdown.send(
                    out, 
                    "Redirecting...", 
                    "Invoice generated successfully!", 
                    "InvoiceMain.jsp", 
                    3
                );
            }
        } catch (SQLException e) {
            throw new ServletException("Database error occurred while generating invoice: " + e.getMessage(), e);
        }
    }
}


