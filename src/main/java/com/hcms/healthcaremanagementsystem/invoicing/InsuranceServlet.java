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

// Servlet mapped to handle insurance-related actions
@WebServlet("/insurance")
public class InsuranceServlet extends jakarta.servlet.http.HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Retrieve the action parameter to determine the user's intended operation
        String action = request.getParameter("action");

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Action to file an insurance claim
            if ("file".equals(action)) {
                // Retrieve form data for filing a claim
                int patientId = Integer.parseInt(request.getParameter("patientId"));
                String provider = request.getParameter("insuranceProvider");
                double amount = Double.parseDouble(request.getParameter("claimAmount"));

                // SQL query to insert a new claim into the database
                String sql = "INSERT INTO InsuranceClaim (PatientID, InsuranceProvider, ClaimAmount) VALUES (?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, patientId);
                    stmt.setString(2, provider);
                    stmt.setDouble(3, amount);
                    stmt.executeUpdate(); // Execute the query
                }

                // Send a response with a countdown and redirect
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                RedirectWithCountdown.send(
                    out,
                    "Redirecting...",
                    "Claim filed successfully!",
                    request.getContextPath() + "/InsuranceMain.jsp", // Redirect URL
                    3 // Countdown duration in seconds
                );
            } 
            // Action to approve or deny an insurance claim
            else if ("approve".equals(action) || "deny".equals(action)) {
                // Retrieve form data for claim approval/denial
                int claimId = Integer.parseInt(request.getParameter("claimId"));
                String status = "approve".equals(action) ? "Approved" : "Denied"; // Determine the status

                // SQL query to update the claim status
                String sql = "UPDATE InsuranceClaim SET ClaimStatus = ? WHERE ClaimID = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, status);
                    stmt.setInt(2, claimId);
                    stmt.executeUpdate(); // Execute the query
                }

                // Send a response with a countdown and redirect
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                RedirectWithCountdown.send(
                    out,
                    "Redirecting...",
                    "Claim status updated successfully!",
                    request.getContextPath() + "/viewClaims.jsp", // Redirect URL
                    3 // Countdown duration in seconds
                );
            }
        } catch (SQLException e) {
            // Handle SQL exceptions and rethrow them as ServletExceptions
            throw new ServletException("Database error occurred: " + e.getMessage(), e);
        }
    }
}

