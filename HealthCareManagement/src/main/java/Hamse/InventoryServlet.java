package Hamse;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/InventoryServlet")
public class InventoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms?useSSL=false&serverTimezone=UTC", "root", "");
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM pharmacyinventory")) {

                List<InventoryItem> inventoryList = new ArrayList<>();

                while (rs.next()) {
                    int itemID = rs.getInt("ItemID");
                    String itemName = rs.getString("ItemName");
                    int quantity = rs.getInt("Quantity");
                    Date expirationDate = rs.getDate("ExpirationDate");
                    double unitPrice = rs.getDouble("UnitPrice");

                    InventoryItem item = new InventoryItem(itemID, itemName, quantity, expirationDate, unitPrice);
                    inventoryList.add(item);
                }

                request.setAttribute("inventoryList", inventoryList);
                request.getRequestDispatcher("inventory.jsp").forward(request, response);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().append("JDBC Driver not found: ").append(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().append("Database error: ").append(e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms?useSSL=false&serverTimezone=UTC", "root", "")) {

                if ("add".equals(action)) {
                    String itemName = request.getParameter("itemName");
                    int quantity = Integer.parseInt(request.getParameter("quantity"));
                    Date expirationDate = Date.valueOf(request.getParameter("expirationDate")); // format: yyyy-MM-dd
                    double unitPrice = Double.parseDouble(request.getParameter("unitPrice"));

                    try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO pharmacyinventory (ItemName, Quantity, ExpirationDate, UnitPrice) VALUES (?, ?, ?, ?)")) {
                        pstmt.setString(1, itemName);
                        pstmt.setInt(2, quantity);
                        pstmt.setDate(3, expirationDate);
                        pstmt.setDouble(4, unitPrice);
                        pstmt.executeUpdate();
                    }
                } else if ("remove".equals(action)) {
                    String removeItem = request.getParameter("removeItem");

                    try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM pharmacyinventory WHERE ItemName = ?")) {
                        pstmt.setString(1, removeItem);
                        pstmt.executeUpdate();
                    }
                }

                response.sendRedirect("InventoryServlet");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().append("JDBC Driver not found: ").append(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().append("Database error: ").append(e.getMessage());
        }
    }
}



