package id.ac.ui.cs.advprog.consultation.service;

import id.ac.ui.cs.advprog.consultation.dto.ScheduleRequestDto;
import id.ac.ui.cs.advprog.consultation.dto.ConsultationResponseDto;
import id.ac.ui.cs.advprog.consultation.entity.Consultation;
import id.ac.ui.cs.advprog.consultation.mapper.ConsultationMapper;
import id.ac.ui.cs.advprog.consultation.repository.ConsultationRepository;
import id.ac.ui.cs.advprog.consultation.util.ScheduleValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {    private final ConsultationRepository consultationRepository;
    private final ConsultationMapper consultationMapper;
    private final ScheduleValidator scheduleValidator;

    @Async("taskExecutor")
    public CompletableFuture<ConsultationResponseDto> scheduleConsultation(ScheduleRequestDto request) {
        scheduleValidator.validateScheduleRequest(request);
        
        var consultation = Consultation.builder()
                .patientId(request.getPatientId())
                .doctorId(request.getDoctorId())
                .scheduledAt(request.getScheduledAt())
                .requestedAt(LocalDateTime.now())
                .status(Consultation.ConsultationStatus.PENDING)
                .meetingUrl(request.getMeetingUrl())
                .note(request.getNote())
                .build();

        var saved = consultationRepository.save(consultation);
        return CompletableFuture.completedFuture(consultationMapper.toDto(saved));
    }

    @Async("taskExecutor")
    public CompletableFuture<List<ConsultationResponseDto>> getDoctorSchedule(Long doctorId, LocalDateTime from, LocalDateTime to) {
        var consultations = consultationRepository.findByDoctorId(doctorId).stream()
                .filter(c -> isWithinRange(c.getScheduledAt(), from, to))
                .map(consultationMapper::toDto)
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(consultations);
    }    private boolean isWithinRange(LocalDateTime dateTime, LocalDateTime from, LocalDateTime to) {
        return (dateTime.isEqual(from) || dateTime.isAfter(from)) &&
               (dateTime.isEqual(to) || dateTime.isBefore(to));
    }
}
