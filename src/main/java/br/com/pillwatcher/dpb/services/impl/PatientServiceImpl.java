package br.com.pillwatcher.dpb.services.impl;

import br.com.pillwatcher.dpb.constants.ErrorMessages;
import br.com.pillwatcher.dpb.constants.ValidationConstraints;
import br.com.pillwatcher.dpb.entities.Patient;
import br.com.pillwatcher.dpb.exceptions.PatientException;
import br.com.pillwatcher.dpb.repositories.PatientRepository;
import br.com.pillwatcher.dpb.services.PatientService;
import io.swagger.model.ErrorCodeEnum;
import io.swagger.model.PatientDTOForCreate;
import io.swagger.model.PatientDTOForUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;

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

//        Patient patient = mapper.toPatientForCreateEntity(patientDto);
        return repository.save(new Patient()); // TODO Fix this
    }

    @Override
    public Patient update(PatientDTOForUpdate patientDtoForUpdate, String document) {
        return null;
    }

    @Override
    public Patient findPatient(String document) {
        return null;
    }

    @Override
    public void deletePatient(String document) {

    }
}
