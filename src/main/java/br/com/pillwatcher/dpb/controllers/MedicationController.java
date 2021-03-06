package br.com.pillwatcher.dpb.controllers;

import br.com.pillwatcher.dpb.constants.ErrorMessages;
import br.com.pillwatcher.dpb.entities.Medication;
import br.com.pillwatcher.dpb.exceptions.MedicationException;
import br.com.pillwatcher.dpb.mappers.MedicationMapper;
import br.com.pillwatcher.dpb.services.MedicationService;
import br.com.pillwatcher.dpb.services.MqttService;
import io.swagger.annotations.Api;
import io.swagger.api.MedicationsApi;
import io.swagger.model.ErrorCodeEnum;
import io.swagger.model.PrescriptionMedicationDTOForAll;
import io.swagger.model.PrescriptionMedicationDTOForCreate;
import io.swagger.model.PrescriptionMedicationDTOForResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static br.com.pillwatcher.dpb.constants.UrlConstants.BASE_URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(tags = {"Medication"})
@RequestMapping(BASE_URI)
public class MedicationController implements MedicationsApi {

    private final MqttService mqttService;
    private final MedicationService service;

    private final MedicationMapper medicationMapper;

    @Override
    public ResponseEntity<PrescriptionMedicationDTOForResponse> createMedication(
            @Valid final PrescriptionMedicationDTOForCreate body, @NotNull @Valid final Long prescriptionId) {

        log.info("MedicationController.createMedication - Start - Input - [{}]", body);

        Medication medication = service.create(body, prescriptionId);

        ResponseEntity<PrescriptionMedicationDTOForResponse> response = ResponseEntity.status(HttpStatus.CREATED)
                .body(medicationMapper.entityToDto(medication));

        mqttService.setupMqtt(medication, "create-medication",
                new MedicationException(ErrorCodeEnum.INVALID_PARAMETER, ErrorMessages.BAD_REQUEST, ""));

        log.debug("MedicationController.createMedication - End - Input: {} - Output: {}", body, response);

        return response;
    }

    @Override
    public ResponseEntity<Void> deleteMedication(final Long medicationId) {

        log.info("MedicationController.deleteMedication - Start - Input - [{}]", medicationId);

        service.deleteMedication(medicationId);

        ResponseEntity<Void> response = ResponseEntity
                .ok()
                .build();

        log.debug("MedicationController.deleteMedication - End - Input: {} - Output: {}", medicationId, response);
        return response;
    }

    @Override
    public ResponseEntity<PrescriptionMedicationDTOForAll> getAllMedication(@NotNull @Valid final Long prescriptionId) {

        log.info("MedicationController.getAllMedication - Start - Input - [{}]", prescriptionId);

        List<Medication> allMedication = service.getAllMedication(prescriptionId);

        ResponseEntity<PrescriptionMedicationDTOForAll> response = ResponseEntity.ok(
                medicationMapper.entitiesToDtos(allMedication));

        log.debug("MedicationController.getAllMedication - End - Input: {} - Output: {}", prescriptionId, response);

        return response;
    }


    @Override
    public ResponseEntity<PrescriptionMedicationDTOForResponse> getMedication(final Long medicationId) {

        log.info("MedicationController.getMedication - Start - Input - [{}]", medicationId);

        Medication medication = service.getMedication(medicationId);

        ResponseEntity<PrescriptionMedicationDTOForResponse> response = ResponseEntity.ok(
                medicationMapper.entityToDto(medication));

        log.debug("MedicationController.getMedication - End - Input: {} - Output: {}", medicationId, response);

        return response;
    }

    @Override
    public ResponseEntity<PrescriptionMedicationDTOForResponse> updateMedication(
            @Valid final PrescriptionMedicationDTOForCreate body, final Long medicationId) {

        log.info("MedicationController.updateMedication - Start - Input - [{}]", body);

        Medication medication = service.updateMedication(body, medicationId);

        ResponseEntity<PrescriptionMedicationDTOForResponse> response = ResponseEntity.ok(
                medicationMapper.entityToDto(medication));

        log.debug("MedicationController.updateMedication - End - Input: {} - Output: {}", medicationId, response);

        return response;
    }

}
