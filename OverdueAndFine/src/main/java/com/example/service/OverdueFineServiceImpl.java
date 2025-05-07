package com.example.service;

import com.example.exceptions.FineNotFoundException;
import com.example.model.OverdueFine;
import com.example.repository.OverdueFineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OverdueFineServiceImpl implements OverdueFineService {

    @Autowired
    private OverdueFineRepository repository;  // ✅ Removed duplicate repository injection

    public int generateFine(int transactionId) {
        OverdueFine fine = new OverdueFine();  // ✅ Fixed entity type
        fine.setTransactionId(transactionId);
        fine.setFineAmount(calculateFine(transactionId));  // ✅ Dynamic calculation
        fine.setFineStatus("Pending");

        OverdueFine savedFine = repository.save(fine);
        return savedFine.getFineId();  // ✅ Returns generated Fine ID
    }

    private double calculateFine(int transactionId) {
        Optional<OverdueFine> transaction = repository.findByTransactionId(transactionId);
        if (transaction.isPresent()) {
            int overdueDays = transaction.get().getOverdueDays();
            return overdueDays * 5.0;  // ✅ ₹5 per overdue day
        }
        return 50.0;  // ✅ Default fine if transaction isn't found
    }

    @Override
    public void trackOverdueFines() {
        List<OverdueFine> overdueTransactions = repository.findOverdueTransactions(LocalDate.now());
        for (OverdueFine fine : overdueTransactions) {
            int overdueDays = fine.getOverdueDays();
            double fineAmount = overdueDays * 5.0;
            fine.setFineAmount(fineAmount);
            fine.setFineStatus("Pending");
            repository.save(fine);
        }
    }

    @Override
    public OverdueFine getFineDetails(int fineId) throws FineNotFoundException {
        Optional<OverdueFine> fine = repository.findByTransactionId(fineId);
        return fine.orElseThrow(() -> new FineNotFoundException("Fine not found for transaction ID: " + fineId));
    }

    @Override
    public String payFine(int fineId, double amountPaid) throws FineNotFoundException {
        Optional<OverdueFine> fine = repository.findById(fineId);
        if (fine.isPresent()) {
            OverdueFine overdueFine = fine.get();
            
            if (amountPaid >= overdueFine.getFineAmount()) {  // ✅ Checks payment amount
                overdueFine.setFineStatus("Paid");
                repository.save(overdueFine);
                return "Fine paid successfully!";
            } else {
                return "Insufficient amount, please pay full fine!";
            }
        } else {
            throw new FineNotFoundException("Fine ID " + fineId + " not found.");
        }
    }


    @Override
    public List<OverdueFine> getPendingFines() {
        return repository.findByFineStatus("Pending");
    }
}
