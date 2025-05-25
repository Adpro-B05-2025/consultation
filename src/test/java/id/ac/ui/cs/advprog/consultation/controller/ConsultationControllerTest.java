package id.ac.ui.cs.advprog.consultation.controller;

import id.ac.ui.cs.advprog.consultation.dto.ConsultationRequestDto;
import id.ac.ui.cs.advprog.consultation.dto.ConsultationResponseDto;
import id.ac.ui.cs.advprog.consultation.service.ConsultationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsultationControllerTest {

    @Mock
    private ConsultationService service;

    @InjectMocks
    private ConsultationController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() throws Exception {
        ConsultationRequestDto req = new ConsultationRequestDto();
        ConsultationResponseDto resp = new ConsultationResponseDto();
        when(service.create(req)).thenReturn(CompletableFuture.completedFuture(resp));

        CompletableFuture<ResponseEntity<ConsultationResponseDto>> result = controller.create(req);
        assertEquals(HttpStatus.CREATED, result.get().getStatusCode());
        assertEquals(resp, result.get().getBody());
        verify(service).create(req);
    }

    @Test
    void testGetById() throws Exception {
        ConsultationResponseDto resp = new ConsultationResponseDto();
        when(service.getById(1L)).thenReturn(CompletableFuture.completedFuture(resp));

        CompletableFuture<ConsultationResponseDto> result = controller.getById(1L);
        assertEquals(resp, result.get());
        verify(service).getById(1L);
    }

    @Test
    void testByPatient() throws Exception {
        List<ConsultationResponseDto> list = List.of(new ConsultationResponseDto());
        when(service.getByPatient(2L)).thenReturn(CompletableFuture.completedFuture(list));

        CompletableFuture<List<ConsultationResponseDto>> result = controller.byPatient(2L);
        assertEquals(list, result.get());
        verify(service).getByPatient(2L);
    }

    @Test
    void testByDoctor() throws Exception {
        List<ConsultationResponseDto> list = List.of(new ConsultationResponseDto());
        when(service.getByDoctor(3L)).thenReturn(CompletableFuture.completedFuture(list));

        CompletableFuture<List<ConsultationResponseDto>> result = controller.byDoctor(3L);
        assertEquals(list, result.get());
        verify(service).getByDoctor(3L);
    }

    @Test
    void testChangeStatus() throws Exception {
        ConsultationResponseDto resp = new ConsultationResponseDto();
        when(service.updateStatus(4L, "APPROVED")).thenReturn(CompletableFuture.completedFuture(resp));

        CompletableFuture<ConsultationResponseDto> result = controller.changeStatus(4L, "APPROVED");
        assertEquals(resp, result.get());
        verify(service).updateStatus(4L, "APPROVED");
    }

    @Test
    void testDelete() throws Exception {
        when(service.delete(5L)).thenReturn(CompletableFuture.completedFuture(null));

        CompletableFuture<ResponseEntity<Void>> result = controller.delete(5L);
        assertEquals(HttpStatus.NO_CONTENT, result.get().getStatusCode());
        verify(service).delete(5L);
    }
}
