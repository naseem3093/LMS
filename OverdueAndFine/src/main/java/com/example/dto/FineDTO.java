package com.example.dto;

import lombok.Data;

@Data
public class FineDTO {
    private int fineId;  // Unique ID for the fine
    private int transactionId;  // Links fine to a specific book transaction
    private double amount;  // Fine amount
    private String status;  // Fine status ("Pending", "Paid")
	public int getFineId() {
		return fineId;
	}
	public void setFineId(int fineId) {
		this.fineId = fineId;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
