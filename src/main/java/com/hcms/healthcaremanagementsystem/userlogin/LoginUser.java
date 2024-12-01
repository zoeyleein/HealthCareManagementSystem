package com.hcms.healthcaremanagementsystem.userlogin;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.hcms.healthcaremanagementsystem.dbconnection.DatabaseConnection;

@WebServlet(name = "LoginUserServlet", value = "/login")
public class LoginUser extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean isValidUser = validateUser(username, password);

        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter()) {
            if (isValidUser) {
                out.println("<html><body>");
                out.println("<h1>Login Successful!</h1>");
                out.println("</body></html>");
            } else {
                out.println("<html><body>");
                out.println("<h1>Invalid Username or Password!</h1>");
                out.println("</body></html>");
            }
        }
    }

    private boolean validateUser(String username, String password) {
        boolean isValid = false;

        String query = "SELECT PasswordHash FROM UserAccount WHERE Username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedPasswordHash = rs.getString("PasswordHash");
                // Apply proper password hashing and checking here
                if (storedPasswordHash.equals(hashPassword(password))) {
                    isValid = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isValid;
    }

    private String hashPassword(String password) {
        // Implement actual password hashing here
        return password; // Placeholder for hashing logic
    }
}