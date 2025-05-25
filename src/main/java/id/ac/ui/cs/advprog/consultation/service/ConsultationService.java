package id.ac.ui.cs.advprog.consultation.service;

import id.ac.ui.cs.advprog.consultation.dto.ConsultationRequestDto;
import id.ac.ui.cs.advprog.consultation.dto.ConsultationResponseDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ConsultationService {
    CompletableFuture<ConsultationResponseDto> create(ConsultationRequestDto request);
    CompletableFuture<ConsultationResponseDto> getById(Long id);
    CompletableFuture<List<ConsultationResponseDto>> getByPatient(Long patientId);
    CompletableFuture<List<ConsultationResponseDto>> getByDoctor(Long doctorId);
    CompletableFuture<ConsultationResponseDto> updateStatus(Long id, String newStatus);
    CompletableFuture<Void> delete(Long id);
}
