package id.ac.ui.cs.advprog.consultation.service;

import id.ac.ui.cs.advprog.consultation.dto.ConsultationRequestDto;
import id.ac.ui.cs.advprog.consultation.dto.ConsultationResponseDto;
import id.ac.ui.cs.advprog.consultation.entity.Consultation;
import id.ac.ui.cs.advprog.consultation.entity.Consultation.ConsultationStatus;
import id.ac.ui.cs.advprog.consultation.exception.BadRequestException;
import id.ac.ui.cs.advprog.consultation.exception.ResourceNotFoundException;
import id.ac.ui.cs.advprog.consultation.mapper.ConsultationMapper;
import id.ac.ui.cs.advprog.consultation.repository.ConsultationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepository repo;
    private final ConsultationMapper mapper;

    @Async("taskExecutor")
    @Override
    public CompletableFuture<ConsultationResponseDto> create(ConsultationRequestDto req) {
        Consultation cons = mapper.toEntity(req);
        cons.setRequestedAt(LocalDateTime.now());
        cons.setStatus(ConsultationStatus.PENDING);
        Consultation saved = repo.save(cons);
        return CompletableFuture.completedFuture(mapper.toDto(saved));
    }

    @Async("taskExecutor")
    @Override
    public CompletableFuture<ConsultationResponseDto> getById(Long id) {
        Consultation c = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Consultation", "id", id));
        return CompletableFuture.completedFuture(mapper.toDto(c));
    }

    @Async("taskExecutor")
    @Override
    public CompletableFuture<List<ConsultationResponseDto>> getByPatient(Long patientId) {
        List<ConsultationResponseDto> consultations = repo.findByPatientId(patientId).stream()
                   .map(mapper::toDto)
                   .collect(Collectors.toList());
        return CompletableFuture.completedFuture(consultations);
    }

    @Async("taskExecutor")
    @Override
    public CompletableFuture<List<ConsultationResponseDto>> getByDoctor(Long doctorId) {
        List<ConsultationResponseDto> consultations = repo.findByDoctorId(doctorId).stream()
                   .map(mapper::toDto)
                   .collect(Collectors.toList());
        return CompletableFuture.completedFuture(consultations);
    }

    @Async("taskExecutor")
    @Override
    public CompletableFuture<ConsultationResponseDto> updateStatus(Long id, String newStatus) {
        Consultation c = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Consultation", "id", id));

        ConsultationStatus statusEnum;
        try {
            statusEnum = ConsultationStatus.valueOf(newStatus.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid status: " + newStatus);
        }

        c.setStatus(statusEnum);
        Consultation updated = repo.save(c);
        return CompletableFuture.completedFuture(mapper.toDto(updated));
    }

    @Async("taskExecutor")
    @Override
    public CompletableFuture<Void> delete(Long id) {
        repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Consultation", "id", id));
        repo.deleteById(id);
        return CompletableFuture.completedFuture(null);
    }
}