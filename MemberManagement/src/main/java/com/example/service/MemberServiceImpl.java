package com.example.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exceptions.MemberNotFoundException;
import com.example.model.Member;
import com.example.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository repository;

    @Override
    public String registerMember(Member member) {
        // Set initial membership expire date (1 year from now)
        member.setMembershipExpiryDate(LocalDate.now().plusYears(1));
        repository.save(member);
        return "Member registered successfully!";
    }

    @Override
    public Member updateMember(Member member) {
        return repository.save(member);
    }

    @Override
    public Member getMember(int memberId) throws MemberNotFoundException {
        Optional<Member> optional = repository.findById(memberId);
        if (optional.isPresent())
            return optional.get();
        else
            throw new MemberNotFoundException("Member ID is not valid");
    }

    @Override
    public List<Member> getAllMembers() {
        return repository.findAll();
    }

    @Override
    public String changeMembershipStatus(int memberId, String status) throws MemberNotFoundException {
        Member member = getMember(memberId);
        member.setMembershipStatus(status);
        repository.save(member);
        return "Membership status updated successfully!";
    }

    @Override
    public String renewMembership(int memberId) throws MemberNotFoundException {
        Member member = getMember(memberId);
        // Extend membership for another year
        member.setMembershipExpiryDate(LocalDate.now().plusYears(1));
        repository.save(member);
        return "Membership renewed successfully!";
    }

    @Override
    public String deleteMember(int memberId) {
        repository.deleteById(memberId);
        return "Member deleted successfully!";
    }
}
