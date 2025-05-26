package id.ac.ui.cs.advprog.consultation.entity;

import lombok.*;

import jakarta.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultation_request")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ConsultationPacillian {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long doctorId;

    private Long patientId;

    private LocalDateTime requestedAt;

    private LocalDateTime scheduledAt;

    @Enumerated(EnumType.STRING)
    private ConsultationStatus status;

    private String meetingUrl;

    private String note;

    public enum ConsultationStatus {
        PENDING,
        APPROVED,
        REJECTED,
        COMPLETED
    }
}
