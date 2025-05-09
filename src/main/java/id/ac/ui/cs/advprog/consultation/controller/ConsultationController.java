package id.ac.ui.cs.advprog.consultation.controller;

import id.ac.ui.cs.advprog.consultation.dto.ConsultationRequestDto;
import id.ac.ui.cs.advprog.consultation.dto.ConsultationResponseDto;
import id.ac.ui.cs.advprog.consultation.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService service;

    @PostMapping
    public ResponseEntity<ConsultationResponseDto> create(
            @RequestBody ConsultationRequestDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ConsultationResponseDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/patient/{pid}")
    public List<ConsultationResponseDto> byPatient(@PathVariable("pid") Long pid) {
        return service.getByPatient(pid);
    }

    @GetMapping("/doctor/{did}")
    public List<ConsultationResponseDto> byDoctor(@PathVariable("did") Long did) {
        return service.getByDoctor(did);
    }

    @PutMapping("/{id}/status")
    public ConsultationResponseDto changeStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return service.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
