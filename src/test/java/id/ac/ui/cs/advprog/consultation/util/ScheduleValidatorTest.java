package id.ac.ui.cs.advprog.consultation.util;

import id.ac.ui.cs.advprog.consultation.dto.ScheduleRequestDto;
import id.ac.ui.cs.advprog.consultation.exception.BadRequestException;
import id.ac.ui.cs.advprog.consultation.repository.ConsultationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScheduleValidatorTest {

    private ConsultationRepository repository;
    private ScheduleValidator validator;

    @BeforeEach
    void setUp() {
        repository = mock(ConsultationRepository.class);
        validator = new ScheduleValidator(repository);
    }

    @Test
    void testValidateScheduleRequest_valid() {
        ScheduleRequestDto dto = new ScheduleRequestDto();
        dto.setDoctorId(1L);
        dto.setScheduledAt(LocalDateTime.now().plusDays(1).withHour(10));
        when(repository.hasConflictingSchedule(anyLong(), any())).thenReturn(false);

        assertDoesNotThrow(() -> validator.validateScheduleRequest(dto));
    }

    @Test
    void testValidateScheduleRequest_inPast() {
        ScheduleRequestDto dto = new ScheduleRequestDto();
        dto.setDoctorId(1L);
        dto.setScheduledAt(LocalDateTime.now().minusDays(1));
        Exception ex = assertThrows(BadRequestException.class, () -> validator.validateScheduleRequest(dto));
        assertTrue(ex.getMessage().contains("Cannot schedule consultations in the past"));
    }

    @Test
    void testValidateScheduleRequest_outsideWorkingHours_before() {
        ScheduleRequestDto dto = new ScheduleRequestDto();
        dto.setDoctorId(1L);
        dto.setScheduledAt(LocalDateTime.now().plusDays(1).withHour(7));
        Exception ex = assertThrows(BadRequestException.class, () -> validator.validateScheduleRequest(dto));
        assertTrue(ex.getMessage().contains("Consultations can only be scheduled"));
    }

    @Test
    void testValidateScheduleRequest_outsideWorkingHours_after() {
        ScheduleRequestDto dto = new ScheduleRequestDto();
        dto.setDoctorId(1L);
        dto.setScheduledAt(LocalDateTime.now().plusDays(1).withHour(18));
        Exception ex = assertThrows(BadRequestException.class, () -> validator.validateScheduleRequest(dto));
        assertTrue(ex.getMessage().contains("Consultations can only be scheduled"));
    }

    @Test
    void testValidateScheduleRequest_conflict() {
        ScheduleRequestDto dto = new ScheduleRequestDto();
        dto.setDoctorId(1L);
        dto.setScheduledAt(LocalDateTime.now().plusDays(1).withHour(10));
        when(repository.hasConflictingSchedule(anyLong(), any())).thenReturn(true);

        Exception ex = assertThrows(BadRequestException.class, () -> validator.validateScheduleRequest(dto));
        assertTrue(ex.getMessage().contains("Doctor already has a consultation scheduled"));
    }
}
