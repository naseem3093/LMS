package com.example.dto;

//import lombok.Data;
//
//@Data
public class MemberDTO {
    private int memberId;
    private String name;
    private String membershipStatus;
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
	public String getMembershipStatus() {
		return membershipStatus;
	}
	public void setMembershipStatus(String membershipStatus) {
		this.membershipStatus = membershipStatus;
	}
    
}
