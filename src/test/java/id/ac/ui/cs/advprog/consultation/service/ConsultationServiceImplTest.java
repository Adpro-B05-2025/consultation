package id.ac.ui.cs.advprog.consultation.service;

import id.ac.ui.cs.advprog.consultation.dto.ConsultationRequestDto;
import id.ac.ui.cs.advprog.consultation.dto.ConsultationResponseDto;
import id.ac.ui.cs.advprog.consultation.entity.Consultation;
import id.ac.ui.cs.advprog.consultation.exception.BadRequestException;
import id.ac.ui.cs.advprog.consultation.exception.ResourceNotFoundException;
import id.ac.ui.cs.advprog.consultation.mapper.ConsultationMapper;
import id.ac.ui.cs.advprog.consultation.repository.ConsultationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsultationServiceImplTest {

    private ConsultationRepository repo;
    private ConsultationMapper mapper;
    private ConsultationServiceImpl service;

    @BeforeEach
    void setUp() {
        repo = mock(ConsultationRepository.class);
        mapper = mock(ConsultationMapper.class);
        service = new ConsultationServiceImpl(repo, mapper);
    }

    @Test
    void testCreate() throws Exception {
        ConsultationRequestDto req = new ConsultationRequestDto();
        Consultation cons = new Consultation();
        Consultation saved = new Consultation();
        ConsultationResponseDto dto = new ConsultationResponseDto();

        when(mapper.toEntity(req)).thenReturn(cons);
        when(repo.save(cons)).thenReturn(saved);
        when(mapper.toDto(saved)).thenReturn(dto);

        assertEquals(dto, service.create(req).get());
    }

    @Test
    void testGetById_found() throws Exception {
        Consultation cons = new Consultation();
        ConsultationResponseDto dto = new ConsultationResponseDto();
        when(repo.findById(1L)).thenReturn(Optional.of(cons));
        when(mapper.toDto(cons)).thenReturn(dto);

        assertEquals(dto, service.getById(1L).get());
    }

    @Test
    void testGetById_notFound() {
        when(repo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getById(1L).join());
    }

    @Test
    void testGetByPatient() throws Exception {
        Consultation cons = new Consultation();
        ConsultationResponseDto dto = new ConsultationResponseDto();
        when(repo.findByPatientId(1L)).thenReturn(List.of(cons));
        when(mapper.toDto(cons)).thenReturn(dto);

        List<ConsultationResponseDto> result = service.getByPatient(1L).get();
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void testGetByDoctor() throws Exception {
        Consultation cons = new Consultation();
        ConsultationResponseDto dto = new ConsultationResponseDto();
        when(repo.findByDoctorId(1L)).thenReturn(List.of(cons));
        when(mapper.toDto(cons)).thenReturn(dto);

        List<ConsultationResponseDto> result = service.getByDoctor(1L).get();
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void testUpdateStatus_valid() throws Exception {
        Consultation cons = new Consultation();
        Consultation updated = new Consultation();
        ConsultationResponseDto dto = new ConsultationResponseDto();
        when(repo.findById(1L)).thenReturn(Optional.of(cons));
        when(repo.save(cons)).thenReturn(updated);
        when(mapper.toDto(updated)).thenReturn(dto);

        assertEquals(dto, service.updateStatus(1L, "PENDING").get());
    }

    @Test
    void testUpdateStatus_invalidStatus() {
        Consultation cons = new Consultation();
        when(repo.findById(1L)).thenReturn(Optional.of(cons));
        assertThrows(BadRequestException.class, () -> service.updateStatus(1L, "INVALID").join());
    }

    @Test
    void testUpdateStatus_notFound() {
        when(repo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.updateStatus(1L, "PENDING").join());
    }

    @Test
    void testDelete_found() throws Exception {
        Consultation cons = new Consultation();
        when(repo.findById(1L)).thenReturn(Optional.of(cons));
        doNothing().when(repo).deleteById(1L);

        assertNull(service.delete(1L).get());
    }

    @Test
    void testDelete_notFound() {
        when(repo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.delete(1L).join());
    }
}
