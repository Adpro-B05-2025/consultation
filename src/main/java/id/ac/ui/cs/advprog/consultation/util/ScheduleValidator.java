package id.ac.ui.cs.advprog.consultation.util;

import id.ac.ui.cs.advprog.consultation.dto.ScheduleRequestDto;
import id.ac.ui.cs.advprog.consultation.exception.BadRequestException;
import id.ac.ui.cs.advprog.consultation.repository.ConsultationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class ScheduleValidator {
    private final ConsultationRepository repository;
    
    private static final LocalTime WORKING_HOURS_START = LocalTime.of(8, 0); // 8 AM
    private static final LocalTime WORKING_HOURS_END = LocalTime.of(17, 0);  // 5 PM
    
    public void validateScheduleRequest(ScheduleRequestDto request) {
        validateScheduleTime(request.getScheduledAt());
        validateWorkingHours(request.getScheduledAt());
        validateNoConflicts(request.getDoctorId(), request.getScheduledAt());
    }
    
    private void validateScheduleTime(LocalDateTime scheduledTime) {
        if (scheduledTime.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Cannot schedule consultations in the past");
        }
    }
    
    private void validateWorkingHours(LocalDateTime scheduledTime) {
        LocalTime time = scheduledTime.toLocalTime();
        if (time.isBefore(WORKING_HOURS_START) || time.isAfter(WORKING_HOURS_END)) {
            throw new BadRequestException(
                String.format("Consultations can only be scheduled between %s and %s",
                    WORKING_HOURS_START, WORKING_HOURS_END));
        }
    }
    
    private void validateNoConflicts(Long doctorId, LocalDateTime scheduledTime) {
        if (repository.hasConflictingSchedule(doctorId, scheduledTime)) {
            throw new BadRequestException("Doctor already has a consultation scheduled at this time");
        }
    }
}
