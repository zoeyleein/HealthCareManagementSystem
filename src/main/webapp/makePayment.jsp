<%@ page import="java.sql.*, java.util.*" %>
<%@ page import="com.hcms.healthcaremanagementsystem.dbconnection.DatabaseConnection" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Make Payment</title>
</head>
<body>
    <h1>Unpaid Invoices</h1>

    <%
        // List to store unpaid invoices retrieved from the database
        List<Map<String, String>> unpaidInvoices = new ArrayList<>();
        try (
            // Establish a database connection
            Connection conn = DatabaseConnection.getConnection();

            // Prepare a SQL statement to fetch unpaid invoices with patient details
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT Invoice.InvoiceID, CONCAT(Patient.FirstName, ' ', Patient.LastName) AS PatientName, " +
                "Invoice.TotalAmount, Invoice.InvoiceDate " +
                "FROM Invoice " +
                "JOIN Patient ON Invoice.PatientID = Patient.PatientID " +
                "WHERE Invoice.PaymentStatus = 'Unpaid'"
            );

            // Execute the query and store the result set
            ResultSet rs = stmt.executeQuery()
        ) {

            // Process each row in the result set
            while (rs.next()) {
                // Create a map to store invoice details
                Map<String, String> invoice = new HashMap<>();
                invoice.put("InvoiceID", String.valueOf(rs.getInt("InvoiceID")));
                invoice.put("PatientName", rs.getString("PatientName"));
                invoice.put("TotalAmount", String.valueOf(rs.getDouble("TotalAmount")));
                invoice.put("InvoiceDate", String.valueOf(rs.getTimestamp("InvoiceDate")));
                // Add the invoice to the list
                unpaidInvoices.add(invoice);
            }
        } catch (Exception e) {
            // Display an error message if any exception occurs during database access
            out.println("<p>Error retrieving unpaid invoices: " + e.getMessage() + "</p>");
        }

        // Check if there are any unpaid invoices to display
        if (unpaidInvoices.isEmpty()) {
    %>
        <!-- Message displayed when no unpaid invoices are found -->
        <p>No unpaid invoices found.</p>
    <%
        } else {
    %>
        <!-- Table to display the list of unpaid invoices -->
        <table border="1">
            <tr>
                <th>Patient Name</th>
                <th>Total Amount</th>
                <th>Invoice Date</th>
                <th>Action</th>
            </tr>
            <% 
                // Loop through the list of unpaid invoices and display each one in a table row
                for (Map<String, String> invoice : unpaidInvoices) { 
            %>
                <tr>
                    <td><%= invoice.get("PatientName") %></td>
                    <td>$<%= invoice.get("TotalAmount") %></td>
                    <td><%= invoice.get("InvoiceDate") %></td>
                    <td>
                        <!-- Form to submit a payment request for the selected invoice -->
                        <form action="payInvoice" method="post">
                            <input type="hidden" name="invoiceID" value="<%= invoice.get("InvoiceID") %>">
                            <button type="submit">Pay Invoice</button>
                        </form>
                    </td>
                </tr>
            <% 
                } 
            %>
        </table>
    <%
        }
    %>

    <br>
    <!-- Button to navigate back to the Invoice Main page -->
    <button onclick="window.location.href='InvoiceMain.jsp'">Back to Invoice Main</button>
</body>
</html>


