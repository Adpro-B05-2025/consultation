package id.ac.ui.cs.advprog.consultation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationResponsePacillianDto {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private LocalDateTime requestedAt;
    private LocalDateTime scheduledAt;
    private String status;
    private String meetingUrl;
    private String note;
}
