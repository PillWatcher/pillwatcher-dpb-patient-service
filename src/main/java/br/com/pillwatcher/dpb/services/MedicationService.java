package br.com.pillwatcher.dpb.services;

import br.com.pillwatcher.dpb.entities.Medication;
import io.swagger.model.PrescriptionMedicationDTOForCreate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface MedicationService {

    Medication create(PrescriptionMedicationDTOForCreate body, Long prescriptionId);

    Medication getMedication(Long medicationId);

    void deleteMedication(Long medicationId);

    List<Medication> getAllMedication(Long prescriptionId);

}
