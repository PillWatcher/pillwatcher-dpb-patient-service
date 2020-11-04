package br.com.pillwatcher.dpb.services.impl;

import br.com.pillwatcher.dpb.constants.ErrorMessages;
import br.com.pillwatcher.dpb.constants.ValidationConstraints;
import br.com.pillwatcher.dpb.entities.Nurse;
import br.com.pillwatcher.dpb.entities.NursePatient;
import br.com.pillwatcher.dpb.entities.Patient;
import br.com.pillwatcher.dpb.exceptions.PatientException;
import br.com.pillwatcher.dpb.exceptions.PrescriptionException;
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

        Optional<Patient> patientFound = repository.findPatientByUserDocument(patientDto.getDocument());

        if (patientFound.isPresent()) {
            log.warn(ValidationConstraints.PATIENT_ALREADY_EXISTS, patientDto.getDocument());
            throw new PatientException(ErrorCodeEnum.PATIENT_ALREADY_EXISTS, ErrorMessages.CONFLICT,
                    StringUtils.replace(ValidationConstraints.PATIENT_ALREADY_EXISTS, "{}", patientDto.getDocument()));
        }

        final Nurse nurse = getNurse(nurseId);

        NursePatient nursePatient = new NursePatient();
        Patient patient = mapper.toPatientForCreateEntity(patientDto);

        nursePatient.setNurse(nurse);
        nursePatient.setPatient(patient);
        nPatientRepository.save(nursePatient);

        return repository.save(patient);
    }

    @Override
    @Transactional
    public Patient update(final PatientDTOForUpdate patientDtoForUpdate, final String document, final Long nurseId) {

        log.info("PatientServiceImpl.update - Start - Input {} {}", patientDtoForUpdate, document);

        NursePatient nursePatient = getNursePatient(document, nurseId);

        Patient patient = nursePatient.getPatient();

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

        NursePatient nursePatient = getNursePatient(document, nurseId);

        return nursePatient.getPatient();
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

        log.info("PatientServiceImpl.deletePatient - Start - Input {}", document);

        NursePatient nursePatient = getNursePatient(document, nurseId);

        repository.delete(nursePatient.getPatient());

    }

    @Override
    public void relationPatientToNurse(final String cpf, final Long nurseId) {
        log.info("PatientServiceImpl.deletePatient - Start - Input {}, {}", cpf, nurseId);

        final Patient patientByUserDocument = findPatientByUserDocument(cpf);

        final Nurse nurse = getNurse(nurseId);

        NursePatient nursePatient = new NursePatient();
        nursePatient.setNurse(nurse);
        nursePatient.setPatient(patientByUserDocument);

        nPatientRepository.save(nursePatient);
    }

    @Override
    public Nurse getNurse(final Long nurseId) {
        Optional<Nurse> nurseFound = nRepository.findById(nurseId);

        if (!nurseFound.isPresent()) {
            log.warn(ValidationConstraints.NURSE_NOT_FOUND, nurseId);
            throw new PatientException(ErrorCodeEnum.NURSE_NOT_FOUND, ErrorMessages.NOT_FOUND,
                    StringUtils.replace(
                            ValidationConstraints.NURSE_NOT_FOUND, "{}",
                            String.valueOf(nurseId)));
        }

        return nurseFound.get();
    }

    @Override
    public NursePatient getNursePatient(final String cpf, final Long nurseId) {

        Optional<NursePatient> byNurseIdAndPatientCPF = nPatientRepository.findByNurseIdAndPatientCPF(nurseId, cpf);
        if (!byNurseIdAndPatientCPF.isPresent()) {
            log.warn(ValidationConstraints.PATIENT_NOT_FOUND, cpf);
            throw new PatientException(ErrorCodeEnum.PATIENT_NOT_FOUND, ErrorMessages.NOT_FOUND,
                    StringUtils.replace(ValidationConstraints.PATIENT_NOT_FOUND, "{}", cpf));
        }
        return byNurseIdAndPatientCPF.get();
    }

    @Override
    public Patient findPatientByUserDocument(final String cpf) {

        Optional<Patient> patientByUserDocument = repository.findPatientByUserDocument(cpf);

        if (!patientByUserDocument.isPresent()) {
            log.warn(ValidationConstraints.PATIENT_NOT_FOUND, cpf);
            throw new PrescriptionException(ErrorCodeEnum.NOT_FOUND, ErrorMessages.NOT_FOUND,
                    StringUtils.replace(ValidationConstraints.PATIENT_NOT_FOUND, "{}", cpf));
        }
        return patientByUserDocument.get();
    }

}
