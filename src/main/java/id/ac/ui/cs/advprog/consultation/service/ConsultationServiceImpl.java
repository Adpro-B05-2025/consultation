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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepository repo;
    private final ConsultationMapper mapper;

    @Override
    public ConsultationResponseDto create(ConsultationRequestDto req) {
        Consultation cons = mapper.toEntity(req);
        cons.setRequestedAt(LocalDateTime.now());
        cons.setStatus(ConsultationStatus.PENDING);
        Consultation saved = repo.save(cons);
        return mapper.toDto(saved);
    }

    @Override
    public ConsultationResponseDto getById(Long id) {
        Consultation c = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation", "id", id));
        return mapper.toDto(c);
    }

    @Override
    public List<ConsultationResponseDto> getByPatient(Long patientId) {
        return repo.findByPatientId(patientId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultationResponseDto> getByDoctor(Long doctorId) {
        return repo.findByDoctorId(doctorId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ConsultationResponseDto updateStatus(Long id, String newStatus) {
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
        return mapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {
        repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation", "id", id));
        repo.deleteById(id);
    }

    @Override
<<<<<<< HEAD
    public ConsultationResponseDto scheduleConsultation(Long id, String scheduledAt) {
        Consultation c = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation", "id", id));
        try {
            LocalDateTime scheduleTime = LocalDateTime.parse(scheduledAt);
            c.setScheduledAt(scheduleTime);
            c.setStatus(ConsultationStatus.APPROVED);
        } catch (Exception e) {
            throw new BadRequestException("Invalid scheduledAt format. Use ISO_LOCAL_DATE_TIME.");
        }
        Consultation updated = repo.save(c);
        return mapper.toDto(updated);
    }

    @Override
    public List<ConsultationResponseDto> getAllConsultations() {
        return repo.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
=======
    public List<ConsultationResponseDto> getAll() {
        List<Consultation> allConsultations = repo.findAll();
        return allConsultations.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
>>>>>>> 2e29ec9bc52a6308dfb174f03ed801fa11720164
