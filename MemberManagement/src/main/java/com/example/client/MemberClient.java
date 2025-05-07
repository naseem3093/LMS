package com.example.client;

import com.example.dto.MemberDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "member-management", url = "http://localhost:8082")
public interface MemberClient {
    @GetMapping("/members/{memberId}")
    MemberDTO getMemberById(@PathVariable int memberId);
}
