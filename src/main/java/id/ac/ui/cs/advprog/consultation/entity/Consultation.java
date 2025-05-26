package id.ac.ui.cs.advprog.consultation.entity;

import lombok.*;

import jakarta.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Consultation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;  // foreign key ke Pacillian
    private Long doctorId;   // foreign key ke CareGiver

    private LocalDateTime requestedAt;
    private LocalDateTime scheduledAt;

    @Enumerated(EnumType.STRING)
    private ConsultationStatus status;  // PENDING, APPROVED, REJECTED, COMPLETED

    private String meetingUrl;
    private String note;

    public enum ConsultationStatus {
        PENDING,
        APPROVED,
        REJECTED,
        COMPLETED
    }

}
