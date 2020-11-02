package br.com.pillwatcher.dpb.repositories;

import br.com.pillwatcher.dpb.entities.NursePatient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NursePatientRepository extends JpaRepository<NursePatient, Long> {

    Optional<NursePatient> findByNurseIdAndPatientId(Long nurseId, Long patientId);

}
