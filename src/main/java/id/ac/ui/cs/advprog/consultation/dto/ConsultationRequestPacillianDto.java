package id.ac.ui.cs.advprog.consultation.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ConsultationRequestPacillianDto {
    private Long patientId;
    private Long doctorId;
    private LocalDateTime scheduledAt;
    private String meetingUrl;
    private String note;
}

