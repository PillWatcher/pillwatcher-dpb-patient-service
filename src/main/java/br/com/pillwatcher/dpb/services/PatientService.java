package br.com.pillwatcher.dpb.services;

import br.com.pillwatcher.dpb.entities.Nurse;
import br.com.pillwatcher.dpb.entities.NursePatient;
import br.com.pillwatcher.dpb.entities.Patient;
import io.swagger.model.PatientDTOForCreate;
import io.swagger.model.PatientDTOForResponse;
import io.swagger.model.PatientDTOForUpdate;
import io.swagger.model.PatientDetailsDTOForResponse;

import java.util.List;

public interface PatientService {

    Patient create(PatientDTOForCreate patientDto, Long nurseId);

    Patient update(PatientDTOForUpdate patientDtoForUpdate, String document, Long nurseId);

    Patient findPatient(String document, Long nurseId);

    List<PatientDTOForResponse> findPatients(Long nurseId);

    void deletePatient(String document, Long nurseId);

    NursePatient getNursePatient(String cpf, Long nurseId);

    Patient findPatientByUserDocument(String cpf);

    void relationPatientToNurse(String cpf, Long nurseId);

    Nurse getNurse(Long nurseId);

    PatientDetailsDTOForResponse getPatientDetails(Long patientId);
}
