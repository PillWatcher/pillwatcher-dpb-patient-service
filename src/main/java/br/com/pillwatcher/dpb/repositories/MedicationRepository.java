package br.com.pillwatcher.dpb.repositories;

import br.com.pillwatcher.dpb.entities.MqttMedication;
import br.com.pillwatcher.dpb.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

    @Query("select medication from Medication medication " +
            "inner join Prescription prescription on medication.prescription.id = prescription.id " +
            "where prescription.id = :prescriptionId")
    List<Medication> findByPrescriptionId(@Param("prescriptionId") final Long prescriptionId);

    @Query("select new br.com.pillwatcher.dpb.entities.MqttMedication(medication.id, " +
            "nursePatient.nurse.id, medication.cup.id, " +
            "medication.location, medication.intervalTime) " +
            "from Medication medication inner join Prescription prescription " +
            "on medication.prescription.id = prescription.id " +
            "inner join Patient patient " +
            "on prescription.patient.id = patient.id " +
            "inner join NursePatient nursePatient " +
            "on patient.id = nursePatient.patient.id " +
            "where medication.startDate >= :startDate " +
            "and medication.startDate < :endDate")
    List<MqttMedication> findMedicationsToApplyFirst(@Param("startDate") LocalDateTime startDate,
                                                     @Param("endDate") LocalDateTime endDate);

    @Query("select new br.com.pillwatcher.dpb.entities.MqttMedication(medication.id, " +
            "nursePatient.nurse.id, medication.cup.id, " +
            "medication.location, medication.intervalTime) " +
            "from Medication medication inner join Prescription prescription " +
            "on medication.prescription.id = prescription.id " +
            "inner join Patient patient " +
            "on prescription.patient.id = patient.id " +
            "inner join NursePatient nursePatient " +
            "on patient.id = nursePatient.patient.id " +
            "inner join AppliedMedication appliedMedication " +
            "on medication.id = appliedMedication.medication.id " +
            "where appliedMedication.nextMedicationDate >= :startDate " +
            "and appliedMedication.nextMedicationDate < :endDate")
    List<MqttMedication> findMedicationsToApply(@Param("startDate") LocalDateTime startDate,
                                                @Param("endDate") LocalDateTime endDate);

}
