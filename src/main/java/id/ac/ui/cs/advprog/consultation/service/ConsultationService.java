package id.ac.ui.cs.advprog.consultation.service;

import id.ac.ui.cs.advprog.consultation.dto.ConsultationRequestDto;
import id.ac.ui.cs.advprog.consultation.dto.ConsultationResponseDto;

import java.util.List;

public interface ConsultationService {
    ConsultationResponseDto create(ConsultationRequestDto request);
    ConsultationResponseDto getById(Long id);
    List<ConsultationResponseDto> getByPatient(Long patientId);
    List<ConsultationResponseDto> getByDoctor(Long doctorId);
    ConsultationResponseDto updateStatus(Long id, String newStatus);
    void delete(Long id);
    List<ConsultationResponseDto> getAll();
}
