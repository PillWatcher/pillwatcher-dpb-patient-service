package br.com.pillwatcher.dpb.controllers;

import br.com.pillwatcher.dpb.entities.Patient;
import br.com.pillwatcher.dpb.mappers.PatientMapper;
import br.com.pillwatcher.dpb.services.PatientService;
import io.swagger.annotations.Api;
import io.swagger.api.PatientsApi;
import io.swagger.model.PatientDTOForCreate;
import io.swagger.model.PatientDTOForGet;
import io.swagger.model.PatientDTOForResponse;
import io.swagger.model.PatientDTOForUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(tags = {"Patient"})
public class PatientController implements PatientsApi {

    private final PatientMapper mapper;
    private final PatientService service;

    @Override
    public ResponseEntity<PatientDTOForResponse> createPatient(@Valid @RequestBody final PatientDTOForCreate dtoForCreate) {

        log.info("PatientController.createPatient - Start - Input - [{}]", dtoForCreate);
        log.debug("PatientController.createPatient - Start - Input - Order: {}", dtoForCreate);

        Patient patient = service.create(dtoForCreate);

        ResponseEntity<PatientDTOForResponse> response = ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toPatientForResponse(patient));

        log.debug("PatientController.createPatient - End - Input: {} - Output: {}", dtoForCreate, response);

        return response;
    }

    @Override
    public ResponseEntity<PatientDTOForResponse> updatePatient(@Valid @RequestBody final PatientDTOForUpdate dtoForUpdate,
                                                               @PathVariable("cpf") final String cpf) {

        log.info("PatientController.updatePatient - Start - Input - [{}, {}]", dtoForUpdate, cpf);
        log.debug("PatientController.updatePatient - Start - Input - Order: {} - {}", dtoForUpdate, cpf);

        Patient patient = service.update(dtoForUpdate, cpf);

        ResponseEntity<PatientDTOForResponse> response = ResponseEntity.ok(
                mapper.toPatientForResponse(patient));

        log.debug("PatientController.updatePatient - End - Input: {} - Output: {}", dtoForUpdate, response);

        return response;
    }

    @Override
    public ResponseEntity<PatientDTOForResponse> getPatient(final String cpf) {

        log.info("PatientController.getPatient - Start - Input - [{}]", cpf);
        log.debug("PatientController.getPatient - Start - Input - Order: {} ", cpf);

        Patient patient = service.findPatient(cpf);

        ResponseEntity<PatientDTOForResponse> response = ResponseEntity.ok(
                mapper.toPatientForResponse(patient));

        log.debug("PatientController.getPatient - End - Input: {} - Output: {}", cpf, response);

        return response;
    }

    @Override
    public ResponseEntity<PatientDTOForGet> getAllPatients() {
        return null;
    }

    @Override
    public ResponseEntity<Void> deletePatient(final String cpf) {

        log.info("PatientController.deletePatient - Start - Input - [{}]", cpf);
        log.debug("PatientController.deletePatient - Start - Input - Order: {} ", cpf);

        service.deletePatient(cpf);

        ResponseEntity<Void> response = ResponseEntity
                .ok()
                .build();

        log.debug("PatientController.deletePatient - End - Input: {} - Output: {}", cpf, response);

        return response;
    }
}
