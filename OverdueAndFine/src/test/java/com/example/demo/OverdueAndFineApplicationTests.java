package com.example.demo;

import com.example.exceptions.FineNotFoundException;
import com.example.model.OverdueFine;
import com.example.repository.OverdueFineRepository;
import com.example.service.OverdueFineServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OverdueFineServiceImplTest {

    @Mock
    private OverdueFineRepository repository;

    @InjectMocks
    private OverdueFineServiceImpl fineService;

    private OverdueFine sampleFine;

    @BeforeEach
    void setUp() {
        sampleFine = new OverdueFine();
        sampleFine.setFineId(1);
        sampleFine.setTransactionId(100);
        sampleFine.setFineAmount(50.0);
        sampleFine.setFineStatus("Pending");
        sampleFine.setOverdueDays(10);
    }

    @Test
    void testGenerateFine() {
        when(repository.save(any(OverdueFine.class))).thenReturn(sampleFine);

        int fineId = fineService.generateFine(100);

        assertEquals(1, fineId);
        verify(repository, times(1)).save(any(OverdueFine.class));
    }

    @Test
    void testGetFineDetails_Success() throws FineNotFoundException {
        when(repository.findByTransactionId(1)).thenReturn(Optional.of(sampleFine));

        OverdueFine retrievedFine = fineService.getFineDetails(1);

        assertNotNull(retrievedFine);
        assertEquals(1, retrievedFine.getFineId());
    }

    @Test
    void testGetFineDetails_NotFound() {
        when(repository.findByTransactionId(1)).thenReturn(Optional.empty());

        assertThrows(FineNotFoundException.class, () -> fineService.getFineDetails(1));
    }

    @Test
    void testTrackOverdueFines() {
        when(repository.findOverdueTransactions(LocalDate.now())).thenReturn(List.of(sampleFine));

        fineService.trackOverdueFines();

        verify(repository, times(1)).save(sampleFine);
    }

    @Test
    void testPayFine_Success() throws FineNotFoundException {
        when(repository.findById(1)).thenReturn(Optional.of(sampleFine));

        String result = fineService.payFine(1, 50.0);

        assertEquals("Fine paid successfully!", result);
        verify(repository, times(1)).save(sampleFine);
    }

    @Test
    void testPayFine_InsufficientAmount() throws FineNotFoundException {
        when(repository.findById(1)).thenReturn(Optional.of(sampleFine));

        String result = fineService.payFine(1, 40.0);

        assertEquals("Insufficient amount, please pay full fine!", result);
    }

    @Test
    void testGetPendingFines() {
        when(repository.findByFineStatus("Pending")).thenReturn(List.of(sampleFine));

        List<OverdueFine> pendingFines = fineService.getPendingFines();

        assertFalse(pendingFines.isEmpty());
        assertEquals(1, pendingFines.size());
    }
}
