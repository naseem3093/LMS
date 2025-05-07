package com.example.service;

import java.util.List;

import com.example.exceptions.MemberNotFoundException;
import com.example.model.Member;

public interface MemberService {
	public abstract  String registerMember(Member member); // Registers a new member

    public abstract  Member updateMember(Member member); // Updates member details

    public abstract  Member getMember(int memberId) throws MemberNotFoundException; // Fetches a member by ID

    public abstract  List<Member> getAllMembers(); // Retrieves all members

    public abstract  String changeMembershipStatus(int memberId, String status) throws MemberNotFoundException; // Activates/deactivates membership

    public abstract  String renewMembership(int memberId) throws MemberNotFoundException; // Renews membership and updates expiry date
    
    public abstract  String deleteMember(int memberId); // Deletes a member
}
