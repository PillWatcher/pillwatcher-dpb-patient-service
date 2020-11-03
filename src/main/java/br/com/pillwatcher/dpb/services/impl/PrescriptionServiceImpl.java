package br.com.pillwatcher.dpb.services.impl;

import br.com.pillwatcher.dpb.constants.ErrorMessages;
import br.com.pillwatcher.dpb.constants.ValidationConstraints;
import br.com.pillwatcher.dpb.entities.NursePatient;
import br.com.pillwatcher.dpb.entities.Patient;
import br.com.pillwatcher.dpb.entities.Prescription;
import br.com.pillwatcher.dpb.exceptions.PatientException;
import br.com.pillwatcher.dpb.mappers.PrescriptionMapper;
import br.com.pillwatcher.dpb.repositories.PrescriptionRepository;
import br.com.pillwatcher.dpb.services.PatientService;
import br.com.pillwatcher.dpb.services.PrescriptionService;
import io.swagger.model.ErrorCodeEnum;
import io.swagger.model.PatientPrescriptionDTOForCreate;
import io.swagger.model.PatientPrescriptionDTOForGetAll;
import io.swagger.model.PatientPrescriptionDTOForResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    private final PatientService patientService;

    private final PrescriptionMapper prescriptionMapper;

    @Override
    public PatientPrescriptionDTOForResponse createPatientPrescription(final PatientPrescriptionDTOForCreate body,
                                                                       final String cpf,
                                                                       final Long nurseId) {

        log.info("PrescriptionServiceImpl.create - Start - Input {}", body);

        final Optional<NursePatient> nursePatientFound = patientService.getNursePatient(cpf, nurseId);

        if (!nursePatientFound.isPresent()) {
            log.warn(ValidationConstraints.PATIENT_NOT_FOUND, nurseId);
            throw new PatientException(ErrorCodeEnum.NOT_FOUND, ErrorMessages.NOT_FOUND,
                    StringUtils.replace(ValidationConstraints.PATIENT_NOT_FOUND, "{}", nurseId.toString()));
        }

        NursePatient nursePatient = nursePatientFound.get();

        Prescription save = prescriptionRepository.save(prescriptionMapper.dtoToEntity(body, nursePatient
                .getPatient()
                .getId()));

        return prescriptionMapper.entityToDto(save);
    }

    @Override
    public void deletePatientPrescription(final Long prescriptionId) {

        log.info("PrescriptionServiceImpl.deletePatientPrescription - Start - Input {}", prescriptionId);

        Optional<Prescription> optionalPrescription = prescriptionRepository.findById(prescriptionId);

        if (!optionalPrescription.isPresent()) {
            log.warn(ValidationConstraints.PRESCRIPTION_NOT_FOUND, prescriptionId);
            throw new PatientException(ErrorCodeEnum.NOT_FOUND, ErrorMessages.NOT_FOUND,
                    StringUtils.replace(ValidationConstraints.PRESCRIPTION_NOT_FOUND, "{}", prescriptionId.toString()));
        }

        prescriptionRepository.delete(optionalPrescription.get());

    }

    @Override
    public PatientPrescriptionDTOForGetAll getAllPatientPrescription(final String cpf) {

        log.info("PrescriptionServiceImpl.getAllPatientPrescription - Start - Input {}", cpf);

        final Optional<Patient> patientByUserDocument = patientService.findPatientByUserDocument(cpf);

        if (!patientByUserDocument.isPresent()) {
            log.warn(ValidationConstraints.PATIENT_NOT_FOUND, cpf);
            throw new PatientException(ErrorCodeEnum.NOT_FOUND, ErrorMessages.NOT_FOUND,
                    StringUtils.replace(ValidationConstraints.PATIENT_NOT_FOUND, "{}", cpf));
        }

        Patient patient = patientByUserDocument.get();
        List<Prescription> allByPatientId = prescriptionRepository.findAllByPatientId(patient.getId());

        return prescriptionMapper.entityToDtos(allByPatientId);
    }

    @Override
    public PatientPrescriptionDTOForResponse getPatientPrescription(final Long prescriptionId) {

        log.info("PrescriptionServiceImpl.getPatientPrescription - Start - Input {}", prescriptionId);

        Optional<Prescription> optionalPrescription = prescriptionRepository.findById(prescriptionId);

        if (!optionalPrescription.isPresent()) {
            log.warn(ValidationConstraints.PRESCRIPTION_NOT_FOUND, prescriptionId);
            throw new PatientException(ErrorCodeEnum.NOT_FOUND, ErrorMessages.NOT_FOUND,
                    StringUtils.replace(ValidationConstraints.PRESCRIPTION_NOT_FOUND, "{}", prescriptionId.toString()));
        }

        return prescriptionMapper.entityToDto(optionalPrescription.get());
    }

    @Override
    public PatientPrescriptionDTOForResponse updatePatientPrescription(
            final PatientPrescriptionDTOForCreate patientPrescriptionDTOForUpdate, final Long prescriptionId) {

        log.info("PrescriptionServiceImpl.updatePatientPrescription - Start - Input {}", patientPrescriptionDTOForUpdate);

        Optional<Prescription> optionalPrescription = prescriptionRepository.findById(prescriptionId);

        if (!optionalPrescription.isPresent()) {
            log.warn(ValidationConstraints.PRESCRIPTION_NOT_FOUND, prescriptionId);
            throw new PatientException(ErrorCodeEnum.NOT_FOUND, ErrorMessages.NOT_FOUND,
                    StringUtils.replace(ValidationConstraints.PRESCRIPTION_NOT_FOUND, "{}", prescriptionId.toString()));
        }

        Prescription prescription = optionalPrescription.get();

        BeanUtils.copyProperties(
                patientPrescriptionDTOForUpdate,
                prescription,
                "id", "inclusionDate");

        Prescription save = prescriptionRepository.save(prescription);

        return prescriptionMapper.entityToDto(save);
    }

}
