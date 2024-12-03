<%--
  Created by IntelliJ IDEA.
  User: cheng
  Date: 12/2/2024
  Time: 8:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Appointment</title>
</head>
<body>
<h1>Create Appointment</h1>
<form action="appointments" method="post">
    <label for="patientId">Patient ID:</label>
    <input type="text" id="patientId" name="patientId" required><br><br>

    <label for="doctorName">Doctor Name:</label>
    <input type="text" id="doctorName" name="doctorName" required><br><br>

    <label for="appointmentTime">Appointment Time:</label>
    <input type="datetime-local" id="appointmentTime" name="appointmentTime" required><br><br>

    <label for="endTime">End Time:</label>
    <input type="datetime-local" id="endTime" name="endTime" required><br><br>

    <label for="status">Status:</label>
    <input type="text" id="status" name="status" required><br><br>

    <button type="submit">Create Appointment</button>
</form>
<% if (request.getParameter("error") != null) { %>
<p style="color:red;">Error creating appointment. Please try again.</p>
<% } %>
</body>
</html>

