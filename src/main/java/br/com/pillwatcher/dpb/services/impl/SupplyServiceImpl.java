package br.com.pillwatcher.dpb.services.impl;

import br.com.pillwatcher.dpb.entities.Medication;
import br.com.pillwatcher.dpb.entities.Nurse;
import br.com.pillwatcher.dpb.entities.Supply;
import br.com.pillwatcher.dpb.repositories.SupplyRepository;
import br.com.pillwatcher.dpb.services.MedicationService;
import br.com.pillwatcher.dpb.services.PatientService;
import br.com.pillwatcher.dpb.services.SupplyService;
import io.swagger.model.SupplyForCreateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SupplyServiceImpl implements SupplyService {

    private final SupplyRepository supplyRepository;

    private final PatientService patientService;

    private final MedicationService medicationService;

    @Override
    public Supply createSupply(final SupplyForCreateDTO body) {

        log.info("SupplyServiceImpl.createSupply - Start - Input {}", body);

        final Nurse nurse = patientService.getNurse(body.getNurseId());

        final Medication medication = medicationService.getMedication(body.getMedicationId());

        Supply supply = new Supply();
        supply.setMedication(medication);
        supply.setNurse(nurse);
        supply.setQuantity(Integer.valueOf(body.getQuantity().toString()));

        return supplyRepository.save(supply);
    }

}
