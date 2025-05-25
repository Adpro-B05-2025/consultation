package id.ac.ui.cs.advprog.consultation.repository;

import id.ac.ui.cs.advprog.consultation.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByPatientId(Long patientId);
    List<Consultation> findByDoctorId(Long doctorId);
    
    @Query("SELECT c FROM Consultation c WHERE c.doctorId = :doctorId " +
           "AND c.scheduledAt BETWEEN :startTime AND :endTime")
    List<Consultation> findSchedulesByDoctorAndTimeRange(
            @Param("doctorId") Long doctorId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Consultation c " +
           "WHERE c.doctorId = :doctorId AND c.scheduledAt = :scheduledTime")
    boolean hasConflictingSchedule(
            @Param("doctorId") Long doctorId,
            @Param("scheduledTime") LocalDateTime scheduledTime);
}
