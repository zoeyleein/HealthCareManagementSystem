<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Generate Report</title>
</head>
<body>
    <form action="ReportServlet" method="get">
        <label for="reportType">Select Report Type:</label>
        <select name="reportType" id="reportType">
            <option value="patientCount">Patient Count</option>
            <option value="appointments">Appointments</option>
            <option value="totalBilling">Total Billing</option>
        </select>
        <input type="submit" value="Generate Report">
    </form>

    <h3>Report Output:</h3>
    <textarea rows="10" cols="80" readonly>
        ${reportContent}
    </textarea>
</body>
</html>



