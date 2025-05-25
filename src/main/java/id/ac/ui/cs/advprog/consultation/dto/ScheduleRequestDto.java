package id.ac.ui.cs.advprog.consultation.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleRequestDto {
    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotNull(message = "Doctor ID is required")
    private Long doctorId;

    @NotNull(message = "Scheduled date and time is required")
    @Future(message = "Scheduled date and time must be in the future")
    private LocalDateTime scheduledAt;

    private String note;
    private String meetingUrl;
}
