package id.ac.ui.cs.advprog.consultation.service;

import id.ac.ui.cs.advprog.consultation.dto.ScheduleRequestDto;
import id.ac.ui.cs.advprog.consultation.dto.ConsultationResponseDto;
import id.ac.ui.cs.advprog.consultation.entity.Consultation;
import id.ac.ui.cs.advprog.consultation.mapper.ConsultationMapper;
import id.ac.ui.cs.advprog.consultation.repository.ConsultationRepository;
import id.ac.ui.cs.advprog.consultation.util.ScheduleValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScheduleServiceTest {

    private ConsultationRepository repo;
    private ConsultationMapper mapper;
    private ScheduleValidator validator;
    private ScheduleService service;

    @BeforeEach
    void setUp() {
        repo = mock(ConsultationRepository.class);
        mapper = mock(ConsultationMapper.class);
        validator = mock(ScheduleValidator.class);
        service = new ScheduleService(repo, mapper, validator);
    }

    @Test
    void testScheduleConsultation() throws Exception {
        ScheduleRequestDto req = new ScheduleRequestDto();
        Consultation saved = new Consultation();
        ConsultationResponseDto dto = new ConsultationResponseDto();

        when(repo.save(any())).thenReturn(saved);
        when(mapper.toDto(saved)).thenReturn(dto);

        assertEquals(dto, service.scheduleConsultation(req).get());
        verify(validator).validateScheduleRequest(req);
    }

    @Test
    void testGetDoctorSchedule_withinRange() throws Exception {
        Consultation cons = new Consultation();
        cons.setScheduledAt(LocalDateTime.of(2024, 1, 1, 10, 0));
        ConsultationResponseDto dto = new ConsultationResponseDto();
        when(repo.findByDoctorId(1L)).thenReturn(List.of(cons));
        when(mapper.toDto(cons)).thenReturn(dto);

        LocalDateTime from = LocalDateTime.of(2024, 1, 1, 9, 0);
        LocalDateTime to = LocalDateTime.of(2024, 1, 1, 11, 0);

        List<ConsultationResponseDto> result = service.getDoctorSchedule(1L, from, to).get();
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void testGetDoctorSchedule_outOfRange() throws Exception {
        Consultation cons = new Consultation();
        cons.setScheduledAt(LocalDateTime.of(2024, 1, 1, 8, 0));
        when(repo.findByDoctorId(1L)).thenReturn(List.of(cons));

        LocalDateTime from = LocalDateTime.of(2024, 1, 1, 9, 0);
        LocalDateTime to = LocalDateTime.of(2024, 1, 1, 11, 0);

        List<ConsultationResponseDto> result = service.getDoctorSchedule(1L, from, to).get();
        assertTrue(result.isEmpty());
    }
}
