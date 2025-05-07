package com.example.service;

import com.example.exceptions.FineNotFoundException;
import com.example.model.OverdueFine;
import java.util.List;

public interface OverdueFineService {
	void trackOverdueFines(); // Identify overdue books and calculate fines

	public int generateFine(int transactionId);

	public abstract OverdueFine getFineDetails(int transactionId) throws FineNotFoundException;

	public abstract String payFine(int fineId, double fineAmount) throws FineNotFoundException;

	public abstract List<OverdueFine> getPendingFines();
}
