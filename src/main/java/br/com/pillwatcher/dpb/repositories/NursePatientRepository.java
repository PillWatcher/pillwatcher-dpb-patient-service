package br.com.pillwatcher.dpb.repositories;

import br.com.pillwatcher.dpb.entities.NursePatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NursePatientRepository extends JpaRepository<NursePatient, Long> {

    Optional<NursePatient> findByNurseIdAndPatientId(Long nurseId, Long patientId);

    @Query("select nursePatient from NursePatient nursePatient " +
            "where nursePatient.nurse.id = :nurseId " +
            "and nursePatient.patient.user.document = :cpf ")
    Optional<NursePatient> findByNurseIdAndPatientCPF(@Param("nurseId") Long nurseId,
                                                      @Param("cpf") String cpf);

}
