package id.ac.ui.cs.advprog.consultation.controller;

import id.ac.ui.cs.advprog.consultation.dto.ConsultationRequestDto;
import id.ac.ui.cs.advprog.consultation.dto.ConsultationResponseDto;
import id.ac.ui.cs.advprog.consultation.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
<<<<<<< HEAD

import java.util.List;

=======
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
>>>>>>> 2e29ec9bc52a6308dfb174f03ed801fa11720164
@RestController
@RequestMapping("/api/consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService service;

    @PostMapping
<<<<<<< HEAD
    public ResponseEntity<ConsultationResponseDto> create(
            @RequestBody ConsultationRequestDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
=======
    public ResponseEntity<ConsultationResponseDto> create(@RequestBody ConsultationRequestDto dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized: JWT token missing or invalid");
        }

        String userIdString = (String) auth.getPrincipal();
        Long userId = Long.valueOf(userIdString);

        dto.setPatientId(userId);

        ConsultationResponseDto response = service.create(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public List<ConsultationResponseDto> getAll() {
        return service.getAll();
>>>>>>> 2e29ec9bc52a6308dfb174f03ed801fa11720164
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
<<<<<<< HEAD

    @GetMapping
    public ResponseEntity<List<ConsultationResponseDto>> getAllConsultations() {
        return ResponseEntity.ok(service.getAllConsultations());
    }
=======
>>>>>>> 2e29ec9bc52a6308dfb174f03ed801fa11720164
}
