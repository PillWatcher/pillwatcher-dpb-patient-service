package br.com.pillwatcher.dpb.controllers;

import br.com.pillwatcher.dpb.services.PrescriptionService;
import io.swagger.annotations.Api;
import io.swagger.api.PrescriptionsApi;
import io.swagger.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Api(tags = {"Prescripton"})
@RequestMapping(BASE_URI)
public class PrescriptionController implements PrescriptionsApi {

    private final PrescriptionService prescriptionService;

    @Override
    public ResponseEntity<PatientPrescriptionDTOForResponse> createPatientPrescription(@Valid PatientPrescriptionDTOForCreate body, @NotNull @Valid String cpf, @NotNull @Valid Long nurseId) {

        log.info("PrescriptionController.createPatientPrescription - Start - Input - {}", body);

        final PatientPrescriptionDTOForResponse patientPrescription = prescriptionService.createPatientPrescription(body, cpf, nurseId);

        ResponseEntity<PatientPrescriptionDTOForResponse> response = ResponseEntity.ok(patientPrescription);

        log.debug("PrescriptionController.createPatientPrescription - End - Input: {} - Output: {}", body, patientPrescription);
        
        return response;
    }

    @Override
    public ResponseEntity<Void> deletePatientPrescription(Long prescriptionId) {
        return null;
    }

    @Override
    public ResponseEntity<PatientPrescriptionDTOForGetAll> getAllPatientPrescription(@NotNull @Valid String cpf) {
        return null;
    }

    @Override
    public ResponseEntity<PatientPrescriptionDTOForResponse> getPatientPrescription(Long prescriptionId) {
        return null;
    }

    @Override
    public ResponseEntity<PatientPrescriptionDTOForResponse> updatePatientPrescription(@Valid PatientPrescriptionDTOForCreate body, Long prescriptionId) {
        return null;
    }
}
