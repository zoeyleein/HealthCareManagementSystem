package com.hcms.healthcaremanagementsystem.dto;

import java.util.Date;

public class Invoice {
    private int invoiceID;
    private Date date;
    private PatientDTO patient;
    private double totalAmount;
    private boolean isPaid;

    public Invoice(int invoiceID, Date date, PatientDTO patient, double totalAmount) {
        this.invoiceID = invoiceID;
        this.date = date;
        this.patient = patient;
        this.totalAmount = totalAmount;
        this.isPaid = false;
    }

    public void generateInvoice() {
        System.out.println("Invoice generated for Patient: " + (patient.getFirstName()+ " " + patient.getLastName()));
        System.out.println("Total Amount: $" + totalAmount);
    }

    public void makePayment() {
        if (!isPaid) {
            isPaid = true;
            System.out.println("Payment completed for Invoice ID: " + invoiceID);
        } else {
            System.out.println("Invoice already paid.");
        }
    }

	public int getInvoiceID() {
		return invoiceID;
	}

	public void setInvoiceID(int invoiceID) {
		this.invoiceID = invoiceID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public PatientDTO getPatient() {
		return patient;
	}

	public void setPatient(PatientDTO patient) {
		this.patient = patient;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
}