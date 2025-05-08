package com.example.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "overdue-fines", url = "http://localhost:8088")
public interface OverdueFinesClient {
	@PostMapping("/fines/generate/{transactionId}")
	int generateFine(@PathVariable int transactionId); // ✅ Returns only Fine ID

}
