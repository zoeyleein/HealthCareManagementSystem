<%--
  Created by IntelliJ IDEA.
  User: cheng
  Date: 12/2/2024
  Time: 8:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Appointments</title>
</head>
<body>
<h1>Appointments</h1>
<table border="1">
    <tr>
        <th>Appointment ID</th>
        <th>Doctor</th>
        <th>Patient ID</th>
        <th>Start Time</th>
        <th>End Time</th>
        <th>Status</th>
    </tr>
    <c:forEach var="appointment" items="${appointments}">
        <tr>
            <td>${appointment.appointmentId}</td>
            <td>${appointment.doctorName}</td>
            <td>${appointment.patientId}</td>
            <td>${appointment.appointmentTime}</td>
            <td>${appointment.endTime}</td>
            <td>${appointment.status}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
