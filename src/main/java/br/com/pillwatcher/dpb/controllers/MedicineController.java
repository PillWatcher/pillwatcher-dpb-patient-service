package br.com.pillwatcher.dpb.controllers;

import br.com.pillwatcher.dpb.entities.Medicine;
import br.com.pillwatcher.dpb.mappers.MedicineMapper;
import br.com.pillwatcher.dpb.services.MedicineService;
import io.swagger.annotations.Api;
import io.swagger.api.MedicinesApi;
import io.swagger.model.MedicineDTOForAll;
import io.swagger.model.MedicineDTOForCreate;
import io.swagger.model.MedicineDTOForResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static br.com.pillwatcher.dpb.constants.UrlConstants.BASE_URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(tags = {"Medicine"})
@RequestMapping(BASE_URI)
public class MedicineController implements MedicinesApi {

    private final MedicineMapper mapper;
    private final MedicineService service;

    @Override
    public ResponseEntity<MedicineDTOForResponse> createMedicine(final @Valid MedicineDTOForCreate body) {

        log.info("MedicineController.createMedicine - Start - Input - [{}]", body);

        Medicine medicine = service.create(body);

        ResponseEntity<MedicineDTOForResponse> response = ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDtoForResponse(medicine));

        log.debug("MedicineController.createMedicine - End - Input: {} - Output: {}", body, response);

        return response;
    }

    @Override
    public ResponseEntity<Void> deleteMedicine(final Long medicineId) {
        log.info("PatientController.deleteMedicine - Start - Input - [{}]", medicineId);

        service.deleteMedicine(medicineId);

        ResponseEntity<Void> response = ResponseEntity
                .ok()
                .build();

        log.debug("PatientController.deleteMedicine - End - Input: {} - Output: {}", medicineId, response);

        return response;
    }

    @Override
    public ResponseEntity<MedicineDTOForAll> getAllMedicines() {
        log.info("PrescriptionController.getAllPatientPrescription - Start");

        List<Medicine> all = service.getAll();

        ResponseEntity<MedicineDTOForAll> response = ResponseEntity.ok(mapper.entitiesToDtos(all));

        log.debug("PrescriptionController.getAllPatientPrescription - End - Output: {}", response);

        return response;
    }

    @Override
    public ResponseEntity<MedicineDTOForResponse> getMedicine(final Long medicineId) {
        log.info("PatientController.getMedicine - Start - Input - {}", medicineId);

        Medicine medication = service.getMedication(medicineId);

        ResponseEntity<MedicineDTOForResponse> response = ResponseEntity.ok(
                mapper.toDtoForResponse(medication));

        log.debug("PatientController.getMedicine - End - Input: {} - Output: {}", medicineId, response);

        return response;
    }

    @Override
    public ResponseEntity<MedicineDTOForResponse> updateMedicine(@Valid MedicineDTOForCreate body, Long medicineId) {
        log.info("PatientController.updateMedicine - Start - Input - {}", body);

        Medicine medication = service.updateMedicine(body, medicineId);

        ResponseEntity<MedicineDTOForResponse> response = ResponseEntity.ok(
                mapper.toDtoForResponse(medication));

        log.debug("PatientController.updateMedicine - End - Input: {} - Output: {}", body, response);

        return response;
    }
}
