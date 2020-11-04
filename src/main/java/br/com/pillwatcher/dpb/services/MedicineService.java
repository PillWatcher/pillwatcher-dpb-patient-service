package br.com.pillwatcher.dpb.services;

import br.com.pillwatcher.dpb.entities.Medicine;
import io.swagger.model.MedicineDTOForCreate;

public interface MedicineService {

    Medicine create(MedicineDTOForCreate body);

    Medicine getMedication(Long medicineId);

    void deleteMedicine(Long medicineId);
}
