package br.com.pillwatcher.dpb.services;

import br.com.pillwatcher.dpb.entities.Patient;
import io.swagger.model.PatientDTOForCreate;
import io.swagger.model.PatientDTOForResponse;
import io.swagger.model.PatientDTOForUpdate;

import java.util.List;

public interface PatientService {

    Patient create(PatientDTOForCreate patientDto);

    Patient update(PatientDTOForUpdate patientDtoForUpdate, String document, String nurseId);

    Patient findPatient(String document, String nurseId);

    List<PatientDTOForResponse> findPatients(String nurseId);

    void deletePatient(String document, String nurseId);
}
