package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "member_info")
public class Member {
    @Id
    @Column(name = "member_id")
    private int memberId;

    private String name;
    public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMembershipStatus() {
		return membershipStatus;
	}

	public void setMembershipStatus(String membershipStatus) {
		this.membershipStatus = membershipStatus;
	}

	public LocalDate getMembershipExpiryDate() {
		return membershipExpiryDate;
	}

	public void setMembershipExpiryDate(LocalDate membershipExpiryDate) {
		this.membershipExpiryDate = membershipExpiryDate;
	}

	private String email;
    private String phone;
    
    @Column(name = "membership_status")
    private String membershipStatus; // Active, Inactive, Expired
    
    @Column(name = "membership_expiry_date")
    private LocalDate membershipExpiryDate; // Tracks membership duration
}
