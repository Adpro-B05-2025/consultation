package id.ac.ui.cs.advprog.consultation.repository;

import id.ac.ui.cs.advprog.consultation.entity.ConsultationPacillian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultationPacillianRepository extends JpaRepository<ConsultationPacillian, Long> {
    List<ConsultationPacillian> findByPatientId(Long patientId);
    List<ConsultationPacillian> findByDoctorId(Long doctorId);
}
