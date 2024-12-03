package com.hcms.healthcaremanagementsystem.invoicing;

import com.hcms.healthcaremanagementsystem.dbconnection.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

// Servlet for handling patient search functionality
@WebServlet("/searchPatient")
public class SearchPatientServlet extends jakarta.servlet.http.HttpServlet {
    
    // Handles GET requests for searching patients
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Extract the search query parameter from the request
        String query = request.getParameter("query");
        
        // List to hold the results of the patient search
        ArrayList<Map<String, String>> patients = new ArrayList<>();

        // Debugging: Log the received search query
        System.out.println("Search Query: " + query);

        // Proceed only if the query parameter is not null or empty
        if (query != null && !query.trim().isEmpty()) {
            try (Connection conn = DatabaseConnection.getConnection()) { // Establish a database connection
                // SQL query to find patients by first or last name
                String sql = "SELECT PatientID, CONCAT(FirstName, ' ', LastName) AS FullName " +
                             "FROM Patient WHERE FirstName LIKE ? OR LastName LIKE ?";
                
                // Prepare the SQL statement with placeholders
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    // Set the query parameter values in the SQL statement
                    stmt.setString(1, "%" + query + "%");
                    stmt.setString(2, "%" + query + "%");

                    // Execute the query and process the results
                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            // Debugging: Log each found patient's full name
                            System.out.println("Found Patient: " + rs.getString("FullName"));

                            // Create a map for the patient data and add it to the list
                            Map<String, String> patient = new HashMap<>();
                            patient.put("id", rs.getString("PatientID"));
                            patient.put("name", rs.getString("FullName"));
                            patients.add(patient);
                        }
                    }
                }
            } catch (Exception e) { // Catch and handle any exceptions
                // Log the stack trace for debugging purposes
                e.printStackTrace();

                // Rethrow the exception as a ServletException with a meaningful error message
                throw new ServletException("Error searching patients: " + e.getMessage());
            }
        }

        // Set the response type to JSON
        response.setContentType("application/json");
        
        // Convert the patient list to JSON and write it to the response
        response.getWriter().write(new Gson().toJson(patients));
    }
}

