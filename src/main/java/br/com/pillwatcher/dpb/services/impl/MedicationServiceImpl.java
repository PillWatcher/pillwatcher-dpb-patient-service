package br.com.pillwatcher.dpb.services.impl;

import br.com.pillwatcher.dpb.constants.ErrorMessages;
import br.com.pillwatcher.dpb.constants.ValidationConstraints;
import br.com.pillwatcher.dpb.entities.Cup;
import br.com.pillwatcher.dpb.entities.Medication;
import br.com.pillwatcher.dpb.entities.Medicine;
import br.com.pillwatcher.dpb.entities.Prescription;
import br.com.pillwatcher.dpb.exceptions.PatientException;
import br.com.pillwatcher.dpb.mappers.MedicationMapper;
import br.com.pillwatcher.dpb.repositories.MedicationRepository;
import br.com.pillwatcher.dpb.repositories.PrescriptionRepository;
import br.com.pillwatcher.dpb.services.CupService;
import br.com.pillwatcher.dpb.services.MedicationService;
import br.com.pillwatcher.dpb.services.MedicineService;
import io.swagger.model.ErrorCodeEnum;
import io.swagger.model.PrescriptionMedicationDTOForCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository repository;

    private final PrescriptionRepository prescriptionRepository;

    private final MedicineService medicineService;

    private final CupService cupService;

    private final MedicationMapper mapper;

    @Override
    public Medication create(final PrescriptionMedicationDTOForCreate body, final Long prescriptionId) {

        log.info("MedicationServiceImpl.create - Start - Input {}", body);

        Optional<Prescription> optionalPrescription = prescriptionRepository.findById(prescriptionId);

        if (!optionalPrescription.isPresent()) {
            log.warn(ValidationConstraints.PRESCRIPTION_NOT_FOUND, prescriptionId);
            throw new PatientException(ErrorCodeEnum.NOT_FOUND, ErrorMessages.NOT_FOUND,
                    StringUtils.replace(ValidationConstraints.PRESCRIPTION_NOT_FOUND, "{}", prescriptionId.toString()));
        }

        Medicine medicine = medicineService.getMedication(body.getMedicineId());

        Cup cupByTag = cupService.findCupByTag(body.getCupTag());

        Medication medication = mapper.dtoToEntity(body, medicine.getId(), cupByTag.getId());

        return repository.save(medication);
    }
}
