package Hamse;
import java.sql.*;

public class ReportsHelper {

    public static String generateReport(String reportType, Connection conn) throws SQLException {
        switch (reportType) {
            case "patientCount":
                return generatePatientCountReport(conn);
            case "appointments":
                return generateAppointmentsReport(conn);
            case "totalBilling":
                return generateTotalBillingReport(conn);
            case "expiredItems":
                return generateExpiredItemsReport(conn);
            case "lowStock":
                return generateLowStockReport(conn);
            case "financialSummary":
                return generateFinancialSummaryReport(conn);
            default:
                return "Invalid Report Type";
        }
    }

    private static String generatePatientCountReport(Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) AS patientCount FROM patient";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return "Total Patient Count: " + rs.getInt("patientCount");
            }
        }
        return "No data available for Patient Count.";
    }

    private static String generateAppointmentsReport(Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) AS appointmentCount FROM appointment";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return "Total Appointments: " + rs.getInt("appointmentCount");
            }
        }
        return "No data available for Appointments.";
    }

    private static String generateTotalBillingReport(Connection conn) throws SQLException {
        String query = "SELECT SUM(amount) AS totalBilling FROM invoice";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return "Total Billing Amount: " + rs.getDouble("totalBilling");
            }
        }
        return "No data available for Total Billing.";
    }

    private static String generateExpiredItemsReport(Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) AS expiredItems FROM pharmacyinventory WHERE expiration_date < CURDATE()";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return "Expired Items: " + rs.getInt("expiredItems");
            }
        }
        return "No expired items found.";
    }

    private static String generateLowStockReport(Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) AS lowStock FROM pharmacyinventory WHERE stock_quantity < minimum_stock_level";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return "Low Stock Items: " + rs.getInt("lowStock");
            }
        }
        return "No low stock items found.";
    }

    private static String generateFinancialSummaryReport(Connection conn) throws SQLException {
        String query = "SELECT SUM(amount) AS totalBilling, COUNT(*) AS totalInvoices FROM invoice";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return "Financial Summary: Total Billing Amount: " + rs.getDouble("totalBilling") +
                       ", Total Invoices: " + rs.getInt("totalInvoices");
            }
        }
        return "No data available for Financial Summary.";
    }
}
