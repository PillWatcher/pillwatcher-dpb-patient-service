package br.com.pillwatcher.dpb.services;

import br.com.pillwatcher.dpb.entities.Medicine;
import io.swagger.model.MedicineDTOForCreate;

import java.util.List;

public interface MedicineService {

    Medicine create(MedicineDTOForCreate body);

    Medicine getMedication(Long medicineId);

    void deleteMedicine(Long medicineId);

    List<Medicine> getAll();

    Medicine updateMedicine(MedicineDTOForCreate medicineDTOForUpdate, Long medicineId);
}
