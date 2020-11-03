package br.com.pillwatcher.dpb.services.impl;

import br.com.pillwatcher.dpb.constants.ErrorMessages;
import br.com.pillwatcher.dpb.constants.ValidationConstraints;
import br.com.pillwatcher.dpb.entities.Medication;
import br.com.pillwatcher.dpb.exceptions.MedicationException;
import br.com.pillwatcher.dpb.repositories.MedicationRepository;
import br.com.pillwatcher.dpb.services.MedicationService;
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
    private final MedicationMapper mapper;

    @Override
    public Medication create(final PrescriptionMedicationDTOForCreate body) {

        log.info("MedicationServiceImpl.create - Start - Input {}", body);

        final Optional<Medication> medicationFound = repository.findById(body.getMedicineId());

        if (medicationFound.isPresent()) {
            log.warn(ValidationConstraints.MEDICATION_ALREADY_EXISTS, body.getMedicineId());
            throw new MedicationException(ErrorCodeEnum.UNEXPECTED_ERROR, ErrorMessages.CONFLICT,
                    StringUtils.replace(ValidationConstraints.MEDICATION_ALREADY_EXISTS, "{}",
                            String.valueOf(body.getMedicineId())));
        }

        Medication medication = mapper.toDtoForCreate(body);

        return repository.save(medication);
    }
}
