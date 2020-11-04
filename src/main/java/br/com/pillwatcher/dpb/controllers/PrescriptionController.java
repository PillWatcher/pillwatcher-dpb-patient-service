package br.com.pillwatcher.dpb.controllers;

import br.com.pillwatcher.dpb.services.PrescriptionService;
import io.swagger.annotations.Api;
import io.swagger.api.PrescriptionsApi;
import io.swagger.model.PatientPrescriptionDTOForCreate;
import io.swagger.model.PatientPrescriptionDTOForGetAll;
import io.swagger.model.PatientPrescriptionDTOForResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static br.com.pillwatcher.dpb.constants.UrlConstants.BASE_URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(tags = {"Prescripton"})
@RequestMapping(BASE_URI)
public class PrescriptionController implements PrescriptionsApi {

    private final PrescriptionService prescriptionService;

    @Override
    public ResponseEntity<PatientPrescriptionDTOForResponse> createPatientPrescription(@Valid final PatientPrescriptionDTOForCreate body,
                                                                                       @NotNull @Valid final String cpf, @NotNull @Valid final Long nurseId) {

        log.info("PrescriptionController.createPatientPrescription - Start - Input - {}", body);

        final PatientPrescriptionDTOForResponse patientPrescription = prescriptionService.createPatientPrescription(body, cpf, nurseId);

        ResponseEntity<PatientPrescriptionDTOForResponse> response =
                ResponseEntity.status(HttpStatus.CREATED).body(patientPrescription);

        log.debug("PrescriptionController.createPatientPrescription - End - Input: {} - Output: {}", body, response);

        return response;
    }

    @Override
    public ResponseEntity<Void> deletePatientPrescription(final Long prescriptionId) {

        log.info("PrescriptionController.deletePatientPrescription - Start - Input - [{}]", prescriptionId);

        prescriptionService.deletePatientPrescription(prescriptionId);

        ResponseEntity<Void> response = ResponseEntity
                .ok()
                .build();

        log.debug("PrescriptionController.deletePatientPrescription - End - Input: {} - Output: {}", prescriptionId, response);

        return response;
    }

    @Override
    public ResponseEntity<PatientPrescriptionDTOForGetAll> getAllPatientPrescription(@NotNull @Valid final String cpf) {

        log.info("PrescriptionController.getAllPatientPrescription - Start - Input - [{}]", cpf);

        PatientPrescriptionDTOForGetAll allPatientPrescription = prescriptionService.getAllPatientPrescription(cpf);

        ResponseEntity<PatientPrescriptionDTOForGetAll> response = ResponseEntity.ok(allPatientPrescription);

        log.debug("PrescriptionController.getAllPatientPrescription - End - Input: {} - Output: {}", cpf, response);

        return response;
    }

    @Override
    public ResponseEntity<PatientPrescriptionDTOForResponse> getPatientPrescription(final Long prescriptionId) {

        log.info("PrescriptionController.getPatientPrescription - Start - Input - [{}]", prescriptionId);

        PatientPrescriptionDTOForResponse patientPrescription = prescriptionService.getPatientPrescription(prescriptionId);

        ResponseEntity<PatientPrescriptionDTOForResponse> response = ResponseEntity.ok(patientPrescription);

        log.debug("PrescriptionController.getPatientPrescription - End - Input: {} - Output: {}", prescriptionId, response);

        return response;
    }

    @Override
    public ResponseEntity<PatientPrescriptionDTOForResponse> updatePatientPrescription(@Valid PatientPrescriptionDTOForCreate body, Long prescriptionId) {

        log.info("PrescriptionController.updatePatientPrescription - Start - Input - [{}]", prescriptionId);

        PatientPrescriptionDTOForResponse patientPrescription = prescriptionService.updatePatientPrescription(body, prescriptionId);

        ResponseEntity<PatientPrescriptionDTOForResponse> response = ResponseEntity.ok(patientPrescription);

        log.debug("PrescriptionController.updatePatientPrescription - End - Input: {} - Output: {}", prescriptionId, response);

        return response;
    }

}
