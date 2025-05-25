package id.ac.ui.cs.advprog.consultation.mapper;

import id.ac.ui.cs.advprog.consultation.dto.ConsultationRequestDto;
import id.ac.ui.cs.advprog.consultation.dto.ConsultationResponseDto;
import id.ac.ui.cs.advprog.consultation.entity.Consultation;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class ConsultationMapperTest {

    private final ConsultationMapper mapper = Mappers.getMapper(ConsultationMapper.class);

    @Test
    void testToEntity() {
        ConsultationRequestDto dto = new ConsultationRequestDto();
        dto.setPatientId(1L);
        dto.setDoctorId(2L);
        Consultation entity = mapper.toEntity(dto);
        assertEquals(1L, entity.getPatientId());
        assertEquals(2L, entity.getDoctorId());
    }

    @Test
    void testToDto() {
        Consultation entity = new Consultation();
        entity.setId(1L);
        entity.setPatientId(2L);
        ConsultationResponseDto dto = mapper.toDto(entity);
        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getPatientId());
    }
}
