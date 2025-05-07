package com.example.controller;

import com.example.exceptions.FineNotFoundException;
import com.example.model.OverdueFine;
import com.example.service.OverdueFineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fines")
public class OverdueFineController {

	@Autowired
    private OverdueFineService service;  // ✅ Remove FineService injection

    @PostMapping("/generate/{transactionId}")
    public int generateFine(@PathVariable int transactionId) {
        return service.generateFine(transactionId);  // ✅ Calls service method
    }

    @PostMapping("/trackOverdue")
    public String trackOverdueFines() {
        service.trackOverdueFines();
        return "Overdue fines have been processed successfully!";
    }

    @GetMapping("/{fineId}")
    public OverdueFine getFineDetails(@PathVariable int fineId) throws FineNotFoundException {
        return service.getFineDetails(fineId);
    }

    @PatchMapping("/pay/{fineId}/{amountPaid}")
    public String payFine(@PathVariable("fineId") int fineId, @PathVariable("amountPaid") double amountPaid) throws FineNotFoundException {
        return service.payFine(fineId, amountPaid);  // ✅ Calls service method with amount
    }


    @GetMapping("/pending")
    public List<OverdueFine> getPendingFines() {
        return service.getPendingFines();
    }
}
