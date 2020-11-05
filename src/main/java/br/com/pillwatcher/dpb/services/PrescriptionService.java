package br.com.pillwatcher.dpb.services;

import br.com.pillwatcher.dpb.entities.Prescription;
import io.swagger.model.PatientPrescriptionDTOForCreate;
import io.swagger.model.PatientPrescriptionDTOForGetAll;
import io.swagger.model.PatientPrescriptionDTOForResponse;

import java.util.List;

public interface PrescriptionService {

    PatientPrescriptionDTOForResponse createPatientPrescription(PatientPrescriptionDTOForCreate body, String cpf, Long nurseId);

    void deletePatientPrescription(Long prescriptionId);

    PatientPrescriptionDTOForGetAll getAllPatientPrescription(String cpf);

    PatientPrescriptionDTOForResponse getPatientPrescription(Long prescriptionId);

    PatientPrescriptionDTOForResponse updatePatientPrescription(PatientPrescriptionDTOForCreate body, Long prescriptionId);

    List<Prescription> getAllPrescriptionByPatientId(Long patientId);
}
