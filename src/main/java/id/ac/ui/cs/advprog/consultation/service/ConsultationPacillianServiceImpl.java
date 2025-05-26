package id.ac.ui.cs.advprog.consultation.service;

import id.ac.ui.cs.advprog.consultation.dto.ConsultationRequestPacillianDto;
import id.ac.ui.cs.advprog.consultation.dto.ConsultationResponsePacillianDto;
import id.ac.ui.cs.advprog.consultation.entity.ConsultationPacillian;
import id.ac.ui.cs.advprog.consultation.exception.ResourceNotFoundException;
import id.ac.ui.cs.advprog.consultation.mapper.ConsultationPacillianMapper;
import id.ac.ui.cs.advprog.consultation.repository.ConsultationPacillianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultationPacillianServiceImpl implements ConsultationPacillianService {

    private final ConsultationPacillianRepository repo;
    private final ConsultationPacillianMapper mapper;

    @Override
    public ConsultationResponsePacillianDto createConsultation(ConsultationRequestPacillianDto req) {
        ConsultationPacillian cons = mapper.toEntity(req);
        cons.setRequestedAt(LocalDateTime.now());
        cons.setStatus(ConsultationPacillian.ConsultationStatus.PENDING); // biasanya PENDING saat baru dibuat
        ConsultationPacillian saved = repo.save(cons);
        return mapper.toDto(saved);
    }

    @Override
    public ConsultationResponsePacillianDto getConsultationById(Long id) {
        ConsultationPacillian c = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation", "id", id));
        return mapper.toDto(c);
    }

    @Override
    public List<ConsultationResponsePacillianDto> getConsultationByPacillian(Long patientId) {
        return repo.findByPatientId(patientId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultationResponsePacillianDto> getConsultationByDoctor(Long doctorId) {
        return repo.findByDoctorId(doctorId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    // Method updateStatus tidak ada di interface, jadi dihilangkan kalau mau strict interface

    @Override
    public void deleteConsultation(Long id) {
        repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation", "id", id));
        repo.deleteById(id);
    }

    @Override
    public List<ConsultationResponsePacillianDto> getAllConsultation() {
        List<ConsultationPacillian> allConsultations = repo.findAll();
        return allConsultations.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
