package id.ac.ui.cs.advprog.consultation.controller;

import id.ac.ui.cs.advprog.consultation.dto.ConsultationRequestPacillianDto;
import id.ac.ui.cs.advprog.consultation.dto.ConsultationResponsePacillianDto;
import id.ac.ui.cs.advprog.consultation.service.ConsultationPacillianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/consultation-pacillian")
@RequiredArgsConstructor
public class ConsultationRequestController {

    private final ConsultationPacillianService service;

    @PostMapping
    public ResponseEntity<ConsultationResponsePacillianDto> create(@RequestBody ConsultationRequestPacillianDto dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized: JWT token missing or invalid");
        }

        String userIdString = (String) auth.getPrincipal();
        Long userId = Long.valueOf(userIdString);

        dto.setPatientId(userId);

        ConsultationResponsePacillianDto response = service.createConsultation(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public List<ConsultationResponsePacillianDto> getAll() {
        return service.getAllConsultation();
    }

    @GetMapping("/{id}")
    public ConsultationResponsePacillianDto getById(@PathVariable Long id) {
        return service.getConsultationById(id);
    }

    @GetMapping("/patient/{pid}")
    public List<ConsultationResponsePacillianDto> byPatient(@PathVariable("pid") Long pid) {
        return service.getConsultationByPacillian(pid);
    }

    @GetMapping("/doctor/{did}")
    public List<ConsultationResponsePacillianDto> byDoctor(@PathVariable("did") Long did) {
        return service.getConsultationByDoctor(did);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteConsultation(id);
        return ResponseEntity.noContent().build();
    }
}
