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


//
//### **🚀 POST: Register a New Member**
//**URL:** `http://localhost:8082/members/register`  
//**Method:** `POST`  
//**JSON Payload:**  
//```json
//{
//    "memberId": 1,
//    "name": "John Doe",
//    "email": "john.doe@example.com",
//    "phone": "9876543210",
//    "membershipStatus": "Active",
//    "membershipExpiryDate": "2025-12-31"
//}
//```
//
//---
//
//### **✏️ PUT: Update Member Profile**
//**URL:** `http://localhost:8082/members/update`  
//**Method:** `PUT`  
//**JSON Payload:**  
//```json
//{
//    "memberId": 1,
//    "name": "John Doe",
//    "email": "john.doe@newmail.com",
//    "phone": "9876543210",
//    "membershipStatus": "Active",
//    "membershipExpiryDate": "2026-12-31"
//}
//```
//
//---
//
//### **🔍 GET: Fetch Member by ID**
//**URL:** `http://localhost:8082/members/fetchById/1`  
//**Method:** `GET`  
//(No Body Required)
//
//---
//
//### **📃 GET: Fetch All Members**
//**URL:** `http://localhost:8082/members/fetchAll`  
//**Method:** `GET`  
//(No Body Required)
//
//---
//
//### **🔄 PATCH: Change Membership Status**
//**URL:** `http://localhost:8082/members/changeStatus/1/Inactive`  
//**Method:** `PATCH`  
//(No Body Required)  
//🔹 Change `"Inactive"` to `"Active"` or `"Expired"` as needed.
//
//---
//
//### **🆕 PATCH: Renew Membership**
//**URL:** `http://localhost:8082/members/renew/1`  
//**Method:** `PATCH`  
//(No Body Required)  
//🔹 Auto-extends **expiry date** upon renewal.
//
//---
//
//### **🗑️ DELETE: Remove a Member**
//**URL:** `http://localhost:8082/members/delete/1`  
//**Method:** `DELETE`  
//(No Body Required)  
//
//Now you can test all these endpoints in **Postman**! 🚀 If anything needs tweaking or debugging, just let me know. You're building an amazing microservice! 😃  
//
