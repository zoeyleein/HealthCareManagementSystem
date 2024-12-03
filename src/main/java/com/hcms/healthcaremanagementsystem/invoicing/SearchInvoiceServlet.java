package com.hcms.healthcaremanagementsystem.invoicing;

import com.hcms.healthcaremanagementsystem.dbconnection.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServlet;

/**
 * Servlet to search and retrieve all unpaid invoices from the database.
 */
@WebServlet("/searchInvoice")
public class SearchInvoiceServlet extends HttpServlet {

    /**
     * Handles GET requests to retrieve unpaid invoices.
     * 
     * @param request  the HttpServletRequest object containing client request details
     * @param response the HttpServletResponse object to send the response to the client
     * @throws ServletException in case of a servlet-specific error
     * @throws IOException      in case of an I/O error
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // SQL query to fetch invoices with an 'Unpaid' status
        String sql = "SELECT * FROM Invoice WHERE PaymentStatus = 'Unpaid'";
        
        // List to hold unpaid invoice details
        ArrayList<Map<String, String>> unpaidInvoices = new ArrayList<>();

        try (
            // Establish a connection to the database
            Connection conn = DatabaseConnection.getConnection();
            
            // Prepare the SQL statement for execution
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            // Execute the query and retrieve results
            ResultSet rs = stmt.executeQuery()
        ) {
            // Iterate through the result set to process each unpaid invoice
            while (rs.next()) {
                // Map to hold individual invoice details
                Map<String, String> invoice = new HashMap<>();
                
                // Add relevant fields from the result set to the map
                invoice.put("InvoiceID", String.valueOf(rs.getInt("InvoiceID"))); // Invoice ID
                invoice.put("PatientID", String.valueOf(rs.getInt("PatientID"))); // Associated Patient ID
                invoice.put("TotalAmount", String.valueOf(rs.getDouble("TotalAmount"))); // Total amount of the invoice
                invoice.put("InvoiceDate", String.valueOf(rs.getTimestamp("InvoiceDate"))); // Date of invoice
                
                // Add the invoice map to the list
                unpaidInvoices.add(invoice);
            }
        } catch (SQLException e) {
            // Handle database exceptions and wrap them in a ServletException
            throw new ServletException("Database error occurred while retrieving unpaid invoices: " + e.getMessage(), e);
        }

        // Attach the list of unpaid invoices to the request object
        request.setAttribute("unpaidInvoices", unpaidInvoices);
        
        // Forward the request to the JSP page for displaying the unpaid invoices
        request.getRequestDispatcher("makePayment.jsp").forward(request, response);
    }
}

