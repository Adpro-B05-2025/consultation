package id.ac.ui.cs.advprog.consultation.controller;

import id.ac.ui.cs.advprog.consultation.dto.ConsultationRequestDto;
import id.ac.ui.cs.advprog.consultation.dto.ConsultationResponseDto;
import id.ac.ui.cs.advprog.consultation.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService service;

    @PostMapping
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
