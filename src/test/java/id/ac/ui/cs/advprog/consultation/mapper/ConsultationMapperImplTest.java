package id.ac.ui.cs.advprog.consultation.mapper;

import id.ac.ui.cs.advprog.consultation.dto.ConsultationRequestDto;
import id.ac.ui.cs.advprog.consultation.dto.ConsultationResponseDto;
import id.ac.ui.cs.advprog.consultation.entity.Consultation;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class ConsultationMapperImplTest {

    private final ConsultationMapper mapper = Mappers.getMapper(ConsultationMapper.class);

    @Test
    void testToEntity_withNull() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    void testToDto_withNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    void testToEntityAndToDto_fullCycle() {
        ConsultationRequestDto req = new ConsultationRequestDto();
        req.setPatientId(1L);
        req.setDoctorId(2L);
        req.setMeetingUrl("url");
        req.setNote("note");
        Consultation entity = mapper.toEntity(req);
        assertNotNull(entity);
        ConsultationResponseDto dto = mapper.toDto(entity);
        assertNotNull(dto);
        assertEquals(entity.getPatientId(), dto.getPatientId());
        assertEquals(entity.getDoctorId(), dto.getDoctorId());
    }

    @Test
    void testConsultationMapperImplDirectly() {
        ConsultationMapperImpl impl = new ConsultationMapperImpl();
        assertNull(impl.toEntity(null));
        assertNull(impl.toDto(null));
        ConsultationRequestDto req = new ConsultationRequestDto();
        req.setPatientId(10L);
        req.setDoctorId(20L);
        req.setMeetingUrl("meet");
        req.setNote("direct");
        Consultation entity = impl.toEntity(req);
        assertNotNull(entity);
        ConsultationResponseDto dto = impl.toDto(entity);
        assertNotNull(dto);
        assertEquals(entity.getPatientId(), dto.getPatientId());
        assertEquals(entity.getDoctorId(), dto.getDoctorId());
    }
}
