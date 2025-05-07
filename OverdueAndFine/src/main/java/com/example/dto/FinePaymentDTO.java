package com.example.dto;

import lombok.Data;

@Data
public class FinePaymentDTO {
	private int fineId;  // ID of the fine to be paid
    private double amountPaid;  // Payment amount
    public int getFineId() {
		return fineId;
	}
	public void setFineId(int fineId) {
		this.fineId = fineId;
	}
	public double getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}
	
}
