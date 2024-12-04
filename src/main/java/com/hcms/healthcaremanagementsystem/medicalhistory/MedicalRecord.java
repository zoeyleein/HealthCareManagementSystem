package com.hcms.healthcaremanagementsystem.medicalhistory;

import java.sql.Timestamp;

public class MedicalRecord {
    private int recordID;
    private int patientID;
    private String diagnosis;
    private String treatment;
    private String testResults;
    private Timestamp recordDate;

 /*   public MedicalRecord(int patientID, String diagnosis, String treatment, String testResults) {
        setPatientID(patientID);
        setDiagnosis(diagnosis);
        setTreatment(treatment);
        setTestResults(testResults);
    }
*/
    public MedicalRecord(int recordID, int patientID, String diagnosis, String treatment, String testResults) {
        setRecordID(recordID);
        setPatientID(patientID);
        setDiagnosis(diagnosis);
        setTreatment(treatment);
        setTestResults(testResults);
    }
/*
    public MedicalRecord(int recordID, int patientID, String diagnosis, String treatment, String testResults, Timestamp recordDate) {
        setRecordID(recordID);
        setPatientID(patientID);
        setDiagnosis(diagnosis);
        setTreatment(treatment);
        setTestResults(testResults);
        setRecordDate(recordDate);
    }
*/
    // Getters
    public int getRecordID() {
        return recordID;
    }

    public int getPatientID() {
        return patientID;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public String getTestResults() {
        return testResults;
    }

    public Timestamp getRecordDate() {
        return recordDate;
    }

    // Setters
    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public void setTestResults(String testResults) {
        this.testResults = testResults;
    }

    public void setRecordDate(Timestamp recordDate) {
        this.recordDate = recordDate;
    }
}
