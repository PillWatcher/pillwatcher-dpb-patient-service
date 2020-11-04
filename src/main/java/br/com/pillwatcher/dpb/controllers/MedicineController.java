package br.com.pillwatcher.dpb.controllers;

import br.com.pillwatcher.dpb.entities.Medication;
import br.com.pillwatcher.dpb.entities.Medicine;
import br.com.pillwatcher.dpb.mappers.MedicineMapper;
import br.com.pillwatcher.dpb.services.MedicineService;
import io.swagger.annotations.Api;
import io.swagger.api.MedicinesApi;
import io.swagger.model.MedicineDTOForAll;
import io.swagger.model.MedicineDTOForCreate;
import io.swagger.model.MedicineDTOForResponse;
import io.swagger.model.PrescriptionMedicationDTOForResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static br.com.pillwatcher.dpb.constants.UrlConstants.BASE_URI;

@Slf4j
@RequiredArgsConstructor
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
    public ResponseEntity<Void> deleteMedicine(Long medicineId) {
        return null;
    }

    @Override
    public ResponseEntity<MedicineDTOForAll> getAllMedicines() {
        return null;
    }

    @Override
    public ResponseEntity<MedicineDTOForResponse> getMedicine(Long medicineId) {
        return null;
    }

    @Override
    public ResponseEntity<MedicineDTOForResponse> updateMedicine(@Valid MedicineDTOForCreate body, Long medicineId) {
        return null;
    }
}
