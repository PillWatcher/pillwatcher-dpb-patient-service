package br.com.pillwatcher.dpb.services.impl;

import br.com.pillwatcher.dpb.constants.ErrorMessages;
import br.com.pillwatcher.dpb.constants.ValidationConstraints;
import br.com.pillwatcher.dpb.entities.Patient;
import br.com.pillwatcher.dpb.exceptions.PatientException;
import br.com.pillwatcher.dpb.mappers.PatientMapper;
import br.com.pillwatcher.dpb.repositories.PatientRepository;
import br.com.pillwatcher.dpb.services.PatientService;
import io.swagger.model.ErrorCodeEnum;
import io.swagger.model.PatientDTOForCreate;
import io.swagger.model.PatientDTOForResponse;
import io.swagger.model.PatientDTOForUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;
    private final PatientMapper mapper;

    @Override
    @Transactional
    public Patient create(final PatientDTOForCreate patientDto) {

        log.info("PatientServiceImpl.create - Start - Input {}", patientDto);

        Optional<Patient> patientFound = repository.findPatientByUserDocument(patientDto.getDocument());

        if (patientFound.isPresent()) {
           log.warn(ValidationConstraints.PATIENT_ALREADY_EXISTS, patientDto.getDocument());
           throw new PatientException(ErrorCodeEnum.PATIENT_ALREADY_EXISTS, ErrorMessages.CONFLICT,
                   StringUtils.replace(ValidationConstraints.PATIENT_ALREADY_EXISTS, "{}", patientDto.getDocument()));
        }

        Patient patient = mapper.toPatientForCreateEntity(patientDto);
        return repository.save(patient);
    }

    @Override
    @Transactional
    public Patient update(final PatientDTOForUpdate patientDtoForUpdate, final String document) {

        log.info("PatientServiceImpl.update - Start - Input {} {}", patientDtoForUpdate, document);

        Optional<Patient> patientOptional = repository.findPatientByUserDocument(document);

        if(!patientOptional.isPresent()) {
            log.warn(ValidationConstraints.PATIENT_NOT_FOUND, document);
            throw new PatientException(ErrorCodeEnum.PATIENT_NOT_FOUND, ErrorMessages.NOT_FOUND,
                    StringUtils.replace(ValidationConstraints.PATIENT_NOT_FOUND, "{}", document));
        }

        Patient patient = patientOptional.get();

        BeanUtils.copyProperties(
                patientDtoForUpdate,
                patient,
                "id", "inclusionDate");

        BeanUtils.copyProperties(
                patientDtoForUpdate,
                patient.getUser(),
                "id", "document");

        return repository.save(patient);
    }

    @Override
    @Transactional
    public Patient findPatient(final String document) {

        log.info("PatientServiceImpl.findPatient - Start - Input {}", document);

        Optional<Patient> patient = repository.findPatientByUserDocument(document);

        if (!patient.isPresent()) {
            log.warn(ValidationConstraints.PATIENT_NOT_FOUND, document);
            throw new PatientException(ErrorCodeEnum.PATIENT_NOT_FOUND, ErrorMessages.NOT_FOUND,
                    StringUtils.replace(ValidationConstraints.PATIENT_NOT_FOUND, "{}", document));
        }

        return patient.get();
    }

    @Override
    @Transactional
    public List<PatientDTOForResponse> findPatients() {

        log.info("PatientServiceImpl.findPatient - Start - Input {}", "");
        List<Patient> patients = repository.findAll();

        List<PatientDTOForResponse> patientDTOForResponses = new ArrayList<>();

        patients.forEach(patient -> {
            patientDTOForResponses.add(mapper.toPatientForResponse(patient));
        });

        return patientDTOForResponses;
    }

    @Override
    public void deletePatient(final String document) {

        log.info("AdminServiceImpl.deleteAdmin - Start - Input {}", document);

        Optional<Patient> patient = repository.findPatientByUserDocument(document);

        if (!patient.isPresent()) {
            log.warn(ValidationConstraints.PATIENT_NOT_FOUND, document);
            throw new PatientException(ErrorCodeEnum.PATIENT_NOT_FOUND, ErrorMessages.NOT_FOUND,
                    StringUtils.replace(ValidationConstraints.PATIENT_NOT_FOUND, "{}", document));
        }

        repository.delete(patient.get());

    }
}
