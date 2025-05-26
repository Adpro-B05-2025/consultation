package id.ac.ui.cs.advprog.consultation.mapper;

import id.ac.ui.cs.advprog.consultation.dto.ConsultationRequestDto;
import id.ac.ui.cs.advprog.consultation.dto.ConsultationResponseDto;
import id.ac.ui.cs.advprog.consultation.entity.Consultation;
<<<<<<< HEAD
import org.mapstruct.*;
=======
import org.mapstruct.Mapper;
>>>>>>> 2e29ec9bc52a6308dfb174f03ed801fa11720164

@Mapper(componentModel = "spring")
public interface ConsultationMapper {
    Consultation toEntity(ConsultationRequestDto dto);
<<<<<<< HEAD

=======
>>>>>>> 2e29ec9bc52a6308dfb174f03ed801fa11720164
    ConsultationResponseDto toDto(Consultation entity);
}
