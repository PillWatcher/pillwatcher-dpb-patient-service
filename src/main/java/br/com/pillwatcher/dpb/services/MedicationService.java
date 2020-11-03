package br.com.pillwatcher.dpb.services;

import br.com.pillwatcher.dpb.entities.Medication;
import io.swagger.model.PrescriptionMedicationDTOForCreate;

public interface MedicationService {

    Medication create(PrescriptionMedicationDTOForCreate body);

}
