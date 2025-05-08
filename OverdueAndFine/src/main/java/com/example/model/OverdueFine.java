package com.example.model;

import jakarta.persistence.*;
//import lombok.Data;
import java.time.LocalDate;

//@Data
@Entity
@Table(name = "overdue_fine")
public class OverdueFine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int fineId;

	private int transactionId;
	private int memberId;

	private double fineAmount;
	private int overdueDays;

	@Column(name = "fine_status")
	private String fineStatus; // Pending, Paid

	private LocalDate fineIssuedDate;

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

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public double getFineAmount() {
		return fineAmount;
	}

	public void setFineAmount(double fineAmount) {
		this.fineAmount = fineAmount;
	}

	public int getOverdueDays() {
		return overdueDays;
	}

	public void setOverdueDays(int overdueDays) {
		this.overdueDays = overdueDays;
	}

	public String getFineStatus() {
		return fineStatus;
	}

	public void setFineStatus(String fineStatus) {
		this.fineStatus = fineStatus;
	}

	public LocalDate getFineIssuedDate() {
		return fineIssuedDate;
	}

	public void setFineIssuedDate(LocalDate fineIssuedDate) {
		this.fineIssuedDate = fineIssuedDate;
	}

}
