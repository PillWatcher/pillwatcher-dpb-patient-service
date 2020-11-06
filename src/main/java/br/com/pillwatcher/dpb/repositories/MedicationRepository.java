package br.com.pillwatcher.dpb.repositories;

import br.com.pillwatcher.dpb.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

    @Query("select medication from Medication medication " +
            "inner join Prescription prescription on medication.prescription.id = prescription.id " +
            "where prescription.id = :prescriptionId")
    List<Medication> findByPrescriptionId(@Param("prescriptionId") final Long prescriptionId);

}
