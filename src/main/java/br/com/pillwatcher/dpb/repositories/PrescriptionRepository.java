package br.com.pillwatcher.dpb.repositories;

import br.com.pillwatcher.dpb.entities.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    @Query("select prescription from Prescription prescription " +
            "inner join Patient patient " +
            "on patient.id = :patientId")
    List<Prescription> findAllByPatientId(@Param("patientId") final Long patientId);

}
