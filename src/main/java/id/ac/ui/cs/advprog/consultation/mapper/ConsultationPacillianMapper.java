package id.ac.ui.cs.advprog.consultation.mapper;

import id.ac.ui.cs.advprog.consultation.dto.ConsultationRequestPacillianDto;
import id.ac.ui.cs.advprog.consultation.dto.ConsultationResponsePacillianDto;
import id.ac.ui.cs.advprog.consultation.entity.ConsultationPacillian;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConsultationPacillianMapper {
    ConsultationPacillian toEntity(ConsultationRequestPacillianDto dto);
    ConsultationResponsePacillianDto toDto(ConsultationPacillian entity);
}
