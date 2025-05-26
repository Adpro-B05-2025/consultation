package id.ac.ui.cs.advprog.consultation.service;

import id.ac.ui.cs.advprog.consultation.dto.ConsultationRequestPacillianDto;
import id.ac.ui.cs.advprog.consultation.dto.ConsultationResponsePacillianDto;

import java.util.List;

public interface ConsultationPacillianService {
    ConsultationResponsePacillianDto createConsultation(ConsultationRequestPacillianDto request);
    ConsultationResponsePacillianDto getConsultationById(Long id);
    List<ConsultationResponsePacillianDto> getConsultationByPacillian(Long patientId);
    List<ConsultationResponsePacillianDto> getConsultationByDoctor(Long doctorId);
    void deleteConsultation(Long id);
    List<ConsultationResponsePacillianDto> getAllConsultation();
}
