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

            if (isValidUser) {
                String loginRole = checkUserRoles(username);
                switch (loginRole) {
                    case "Admin" -> {
                        String contextPath = request.getContextPath();
                        response.sendRedirect(contextPath + "/patient-management");
                    }
                    case "Doctor" -> {
                        String contextPath = request.getContextPath();
                        response.sendRedirect(contextPath + "/doctor-portal");
                    }
                    case "Patient" -> {
                        String contextPath = request.getContextPath();
                        response.sendRedirect(contextPath + "/patient-portal");
                    }
                }

            } else {
                response.setContentType("text/html");
                try (PrintWriter out = response.getWriter()) {
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
                String storedPassword = rs.getString("PasswordHash");
                if (storedPassword.equals(checkPassword(password))) {
                    isValid = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isValid;
    }

    private String checkPassword(String password) {
        return password; // Placeholder for hashing logic
    }

    private String checkUserRoles(String username) {
        String loginRole = null;
        String query = "SELECT Role FROM UserAccount WHERE Username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String role = rs.getString("Role");
                switch (role) {
                    case "Admin" -> loginRole = "Admin";
                    case "Doctor" -> loginRole = "Doctor";
                    case "Patient" -> loginRole = "Patient";
                    default -> {
                        // Handle unknown role
                        return "Unknown Role: " + role;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return loginRole;
    }

}