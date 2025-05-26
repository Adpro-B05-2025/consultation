package id.ac.ui.cs.advprog.consultation.repository;

import id.ac.ui.cs.advprog.consultation.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByPatientId(Long patientId);
    List<Consultation> findByDoctorId(Long doctorId);
}
