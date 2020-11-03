package br.com.pillwatcher.dpb.services.impl;

import br.com.pillwatcher.dpb.constants.ErrorMessages;
import br.com.pillwatcher.dpb.constants.ValidationConstraints;
import br.com.pillwatcher.dpb.entities.Nurse;
import br.com.pillwatcher.dpb.entities.NursePatient;
import br.com.pillwatcher.dpb.entities.Patient;
import br.com.pillwatcher.dpb.exceptions.PatientException;
import br.com.pillwatcher.dpb.mappers.PatientMapper;
import br.com.pillwatcher.dpb.repositories.NursePatientRepository;
import br.com.pillwatcher.dpb.repositories.NurseRepository;
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
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {

    private final NurseRepository nRepository;
    private final PatientRepository repository;
    private final NursePatientRepository nPatientRepository;

    private final PatientMapper mapper;

    @Override
    @Transactional
    public Patient create(final PatientDTOForCreate patientDto, final Long nurseId) {

        log.info("PatientServiceImpl.create - Start - Input {}", patientDto);

        Optional<Nurse> nurseFound = nRepository.findById(nurseId);
        Optional<Patient> patientFound = repository.findPatientByUserDocument(patientDto.getDocument());

        if (patientFound.isPresent()) {
            log.warn(ValidationConstraints.PATIENT_ALREADY_EXISTS, patientDto.getDocument());
            throw new PatientException(ErrorCodeEnum.PATIENT_ALREADY_EXISTS, ErrorMessages.CONFLICT,
                    StringUtils.replace(ValidationConstraints.PATIENT_ALREADY_EXISTS, "{}", patientDto.getDocument()));
        }

        if (!nurseFound.isPresent()) {
            log.warn(ValidationConstraints.NURSE_NOT_FOUND, nurseId);
            throw new PatientException(ErrorCodeEnum.NURSE_NOT_FOUND, ErrorMessages.NOT_FOUND,
                    StringUtils.replace(
                            ValidationConstraints.NURSE_NOT_FOUND, "{}",
                            String.valueOf(nurseId)));
        }

        NursePatient nursePatient = new NursePatient();
        Patient patient = mapper.toPatientForCreateEntity(patientDto);

        nursePatient.setNurse(nurseFound.get());
        nursePatient.setPatient(patient);
        nPatientRepository.save(nursePatient);

        return repository.save(patient);
    }

    @Override
    @Transactional
    public Patient update(final PatientDTOForUpdate patientDtoForUpdate, final String document, final Long nurseId) {

        log.info("PatientServiceImpl.update - Start - Input {} {}", patientDtoForUpdate, document);

        Optional<NursePatient> nursePatient = getNursePatient(document, nurseId);

        if (!nursePatient.isPresent()) {
            log.warn(ValidationConstraints.PATIENT_NOT_FOUND, document);
            throw new PatientException(ErrorCodeEnum.PATIENT_NOT_FOUND, ErrorMessages.NOT_FOUND,
                    StringUtils.replace(ValidationConstraints.PATIENT_NOT_FOUND, "{}", document));
        }

        Patient patient = nursePatient.get().getPatient();

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

    /**
     * Find Patients based on CPF and Nurse Id.
     * A Nurse can only see Patients that are linked to they
     *
     * @param document
     * @param nurseId
     * @return PatientDTOForGet
     */
    @Override
    @Transactional
    public Patient findPatient(final String document, final Long nurseId) {

        log.info("PatientServiceImpl.findPatient - Start - Input {}", document);

        Optional<NursePatient> nursePatient = getNursePatient(document, nurseId);

        if (!nursePatient.isPresent()) {
            log.warn(ValidationConstraints.PATIENT_NOT_FOUND, nurseId);
            throw new PatientException(ErrorCodeEnum.NOT_FOUND, ErrorMessages.NOT_FOUND,
                    StringUtils.replace(ValidationConstraints.PATIENT_NOT_FOUND, "{}", nurseId.toString()));
        }

        return nursePatient.get().getPatient();
    }


    /*
    TODO include as a Pageable type
    TODO refactor to create a join
     */
    @Override
    @Transactional
    public List<PatientDTOForResponse> findPatients(final Long nurseId) {

        log.info("PatientServiceImpl.findPatient - Start - Input {}", "");

        return mapper.toPatientForResponse(repository.findAllByNurse(nurseId));
    }

    @Override
    public void deletePatient(final String document, final Long nurseId) {

        log.info("AdminServiceImpl.deleteAdmin - Start - Input {}", document);

        Optional<NursePatient> nursePatient = getNursePatient(document, nurseId);

        if (!nursePatient.isPresent()) {
            log.warn(ValidationConstraints.PATIENT_NOT_FOUND, nurseId);
            throw new PatientException(ErrorCodeEnum.NOT_FOUND, ErrorMessages.NOT_FOUND,
                    StringUtils.replace(ValidationConstraints.PATIENT_NOT_FOUND, "{}", nurseId.toString()));
        }

        repository.delete(nursePatient.get().getPatient());

    }

    @Override
    public Optional<NursePatient> getNursePatient(final String cpf, final Long nurseId) {
        return nPatientRepository.findByNurseIdAndPatientCPF(nurseId, cpf);
    }

    @Override
    public Optional<Patient> findPatientByUserDocument(final String cpf) {
        return repository.findPatientByUserDocument(cpf);
    }

}
