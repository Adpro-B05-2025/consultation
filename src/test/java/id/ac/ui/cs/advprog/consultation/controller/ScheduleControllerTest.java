package id.ac.ui.cs.advprog.consultation.controller;

import id.ac.ui.cs.advprog.consultation.dto.ScheduleRequestDto;
import id.ac.ui.cs.advprog.consultation.dto.ConsultationResponseDto;
import id.ac.ui.cs.advprog.consultation.service.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScheduleControllerTest {

    @Mock
    private ScheduleService scheduleService;

    @InjectMocks
    private ScheduleController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSchedule() throws Exception {
        ScheduleRequestDto req = new ScheduleRequestDto();
        ConsultationResponseDto resp = new ConsultationResponseDto();
        when(scheduleService.scheduleConsultation(req)).thenReturn(CompletableFuture.completedFuture(resp));

        CompletableFuture<ResponseEntity<ConsultationResponseDto>> result = controller.createSchedule(req);
        assertEquals(HttpStatus.CREATED, result.get().getStatusCode());
        assertEquals(resp, result.get().getBody());
        verify(scheduleService).scheduleConsultation(req);
    }

    @Test
    void testGetDoctorSchedule() throws Exception {
        List<ConsultationResponseDto> list = List.of(new ConsultationResponseDto());
        LocalDateTime from = LocalDateTime.now();
        LocalDateTime to = from.plusDays(1);
        when(scheduleService.getDoctorSchedule(1L, from, to)).thenReturn(CompletableFuture.completedFuture(list));

        CompletableFuture<List<ConsultationResponseDto>> result = controller.getDoctorSchedule(1L, from, to);
        assertEquals(list, result.get());
        verify(scheduleService).getDoctorSchedule(1L, from, to);
    }
}
