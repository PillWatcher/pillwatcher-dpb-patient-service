package br.com.pillwatcher.dpb.repositories;

import br.com.pillwatcher.dpb.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Optional<Patient> findPatientByUserDocument(String document);

}
