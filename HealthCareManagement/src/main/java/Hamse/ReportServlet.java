package Hamse;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReportServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String reportType = request.getParameter("reportType"); // Getting the selected report type

        try {
            // Connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hms?useSSL=false&serverTimezone=UTC", "root", "");
                 Statement stmt = conn.createStatement()) {

                String reportContent = "";

                // Generate the report based on the selected report type
                if ("patientCount".equals(reportType)) {
                    reportContent = generatePatientCountReport(conn);
                } else if ("appointments".equals(reportType)) {
                    reportContent = generateAppointmentsReport(conn);
                } else if ("totalBilling".equals(reportType)) {
                    reportContent = generateTotalBillingReport(conn);
                }

                // Insert the generated report into the report_history table
                String insertQuery = "INSERT INTO report_history (report_type, report_content) VALUES (?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                    pstmt.setString(1, reportType);
                    pstmt.setString(2, reportContent);
                    pstmt.executeUpdate();
                }

                // Set the report content to the request for display in the same JSP
                request.setAttribute("reportContent", reportContent);
                request.getRequestDispatcher("report.jsp").forward(request, response);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.getWriter().append("Database error: ").append(e.getMessage());
        }
    }

    // Method to generate Patient Count Report
    private String generatePatientCountReport(Connection conn) throws SQLException {
        StringBuilder report = new StringBuilder();
        String query = "SELECT COUNT(*) AS patientCount FROM patient";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                report.append("Total Patients: ").append(rs.getInt("patientCount")).append("\n");
            }
        }
        return report.toString();
    }

    // Method to generate Appointments Report
    private String generateAppointmentsReport(Connection conn) throws SQLException {
        StringBuilder report = new StringBuilder();
        String query = "SELECT COUNT(*) AS appointmentCount FROM appointment";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                report.append("Total Appointments: ").append(rs.getInt("appointmentCount")).append("\n");
            }
        }
        return report.toString();
    }

    // Method to generate Total Billing Report
    private String generateTotalBillingReport(Connection conn) throws SQLException {
        StringBuilder report = new StringBuilder();
        String query = "SELECT SUM(TotalAmount) AS totalBilling FROM invoice";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                report.append("Total Billing Amount: $").append(rs.getDouble("totalBilling")).append("\n");
            }
        }
        return report.toString();
    }
}



