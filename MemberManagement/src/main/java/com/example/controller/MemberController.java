package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.exceptions.MemberNotFoundException;
import com.example.model.Member;
import com.example.service.MemberService;

@RestController
@RequestMapping("/members")
public class MemberController {
	@Autowired
	private MemberService service;

	@PostMapping("/register")
	public String registerMember(@RequestBody Member member) {
		return service.registerMember(member);
	}

	@PutMapping("/update")
	public Member updateMember(@RequestBody Member member) {
		return service.updateMember(member);
	}

	@GetMapping("/fetchById/{id}")
	public Member getMember(@PathVariable("id") int memberId) throws MemberNotFoundException {
		return service.getMember(memberId);
	}

	@GetMapping("/fetchAll")
	public List<Member> getAllMembers() {
		return service.getAllMembers();
	}

	@PatchMapping("/changeStatus/{id}/{status}")
	public String changeMembershipStatus(@PathVariable("id") int memberId, @PathVariable("status") String status)
			throws MemberNotFoundException {
		return service.changeMembershipStatus(memberId, status);
	}

	@PatchMapping("/renew/{id}")
	public String renewMembership(@PathVariable("id") int memberId) throws MemberNotFoundException {
		return service.renewMembership(memberId);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteMember(@PathVariable("id") int memberId) {
		return service.deleteMember(memberId);
	}
}
