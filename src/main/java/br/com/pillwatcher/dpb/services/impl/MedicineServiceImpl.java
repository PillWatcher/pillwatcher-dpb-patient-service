package br.com.pillwatcher.dpb.services.impl;

import br.com.pillwatcher.dpb.constants.ErrorMessages;
import br.com.pillwatcher.dpb.constants.ValidationConstraints;
import br.com.pillwatcher.dpb.entities.Medicine;
import br.com.pillwatcher.dpb.exceptions.PatientException;
import br.com.pillwatcher.dpb.repositories.MedicineRepository;
import br.com.pillwatcher.dpb.services.MedicineService;
import io.swagger.model.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;

    @Override
    public Medicine getMedication(final Long medicineId) {

        Optional<Medicine> medicineOptional = medicineRepository.findById(medicineId);

        if (!medicineOptional.isPresent()) {
            log.warn(ValidationConstraints.MEDICINE_NOT_FOUND, medicineId);
            throw new PatientException(ErrorCodeEnum.NOT_FOUND, ErrorMessages.NOT_FOUND,
                    StringUtils.replace(ValidationConstraints.MEDICINE_NOT_FOUND, "{}", medicineId.toString()));
        }

        return medicineOptional.get();
    }
}
