package br.com.pillwatcher.dpb.services;

import br.com.pillwatcher.dpb.entities.Patient;
import io.swagger.model.PatientDTOForCreate;
import io.swagger.model.PatientDTOForUpdate;

public interface PatientService {

    Patient create(PatientDTOForCreate patientDto);

    Patient update(PatientDTOForUpdate patientDtoForUpdate, String document);

    Patient findPatient(String document);

    void deletePatient(String document);
}
