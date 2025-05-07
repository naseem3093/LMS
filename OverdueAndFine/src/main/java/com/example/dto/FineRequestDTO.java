package com.example.dto;

import lombok.Data;

@Data
public class FineRequestDTO {
    private int transactionId;  // Requesting fine based on transaction

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
}
