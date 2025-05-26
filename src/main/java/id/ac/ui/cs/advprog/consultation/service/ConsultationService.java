package id.ac.ui.cs.advprog.consultation.service;

import id.ac.ui.cs.advprog.consultation.dto.ConsultationRequestDto;
import id.ac.ui.cs.advprog.consultation.dto.ConsultationResponseDto;

import java.util.List;

public interface ConsultationService {
<<<<<<< HEAD
    List<ConsultationResponseDto> getAllConsultations();

    ConsultationResponseDto create(ConsultationRequestDto request);

    ConsultationResponseDto getById(Long id);

    List<ConsultationResponseDto> getByPatient(Long patientId);

    List<ConsultationResponseDto> getByDoctor(Long doctorId);

    ConsultationResponseDto updateStatus(Long id, String newStatus);

    void delete(Long id);

    ConsultationResponseDto scheduleConsultation(Long id, String scheduledAt);
=======
    ConsultationResponseDto create(ConsultationRequestDto request);
    ConsultationResponseDto getById(Long id);
    List<ConsultationResponseDto> getByPatient(Long patientId);
    List<ConsultationResponseDto> getByDoctor(Long doctorId);
    ConsultationResponseDto updateStatus(Long id, String newStatus);
    void delete(Long id);
    List<ConsultationResponseDto> getAll();
>>>>>>> 2e29ec9bc52a6308dfb174f03ed801fa11720164
}
