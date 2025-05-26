package id.ac.ui.cs.advprog.consultation.mapper;

import id.ac.ui.cs.advprog.consultation.dto.ConsultationRequestDto;
import id.ac.ui.cs.advprog.consultation.dto.ConsultationResponseDto;
import id.ac.ui.cs.advprog.consultation.entity.Consultation;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ConsultationMapper {
    Consultation toEntity(ConsultationRequestDto dto);

    ConsultationResponseDto toDto(Consultation entity);
}
