package com.hcms.healthcaremanagementsystem.medicalhistory;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/medicalRecord")
public class MedicalRecordServlet extends HttpServlet {
    private MedicalRecordDAO medicalRecordDAO;

    @Override
    public void init() {
        try {
            medicalRecordDAO = new MedicalRecordDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<MedicalRecord> records = medicalRecordDAO.getAllRecords();
            req.setAttribute("records", records);
            RequestDispatcher rd = req.getRequestDispatcher("medicalrecord.jsp");
            rd.forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error retrieving medical records", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if (action.equals("add")) {
                addRecord(req);
            } else if (action.equals("update")) {
                updateRecord(req);
            } else if (action.equals("delete")) {
                deleteRecord(req);
            }
            resp.sendRedirect("medicalrecord.jsp");
        } catch (Exception e) {
            throw new ServletException("Error processing request", e);
        }
    }

    private void addRecord(HttpServletRequest req) throws Exception {
        int patientID = Integer.parseInt(req.getParameter("patientID"));
        String diagnosis = req.getParameter("diagnosis");
        String treatment = req.getParameter("treatment");
        String testResults = req.getParameter("testResults");

        MedicalRecord record = new MedicalRecord(0, patientID, diagnosis, treatment, testResults); // ID will be set by DB
        record.setPatientID(patientID);
        record.setDiagnosis(diagnosis);
        record.setTreatment(treatment);
        record.setTestResults(testResults);

        medicalRecordDAO.addRecord(record);
    }

    private void updateRecord(HttpServletRequest req) throws Exception {
        int recordID = Integer.parseInt(req.getParameter("recordID"));
        int patientID = Integer.parseInt(req.getParameter("patientID"));
        String diagnosis = req.getParameter("diagnosis");
        String treatment = req.getParameter("treatment");
        String testResults = req.getParameter("testResults");

        MedicalRecord record = new MedicalRecord(recordID, patientID, diagnosis, treatment, testResults);
        record.setRecordID(recordID);
        record.setPatientID(patientID);
        record.setDiagnosis(diagnosis);
        record.setTreatment(treatment);
        record.setTestResults(testResults);

        medicalRecordDAO.updateRecord(record);
    }

    private void deleteRecord(HttpServletRequest req) throws Exception {
        int recordID = Integer.parseInt(req.getParameter("recordID"));
        medicalRecordDAO.deleteRecord(recordID);
    }
}
