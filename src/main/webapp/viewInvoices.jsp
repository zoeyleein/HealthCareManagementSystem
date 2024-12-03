<%@ page import="java.sql.*, java.util.*" %>
<%@ page import="com.hcms.healthcaremanagementsystem.dbconnection.DatabaseConnection" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>View Invoices</title>
</head>
<body>
    <h1>All Invoices</h1>

    <%
        // List to store the retrieved invoices as maps
        List<Map<String, String>> invoices = new ArrayList<>();

        // SQL query to fetch invoice details along with patient names
        String sql = "SELECT i.InvoiceID, CONCAT(p.FirstName, ' ', p.LastName) AS PatientName, i.TotalAmount, i.InvoiceDate, i.PaymentStatus " +
                     "FROM Invoice i " +
                     "JOIN Patient p ON i.PatientID = p.PatientID";

        // Try-with-resources to ensure proper resource cleanup
        try (Connection conn = DatabaseConnection.getConnection(); // Establish database connection
             PreparedStatement stmt = conn.prepareStatement(sql); // Prepare the SQL statement
             ResultSet rs = stmt.executeQuery()) { // Execute the query

            // Process the ResultSet
            while (rs.next()) {
                // Map to store the invoice details
                Map<String, String> invoice = new HashMap<>();
                invoice.put("InvoiceID", String.valueOf(rs.getInt("InvoiceID"))); // Store InvoiceID
                invoice.put("PatientName", rs.getString("PatientName")); // Store PatientName
                invoice.put("TotalAmount", String.valueOf(rs.getDouble("TotalAmount"))); // Store TotalAmount
                invoice.put("InvoiceDate", String.valueOf(rs.getTimestamp("InvoiceDate"))); // Store InvoiceDate
                invoice.put("PaymentStatus", rs.getString("PaymentStatus")); // Store PaymentStatus
                invoices.add(invoice); // Add the map to the list
            }
        } catch (Exception e) {
            // Handle any errors that occur during database access
            out.println("<p>Error retrieving invoices: " + e.getMessage() + "</p>");
        }

        // Check if any invoices were retrieved
        if (invoices.isEmpty()) {
    %>
        <p>No invoices found.</p> <!-- Message if no invoices exist -->
    <%
        } else {
    %>
        <!-- Display the retrieved invoices in a table -->
        <table border="1">
            <tr>
                <th>Invoice ID</th>
                <th>Patient Name</th>
                <th>Total Amount</th>
                <th>Invoice Date</th>
                <th>Payment Status</th>
            </tr>
            <% for (Map<String, String> invoice : invoices) { %> <!-- Iterate over the list of invoices -->
                <tr>
                    <td><%= invoice.get("InvoiceID") %></td> <!-- Display InvoiceID -->
                    <td><%= invoice.get("PatientName") %></td> <!-- Display PatientName -->
                    <td>$<%= invoice.get("TotalAmount") %></td> <!-- Display TotalAmount -->
                    <td><%= invoice.get("InvoiceDate") %></td> <!-- Display InvoiceDate -->
                    <td><%= invoice.get("PaymentStatus") %></td> <!-- Display PaymentStatus -->
                </tr>
            <% } %>
        </table>
    <%
        }
    %>
    
    <!-- Button to navigate back to the Invoice Main Page -->
    <form action="InvoiceMain.jsp" method="get">
        <button type="submit">Back to Invoice Main Page</button>
    </form>

</body>
</html>


