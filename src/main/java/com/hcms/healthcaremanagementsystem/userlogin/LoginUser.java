package com.hcms.healthcaremanagementsystem.userlogin;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "LoginUserServlet", value = "/login")
public class LoginUser extends HttpServlet {

    private final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl"; // From dataSource.xml
    private final String DB_USER = "C##HMS_USER"; // Placeholder for actual username
    private final String DB_PASSWORD = "hms_password"; // Placeholder for actual password

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean isValidUser = validateUser(username, password);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

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

    private boolean validateUser(String username, String password) {
        boolean isValid = false;

        String query = "SELECT PasswordHash FROM UserAccount WHERE Username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            Class.forName("oracle.jdbc.OracleDriver"); // Ensure Driver is loaded

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedPasswordHash = rs.getString("PasswordHash");
                // Apply proper password hashing and checking here
                if (storedPasswordHash.equals(hashPassword(password))) {
                    isValid = true;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return isValid;
    }

    private String hashPassword(String password) {
        // Implement actual password hashing here
        return password; // Placeholder for hashing logic
    }
}