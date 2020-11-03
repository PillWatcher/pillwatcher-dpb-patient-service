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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    private final PatientService patientService;

    private final PrescriptionMapper prescriptionMapper;

    @Override
    public PatientPrescriptionDTOForResponse createPatientPrescription(final PatientPrescriptionDTOForCreate body, final String cpf, final Long nurseId) {

        log.info("PrescriptionServiceImpl.create - Start - Input {}", body);

        final Optional<NursePatient> nursePatient = patientService.getNursePatient(cpf, nurseId);

        if (!nursePatient.isPresent()) {
            log.warn(ValidationConstraints.PATIENT_NOT_FOUND, nurseId);
            throw new PatientException(ErrorCodeEnum.NOT_FOUND, ErrorMessages.NOT_FOUND,
                    StringUtils.replace(ValidationConstraints.PATIENT_NOT_FOUND, "{}", nurseId.toString()));
        }

        Prescription save = prescriptionRepository.save(prescriptionMapper.dtoToEntity(body, nursePatient.get().getPatient().getId()));
        return prescriptionMapper.entityToDto(save);
    }

    @Override
    public void deletePatientPrescription(final Long prescriptionId) {

        log.info("PrescriptionServiceImpl.deletePatientPrescription - Start - Input {}", prescriptionId);

        Prescription one = prescriptionRepository.getOne(prescriptionId);

        if (Objects.isNull(one)) {
            log.warn(ValidationConstraints.PRESCRIPTION_NOT_FOUND, prescriptionId);
            throw new PatientException(ErrorCodeEnum.NOT_FOUND, ErrorMessages.NOT_FOUND,
                    StringUtils.replace(ValidationConstraints.PRESCRIPTION_NOT_FOUND, "{}", prescriptionId.toString()));
        }

        prescriptionRepository.delete(one);

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

        List<Prescription> allByPatientId = prescriptionRepository.findAllByPatientId(patientByUserDocument.get().getId());

        return prescriptionMapper.entityToDtos(allByPatientId);
    }

    @Override
    public PatientPrescriptionDTOForResponse getPatientPrescription(final Long prescriptionId) {

        log.info("PrescriptionServiceImpl.getPatientPrescription - Start - Input {}", prescriptionId);

        final Prescription one = prescriptionRepository.getOne(prescriptionId);

        if (Objects.isNull(one)) {
            log.warn(ValidationConstraints.PRESCRIPTION_NOT_FOUND, prescriptionId);
            throw new PatientException(ErrorCodeEnum.NOT_FOUND, ErrorMessages.NOT_FOUND,
                    StringUtils.replace(ValidationConstraints.PRESCRIPTION_NOT_FOUND, "{}", prescriptionId.toString()));
        }

        return prescriptionMapper.entityToDto(one);
    }

    @Override
    public PatientPrescriptionDTOForResponse updatePatientPrescription(final PatientPrescriptionDTOForCreate body, final Long prescriptionId) {

        log.info("PrescriptionServiceImpl.updatePatientPrescription - Start - Input {}", body);

        final Prescription one = prescriptionRepository.getOne(prescriptionId);

        if (Objects.isNull(one)) {
            log.warn(ValidationConstraints.PRESCRIPTION_NOT_FOUND, prescriptionId);
            throw new PatientException(ErrorCodeEnum.NOT_FOUND, ErrorMessages.NOT_FOUND,
                    StringUtils.replace(ValidationConstraints.PRESCRIPTION_NOT_FOUND, "{}", prescriptionId.toString()));
        }

        return null;
    }

}
