package br.com.pillwatcher.dpb.controllers;

import io.swagger.api.MedicinesApi;
import io.swagger.model.MedicineDTOForAll;
import io.swagger.model.MedicineDTOForCreate;
import io.swagger.model.MedicineDTOForResponse;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.math.BigDecimal;

public class MedicationController implements MedicinesApi {

    @Override
    public ResponseEntity<MedicineDTOForResponse> createMedicine(@Valid final MedicineDTOForCreate body) {
        return null;
    }

    @Override
    public ResponseEntity<MedicineDTOForResponse> getMedicine(final BigDecimal medicineId) {
        return null;
    }

    @Override
    public ResponseEntity<MedicineDTOForAll> getAllMedicines() {
        return null;
    }

    @Override
    public ResponseEntity<MedicineDTOForResponse> updateMedicine(@Valid final MedicineDTOForCreate body,
                                                                 final BigDecimal medicineId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteMedicine(final BigDecimal medicineId) {
        return null;
    }
}
