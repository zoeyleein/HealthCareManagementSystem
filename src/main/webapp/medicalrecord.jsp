<%@ page import="java.util.List" %>
<%@ page import="com.hcms.healthcaremanagementsystem.medicalhistory.MedicalRecord" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Medical Records</title>
</head>
<body>
<h1>Medical Records</h1>
<form action="medicalRecord" method="GET">
  <button type="submit">Refresh Records</button>
</form>
<table border="1">
  <thead>
  <tr>
    <th>Record ID</th>
    <th>Patient ID</th>
    <th>Diagnosis</th>
    <th>Treatment</th>
    <th>Test Results</th>
    <th>Record Date</th>
    <th>Actions</th>
  </tr>
  </thead>
  <tbody>
  <%
    List<MedicalRecord> records = (List<MedicalRecord>) request.getAttribute("records");
    if (records != null) {
      for (MedicalRecord record : records) {
  %>
  <tr>
    <td><%= record.getRecordID() %></td>
    <td><%= record.getPatientID() %></td>
    <td><%= record.getDiagnosis() %></td>
    <td><%= record.getTreatment() %></td>
    <td><%= record.getTestResults() %></td>
    <td><%= record.getRecordDate() %></td>
    <td>
      <form action="medicalRecord" method="post" style="display:inline;">
        <input type="hidden" name="recordID" value="<%= record.getRecordID() %>">
        <button type="submit" name="action" value="delete">Delete</button>
      </form>
    </td>
  </tr>
  <%
      }
    }
  %>
  </tbody>
</table>
<h2>Add New Record</h2>
<form action="medicalRecord" method="post">
  <input type="hidden" name="action" value="add">
  <label for="patientID">Patient ID:</label>
  <input type="text" id="patientID" name="patientID" required><br><br>

  <label for="diagnosis">Diagnosis:</label>
  <input type="text" id="diagnosis" name="diagnosis" required><br><br>

  <label for="treatment">Treatment:</label>
  <input type="text" id="treatment" name="treatment" required><br><br>

  <label for="testResults">Test Results:</label>
  <input type="text" id="testResults" name="testResults" required><br><br>

  <button type="submit">Add Record</button>
</form>
</body>
</html>
