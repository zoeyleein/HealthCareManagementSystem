package com.hcms.healthcaremanagementsystem.invoicing;

import com.hcms.healthcaremanagementsystem.dbconnection.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServlet;

/**
 * Servlet for handling invoice payments.
 * Maps to the URL pattern "/payInvoice".
 */
@WebServlet("/payInvoice")
public class PayInvoiceServlet extends HttpServlet {
    
    /**
     * Handles POST requests to mark an invoice as paid.
     *
     * @param request  The HTTP request object containing the invoice ID parameter.
     * @param response The HTTP response object used to return feedback to the client.
     * @throws ServletException If a servlet-related error occurs.
     * @throws IOException      If an input/output error occurs.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the invoice ID from the request parameters
        String invoiceID = request.getParameter("invoiceID");

        // SQL query to update the payment status of the invoice to 'Paid'
        String sql = "UPDATE Invoice SET PaymentStatus = 'Paid' WHERE InvoiceID = ?";
        try (
            // Get a database connection
            Connection conn = DatabaseConnection.getConnection();
            // Prepare the SQL statement to prevent SQL injection
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            // Set the invoice ID parameter in the SQL query
            stmt.setInt(1, Integer.parseInt(invoiceID));

            // Execute the update query and get the number of affected rows
            int rowsUpdated = stmt.executeUpdate();

            // Set the response content type to HTML
            response.setContentType("text/html");

            // Write the response content
            response.getWriter().println("<html>");
            response.getWriter().println("<head><title>Invoice Payment</title></head>");
            response.getWriter().println("<body>");

            // Check if the invoice was successfully updated
            if (rowsUpdated > 0) {
                response.getWriter().println("<h1>Invoice ID " + invoiceID + " has been paid successfully!</h1>");
                // Redirect to the payment page after 3 seconds
                response.getWriter().println("<meta http-equiv='refresh' content='3;url=makePayment.jsp'>");
            } else {
                // Handle the case where the invoice ID was not found in the database
                response.getWriter().println("<h1>Error: Invoice ID " + invoiceID + " not found.</h1>");
            }

            response.getWriter().println("</body>");
            response.getWriter().println("</html>");
        } catch (SQLException e) {
            // Handle database-related errors and wrap them in a ServletException
            throw new ServletException("Database error occurred while updating the invoice: " + e.getMessage(), e);
        }
    }
}


