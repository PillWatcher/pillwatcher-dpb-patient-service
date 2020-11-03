package br.com.pillwatcher.dpb.repositories;

import br.com.pillwatcher.dpb.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findPatientByUserDocument(String document);

    @Query("select patient from Patient patient " +
            "inner join NursePatient nursePatient on nursePatient.patient = patient " +
            "inner join Nurse nurse on nursePatient.nurse = nurse " +
            "where nurse.id = :nurseId")
    List<Patient> findAllByNurse(@Param("nurseId") final Long nurseId);

}
