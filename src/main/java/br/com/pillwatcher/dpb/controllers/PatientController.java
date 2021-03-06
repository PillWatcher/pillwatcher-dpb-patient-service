package br.com.pillwatcher.dpb.controllers;

import br.com.pillwatcher.dpb.constants.ErrorMessages;
import br.com.pillwatcher.dpb.entities.Patient;
import br.com.pillwatcher.dpb.exceptions.MedicationException;
import br.com.pillwatcher.dpb.mappers.PatientMapper;
import br.com.pillwatcher.dpb.services.MqttService;
import br.com.pillwatcher.dpb.services.PatientService;
import io.swagger.annotations.Api;
import io.swagger.api.PatientsApi;
import io.swagger.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static br.com.pillwatcher.dpb.constants.UrlConstants.BASE_URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(tags = {"Patient"})
@RequestMapping(BASE_URI)
public class PatientController implements PatientsApi {

    private final MqttService mqttService;
    private final PatientService service;

    private final PatientMapper mapper;

    @Override
    public ResponseEntity<PatientDTOForResponse> createPatient(@Valid @RequestBody final PatientDTOForCreate dtoForCreate,
                                                               @Valid @RequestParam(value = "nurseId") final Long nurseId) {

        log.info("PatientController.createPatient - Start - Input - [{}]", dtoForCreate);
        log.debug("PatientController.createPatient - Start - Input - Order: {}", dtoForCreate);

        Patient patient = service.create(dtoForCreate, nurseId);

        ResponseEntity<PatientDTOForResponse> response = ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toPatientForResponse(patient));

        log.debug("PatientController.createPatient - End - Input: {} - Output: {}", dtoForCreate, response);

        mqttService.setupMqtt(patient, "create-patient",
                new MedicationException(ErrorCodeEnum.INVALID_PARAMETER, ErrorMessages.BAD_REQUEST, ""));

        return response;
    }

    @Override
    public ResponseEntity<PatientDTOForResponse> updatePatient(@Valid final @RequestBody PatientDTOForUpdate dtoForUpdate,
                                                               final @RequestParam(value = "nurseId") Long nurseId,
                                                               final @PathVariable("cpf") String cpf) {

        log.info("PatientController.updatePatient - Start - Input - [{}, {}]", dtoForUpdate, cpf);
        log.debug("PatientController.updatePatient - Start - Input - Order: {} - {}", dtoForUpdate, cpf);

        Patient patient = service.update(dtoForUpdate, cpf, nurseId);

        ResponseEntity<PatientDTOForResponse> response = ResponseEntity.ok(
                mapper.toPatientForResponse(patient));

        log.debug("PatientController.updatePatient - End - Input: {} - Output: {}", dtoForUpdate, response);

        return response;
    }

    @Override
    public ResponseEntity<PatientDTOForResponse> getPatient(final @PathVariable("cpf") String cpf,
                                                            final @RequestParam(value = "nurseId") Long nurseId) {

        log.info("PatientController.getPatient - Start - Input - [{}]", cpf);
        log.debug("PatientController.getPatient - Start - Input - Order: {} ", cpf);

        Patient patient = service.findPatient(cpf, nurseId);

        ResponseEntity<PatientDTOForResponse> response = ResponseEntity.ok(
                mapper.toPatientForResponse(patient));

        log.debug("PatientController.getPatient - End - Input: {} - Output: {}", cpf, response);

        return response;
    }

    @Override
    public ResponseEntity<PatientDTOForGet> getAllPatients(final @RequestParam("nurseId") Long nurseId) {

        log.info("PatientController.getAllPatients - Start - Input - [{}]", "");
        log.debug("PatientController.getAllPatients - Start - Input - Order: {} ", "");

        List<PatientDTOForResponse> patientList = service.findPatients(nurseId);

        ResponseEntity<PatientDTOForGet> response = ResponseEntity.ok(
                mapper.toPatientDtoForGet(patientList));

        log.debug("PatientController.getAllPatients - End - Input: {} - Output: {}", response, patientList);

        return response;
    }

    @Override
    public ResponseEntity<Void> deletePatient(final @PathVariable("cpf") String cpf,
                                              final @RequestParam(name = "nurseId") Long nurseId) {

        log.info("PatientController.deletePatient - Start - Input - [{}]", cpf);
        log.debug("PatientController.deletePatient - Start - Input - Order: {} ", cpf);

        service.deletePatient(cpf, nurseId);

        ResponseEntity<Void> response = ResponseEntity
                .ok()
                .build();

        log.debug("PatientController.deletePatient - End - Input: {} - Output: {}", cpf, response);

        return response;
    }

    @Override
    public ResponseEntity<Void> relationPatientToNurse(final String cpf, final Long nurseId) {
        log.info("PatientController.relationPatientToNurse - Start - Input - {}, {}", cpf, nurseId);

        service.relationPatientToNurse(cpf, nurseId);

        ResponseEntity<Void> response = ResponseEntity
                .ok()
                .build();

        log.debug("PatientController.relationPatientToNurse - End - Input: {}, {} - Output: {}", cpf, nurseId, response);

        return response;
    }

    @Override
    public ResponseEntity<PatientDetailsDTOForResponse> getPatientDetails(final Long patientId) {

        log.info("PatientController.getPatientDetails - Start - Input - {}", patientId);

        PatientDetailsDTOForResponse patientDetails = service.getPatientDetails(patientId);

        ResponseEntity<PatientDetailsDTOForResponse> response = ResponseEntity
                .ok(patientDetails);

        log.debug("PatientController.relationPatientToNurse - End - Input: {} - Output: {}", patientId, response);

        return response;
    }
}
