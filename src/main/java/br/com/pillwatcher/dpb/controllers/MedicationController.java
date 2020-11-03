package br.com.pillwatcher.dpb.controllers;

import io.swagger.annotations.Api;
import io.swagger.api.MedicationsApi;
import io.swagger.model.PrescriptionMedicationDTOForAll;
import io.swagger.model.PrescriptionMedicationDTOForCreate;
import io.swagger.model.PrescriptionMedicationDTOForResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public ResponseEntity<PrescriptionMedicationDTOForResponse> createMedication(@Valid PrescriptionMedicationDTOForCreate body, @NotNull @Valid Long prescriptionId) {
        return null;
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
