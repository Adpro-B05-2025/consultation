package id.ac.ui.cs.advprog.consultation.controller;

import id.ac.ui.cs.advprog.consultation.dto.ConsultationRequestDto;
import id.ac.ui.cs.advprog.consultation.dto.ConsultationResponseDto;
import id.ac.ui.cs.advprog.consultation.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService service;

    @PostMapping
    public CompletableFuture<ResponseEntity<ConsultationResponseDto>> create(
            @RequestBody ConsultationRequestDto dto) {
        return service.create(dto)
                .thenApply(response -> new ResponseEntity<>(response, HttpStatus.CREATED));
    }

    @GetMapping("/{id}")
    public CompletableFuture<ConsultationResponseDto> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/patient/{pid}")
    public CompletableFuture<List<ConsultationResponseDto>> byPatient(@PathVariable("pid") Long pid) {
        return service.getByPatient(pid);
    }

    @GetMapping("/doctor/{did}")
    public CompletableFuture<List<ConsultationResponseDto>> byDoctor(@PathVariable("did") Long did) {
        return service.getByDoctor(did);
    }

    @PutMapping("/{id}/status")
    public CompletableFuture<ConsultationResponseDto> changeStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return service.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return service.delete(id)
                .thenApply(result -> ResponseEntity.noContent().build());
    }
}
