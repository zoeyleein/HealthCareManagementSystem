<%@ page import="java.sql.*, com.hcms.healthcaremanagementsystem.dbconnection.DatabaseConnection" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Claims</title>
</head>
<body>
    <h1>All Insurance Claims</h1>
    
    <!-- Display the claims in a tabular format -->
    <table border="1">
        <thead>
            <tr>
                <!-- Table headers for displaying claim details -->
                <th>Patient Name</th>
                <th>Insurance Provider</th>
                <th>Claim Amount</th>
                <th>Claim Date</th>
                <th>Claim Status</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                // Initialize the database connection
                Connection conn = null;
                try {
                    // Obtain a connection to the database
                    conn = DatabaseConnection.getConnection();

                    // SQL query to fetch insurance claims and related patient details
                    String sql = "SELECT " +
                                 "CONCAT(p.FirstName, ' ', p.LastName) AS PatientName, " +
                                 "c.InsuranceProvider, c.ClaimAmount, c.ClaimDate, c.ClaimStatus, c.ClaimID " +
                                 "FROM InsuranceClaim c " +
                                 "JOIN Patient p ON c.PatientID = p.PatientID";

                    // Create a statement object to execute the query
                    Statement stmt = conn.createStatement();
                    // Execute the query and obtain the result set
                    ResultSet rs = stmt.executeQuery(sql);

                    // Iterate through the result set and display each claim in a table row
                    while (rs.next()) {
                        String patientName = rs.getString("PatientName");
                        String provider = rs.getString("InsuranceProvider");
                        double amount = rs.getDouble("ClaimAmount");
                        Timestamp date = rs.getTimestamp("ClaimDate");
                        String status = rs.getString("ClaimStatus");
                        int claimId = rs.getInt("ClaimID");
            %>
            <tr>
                <!-- Display the fetched claim details in table cells -->
                <td><%= patientName %></td>
                <td><%= provider %></td>
                <td><%= amount %></td>
                <td><%= date %></td>
                <td><%= status %></td>
                <td>
                    <!-- Form for approving or denying the claim -->
                    <form action="<%=request.getContextPath()%>/insurance" method="post" style="display:inline;">
                        <!-- Hidden input to pass claim ID -->
                        <input type="hidden" name="claimId" value="<%= claimId %>">
                        <!-- Approve button -->
                        <button type="submit" name="action" value="approve">Approve</button>
                        <!-- Deny button -->
                        <button type="submit" name="action" value="deny">Deny</button>
                    </form>
                </td>
            </tr>
            <%
                    }
                } catch (SQLException e) {
                    // Log any SQL exception that occurs
                    e.printStackTrace();
                } finally {
                    // Ensure the database connection is closed
                    DatabaseConnection.closeConnection();
                }
            %>
        </tbody>
    </table>

    <br>
    <!-- Button to navigate back to the main insurance page -->
    <form action="InsuranceMain.jsp" method="get">
        <button type="submit">Back to Main</button>
    </form>
</body>
</html>


