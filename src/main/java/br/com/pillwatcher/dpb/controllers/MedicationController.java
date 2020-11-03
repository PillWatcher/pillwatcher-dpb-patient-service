package br.com.pillwatcher.dpb.controllers;

import br.com.pillwatcher.dpb.entities.Medication;
import br.com.pillwatcher.dpb.entities.Patient;
import br.com.pillwatcher.dpb.mappers.MedicationMapper;
import br.com.pillwatcher.dpb.services.MedicationService;
import io.swagger.annotations.Api;
import io.swagger.api.MedicationsApi;
import io.swagger.model.PatientDTOForResponse;
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

import static br.com.pillwatcher.dpb.constants.UrlConstants.BASE_URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(tags = {"Medication"})
@RequestMapping(BASE_URI)
public class MedicationController implements MedicationsApi {

    private final MedicationMapper medicationMapper;

    private final MedicationService service;

    @Override
    public ResponseEntity<PrescriptionMedicationDTOForResponse> createMedication(@Valid final PrescriptionMedicationDTOForCreate body,
                                                                                 @NotNull @Valid final Long prescriptionId) {

        log.info("MedicationController.MedicationController - Start - Input - [{}]", body);

        Medication medication = service.create(body, prescriptionId);

        ResponseEntity<PrescriptionMedicationDTOForResponse> response = ResponseEntity.status(HttpStatus.CREATED)
                .body(medicationMapper.entityToDto(medication));

        log.debug("MedicationController.MedicationController - End - Input: {} - Output: {}", body, response);

        return response;
    }

    @Override
    public ResponseEntity<Void> deleteMedication(Long medicationId) {
        return null;
    }

    @Override
    public ResponseEntity<PrescriptionMedicationDTOForAll> getAllMedication(@NotNull @Valid Long prescriptionId) {
        return null;
    }

    @Override
    public ResponseEntity<PrescriptionMedicationDTOForResponse> getMedication(Long medicationId) {
        return null;
    }

    @Override
    public ResponseEntity<PrescriptionMedicationDTOForResponse> updateMedication(@Valid PrescriptionMedicationDTOForCreate body, Long medicationId) {
        return null;
    }
}
